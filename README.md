# Amani Flutter VideoSDK #

# Table of Content
- [Overview](#overview)
- [Basics](#basics)
    - [General Requirements](#general-requirements)
    - [Permissions](#permissions)
    - [Integration](#integration)
- [Installation](#Installation)
  
# Overview

The Amani Video SDK provides a video-based KYC verification solution. This Android SDK allows you to integrate real-time video calls with support for remote agent communication, camera switching, torch toggling, and event handling for call states.

# General Requirements - Android

The minimum requirements for the SDK are:

minSdkVersion 21  
compileSdk 34  
Compiled with Java 17, minimum Java Version should be 17 as follows.
```groovy
compileOptions {  
        sourceCompatibility JavaVersion.VERSION_17  
        targetCompatibility JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget=17
    }

    kotlin {
        jvmToolchain(17)
    }  
```    

# Permissions - Android

This SDK makes use of the devices Camera, Location and NFC permissions. If you don't want to use location service, please provide in init method.

You must have the folowing keys in your application's manifest file:
```xml
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

# ProGuard Rule Usage

If you are using ProGuard in your application, you just need to add this line into your ProGuard Rules!

```xml
-keep class ai.amani** {*;}
-dontwarn ai.amani**
-keep class com.cloudwebrtc.webrtc.** { *; }
-keep class org.webrtc.** { *; }     
```

# Installation

##Adding the flutter plugin

To add this flutter plugin you must add the lines below to your pubspec.yaml file.

```xml
flutter_videosdkamani:
  git:
    url: https://github.com/bedryy/flutter_videosdkamani.git
    ref: 'main'
```

1. Add the following dependencies to your Module build.gradle file.
```groovy
 implementation 'ai.amani.android:amanivideosdk:1.4.1'
 ```
2. Enable view-binding in the Module build.gradle by adding this line into code block of android : 
```groovy
buildFeatures {
    viewBinding true
}
```
3. Add the following in the Project build.gradle within in buildscript within the buildscript->repositories and buildscript->allprojects.

```groovy
 maven { url "https://jfrog.amani.ai/artifactory/amani-video-sdk"}
  ```
 
 
 # General Requirements - iOS
 The minimum requirements for this SDK are:

  iOS 13.0 and higher  
  Xcode 14 and higher (after version 1.1.1)
  
  
Add these lines on your Podfile\
```xml
source "https://github.com/AmaniTechnologiesLtd/Mobile_SDK_Repo"
source "https://github.com/CocoaPods/Specs"
```

```xml
post_install do |installer|
  installer.pods_project.targets.each do |target|
    
    flutter_additional_ios_build_settings(target)
    target.build_configurations.each do |config|
     config.build_settings['BUILD_LIBRARY_FOR_DISTRIBUTION'] = 'YES'
    end
  end
end
```

# Permissions - iOS

##For Location

```xml
<key>NSLocationWhenInUseUsageDescription</key>
<string>This application requires access to your location to upload the document.</string>
<key>NSLocationUsageDescription</key>
<string>This application requires access to your location to upload the document.</string>
<key>NSLocationAlwaysUsageDescription</key>
<string>This application requires access to your location to upload the document.</string>
<key>NSLocationAlwaysAndWhenInUseUsageDescription</key>
<string>This application requires access to your location to upload the document.</string>
```

##For Microphone

```xml
<key>NSMicrophoneUsageDescription</key>
<string>This app needs to use camera for video call features</string>
```

##For Camera

```xml
<key>NSCameraUsageDescription</key>
<string>This application requires access to your camera for scanning and uploading the document.</string>
 ```
 
##Required background modes - Caution Here!

You need to add Background Modes on the Signing and Capabilities section on your project.

Required background modes:

Audio, AirPlay and Picture in Picture
Voice over IP
Alternatively you can add the block below on your info.plist file

```xml
<key>UIBackgroundModes</key>
	<array>
		<string>audio</string>
		<string>voip</string>
	</array>
 ```
 
 
 # Usage

```groovy
class _AmaniVideoSDKScreenState extends State<AmaniVideoSDKScreen> {

final VideoSDK _videoSDKModule = FlutterVideosdkamani().getAmaniVideo();


@override
void initState() {
    super.initState();
    
    //During this video call, you need to listen to this broadcast to follow the instructions that may come from the user or agent side, such as changing the camera direction, turning on the flash, or whether or not the video call is connected.
    
    _videoStreamSubscription = _videoEventChannel.receiveBroadcastStream().listen(_handleNativeEvent);
    
    setupVideoBuilder();
}


 Future<void> setupVideoBuilder() async {
  _videoSDKModule.startVideo("server_url", 
                               "token",
                               "name", 
                               "surname", 
                               "stun_server",
                               "turn_server", 
                               "st_user", 
                               "st_user_pass");
    _videoSDKModule.setAmaniVideoDelegate();

  }
  
  void _handleNativeEvent(dynamic event) {
    debugPrint("Native Event: $event");

    if (event == "camera_switch_requested") {
      _videoSDKModule.switchCamera();
    } else if (event == "torch_toggle_requested") {
      _videoSDKModule.toggleTorch();
    } else if (event == "call_end") {
      _videoSDKModule.closeSDK();
      
    }
  }


  
}
```

Events from the native SDK are streamed to Flutter via EventChannel. Example event types:

| Event Name                     | Description                          |
|-------------------------------|--------------------------------------|
| connectState_connecting       | Video session is connecting          |
| connectState_connected        | Video session successfully connected |
| connectState_failed           | Connection attempt failed            |
| connectState_disconnected     | Disconnected from session            |
| call_end                      | Call ended by remote                 |
| torch_toggle_requested        | Remote requested torch toggle        |
| camera_switch_requested       | Remote requested camera switch       |
| call_escaled                  | Video call escalated                 |
| on_ui_call_end                | End button clicked by user           |
| on_ui_camera_switch           | Camera switched by user              |
| on_ui_muted                   | Microphone muted                     |
| on_ui_camera_close            | Camera turned off by user            |
