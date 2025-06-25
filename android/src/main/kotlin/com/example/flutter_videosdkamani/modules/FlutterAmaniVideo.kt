package com.example.flutter_videosdkamani.modules
import androidx.fragment.app.FragmentManager
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
import MessageAlertDialogFragment
import java.lang.ref.WeakReference



class FlutterAmaniVideo : Module {

    //Properties
    private var videoModule = VideoSDK()
    private var currentActivity: WeakReference<Activity>? = null
    private lateinit var videoBuilder: VideoSDK.Builder
    
    private var frag: Fragment? = null
    private var videoContainer: FrameLayout? = null
    private var containerId: Int = 0

    //AmaniVideoSDK observer funcs and start func
    fun switchCamera() {
    val act = currentActivity?.get()
    if (act is FragmentActivity) {
        val dialog = MessageAlertDialogFragment(
            message = "Do you want to switch the camera?",
            onConfirm = {
                Log.e("CallActivity", "confirm tapped")
                VideoSDK.switchCamera(object : SwitchCameraObserver {
                    override fun onSuccess(cameraPosition: CameraPosition) {
                        Log.i("CallActivity", "switchCamera success: $cameraPosition")
                    }

                    override fun onException(exception: Throwable) {
                        Log.e("CallActivity", "switchCamera error: ${exception.message}")
                    }
                })
            },
            onCancel = { /* nothing */ }
        )
        dialog.show(act.supportFragmentManager, "SwitchCameraDialog")
    } else {
        Log.e("CallActivity", "Invalid activity reference")
    }
    }

    fun toggleTorch() {
    val act = currentActivity?.get()
    if (act is FragmentActivity) {
        val dialog = MessageAlertDialogFragment(
            message = "Do you want to toggle the flash?",
            onConfirm = {
                Log.e("CallActivity", "torch tapped")
                VideoSDK.toggleTorch(object : ToggleTorchObserver {
                    override fun onSuccess(isEnabled: Boolean) {
                        Log.i("CallActivity", "toggleTorch success: $isEnabled")
                    }

                    override fun onError(error: Throwable) {
                        Log.e("CallActivity", "toggleTorch error: ${error.message}")
                    }
                })
            },
            onCancel = { /* nothing */ }
        )
        dialog.show(act.supportFragmentManager, "ToggleTorchDialog")
    } else {
        Log.e("CallActivity", "Invalid activity reference")
        }
    }

    /* 
    fun closeSDK(
    ) {
        val act = currentActivity?.get()
        if (act is FragmentActivity) {
            act.removeFragment(frag)

         videoContainer?.let {
            val root = act.findViewById<ViewGroup>(android.R.id.content)
            root.removeView(it) // 🔥 Video container'ı kaldır
            videoContainer = null
        }

        frag = null
    }
       
       
    }
*/
   
/* 
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
                "You cannot call start function before previous session is ended."
            )
            return
        }

        currentActivity = WeakReference(activity)

        val context = activity.applicationContext
        containerId = 0x123456

        val viewParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

    
        videoContainer = FrameLayout(context).apply {
            this.id = containerId
            this.layoutParams = viewParams
            this.setBackgroundColor(Color.RED) // veya RED for debug
            this.isClickable = true
            this.isFocusable = true
            this.setOnTouchListener { _, _ -> true }
        }

        activity.addContentView(videoContainer, viewParams)

        // SDK Builder
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

        // Fragment'i container'a yerleştir
        (activity as FragmentActivity).replaceFragment(containerViewId = containerId, fragment = frag)
}

*/
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
 

    if (frag != null) {
        result.error(
            "30021",
            "Start function is already triggered before",
            "You cannot call start function before previous session is ended."
        )
        return
    }

    currentActivity = WeakReference(activity)

    activity.setContentView(R.layout.activity_video_call)

    val containerId = R.id.video_call_container

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

    (activity as FragmentActivity).replaceFragment(containerViewId = containerId, fragment = frag)
}



fun closeSDK(result: Result) {
   val act = currentActivity?.get()

    if (act is FragmentActivity) {
        frag?.let {
            act.supportFragmentManager.beginTransaction()
                .remove(it)
                .commitNow()
            frag = null
        }

        result.success("Closed")

        
        act.recreate()
    } else {
        result.error("30031", "Not a FragmentActivity", null)
    }
}

}



