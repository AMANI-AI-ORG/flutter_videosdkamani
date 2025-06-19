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
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class AmaniVideoDelegateEventHandler: EventChannel.StreamHandler {

    companion object {
        private const val TAG = "CallObserver"
        var events: EventChannel.EventSink? = null

        lateinit var frag: Fragment
        val videoCallObserver: AmaniVideoCallObserver = object :
         AmaniVideoCallObserver {
            override fun onConnectionState(connectionState: ConnectionState) {
            when (connectionState) {
                ConnectionState.CONNECTING -> {
                    Log.i(TAG, "Connecting: ")
                }

                ConnectionState.FAILED -> {
                    // snackBar("Connection failed")
                    // removeFragment(videoCallFragment)
                    // visibleLoader(false)
                }

                ConnectionState.CONNECTED -> {
                     Log.i(TAG, "Connected")
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
                        Log.i(TAG, "Call end - fragment remove")
                        Handler(Looper.getMainLooper()).post {
                            try {
                                events?.success("call_end")
                            } catch (e: Exception) {
                                Log.e(TAG, "Fragment remove error: ${e.message}")
                            }
                        }
                }

                AmaniVideoRemoteEvents.CAMERA_SWITCH -> {
                    Log.i(TAG, "Camera değiştir")
                    
                    Handler(Looper.getMainLooper()).post {
                            try {
                                events?.success("camera_switch_requested")
                            } catch (e: Exception) {
                                Log.e(TAG, "Fragment remove error: ${e.message}")
                            }
                    }
                }

                AmaniVideoRemoteEvents.TORCH -> {
                    Log.i(TAG, "flaş aç")
                    Handler(Looper.getMainLooper()).post {
                            try {
                                events?.success("torch_toggle_requested")
                            } catch (e: Exception) {
                                Log.e(TAG, "Fragment remove error: ${e.message}")
                            }
                    }
                }

                AmaniVideoRemoteEvents.CALL_ESCALATED -> {
                    Log.i(TAG, "escalted oldu")
                    Handler(Looper.getMainLooper()).post {
                            try {
                                events?.success("call_escaled")
                            } catch (e: Exception) {
                                Log.e(TAG, "Fragment remove error: ${e.message}")
                            }
                    }
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
                    Log.i(TAG, "Camera switched to back camera")
                    // } else Log.i(TAG, "Camera re-switch to front camera")
                }

                AmaniVideoButtonEvents.MUTE -> {
                    // if (isActivated) {
                    Log.i(TAG, "Muted")
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
      
    }

  

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        AmaniVideoDelegateEventHandler.events = events
    }

    override fun onCancel(arguments: Any?) {
        AmaniVideoDelegateEventHandler.events = null
    }
}