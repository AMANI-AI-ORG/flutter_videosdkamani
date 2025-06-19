package com.example.flutter_videosdkamani
import android.app.Activity
import androidx.annotation.NonNull
import com.example.flutter_videosdkamani.modules.*
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.EventChannel
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding

/** FlutterVideosdkamaniPlugin */
class FlutterVideosdkamaniPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private var activity: Activity? = null
  private lateinit var delegateChannel: EventChannel

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "amanivideosdk_method_channel")
    channel.setMethodCallHandler(this)

    delegateChannel = EventChannel(flutterPluginBinding.binaryMessenger, "amanivideosdk_delegate_channel")
    delegateChannel.setStreamHandler(AmaniVideoDelegateEventHandler())
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    when (call.method) {
    "startVideo" -> {
      val serverUrl = call.argument<String>("serverUrl") ?: ""
      val token = call.argument<String>("token") ?: ""
      val name = call.argument<String>("name") ?: ""
      val surname = call.argument<String>("surname") ?: ""
      val stunServer = call.argument<String>("stunServer") ?: ""
      val turnServer = call.argument<String>("turnServer") ?: ""
      val turnUser = call.argument<String>("turnUser") ?: ""
      val turnPass = call.argument<String>("turnPass") ?: ""

      if (serverUrl == null || token == null || name == null || surname == null ||
            stunServer == null || turnServer == null || turnUser == null || turnPass == null) {
            result.error("INVALID_ARGUMENTS", "One or more arguments are null", null)
            return
        }

    print("android tarafında plugin")
    FlutterAmaniVideo.instance.start(serverUrl, token, name, surname, stunServer, turnServer, turnUser, turnPass, activity!!, result)
  }
     
    "closeSDK" -> {
        print("close sdk plugin")
        FlutterAmaniVideo.instance.closeSDK(activity!!)

      }
    "switchCamera" -> {
      FlutterAmaniVideo.instance.switchCamera(activity!!)
    }
    "toggleTorch" -> {
      FlutterAmaniVideo.instance.toggleTorch(activity!!)
    }

}
}
  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
    activity = null
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    activity = binding.activity
  }

  override fun onDetachedFromActivityForConfigChanges() {
      activity = null
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    activity = binding.activity
  }

  override fun onDetachedFromActivity() {
    activity = null
  }
}
