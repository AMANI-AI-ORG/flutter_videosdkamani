import 'dart:convert';
import 'dart:io';

/// Values read from an Amani verification QR code.
class QrSessionInfo {
  final String pid;
  final String serverUrl;

  const QrSessionInfo({required this.pid, required this.serverUrl});

  @override
  String toString() => 'QrSessionInfo(pid: $pid, serverUrl: $serverUrl)';
}

/// Response of `GET {serverUrl}/api/v2/profile/url?pid={pid}`.
class UserAccessData {
  final String accessToken;
  final String serverUrl;
  final String? language;
  final String company;

  const UserAccessData({
    required this.accessToken,
    required this.serverUrl,
    this.language,
    required this.company,
  });

  factory UserAccessData.fromJson(Map<String, dynamic> json) {
    final token = json['access_token'];
    final server = json['server_url'];
    final company = json['company'];
    if (token is! String ||
        token.isEmpty ||
        server is! String ||
        server.isEmpty ||
        company is! String ||
        company.isEmpty) {
      throw const FormatException(
          'access_token, server_url or company is missing in the response');
    }
    return UserAccessData(
      accessToken: token,
      serverUrl: server,
      language: json['language'] as String?,
      company: company,
    );
  }
}

/// `video_configs` of `GET {server}/api/v2/company/{company}/config/app`.
class VideoConfig {
  final String socketServer;
  final String stunServer;
  final String turnServer;
  final String turnUsername;
  final String turnPassword;

  const VideoConfig({
    required this.socketServer,
    required this.stunServer,
    required this.turnServer,
    required this.turnUsername,
    required this.turnPassword,
  });

  /// ice_servers[0] is the STUN server, ice_servers[1] the TURN server.
  factory VideoConfig.fromJson(Map<String, dynamic> json) {
    final socket = json['socket_server'];
    final ice = json['ice_servers'];
    if (socket is! String || socket.isEmpty || ice is! List || ice.length < 2) {
      throw const FormatException('video_configs is incomplete');
    }
    final stun = ice[0] as Map<String, dynamic>;
    final turn = ice[1] as Map<String, dynamic>;
    final stunUrl = stun['urls'];
    final turnUrl = turn['urls'];
    final user = turn['username'];
    final credential = turn['credential'];
    if (stunUrl is! String ||
        turnUrl is! String ||
        user is! String ||
        credential is! String) {
      throw const FormatException('ice_servers is incomplete');
    }
    return VideoConfig(
      socketServer: socket,
      stunServer: stunUrl,
      turnServer: turnUrl,
      turnUsername: user,
      turnPassword: credential,
    );
  }
}

/// Example-only flow: QR code -> pid + server URL -> access token ->
/// video config (socket + ICE servers) -> `startVideo`.
class AmaniQrSession {
  AmaniQrSession._();

  /// Reads the pid and server URL from the scanned QR payload.
  ///
  /// The pid comes from the `pid` query parameter. The server URL comes from a
  /// `server_url` / `serverUrl` / `server` / `base_url` / `baseUrl` query
  /// parameter, or falls back to the origin of an http(s) QR link.
  /// Returns `null` when the payload is not an Amani verification QR code.
  static QrSessionInfo? parse(String raw) {
    final uri = Uri.tryParse(raw.trim());
    if (uri == null) return null;

    // BioLogin QR codes use the same pid/server_url, but not for a video call.
    if (uri.path == '/biologin') return null;

    final query = uri.queryParameters;
    final pid = query['pid'] ?? query['PID'];
    if (pid == null || pid.isEmpty) return null;

    String? server;
    for (final key in const [
      'server_url',
      'serverUrl',
      'server',
      'base_url',
      'baseUrl'
    ]) {
      final value = query[key];
      if (value != null && value.isNotEmpty) {
        server = value;
        break;
      }
    }
    if (server == null && (uri.scheme == 'http' || uri.scheme == 'https')) {
      server = uri.origin;
    }
    if (server == null) return null;

    final serverUri =
        Uri.tryParse(server.contains('://') ? server : 'https://$server');
    if (serverUri == null || serverUri.host.isEmpty) return null;

    return QrSessionInfo(pid: pid, serverUrl: serverUri.origin);
  }

  /// Exchanges the pid for an access token.
  static Future<UserAccessData> fetchAccessData(QrSessionInfo info) async {
    final url = Uri.parse('${info.serverUrl}/api/v2/profile/url')
        .replace(queryParameters: {'pid': info.pid});
    final json = await _getJson(url);
    return UserAccessData.fromJson(json);
  }

  /// Loads the socket and ICE server settings used by the video call.
  static Future<VideoConfig> fetchVideoConfig(UserAccessData data) async {
    final url = Uri.parse(
        '${data.serverUrl}/api/v2/company/${data.company}/config/app');
    final json = await _getJson(url, bearerToken: data.accessToken);
    final videoConfigs = json['video_configs'];
    if (videoConfigs is! Map<String, dynamic>) {
      throw const FormatException('video_configs is missing in the response');
    }
    return VideoConfig.fromJson(videoConfigs);
  }

  static Future<Map<String, dynamic>> _getJson(Uri url,
      {String? bearerToken}) async {
    final client = HttpClient()
      ..connectionTimeout = const Duration(seconds: 20);
    try {
      final request = await client.getUrl(url);
      request.headers.set(HttpHeaders.acceptHeader, 'application/json');
      if (bearerToken != null) {
        request.headers
            .set(HttpHeaders.authorizationHeader, 'Bearer $bearerToken');
      }
      final response = await request.close();
      final body = await response.transform(utf8.decoder).join();
      if (response.statusCode != 200) {
        throw HttpException('Request failed (${response.statusCode}): $body',
            uri: url);
      }
      return jsonDecode(body) as Map<String, dynamic>;
    } finally {
      client.close();
    }
  }
}
