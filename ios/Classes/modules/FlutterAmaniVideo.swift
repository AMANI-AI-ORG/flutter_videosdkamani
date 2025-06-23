//
//  FlutterAmaniVideo.swift
//  flutter_amanivideosdk
//
//  Created by Bedri Doğan on 08.05.2025.
//

import AmaniVideoSDK
import Flutter
import UIKit

class FlutterAmaniVideo {
  var module: AmaniVideo?
  private var sdkView: SDKView!


    init() {

    }

    public func start(status: AmaniVideoSDK.CallStatus, result: @escaping FlutterResult) {
         print("start() çağrıldı. Module değeri: \(String(describing: module))")
        guard let vc = UIApplication.shared.windows.last?.rootViewController else {

            result(FlutterError(code: "30001", message: "No root view controller", details: nil))
            return
        }

        do {
          guard let modul = self.module else {
            return
          }
          let moduleView: UIView = modul.start(on: vc.view, status: status)
          
           

            sdkView = SDKView(sdkView: moduleView)
            sdkView.start(on: vc)
            sdkView.setupBackButton(on: moduleView)

        } catch let err {
            result(FlutterError(code: "30007", message: err.localizedDescription, details: nil))
        }
    }

    public func setDelegate(delegate: AmaniVideoSDK.AmaniVideoDelegate, result: @escaping FlutterResult) {
      module?.setDelegate(delegate)
        result(nil)
    }


    public func toggleTorch(result: @escaping FlutterResult) {
      guard let vc = UIApplication.shared.windows.last?.rootViewController else {
        result(FlutterError(code: "30001", message: "No root view controller", details: nil))
        return
      }

      let alertVC = MessageAlertController(
        message: "Do you want to toggle the flash?",
        onConfirm: { [weak self] in
            print("Confirmed toggle ")
            self?.module?.toggleTorch()
            result(nil)
        },
        onCancel: {
            print("Cancelled toggle")
            result(nil) // ya da error verebilirsin
        }
    )

    DispatchQueue.main.async {
        vc.present(alertVC, animated: true, completion: nil)
    }
        // module?.toggleTorch()
        // result(nil)
    }

    public func switchCamera(result: @escaping FlutterResult) {

        guard let vc = UIApplication.shared.windows.last?.rootViewController else {
            result(FlutterError(code: "30001", message: "No root view controller", details: nil))
            return
        }

        let alertVC = MessageAlertController(
            message: "Do you want to switch the camera?",
            onConfirm: { [weak self] in
                print("Confirmed switch camera")
                self?.module?.switchCamera()
                result(nil)
            },
            onCancel: {
                print("Cancelled switch camera")
                result(nil) // ya da error verebilirsin
            }
        )

        DispatchQueue.main.async {
            vc.present(alertVC, animated: true, completion: nil)
        }
      
      // module?.switchCamera()
      //   result(nil)
    }

    public func closeSDK() {
      
      DispatchQueue.main.async {
       self.module?.closeSDK()
      }
      
      
     
    }
}
