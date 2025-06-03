package ai.amani.flutter_amanisdk
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
import com.google.gson.Gson
import io.flutter.plugin.common.EventChannel

class AmaniVideoDelegateEventHandler: EventChannel.StreamHandler {
 private var eventSink: EventChannel.EventSink? = null
    private val TAG = "AmaniVideoDelegate"

    private val videoCallObserver = object : AmaniVideoCallObserver {

        override fun onConnectionState(connectionState: ConnectionState) {
            (TAG, "ConnectionState: $connectionState")
            eventSink?.success(
                mapOf(
                    "type" to "connectionState",
                    "data" to connectionState.name
                )
            )
        }

        override fun onException(exception: String) {
           (TAG, "Video Call Exception: $exception")
            eventSink?.success(
                mapOf(
                    "type" to "exception",
                    "data" to exception
                )
            )
        }

        override fun onRemoteEvent(event: AmaniVideoRemoteEvents, isActivated: Boolean) {
            Log.i(TAG, "RemoteEvent: $event, Activated: $isActivated")
            eventSink?.success(
                mapOf(
                    "type" to "remoteEvent",
                    "data" to mapOf(
                        "event" to event.name,
                        "isActivated" to isActivated
                    )
                )
            )
        }

        override fun onUiEvent(event: AmaniVideoButtonEvents, isActivated: Boolean) {
            (TAG, "UiEvent: $event, Activated: $isActivated")
            eventSink?.success(
                mapOf(
                    "type" to "uiEvent",
                    "data" to mapOf(
                        "event" to event.name,
                        "isActivated" to isActivated
                    )
                )
            )
        }
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        eventSink = events
        (TAG, "EventChannel is listening")

        // Set observer on listen
        AmaniVideoSDK.setVideoCallObserver(videoCallObserver)
    }

    override fun onCancel(arguments: Any?) {
        eventSink = null
        (TAG, "EventChannel canceled")
    }
}