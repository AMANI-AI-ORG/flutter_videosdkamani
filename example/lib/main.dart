import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_videosdkamani/flutter_videosdkamani.dart';

import 'package:flutter/material.dart';
import 'package:flutter_videosdkamani_example/screens/home.dart';
import 'package:flutter_videosdkamani_example/screens/AmaniVideoSDKScreen.dart';


void main() {
  runApp(MaterialApp(
    initialRoute: '/',
    routes: {
      '/': (ctx) => const HomeScreen(),
      '/amani-videosdk': (ctx) => const AmaniVideoSDKScreen(),
    },
  ));
}
