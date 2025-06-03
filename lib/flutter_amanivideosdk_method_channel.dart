import 'dart:async';
import 'dart:convert';
import 'dart:io';
import 'dart:typed_data';
import 'package:flutter/services.dart';


import 'flutter_amanivideosdk_platform_interface.dart';

class MethodChannelAmaniVideoSDK extends AmaniVideoSDKPlatform {

  final methodChannel = const MethodChannel('amanivideosdk_method_channel');
  @override 
  Future<dynamic> startVideoSDK({
      required String serverURL,
      required String token,
      required String name,
      required String surname,
      required String stunServer,
      required String turnServer,
      required String turnUser,
      required String turnPass,
    }) async {
      try {
        print("method channel tarafında plugine'e gidecek");
        await methodChannel.invokeMethod('startVideo', {
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
   Future<void> switchCamera() async {
    await methodChannel.invokeMethod("switchCamera");
   }

   @override
   Future<void> toggleTorch() async {
    await methodChannel.invokeMethod("toggleTorch");
   }

   @override
   Future<void> closeSDK() async {
    await methodChannel.invokeMethod("closeSDK");
   }

}