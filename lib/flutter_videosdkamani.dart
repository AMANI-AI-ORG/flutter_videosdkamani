import 'package:flutter/services.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_videosdkamani/modules/amani_video.dart';
import 'package:flutter_videosdkamani/flutter_videosdkamani_method_channel.dart';
import 'flutter_videosdkamani_platform_interface.dart';

class FlutterVideosdkamani {
  final MethodChannelFlutterVideosdkamani _methodChannel = MethodChannelFlutterVideosdkamani();

  final delegateEventChannel = const EventChannel("amanivideosdk_delegate_channel");
    VideoSDK getAmaniVideo() {
    return VideoSDK(_methodChannel);
  }


  Stream<dynamic> getDelegateStream() {
    return delegateEventChannel.receiveBroadcastStream().map((event) => event);
  }

  void listenToEvents(Function(String) onEvent) {
    delegateEventChannel.receiveBroadcastStream().listen((dynamic event) {
      if (event is String) {
        onEvent(event);
      }
    });
  }


  Future<String?> getPlatformVersion() {
    return FlutterVideosdkamaniPlatform.instance.getPlatformVersion();
  }
}
