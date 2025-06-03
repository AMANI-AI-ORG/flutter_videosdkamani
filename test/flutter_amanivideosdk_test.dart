import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_amanivideosdk/flutter_amanivideosdk.dart';
import 'package:flutter_amanivideosdk/flutter_amanivideosdk_platform_interface.dart';
import 'package:flutter_amanivideosdk/flutter_amanivideosdk_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterAmanivideosdkPlatform
    with MockPlatformInterfaceMixin
    implements FlutterAmanivideosdkPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterAmanivideosdkPlatform initialPlatform = FlutterAmanivideosdkPlatform.instance;

  test('$MethodChannelFlutterAmanivideosdk is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterAmanivideosdk>());
  });

  test('getPlatformVersion', () async {
    FlutterAmanivideosdk flutterAmanivideosdkPlugin = FlutterAmanivideosdk();
    MockFlutterAmanivideosdkPlatform fakePlatform = MockFlutterAmanivideosdkPlatform();
    FlutterAmanivideosdkPlatform.instance = fakePlatform;

    expect(await flutterAmanivideosdkPlugin.getPlatformVersion(), '42');
  });
}
