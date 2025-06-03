#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint flutter_videosdkamani.podspec` to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'flutter_videosdkamani'
  s.version          = '1.0.0'
    s.summary          = 'Amani Video SDK Flutter bindings for ios and android'
    s.description      = <<-DESC
    Amani Video SDK Flutter bindings for ios and android
                         DESC
    s.homepage         = 'https://amani.ai'
    s.license          = { :file => '../LICENSE' }
    s.author           = { 'Mobile Team' => 'mobile_team@amani.ai' }
    s.source           = { :path => '.' }
    s.source_files = 'Classes/**/*'
    s.resources = 'Assets/**/*'
    s.dependency 'Flutter'
    s.dependency 'AmaniVideoSDK', '2.0.1-xcode16'
    s.platform = :ios, '13.0'
  
    # Flutter.framework does not contain a i386 slice.
    s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386' }
    s.swift_version = '5.0'
end
