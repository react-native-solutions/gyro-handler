#import "RNGyroHandlerManager.h"
#import "RNGyroHandlerModule.h"

@implementation RNGyroHandlerManager

RCT_EXPORT_MODULE()

+ (BOOL)requiresMainQueueSetup
{
    return YES;
}

RCT_EXPORT_VIEW_PROPERTY(onGyroChange, RCTDirectEventBlock)

- (instancetype)init
{
    self = [super init];
    self.gyroHandler = [[RNGyroHandlerModule alloc] init];
  
    return self;
}

- (UIView *)view
{
  if (self) {
    return self.gyroHandler;
  }
  
  return [RCTView new];
}

@end
