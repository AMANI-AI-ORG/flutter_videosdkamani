#import "FlutterAmanivideosdkPlugin.h"
#if __has_include(<flutter_amanivideosdk/flutter_amanivideosdk-Swift.h>)
#import <flutter_amanivideosdk/flutter_amanivideosdk-Swift.h>
#else
#import "flutter_amanivideosdk-Swift.h"
#endif

@implementation FlutterAmanivideosdkPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterAmanivideosdkPlugin registerWithRegistrar:registrar];
}
@end