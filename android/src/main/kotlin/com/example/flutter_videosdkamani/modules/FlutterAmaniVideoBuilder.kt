// package com.example.flutter_videosdkamani

// import android.app.Activity
// import ai.amani.videosdk.VideoSDK
// import ai.amani.videosdk.observer.AmaniVideoCallObserver

// class FlutterVideoSDKBuilder(private val activity: Activity) {
//     private lateinit var builder: VideoSDK.Builder

//     fun init(): FlutterVideoSDKBuilder {
//         builder = VideoSDK.Builder()
//         return this
//     }

//     fun setNameSurname(name: String, surname: String): FlutterVideoSDKBuilder {
//         builder.nameSurname("$name $surname")
//         return this
//     }

//     fun setEscalatedCall(escalated: Boolean): FlutterVideoSDKBuilder {
//         builder.escalatedCall(escalated)
//         return this
//     }

//     fun setServers(mainServer: String, stunServer: String, turnServer: String): FlutterVideoSDKBuilder {
//         builder.servers(
//             mainServerURL = mainServer,
//             stunServerURL = stunServer,
//             turnServerURL = turnServer
//         )
//         return this
//     }

//     fun setAuthentication(token: String, userName: String, password: String): FlutterVideoSDKBuilder {
//         builder.authentication(token, userName, password)
//         return this
//     }

//     fun setRemoteViewMode(mode: VideoSDK.RemoteViewAspectRatio): FlutterVideoSDKBuilder {
//         builder.remoteViewAspectRatio(mode)
//         return this
//     }

//     fun setAudioOptions(option: VideoSDK.AudioOptions): FlutterVideoSDKBuilder {
//         builder.audioOptions(option)
//         return this
//     }

//     fun setUserInterface(): FlutterVideoSDKBuilder {
//         builder.userInterface(
//             cameraSwitchButton = R.drawable.ic_camera_switch,
//             cameraSwitchButtonBackground = R.drawable.oval_gray,
//             microphoneMuteButton = R.drawable.ic_mic_on,
//             microphoneMuteButtonEnabled = R.drawable.ic_mic_off,
//             microphoneMuteButtonBackground = R.drawable.oval_gray,
//             cameraCloseButton = R.drawable.ic_camera_on,
//             cameraCloseButtonEnabled = R.drawable.ic_camera_off,
//             cameraCloseButtonBackground = R.drawable.oval_gray,
//             callEndButton = R.drawable.call_end,
//             callEndButtonBackground = R.drawable.oval_red
//         )
//         return this
//     }

//     fun setObserver(observer: AmaniVideoCallObserver): FlutterVideoSDKBuilder {
//         builder.videoCallObserver(observer)
//         return this
//     }

//     fun build(): VideoSDK.Builder {
//         return VideoSDK.Builder
//     }
// }