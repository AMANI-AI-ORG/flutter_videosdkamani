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
import com.example.flutter_videosdkamani.ui.AmaniVideoCallActivity
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

    object AmaniCallHolder {
    var activity: AmaniVideoCallActivity? = null
    }
    
    private var frag: Fragment? = null
    private var videoContainer: FrameLayout? = null
    private var containerId: Int = 0

    //AmaniVideoSDK observer funcs and start func
    fun switchCamera() {
        AmaniVideoCallActivity.switchCameraUI()
    }

    fun toggleTorch() {
        AmaniVideoCallActivity.toggleTorchUI()
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
        val intent = Intent(activity, AmaniVideoCallActivity::class.java).apply {
            putExtra("serverUrl", serverUrl)
            putExtra("token", token)
            putExtra("name", name)
            putExtra("surname", surname)
            putExtra("stunServer", stunServer)
            putExtra("turnServer", turnServer)
            putExtra("turnUser", turnUser)
            putExtra("turnPass", turnPass)
        }

        activity.startActivity(intent)
        result.success("Video call activity started")
}



fun closeSDK(result: Result) {
    FlutterAmaniVideo.AmaniCallHolder.activity?.let {
        it.finish()
        result.success("Closed")
    } ?: run {
        result.error("30032", "AmaniVideoCallActivity is null", null)
    }
}

}



