package com.example.flutter_videosdkamani

import ai.amani.videosdk.observer.AmaniVideoButtonEvents
import ai.amani.videosdk.observer.AmaniVideoCallObserver
import ai.amani.videosdk.observer.AmaniVideoRemoteEvents
import ai.amani.videosdk.observer.ConnectionState
import android.os.Handler
import android.os.Looper
import android.util.Log
import io.flutter.plugin.common.EventChannel

class AmaniVideoDelegateEventHandler : EventChannel.StreamHandler {

    companion object {
        private const val TAG = "CallObserver"

        var events: EventChannel.EventSink? = null

        private fun emit(event: String) {
            Handler(Looper.getMainLooper()).post {
                try {
                    events?.success(event)
                } catch (e: Exception) {
                    Log.e(TAG, "Emit error for $event: ${e.message}")
                }
            }
        }

        val videoCallObserver: AmaniVideoCallObserver = object : AmaniVideoCallObserver {

            override fun onConnectionState(connectionState: ConnectionState) {
                Log.i(TAG, "Connection state: $connectionState")

                when (connectionState) {
                    ConnectionState.CONNECTING -> emit("connectState_connecting")
                    ConnectionState.CONNECTED -> emit("connectState_connected")
                    ConnectionState.DISCONNECTED -> emit("connectState_disconnected")
                    ConnectionState.FAILED -> emit("connectState_failed")
                    ConnectionState.BACKGROUNDED -> emit("connectState_disconnected")
                }
            }

            override fun onError(error: String) {
                Log.e(TAG, "Video Call Error: $error")
                emit("on_error")
            }

            override fun onException(exception: String) {
                Log.e(TAG, "Video Call Exception: $exception")
                emit("on_exception")
            }

            override fun onRemoteEvent(
                amaniVideoRemoteEvents: AmaniVideoRemoteEvents,
                isActivated: Boolean
            ) {
                when (amaniVideoRemoteEvents) {
                    AmaniVideoRemoteEvents.CALL_END -> {
                        Log.i(TAG, "Call end")
                        emit("call_end")
                    }

                    AmaniVideoRemoteEvents.CAMERA_SWITCH -> {
                        Log.i(TAG, "Camera switch requested")
                        emit("camera_switch_requested")
                    }

                    AmaniVideoRemoteEvents.TORCH -> {
                        Log.i(TAG, "Torch toggle requested")
                        emit("torch_toggle_requested")
                    }

                    AmaniVideoRemoteEvents.CALL_ESCALATED -> {
                        Log.i(TAG, "Call escalated")
                        emit("call_escaled")
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
                            Log.i(TAG, "UI call end")
                            emit("on_ui_call_end")
                        }
                    }

                    AmaniVideoButtonEvents.CAMERA_SWITCH -> {
                        Log.i(TAG, "UI camera switch")
                        emit("on_ui_camera_switch")
                    }

                    AmaniVideoButtonEvents.MUTE -> {
                        Log.i(TAG, "UI mute")
                        emit("on_ui_muted")
                    }

                    AmaniVideoButtonEvents.CAMERA_CLOSE -> {
                        Log.i(TAG, "UI camera close")
                        emit("on_ui_camera_close")
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