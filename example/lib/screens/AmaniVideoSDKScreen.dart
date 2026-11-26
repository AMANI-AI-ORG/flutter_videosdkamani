// ignore_for_file: use_build_context_synchronously

import 'dart:async';
import '../main.dart';
import '../qr/amani_qr_session.dart';
import 'qr_scanner.dart';
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
    } else if (event == "on_ui_call_end") {
      _videoSDKModule.closeSDK();
    } else if (event == "call_end") {
      _videoSDKModule.closeSDK().then((_) {
      debugPrint("navigate did pop ");
     
    });
    }
  }



  @override
  void dispose() {
    _videoStreamSubscription?.cancel();
    super.dispose();
  }

  bool _isStarting = false;
  String _status = "Görüntülü görüşmeyi başlatmak için QR kodu okutun.";

  /// QR code -> pid + server URL -> access token -> video config -> startVideo.
  Future<void> setupVideoBuilder() async {
    final info = await Navigator.push<QrSessionInfo>(
      context,
      MaterialPageRoute(builder: (_) => const QrScannerScreen()),
    );
    if (info == null || !mounted) return;

    setState(() {
      _isStarting = true;
      _status = "Oturum başlatılıyor...";
    });
    try {
      final accessData = await AmaniQrSession.fetchAccessData(info);
      final videoConfig = await AmaniQrSession.fetchVideoConfig(accessData);
      _videoSDKModule.startVideo(
          videoConfig.socketServer,
          accessData.accessToken,
          "name",
          "surname",
          videoConfig.stunServer,
          videoConfig.turnServer,
          videoConfig.turnUsername,
          videoConfig.turnPassword);
      _videoSDKModule.setAmaniVideoDelegate();
      if (mounted) setState(() => _status = "Görüşme başlatıldı.");
    } catch (e) {
      debugPrint("[QR] session start failed: $e");
      if (mounted) setState(() => _status = "Hata: $e");
    } finally {
      if (mounted) setState(() => _isStarting = false);
    }
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
              Padding(
                padding: const EdgeInsets.all(16),
                child: Text(_status, textAlign: TextAlign.center),
              ),
              OutlinedButton.icon(
                onPressed: _isStarting ? null : setupVideoBuilder,
                icon: _isStarting
                    ? const SizedBox(
                        width: 18,
                        height: 18,
                        child: CircularProgressIndicator(strokeWidth: 2))
                    : const Icon(Icons.qr_code_scanner),
                label: const Text("QR Kodu Tara"),
              ),
            ],
          ),
        ),
      ),
    );
    }
}
  

