package com.example.flutter_amanivideosdk
import android.app.Activity
import android.content.Context
import android.util.Log
import android.content.Intent
import ai.amani.videosdk.VideoSDK
import ai.amanivideosdk.video.CallStatus
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry.Registrar

class FlutterAmaniVideo(private val activity: Activity) : MethodChannel.MethodCallHandler {

    private var module: AmaniVideo? = null

    init {
        module = AmaniVideo(activity)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "start" -> {
                val statusOrdinal = call.argument<Int>("status") ?: 0
                val status = module.CallStatus.values()[statusOrdinal]
                start(status, result)
            }
            "setDelegate" -> {
                // Eğer SDK delegate destekliyorsa burada setlenebilir
                result.success(null)
            }
            "toggleTorch" -> {
                module?.toggleTorch()
                result.success(null)
            }
            "switchCamera" -> {
                module?.switchCamera()
                result.success(null)
            }
            "closeSDK" -> {
                module?.closeSDK()
                result.success(null)
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    private fun start(status: AmaniVideo.CallStatus, result: MethodChannel.Result) {
        try {
            module?.start(activity, status)
            result.success(null)
        } catch (e: Exception) {
            Log.e("FlutterAmaniVideo", "start() failed: ${e.localizedMessage}")
            result.error("30007", e.localizedMessage, null)
        }
    }
}
