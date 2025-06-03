import Foundation
import Flutter
import AmaniVideoSDK

class DelegateEventHandler: NSObject, FlutterStreamHandler {
  private var eventSink: FlutterEventSink?
  private var amaniVideo: AmaniVideo?
  private var sdkView: SDKView?
  
  func onListen(withArguments arguments: Any?, eventSink events: @escaping FlutterEventSink) -> FlutterError? {
    print("DELEGATE EVENT HANDLER EVENTS TETİKLENDİ ---------------------");
    print(events);
    self.eventSink = events
    return nil
  }
  
  func onCancel(withArguments arguments: Any?) -> FlutterError? {
    eventSink = nil
    return nil
  }
}

extension DelegateEventHandler: AmaniVideoDelegate {
  public func onConnectionState(newState: AmaniVideoSDK.AmaniVideo.ConnectionState) {
    debugPrint("flutter on newState event: \(newState)")

    switch newState {
    case .connecting:
      break
    case .connected:
      break
    case .disconnected:
      break
    case .failed:
     eventSink?("call_end")
    }
  }

  public func onException(error: [String]) {
    debugPrint(error)
    DispatchQueue.main.async {
      // self.videoView?.removeFromSuperview()
    }
  }

  public func onUIEvent(event: AmaniVideoSDK.AmaniVideo.UIEvent) {
     debugPrint("flutter on UI event: \(event)")

    switch event {
    case .cameraSwitch:
      break
    case .cameraClose:
      break
    case .callEnd:
      eventSink?("call_end")
    case .mute:
      break
    case .torch:
      break
    }
  }

  public func onRemoteEvent(event: AmaniVideoSDK.AmaniVideo.RemoteEvent) {
      debugPrint("flutter on remote event: \(event)")

    switch event {
    case .callEnd:
    debugPrint("flutter on remote event1: \(event)")
      eventSink?("call_end")
    case .torch:
    debugPrint("flutter on remote event2: \(event)")
      eventSink?("torch_toggle_requested")
    case .cameraSwitch:
    debugPrint("flutter on remote event3: \(event)")
      eventSink?("camera_switch_requested")
    case .escalated:
      print("escalated")
      
    }
  }
  
}