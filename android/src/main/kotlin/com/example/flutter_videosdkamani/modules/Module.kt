package com.example.flutter_videosdkamani.modules
import android.app.Activity
import io.flutter.plugin.common.MethodChannel.Result
import com.example.flutter_videosdkamani.VideoSDKCredentials

interface Module {
    fun start(
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
    )
}
