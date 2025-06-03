//  FlutterAmaniVideoBuilder.swift
//  flutter_amanivideosdk
//
//  Created by Bedri Doğan on 08.05.2025.
//

import AmaniVideoSDK
import Flutter
import UIKit
import Foundation

class FlutterAmaniVideoBuilder {
    private var module: AmaniVideoSDK.AmaniVideoBuilder!
    private var amaniVideo: AmaniVideo?

    init() {

    }

    @discardableResult
    public func setServerURL(url: URL)  -> AmaniVideoSDK.AmaniVideoBuilder {
        module.setServerURL(url: url)
        return module
    
    }

    @discardableResult
    public func setToken(token: String) -> AmaniVideoSDK.AmaniVideoBuilder {
        module?.setToken(token: token)
        return module
      
    }

    @discardableResult
    public func setName(name: String, surname: String) -> AmaniVideoSDK.AmaniVideoBuilder {
        module?.setName(name: name, surname: surname)
        return module
        
    }

    @discardableResult
    public func setDelegate(delegate: AmaniVideoSDK.AmaniVideoDelegate) -> AmaniVideoSDK.AmaniVideoBuilder {
        module?.setDelegate(delegate: delegate)
        return module
     
    }

    @discardableResult
    public func setRemoteViewMode(viewMode: AmaniVideoSDK.AmaniVideo.ViewMode) -> AmaniVideoSDK.AmaniVideoBuilder {
        module?.setRemoteViewMode(viewMode: viewMode)
        return module
        
    }

    @discardableResult
    public func setBackgroundViewColor(color: UIColor) -> AmaniVideoSDK.AmaniVideoBuilder {
        module?.setBackgroundViewColor(color: color)
        return module
       
    }

    @discardableResult
    public func setButtonColors(colors: [AmaniVideoSDK.AmaniVideo.ButtonColors: UIColor]) -> AmaniVideoSDK.AmaniVideoBuilder {
        module?.setButtonColors(colors: colors)
      return module
      
    }

    @discardableResult
    public func setButtonIcons(icons: [AmaniVideoSDK.AmaniVideo.ButtonIcons: UIImage]) -> AmaniVideoSDK.AmaniVideoBuilder {
        module?.setButtonIcons(icons: icons)
        return module
       
    }

    @discardableResult
    public func setRTCConfig(stunServerURL: String, turnServerURL: String, turnUsername: String, turnPassword: String) -> AmaniVideoSDK.AmaniVideoBuilder {
        module?.setRTCConfig(stunServerURL: stunServerURL, turnServerURL: turnServerURL, turnUsername: turnUsername, turnPassword: turnPassword)
        return module
      
    }

    @discardableResult
    public func build() throws -> AmaniVideo? {
        do {
          self.amaniVideo = try module?.build() 
          return amaniVideo
        }
        catch let error {
            debugPrint(error)
            throw error
        }
       
       
    }
}
