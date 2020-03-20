#import <UIKit/UIKit.h>
#import <React/RCTComponent.h>
#import <CoreMotion/CoreMotion.h>

@interface RNGyroHandlerModule : UIView

@property (nonatomic, copy) RCTDirectEventBlock onGyroChange;
@property (nonatomic) CMMotionManager * motionManager;

- (void) startGyroUpdates;

@end
