
package com.example.flutter_videosdkamani
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
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope

data class VideoSDKCredentials(
    val mainServerURL: String,
    val stunServerURL: String,
    val turnServerURL: String,
    val token: String,
    val userName: String,
    val password: String
) {
    companion object {
        val mainServerURL = "https://sandbox.amani.ai:8091"
        val stunServerURL = "stun:tr.amani.ai:3478"
        val turnServerURL = "turn:tr.amani.ai:3478"
        val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzQ4NTE5MTM1LCJpYXQiOjE3NDg1MTU1MzUsImp0aSI6IjNlNmUyMTA1Y2ZkNjQwMjVhMjA5MmE5MTU0NjU1YjNiIiwidXNlcl9pZCI6IjM5ODQ4Yjc5LWM3NjItNGExNi1iMDFlLTdkYjlkMjJmNmNkNyIsImFwaV91c2VyIjpmYWxzZSwicHJvZmlsZV9pZCI6IjBjNWQyOTM5LWM1ZmMtNDliNS05YzM1LWExMTk3YzM5YjYwNyIsImNvbXBhbnlfaWQiOiJlOWFmYmQzMS03M2NlLTRkZDYtYTI1Ny1mOGYxNmExZjBkNTIifQ._zzYGIjTFZfI0cx8Az9oWw-p3jMtMnVuMvhV1fsPKGo"
        val userName = "st_user"
        val password = "yHB4N!ec%FA3kZ%5xsgB"
    }
}