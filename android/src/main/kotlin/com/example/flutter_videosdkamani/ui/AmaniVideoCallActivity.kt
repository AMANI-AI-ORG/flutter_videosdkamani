package com.example.flutter_videosdkamani.ui
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flutter_videosdkamani.R
import ai.amani.videosdk.VideoSDK
import ai.amani.videosdk.observer.AmaniVideoButtonEvents
import ai.amani.videosdk.observer.AmaniVideoCallObserver
import ai.amani.videosdk.observer.AmaniVideoRemoteEvents
import ai.amani.videosdk.observer.CameraPosition
import ai.amani.videosdk.observer.ConnectionState
import ai.amani.videosdk.observer.SwitchCameraObserver
import ai.amani.videosdk.observer.ToggleTorchObserver
import com.example.flutter_videosdkamani.AmaniVideoDelegateEventHandler
import com.example.flutter_videosdkamani.modules.FlutterAmaniVideo
import MessageAlertDialogFragment
import android.util.Log

class AmaniVideoCallActivity : AppCompatActivity() {
    
     private var frag: Fragment? = null

     

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FlutterAmaniVideo.AmaniCallHolder.activity = this

        setContentView(R.layout.activity_video_call)

        val containerId = R.id.video_call_container

        // Parametreleri intent’ten al
        val serverUrl = intent.getStringExtra("serverUrl") ?: return
        val token = intent.getStringExtra("token") ?: return
        val name = intent.getStringExtra("name") ?: ""
        val surname = intent.getStringExtra("surname") ?: ""
        val stunServer = intent.getStringExtra("stunServer") ?: ""
        val turnServer = intent.getStringExtra("turnServer") ?: ""
        val turnUser = intent.getStringExtra("turnUser") ?: ""
        val turnPass = intent.getStringExtra("turnPass") ?: ""

        val builder = VideoSDK.Builder()
            .nameSurname("$name $surname")
            .escalatedCall(true)
            .servers(serverUrl, stunServer, turnServer)
            .authentication(token, turnUser, turnPass)
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

        frag?.let {
            supportFragmentManager.beginTransaction()
                .replace(containerId, it)
                .commit()
        }
    }

    override fun onDestroy() {
        frag?.let {
            supportFragmentManager.beginTransaction().remove(it).commitNowAllowingStateLoss()
            frag = null
        }

      
        FlutterAmaniVideo.AmaniCallHolder.activity = null

        super.onDestroy()
    }


    fun showSwitchCameraDialog() {
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
        dialog.show(supportFragmentManager, "SwitchCameraDialog")
    }

    fun showToggleTorchDialog() {
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
        dialog.show(supportFragmentManager, "ToggleTorchDialog")
    }


    companion object {
        fun switchCameraUI() {
            FlutterAmaniVideo.AmaniCallHolder.activity?.showSwitchCameraDialog()
        }

        fun toggleTorchUI() {
            FlutterAmaniVideo.AmaniCallHolder.activity?.showToggleTorchDialog()
        }
    }
}
