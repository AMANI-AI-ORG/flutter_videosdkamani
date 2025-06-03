import Flutter
import UIKit
import AmaniVideoSDK

public class FlutterVideosdkamaniPlugin: NSObject, FlutterPlugin {
  var amaniVideo: AmaniVideo?
  var flutterAmaniVideo: FlutterAmaniVideo?
  var methodChannel: FlutterMethodChannel!
  var delegateChannel: FlutterEventChannel!
  static var eventHandler = DelegateEventHandler()
    
  public static func register(with registrar: FlutterPluginRegistrar) {
    let methodChannel = FlutterMethodChannel(name: "amanivideosdk_method_channel", binaryMessenger: registrar.messenger())
    let delegateChannel = FlutterEventChannel(name: "amanivideosdk_delegate_channel", binaryMessenger: registrar.messenger())
    
    let instance = FlutterVideosdkamaniPlugin()
    instance.methodChannel = methodChannel
    instance.methodChannel = methodChannel
    instance.delegateChannel = delegateChannel
    instance.delegateChannel.setStreamHandler(FlutterVideosdkamaniPlugin.eventHandler)
    
    registrar.addMethodCallDelegate(instance, channel: methodChannel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    let arguments = call.arguments as? [String: Any]
    switch call.method {
    case "startVideo":
     if let args = call.arguments as? [String: Any] {
      self.flutterAmaniVideo = FlutterAmaniVideo()
        do {
          self.amaniVideo = try AmaniVideoBuilder()
            .setServerURL(url: URL(string: args["serverUrl"] as! String)!)
            .setToken(token: args["token"] as! String)
            .setName(name: args["name"] as! String, surname: args["surname"] as! String)
            .setRemoteViewMode(viewMode: AmaniVideoSDK.AmaniVideo.ViewMode.portrait)
            .setRTCConfig(
              stunServerURL: args["stunServer"] as! String,
              turnServerURL: args["turnServer"] as! String,
              turnUsername: args["turnUser"] as! String,
              turnPassword: args["turnPass"] as! String
            )
            .setBackgroundViewColor(color: UIColor.darkGray)
            .setDelegate(delegate: FlutterVideosdkamaniPlugin.eventHandler)
            .build()
        debugPrint("pluginde amaniVideo değişkeni değeri: \(self.amaniVideo)")
        self.flutterAmaniVideo?.module = self.amaniVideo
        
        self.flutterAmaniVideo?.start(status: AmaniVideoSDK.CallStatus.waiting, result: result)
          
        // if let videoView = try self.amaniVideo?.start(on: rootVC.view, status: AmaniVideoSDK.CallStatus.waiting) {
        //       videoView.frame = rootVC.view.bounds
        //       videoView.autoresizingMask = [.flexibleWidth, .flexibleHeight]
        //       rootVC.view.addSubview(videoView)
        //   }
        // rootVC.present(self, animated: true)
        // try self.amaniVideo?.start(on: rootVC.view, status: AmaniVideoSDK.CallStatus.waiting) 

        
        } catch {
          print("AmaniVideo build error: \(error.localizedDescription)")
        }
      }
      result(nil)
    case "setAmaniVideoDelegate":
      setAmaniVideoDelegate(result: result)
    case "switchCamera":
      self.flutterAmaniVideo = FlutterAmaniVideo()
      self.flutterAmaniVideo?.module = self.amaniVideo
      self.flutterAmaniVideo?.switchCamera(result: result)

    case "closeSDK":
      self.flutterAmaniVideo = FlutterAmaniVideo()
      self.flutterAmaniVideo?.module = self.amaniVideo
      self.flutterAmaniVideo?.closeSDK()
    case "toggleTorch":
      self.flutterAmaniVideo = FlutterAmaniVideo()
      self.flutterAmaniVideo?.module = self.amaniVideo
      self.flutterAmaniVideo?.toggleTorch(result: result)

    default:
      result(FlutterMethodNotImplemented)
    }
  }

  private func setAmaniVideoDelegate(result: @escaping FlutterResult) {
    amaniVideo?.setDelegate(FlutterVideosdkamaniPlugin.eventHandler)
}
 
}
