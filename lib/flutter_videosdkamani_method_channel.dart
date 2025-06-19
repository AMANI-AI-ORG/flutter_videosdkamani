import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';
import 'dart:async';
import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';

import 'flutter_videosdkamani_platform_interface.dart';

/// An implementation of [FlutterVideosdkamaniPlatform] that uses method channels.
class MethodChannelFlutterVideosdkamani extends FlutterVideosdkamaniPlatform {
  /// The method channel used to interact with the native platform.



  final methodChannel = const MethodChannel('amanivideosdk_method_channel');
  @override 
  Future<Object>? startVideoSDK({
      required String serverURL,
      required String token,
      required String name,
      required String surname,
      required String stunServer,
      required String turnServer,
      required String turnUser,
      required String turnPass,
    })  {
      try {
        print("method channel tarafında plugine'e gidecek");
         methodChannel.invokeMethod('startVideo', {
          'serverUrl': serverURL,
          'token': token,
          'name': name,
          'surname': surname,
          'stunServer': stunServer,
          'turnServer': turnServer,
          'turnUser': turnUser,
          'turnPass': turnPass,
        });
      } catch (e) {
        rethrow;
      }
    }

  @override
  Future<void> setAmaniVideoDelegate() async {
    await methodChannel.invokeMethod('setAmaniVideoDelegate');
  }

  @override
  Future<void> switchCamera() {
   final result = methodChannel.invokeMethod("switchCamera");

   return result;
  }

  @override
  Future<void> toggleTorch()  {
    final result = methodChannel.invokeMethod("toggleTorch");

    return result;
  }

  @override
  Future<void> closeSDK() async {
    await methodChannel.invokeMethod("closeSDK");
  }


  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
