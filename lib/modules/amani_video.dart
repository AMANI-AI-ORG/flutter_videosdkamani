import 'dart:convert';
import 'dart:ffi';
import 'dart:io';
import 'dart:typed_data';
import 'package:flutter/services.dart';
import 'package:flutter_amanivideosdk/flutter_amanivideosdk_method_channel.dart';
import 'package:flutter/material.dart';


class VideoSDK {
  final MethodChannelAmaniVideoSDK _methodChannel;
  final eventChannel = const EventChannel('amanivideosdk_event_channel');

  VideoSDK(this._methodChannel);

  Future<dynamic> startVideo(String serverURL,  String token,  String name, String surname, String stunServer, String turnServer,   String turnUser, String turnPass,) async {
      final dynamic result = await _methodChannel.startVideoSDK(serverURL: serverURL, token: token, name: name, surname: surname, stunServer: stunServer, turnServer: turnServer, turnUser: turnUser, turnPass: turnPass);
         
      return result;
    }

  Future<void> setAmaniVideoDelegate() async {
    await _methodChannel.setAmaniVideoDelegate();
  }

  Future<void> switchCamera() async {
    await _methodChannel.switchCamera();
  }

  Future<void> toggleTorch() async {
    await _methodChannel.toggleTorch();
  }

  Future<void> closeSDK() async {
    await _methodChannel.closeSDK();
  }
}