import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_videosdkamani_method_channel.dart';

abstract class FlutterVideosdkamaniPlatform extends PlatformInterface {
  /// Constructs a FlutterVideosdkamaniPlatform.
  FlutterVideosdkamaniPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterVideosdkamaniPlatform _instance = MethodChannelFlutterVideosdkamani();

  /// The default instance of [FlutterVideosdkamaniPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterVideosdkamani].
  static FlutterVideosdkamaniPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterVideosdkamaniPlatform] when
  /// they register themselves.
  static set instance(FlutterVideosdkamaniPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }


    Future<Object>? startVideoSDK({
    required String serverURL,
    required String token,
    required String name,
    required String surname,
    required String stunServer,
    required String turnServer,
    required String turnUser,
    required String turnPass,
  }) {
    throw  UnimplementedError('startVideoSDK() has not been implemented.');
  }

  Future<void> switchCamera() {
    throw  UnimplementedError('startVideoSDK() has not been implemented.');
  }

  Future<void> toggleTorch() {
    throw  UnimplementedError('startVideoSDK() has not been implemented.');
  }

  Future<void> setAmaniVideoDelegate() {
    throw UnimplementedError('setDelegate has not been setted.');
  }
}
