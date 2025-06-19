package com.example.flutter_videosdkamani.modules

import com.example.flutter_videosdkamani.R
import android.app.Activity
import android.content.Context
import ai.amani.videosdk.VideoSDK
import ai.amani.videosdk.observer.AmaniVideoButtonEvents
import ai.amani.videosdk.observer.AmaniVideoCallObserver
import ai.amani.videosdk.observer.AmaniVideoRemoteEvents
import ai.amani.videosdk.observer.CameraPosition
import ai.amani.videosdk.observer.ConnectionState
import ai.amani.videosdk.observer.SwitchCameraObserver
import ai.amani.videosdk.observer.ToggleTorchObserver
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.example.flutter_videosdkamani.VideoSDKCredentials
import com.example.flutter_videosdkamani.replaceFragment
import com.example.flutter_videosdkamani.AmaniVideoDelegateEventHandler
import com.example.flutter_videosdkamani.removeFragment
import kotlinx.coroutines.CoroutineScope
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class FlutterAmaniVideo : Module {

    //Properties
    private var videoModule = VideoSDK()
    companion object {
        val instance = FlutterAmaniVideo()
        private const val TAG = "CallActivity"
    }


    private lateinit var videoBuilder: VideoSDK.Builder
    private var containerId = 0x123456
    private var frag: Fragment? = null




    //AmaniVideoSDK observer funcs and start func

    fun switchCamera(activity: Activity) {
        if (activity is AppCompatActivity) {
        AlertDialog.Builder(activity)
            .setTitle("Kamera Değiştir")
            .setMessage("Kamera yönünü değiştirmek istiyor musunuz?")
            .setPositiveButton("Evet") { _, _ ->
                // Kamera değiştir
            }
            .setNegativeButton("Hayır", null)
            .show()
        } else {
        Log.e("VideoSDK", "Activity is not AppCompatActivity!")
    }
    }

    fun toggleTorch(activity: Activity) {
        AlertDialog.Builder(activity)
        .setTitle("Flaş Kullanımı")
        .setMessage("Flaşı açmak/kapatmak ister misiniz?")
        .setPositiveButton("Evet") { _, _ ->
          
        }
        .setNegativeButton("Hayır", null)
        .show()
    }
    
    fun closeSDK(
    activity: Activity,
    ) {
        print("closeSDK kısmına geldi.")
        val fragmentActivity = activity as? FragmentActivity
        if (fragmentActivity == null) {
        Log.e("FlutterAmaniVideo", "Activity is not a FragmentActivity. Cannot remove fragment.")
        return
        }
        fragmentActivity.removeFragment(frag)
    }

    override fun start(
    serverUrl: String,
    token: String,
    name: String,
    surname: String,
    stunServer: String,
    turnServer: String,
    turnUser: String,
    turnPass: String,
    activity: Activity,
    result: Result
) {
    print("FlutteramaniVideoKT kısmına geldi.")

    if (frag != null) {
        result.error(
            "30021",
            "Start function is already triggered before",
            "You cannot call start function before previous session is end up."
        )
        return
    }

    var id = 0x123456
    val context = activity.applicationContext
    val viewParams = FrameLayout.LayoutParams(
    ViewGroup.LayoutParams.MATCH_PARENT,
    ViewGroup.LayoutParams.MATCH_PARENT
    )

    val container = FrameLayout(context).apply {
        this.id = id
        this.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        this.setBackgroundColor(Color.RED)
        Log.i(TAG, "container ID degerii ---------: $id")
        
        this.visibility = View.VISIBLE


        this.isClickable = true
        this.isFocusable = true
        this.setOnTouchListener { _, _ -> true }
    }

    activity.addContentView(container, viewParams)


    val builder = VideoSDK.Builder()
    .nameSurname("$name $surname")
    .escalatedCall(true)
    .servers(
    mainServerURL = serverUrl,
    stunServerURL = stunServer,
    turnServerURL = turnServer
    )
    .authentication(
    token = token,
    userName = turnUser,
    password = turnPass
    )
    .remoteViewAspectRatio(VideoSDK.RemoteViewAspectRatio.Vertical)
    .audioOptions(VideoSDK.AudioOptions.SpeakerPhoneOn)
    .userInterface(
    cameraSwitchButton = R.drawable.ic_camera_switch,
    cameraSwitchButtonBackground = R.drawable.oval_gray,
    microphoneMuteButton = R.drawable.ic_mic_on,
    microphoneMuteButtonEnabled = R.drawable.ic_mic_off,
    microphoneMuteButtonBackground = R.drawable.oval_gray,
    cameraCloseButton = R.drawable.ic_camera_on,
    cameraCloseButtonEnabled = R.drawable.ic_camera_off,
    cameraCloseButtonBackground = R.drawable.oval_gray,
    callEndButton = R.drawable.call_end,
    callEndButtonBackground = R.drawable.oval_red
    )
    .videoCallObserver(AmaniVideoDelegateEventHandler.videoCallObserver)
    .build()

    frag = VideoSDK.startVideoCall(builder)

    if (frag == null) {
    result.error("30005", "Failed to initialize video call", null)
    return
    }

    // Fragment'i göster
    (activity as FragmentActivity).replaceFragment(containerViewId = id, fragment = frag)
}

   
   
}

