import 'dart:convert';
import 'dart:ffi';
import 'dart:io';
import 'dart:typed_data';
import 'package:flutter/services.dart';
import 'package:flutter_videosdkamani/flutter_videosdkamani_method_channel.dart';
import 'package:flutter/material.dart';


class VideoSDK {
  final MethodChannelFlutterVideosdkamani _methodChannel;
  final eventChannel = const EventChannel('amanivideosdk_event_channel');

  VideoSDK(this._methodChannel);

  Future<Object>? startVideo(String serverURL,  String token,  String name, String surname, String stunServer, String turnServer,   String turnUser, String turnPass,) {
      final dynamic result =  _methodChannel.startVideoSDK(serverURL: serverURL, token: token, name: name, surname: surname, stunServer: stunServer, turnServer: turnServer, turnUser: turnUser, turnPass: turnPass);
         
      return result;
    }

  Future<void> setAmaniVideoDelegate() async {
    await _methodChannel.setAmaniVideoDelegate();
  }

  Future<void>? switchCamera(){
    final result = _methodChannel.switchCamera();
    return result;
  }

  Future<void>? toggleTorch() {
    final result = _methodChannel.toggleTorch();

    return result;
  }

  Future<void> closeSDK() async {
    await _methodChannel.closeSDK();
  }
}