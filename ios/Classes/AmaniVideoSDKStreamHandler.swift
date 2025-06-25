import Foundation
import Flutter
import AmaniVideoSDK

class DelegateEventHandler: NSObject, FlutterStreamHandler {
  private var eventSink: FlutterEventSink?
  private var amaniVideo: AmaniVideo?
  private var sdkView: SDKView?
  
  func onListen(withArguments arguments: Any?, eventSink events: @escaping FlutterEventSink) -> FlutterError? {
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
      eventSink?("connectState_connecting")
    case .connected:
      eventSink?("connectState_connected")
    case .disconnected:
      eventSink?("connectState_disconnected")
    case .failed:
     eventSink?("call_end")
    }
  }

  public func onException(error: [String]) {
    debugPrint(error)
    DispatchQueue.main.async {
      // self.videoView?.removeFromSuperview()
      self.eventSink?("on_exception")
    }
  }

  public func onUIEvent(event: AmaniVideoSDK.AmaniVideo.UIEvent) {
     debugPrint("flutter on UI event: \(event)")

    switch event {
    case .cameraSwitch:
      eventSink?("on_ui_camera_switch")
    case .cameraClose:
      eventSink?("on_ui_camera_close")
    case .callEnd:
      eventSink?("on_ui_call_end")
    case .mute:
      eventSink?("on_ui_muted")
    case .torch:
      break
    }
  }

  public func onRemoteEvent(event: AmaniVideoSDK.AmaniVideo.RemoteEvent) {
      debugPrint("flutter on remote event: \(event)")

    switch event {
    case .callEnd:
      eventSink?("call_end")
    case .torch:
      eventSink?("torch_toggle_requested")
    case .cameraSwitch:
      eventSink?("camera_switch_requested")
    case .escalated:
      eventSink?("call_escalated")
      
    }
  }
  
}