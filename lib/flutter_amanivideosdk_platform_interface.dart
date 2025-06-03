
import 'flutter_amanivideosdk_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

abstract class AmaniVideoSDKPlatform extends PlatformInterface {
    /// Constructs a [AmaniVideoSDKPlatform]
  AmaniVideoSDKPlatform() : super(token: _token);

  static final Object _token = Object();

  static AmaniVideoSDKPlatform _instance = MethodChannelAmaniVideoSDK();

  /// The default instance of [AmaniVideoSDKPlatform] to use.
  ///
  /// Defaults to [MethodChannelAmaniVideoSDK].
  static AmaniVideoSDKPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [AmaniVideoSDKPlatform] when
  /// they register themselves.
  static set instance(AmaniVideoSDKPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<dynamic> startVideoSDK({
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

  Future<void> setAmaniVideoDelegate() {
    throw UnimplementedError('setDelegate has not been setted.');
  }
}