import 'package:flutter/material.dart';
import 'package:flutter_amanivideosdk_example/screens/home.dart';
import 'package:flutter_amanivideosdk_example/screens/AmaniVideoSDKScreen.dart';


void main() {
  runApp(MaterialApp(
    initialRoute: '/',
    routes: {
      '/': (ctx) => const HomeScreen(),
      '/amani-videosdk': (ctx) => const AmaniVideoSDKScreen(),
    },
  ));
}
