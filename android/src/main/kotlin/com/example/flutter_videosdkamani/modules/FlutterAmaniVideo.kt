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
import kotlinx.coroutines.CoroutineScope
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import android.content.Intent

class FlutterAmaniVideo : Module {
    private var videoModule = VideoSDK()
    companion object {
        val instance = FlutterAmaniVideo()
    }

    /** Call starter button to start a call */
    // private val callStart: Button by lazy { findViewById(R.id.call_start) }

    /** Name surname edit text input to pass it SDK */
    // private val nameSurnameTxt: EditText by lazy { findViewById(R.id.name_surname_txt) }

    /** Progressbar to show until call is answered */
    // private val progressLoader: ProgressBar by lazy { findViewById(R.id.progress_loader) }
    /** Video Call configuration object*/
    private lateinit var videoBuilder: VideoSDK.Builder
    private var containerId = 0x123456

    /** Video Call fragment instance*/
    // private var videoCallFragment: Fragment? = null
    private var frag: Fragment? = null

    
    private val videoCallObserver: AmaniVideoCallObserver = object :
        AmaniVideoCallObserver {
            override fun onConnectionState(connectionState: ConnectionState) {
            when (connectionState) {
                ConnectionState.CONNECTING -> {
                    // Log.i(TAG, "Connecting: ")
                }

                ConnectionState.FAILED -> {
                    // snackBar("Connection failed")
                    // removeFragment(videoCallFragment)
                    // visibleLoader(false)
                }

                ConnectionState.CONNECTED -> {
                    // Log.i(TAG, "Connected")
                    // visibleLoader(false)
                }

                ConnectionState.DISCONNECTED -> {
                    // snackBar("Connection disconnected")
                    // visibleLoader(false)
                }
            }
        }

        override fun onException(exception: String) {
            // Log.e(TAG, "Video Call Exception: $exception")
            // visibleLoader(false)
            // removeFragment(videoCallFragment)
            // Snackbar.make(
            //     findViewById(R.id.layout),
            //     exception,
            //     Snackbar.LENGTH_SHORT
            // ).show()
        }

        override fun onRemoteEvent(
            amaniVideoRemoteEvents: AmaniVideoRemoteEvents,
            isActivated: Boolean
        ) {
            when (amaniVideoRemoteEvents) {
                AmaniVideoRemoteEvents.CALL_END -> {
                    // snackBar("Call is ended")
                    // removeFragment(videoCallFragment)
                }

                AmaniVideoRemoteEvents.CAMERA_SWITCH -> {

                    // alertDialog(
                    //     title = "Camera Switch Request",
                    //     message = "Camera switch is requested by agent. Could you give permission to switch it?",
                    //     positiveButton = "Sure",
                    //     negativeButton = "Not now",
                    //     positiveClick = {
                    //         VideoSDK.switchCamera(object : SwitchCameraObserver {
                    //             override fun onSuccess(cameraPosition: CameraPosition) {

                    //             }

                    //             override fun onException(exception: Throwable) {

                    //             }

                    //         })
                    //     },
                    //     negativeClick = {
                    //         snackBar("Camera switch request is denied")
                    //     }
                    // )
                }

                AmaniVideoRemoteEvents.TORCH -> {
                    // alertDialog(
                    //     title = "Flash Request",
                    //     message = "Flash is requested by agent. Could you give permission to enable it?",
                    //     positiveButton = "Sure",
                    //     negativeButton = "Not now",
                    //     positiveClick = {
                    //         VideoSDK.toggleTorch(object : ToggleTorchObserver {
                    //             override fun onSuccess(isEnabled: Boolean) {

                    //             }

                    //             override fun onError(error: Throwable) {
                    //             }
                    //         })
                    //     },
                    //     negativeClick = {
                    //         snackBar("Flash request is denied")
                    //     }
                    // )
                }

                AmaniVideoRemoteEvents.CALL_ESCALATED -> {

                    // removeFragment(videoCallFragment)

                    // setVideoSDK(escalated = true)

                    // navigateVideoSDKFragment()
                }
            }
        }

        override fun onUiEvent(
            amaniVideoButtonEvents: AmaniVideoButtonEvents,
            isActivated: Boolean
        ) {
            when (amaniVideoButtonEvents) {
                AmaniVideoButtonEvents.CALL_END -> {
                    if (isActivated) {
                        // alertDialog(
                        //     title = "Are you sure?",
                        //     message = "Are you sure you want to end the call? If this was " +
                        //             "accidental, press No to continue the call. Press OK to end it.",
                        //     positiveButton = "OK",
                        //     negativeButton = "NO",
                        //     positiveClick = {
                        //         snackBar("Call is ended")
                        //         removeFragment(videoCallFragment)
                        //     },
                        //     negativeClick = {

                        //     }
                        // )
                    }
                }
                AmaniVideoButtonEvents.CAMERA_SWITCH -> {
                    // if (isActivated) {
                    //     Log.i(TAG, "Camera switched to back camera")
                    // } else Log.i(TAG, "Camera re-switch to front camera")
                }

                AmaniVideoButtonEvents.MUTE -> {
                    // if (isActivated) {
                    //     Log.i(TAG, "Muted")
                    // } else Log.i(TAG, "Um-muted")
                }

                AmaniVideoButtonEvents.CAMERA_CLOSE -> {
                    // if (isActivated) {
                    //     Log.i(TAG, "Camera closed")
                    // } else Log.i(TAG, "Camera re-opened")
                }
            }
        }
    }

   
    // fun setVideoSDK(escalated: Boolean = false) {
       
    // }

    override fun start(serverUrl: String, token: String, name: String, surname: String, stunServer: String, turnServer: String, turnUser: String, turnPass: String, activity: Activity, result: Result) {
        print("FlutteramaniVideoKT kısmına geldi.")

        if (frag != null) {
            result.error(
                "30021",
                "Start function is already triggered before",
                "You cannot call start function before previous session is end up."
            )
            return
        }

        (activity as FragmentActivity)
        val id = 0x123456
        val context = activity.applicationContext
        val viewParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val container = FrameLayout(context)
        container.id = id
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
            .videoCallObserver(videoCallObserver)
            .build()

        frag = VideoSDK.startVideoCall(builder)

        if (frag == null) {
            result.error("30005", "Failed to initialize video call", null)
            return
        }

        // fragmentActivity.replaceFragment(containerId, videoCallFragment!!)
         activity.replaceFragment(
            containerViewId = id,
            fragment = frag
        )

    }


        // (activity as FragmentActivity)
        // val id = 0x123456
        // val context = activity.applicationContext
        // val viewParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        // val container = FrameLayout(context)
        // container.id = id
        // activity.addContentView(container, viewParams)

        //  videoBuilder = VideoSDK.Builder()
        //     .nameSurname(nameSurname = "Bedri Dogan")
        //     .escalatedCall(escalated = true)
        //     .servers(
        //         mainServerURL = VideoSDKCredentials.mainServerURL,
        //         stunServerURL = VideoSDKCredentials.stunServerURL,
        //         turnServerURL = VideoSDKCredentials.turnServerURL
        //     )
        //     .authentication(
        //         token = VideoSDKCredentials.token,
        //         userName = VideoSDKCredentials.userName,
        //         password = VideoSDKCredentials.password
        //     )
        //     .remoteViewAspectRatio(
        //         VideoSDK.RemoteViewAspectRatio.Vertical
        //     )
        //     .audioOptions(
        //         VideoSDK.AudioOptions.SpeakerPhoneOn
        //     )
        //     .userInterface(
        //         cameraSwitchButton = R.drawable.ic_camera_switch,
        //         cameraSwitchButtonBackground = R.drawable.oval_gray,
        //         microphoneMuteButton = R.drawable.ic_mic_on,
        //         microphoneMuteButtonEnabled = R.drawable.ic_mic_off,
        //         microphoneMuteButtonBackground = R.drawable.oval_gray,
        //         cameraCloseButton = R.drawable.ic_camera_on,
        //         cameraCloseButtonEnabled = R.drawable.ic_camera_off,
        //         cameraCloseButtonBackground = R.drawable.oval_gray,
        //         callEndButton = R.drawable.call_end,
        //         callEndButtonBackground = R.drawable.oval_red
        //     )
        //     .videoCallObserver(videoCallObserver)
        //     .build()

        // videoCallFragment = VideoSDK.startVideoCall(videoBuilder)
   


    /**
     * Listen click events
     */


    /**
     * Navigating Video Call Fragment
     */
 

    /**
     * Show/hide progressbar
     */
 
  
}

// class AppActivity : AppCompactActivity() {

//     private var module: VideoSDK? = null

//     private val callStart: Button by lazy { findViewById(R.id.call_start) }

//     /** Name surname edit text input to pass it SDK */
//     private val nameSurnameTxt: EditText by lazy { findViewById(R.id.name_surname_txt) }

//     /** Progressbar to show until call is answered */
//     private val progressLoader: ProgressBar by lazy { findViewById(R.id.progress_loader) }

//     /** TextView to show current app version*/
//     private val appVersion: TextView by lazy { findViewById(R.id.app_version) }

//     /** Video Call configuration object*/
//     private lateinit var videoBuilder: VideoSDK.Builder

//     /** Video Call fragment instance*/
//     private var videoCallFragment: Fragment? = null

//     override fun onCreate(savedInstanceState: Bundle?) {
//         super.onCreate(savedInstanceState)
//         setContentView(R.layout.activity_main)

//         appVersion.text = BuildConfig.VERSION_NAME

//         clickEvent()
//     }

//     companion object {
//         const val TAG = "AmaniVideoCallManager"
//     }

//      private val videoCallObserver: AmaniVideoCallObserver = object :
//         AmaniVideoCallObserver {
//         override fun onConnectionState(connectionState: ConnectionState) {
//             when (connectionState) {
//                 ConnectionState.CONNECTING -> {
//                     Log.i(TAG, "Connecting: ")
//                 }

//                 ConnectionState.FAILED -> {
//                     snackBar("Connection failed")
//                     removeFragment(videoCallFragment)
//                     visibleLoader(false)
//                 }

//                 ConnectionState.CONNECTED -> {
//                     Log.i(TAG, "Connected")
//                     visibleLoader(false)
//                 }

//                 ConnectionState.DISCONNECTED -> {
//                     snackBar("Connection disconnected")
//                     visibleLoader(false)
//                 }
//             }
//         }

//         override fun onException(exception: String) {
//             Log.e(TAG, "Video Call Exception: $exception")
//             visibleLoader(false)
//             removeFragment(videoCallFragment)
//             Snackbar.make(
//                 findViewById(R.id.layout),
//                 exception,
//                 Snackbar.LENGTH_SHORT
//             ).show()
//         }

//         override fun onRemoteEvent(
//             amaniVideoRemoteEvents: AmaniVideoRemoteEvents,
//             isActivated: Boolean
//         ) {
//             when (amaniVideoRemoteEvents) {
//                 AmaniVideoRemoteEvents.CALL_END -> {
//                     snackBar("Call is ended")
//                     removeFragment(videoCallFragment)
//                 }

//                 AmaniVideoRemoteEvents.CAMERA_SWITCH -> {

//                     alertDialog(
//                         title = "Camera Switch Request",
//                         message = "Camera switch is requested by agent. Could you give permission to switch it?",
//                         positiveButton = "Sure",
//                         negativeButton = "Not now",
//                         positiveClick = {
//                             VideoSDK.switchCamera(object : SwitchCameraObserver {
//                                 override fun onSuccess(cameraPosition: CameraPosition) {

//                                 }

//                                 override fun onException(exception: Throwable) {

//                                 }

//                             })
//                         },
//                         negativeClick = {
//                             snackBar("Camera switch request is denied")
//                         }
//                     )
//                 }

//                 AmaniVideoRemoteEvents.TORCH -> {
//                     alertDialog(
//                         title = "Flash Request",
//                         message = "Flash is requested by agent. Could you give permission to enable it?",
//                         positiveButton = "Sure",
//                         negativeButton = "Not now",
//                         positiveClick = {
//                             VideoSDK.toggleTorch(object : ToggleTorchObserver {
//                                 override fun onSuccess(isEnabled: Boolean) {

//                                 }

//                                 override fun onError(error: Throwable) {
//                                 }
//                             })
//                         },
//                         negativeClick = {
//                             snackBar("Flash request is denied")
//                         }
//                     )
//                 }

//                 AmaniVideoRemoteEvents.CALL_ESCALATED -> {

//                     removeFragment(videoCallFragment)

//                     setVideoSDK(escalated = true)

//                     navigateVideoSDKFragment()
//                 }
//             }
//         }

//         override fun onUiEvent(
//             amaniVideoButtonEvents: AmaniVideoButtonEvents,
//             isActivated: Boolean
//         ) {
//             when (amaniVideoButtonEvents) {
//                 AmaniVideoButtonEvents.CALL_END -> {
//                     if (isActivated) {
//                         alertDialog(
//                             title = "Are you sure?",
//                             message = "Are you sure you want to end the call? If this was " +
//                                     "accidental, press No to continue the call. Press OK to end it.",
//                             positiveButton = "OK",
//                             negativeButton = "NO",
//                             positiveClick = {
//                                 snackBar("Call is ended")
//                                 removeFragment(videoCallFragment)
//                             },
//                             negativeClick = {

//                             }
//                         )
//                     }
//                 }
//                 AmaniVideoButtonEvents.CAMERA_SWITCH -> {
//                     if (isActivated) {
//                         Log.i(TAG, "Camera switched to back camera")
//                     } else Log.i(TAG, "Camera re-switch to front camera")
//                 }

//                 AmaniVideoButtonEvents.MUTE -> {
//                     if (isActivated) {
//                         Log.i(TAG, "Muted")
//                     } else Log.i(TAG, "Um-muted")
//                 }

//                 AmaniVideoButtonEvents.CAMERA_CLOSE -> {
//                     if (isActivated) {
//                         Log.i(TAG, "Camera closed")
//                     } else Log.i(TAG, "Camera re-opened")
//                 }
//             }
//         }
//     }

// }