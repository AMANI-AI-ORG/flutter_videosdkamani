import 'dart:async';
import '../main.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_videosdkamani/flutter_videosdkamani.dart';
import 'package:flutter_videosdkamani/modules/amani_video.dart';
import 'package:flutter_videosdkamani/flutter_videosdkamani_method_channel.dart';
import 'package:flutter_videosdkamani/flutter_videosdkamani.dart';
// import 'package:flutter_amanisdk/amani_sdk.dart';
// import 'package:flutter_amanisdk/common/models/api_version.dart';



class AmaniVideoSDKScreen extends StatefulWidget {
  const AmaniVideoSDKScreen({Key? key}) : super(key: key);

  @override
  State<AmaniVideoSDKScreen> createState() => _AmaniVideoSDKScreenState();
}

class _AmaniVideoSDKScreenState extends State<AmaniVideoSDKScreen> {

  final VideoSDK _videoSDKModule = FlutterVideosdkamani().getAmaniVideo();

  
  static const EventChannel _videoEventChannel = EventChannel('amanivideosdk_delegate_channel');

  StreamSubscription? _videoStreamSubscription;

  @override
  void initState() {
    super.initState();
    
    _videoStreamSubscription = _videoEventChannel.receiveBroadcastStream().listen(_handleNativeEvent);
  }

  void _handleNativeEvent(dynamic event) {
    debugPrint("Native Event: $event");

    if (event == "camera_switch_requested") {
      _videoSDKModule.switchCamera();
    } else if (event == "torch_toggle_requested") {
      _videoSDKModule.toggleTorch();
    } else if (event == "call_end") {
      _videoSDKModule.closeSDK();
      
    }
  }

//   void _showCameraSwitchDialog() {
//   final ctx = navigatorKey.currentContext;

//   debugPrint("AmaniVideoScreen showcamera func içi ctx::::: $ctx");
//   if (ctx == null) return;

//   showDialog(
//     context: ctx,
//     builder: (context) => AlertDialog(
//       title: const Text("Camera Switch Request"),
//       content: const Text("Agent is requesting a camera switch. Allow?"),
//       actions: [
//         TextButton(
//           onPressed: () {
//             Navigator.of(context).pop();
//             _videoSDKModule.switchCamera(); // Flutter method that triggers native switch
//           },
//           child: const Text("Allow"),
//         ),
//         TextButton(
//           onPressed: () {
//             Navigator.of(context).pop();
//           },
//           child: const Text("Deny"),
//         ),
//       ],
//     ),
//   );
// }


  @override
  void dispose() {
    _videoStreamSubscription?.cancel();
    super.dispose();
  }
  Future<void> setupVideoBuilder() async {
  _videoSDKModule.startVideo("https://sandbox.amani.ai:8091", 
                               "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzQ4OTg1NDY2LCJpYXQiOjE3NDg5ODU0MDYsImp0aSI6IjhiNjUyNmU3MGNkMzRiNWI4MmUyNjJiNjc4YzgxNDExIiwidXNlcl9pZCI6IjM5ODQ4Yjc5LWM3NjItNGExNi1iMDFlLTdkYjlkMjJmNmNkNyIsImFwaV91c2VyIjpmYWxzZSwicHJvZmlsZV9pZCI6IjBjNWQyOTM5LWM1ZmMtNDliNS05YzM1LWExMTk3YzM5YjYwNyIsImNvbXBhbnlfaWQiOiJlOWFmYmQzMS03M2NlLTRkZDYtYTI1Ny1mOGYxNmExZjBkNTIifQ.tKd5ABW-Byk1jqluyjX5RZ5g-Qo0LXDMNhOZj952OhU",
                               "Bedri", 
                               "surname", 
                               "stun:tr.amani.ai:3478",
                               "turn:tr.amani.ai:3478", 
                               "st_user", 
                               "yHB4N!ec%FA3kZ%5xsgB");
    _videoSDKModule.setAmaniVideoDelegate();

  }

  Future<bool> onWillPop() async {
  
      return true;
  
  }

  @override
  Widget build(BuildContext context) {
    // ignore: deprecated_member_use
    return WillPopScope(
      onWillPop: onWillPop,
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.purple,
          title: const Text('Amani Video Screen'),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              OutlinedButton(
                  onPressed: () {
                    setupVideoBuilder();
                  },
                  child: const Text("Start")),
            ],
          ),
        ),
      ),
    );
    }
}
  

