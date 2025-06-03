
import 'package:flutter/services.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_amanivideosdk/modules/amani_video.dart';
import 'flutter_amanivideosdk_method_channel.dart';

class AmaniVideoSDK {
  final MethodChannelAmaniVideoSDK _methodChannel = MethodChannelAmaniVideoSDK();

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
}