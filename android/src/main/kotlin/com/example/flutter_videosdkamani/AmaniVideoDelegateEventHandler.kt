package com.example.flutter_videosdkamani
import ai.amani.videosdk.VideoSDK
import ai.amani.videosdk.observer.AmaniVideoButtonEvents
import ai.amani.videosdk.observer.AmaniVideoCallObserver
import ai.amani.videosdk.observer.AmaniVideoRemoteEvents
import ai.amani.videosdk.observer.CameraPosition
import ai.amani.videosdk.observer.ConnectionState
import ai.amani.videosdk.observer.SwitchCameraObserver
import ai.amani.videosdk.observer.ToggleTorchObserver
import android.os.Handler
import android.os.Looper
import io.flutter.plugin.common.EventChannel

class AmaniVideoDelegateEventHandler: EventChannel.StreamHandler {

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        Handler(Looper.getMainLooper()).post {
    
        }
    }

    override fun onCancel(arguments: Any?) {
    }
}