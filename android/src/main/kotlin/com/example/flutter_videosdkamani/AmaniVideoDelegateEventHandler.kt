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
        var events: EventChannel.EventSink? = null

        lateinit var frag: Fragment
        val videoCallObserver: AmaniVideoCallObserver = object :
         AmaniVideoCallObserver {
            override fun onConnectionState(connectionState: ConnectionState) {
            when (connectionState) {
                ConnectionState.CONNECTING -> {
                    Log.i("CallObserver", "Connecting: $connectionState")
                    Handler(Looper.getMainLooper()).post {
                    try {
                        events?.success("connectState_connecting")
                    } catch (e: Exception) {
                        Log.e("CallObserver", "connecting: ${e.message}")
                    }
                    }
                }

                ConnectionState.FAILED -> {
                    Log.i("CallObserver", "failed: $connectionState")
                    Handler(Looper.getMainLooper()).post {
                    try {
                        events?.success("connectState_failed")
                    } catch (e: Exception) {
                        Log.e("CallObserver", "failed: ${e.message}")
                    }
                    }
                }

                ConnectionState.CONNECTED -> {
                    Log.i("CallObserver", "Connected: $connectionState")
                    Handler(Looper.getMainLooper()).post {
                    try {
                        events?.success("connectState_connected")
                    } catch (e: Exception) {
                        Log.e("CallObserver", "connected: ${e.message}")
                    }
                    }
                }

                ConnectionState.DISCONNECTED -> {
                    Log.i("CallObserver", "Disconnected: $connectionState")
                    Handler(Looper.getMainLooper()).post {
                        try {
                            events?.success("connectState_disconnected")
                        } catch (e: Exception) {
                            Log.e("CallObserver", "disconnected: ${e.message}")
                        }
                    }
                }
            }
        }

        override fun onException(exception: String) {
            Log.e("CallObserver", "Video Call Exception: $exception")
            Handler(Looper.getMainLooper()).post {
                try {
                    events?.success("on_exception")
                } catch (e: Exception) {
                    Log.e("CallObserver", "Fragment remove error: ${e.message}")
                }
            }
        }

        override fun onRemoteEvent(
            amaniVideoRemoteEvents: AmaniVideoRemoteEvents,
            isActivated: Boolean
        ) {
            when (amaniVideoRemoteEvents) {
                AmaniVideoRemoteEvents.CALL_END -> {
                        Log.i("CallObserver", "Call end - fragment remove")
                        Handler(Looper.getMainLooper()).post {
                            try {
                                events?.success("call_end")
                            } catch (e: Exception) {
                                Log.e("CallObserver", "Fragment remove error: ${e.message}")
                            }
                        }
                }

                AmaniVideoRemoteEvents.CAMERA_SWITCH -> {
                    Log.i("CallObserver", "Camera değiştir")
                    
                    Handler(Looper.getMainLooper()).post {
                            try {
                                events?.success("camera_switch_requested")
                            } catch (e: Exception) {
                                Log.e("CallObserver", "Fragment remove error: ${e.message}")
                            }
                    }
                }

                AmaniVideoRemoteEvents.TORCH -> {
                    Log.i("CallObserver", "toggle torch")
                    Handler(Looper.getMainLooper()).post {
                            try {
                                events?.success("torch_toggle_requested")
                            } catch (e: Exception) {
                                Log.e("CallObserver", "Fragment remove error: ${e.message}")
                            }
                    }
                }

                AmaniVideoRemoteEvents.CALL_ESCALATED -> {
                    Log.i("CallObserver", "call escalated")
                    Handler(Looper.getMainLooper()).post {
                            try {
                                events?.success("call_escaled")
                            } catch (e: Exception) {
                                Log.e("CallObserver", "Fragment remove error: ${e.message}")
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
                        Log.i("CallObserver", "on ui call end")
                          Handler(Looper.getMainLooper()).post {
                            try {
                                events?.success("on_ui_call_end")
                            } catch (e: Exception) {
                                Log.e("CallObserver", "Fragment remove error: ${e.message}")
                            }
                        }
                    }
                }
                AmaniVideoButtonEvents.CAMERA_SWITCH -> {
                    // if (isActivated) {
                    Log.i("CallObserver", "Camera switched to back camera")
                        Handler(Looper.getMainLooper()).post {
                        try {
                            events?.success("on_ui_camera_switch")
                        } catch (e: Exception) {
                            Log.e("CallObserver", "on ui camera switch error: ${e.message}")
                        }
                    }
                    // } else Log.i(TAG, "Camera re-switch to front camera")
                }

                AmaniVideoButtonEvents.MUTE -> {
                    // if (isActivated) {
                    Log.i("CallObserver", "Muted")
                    Handler(Looper.getMainLooper()).post {
                    try {
                        events?.success("on_ui_muted")
                    } catch (e: Exception) {
                        Log.e("CallObserver", "on ui muted error: ${e.message}")
                    }
                    }
                    // } else Log.i(TAG, "Um-muted")
                }

                AmaniVideoButtonEvents.CAMERA_CLOSE -> {
                    // if (isActivated) {
                    Log.i("CallObserver", "Camera closed")
                    Handler(Looper.getMainLooper()).post {
                    try {
                        events?.success("on_ui_camera_close")
                    } catch (e: Exception) {
                        Log.e("CallObserver", "Fragment remove error: ${e.message}")
                    }
                    }
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