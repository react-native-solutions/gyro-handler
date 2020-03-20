#import "RNGyroHandlerModule.h"

@implementation RNGyroHandlerModule

- (instancetype)init
{
  self = [super init];
  self.motionManager = [[CMMotionManager alloc] init];

  return self;
}

- (void)didMoveToSuperview {
  [super didMoveToSuperview];
  
  [self startGyroUpdates];
}

- (void)willRemoveSubview {
  [super willRemoveSubview:self];
  
  [self.motionManager stopGyroUpdates];
}

- (void)startGyroUpdates {
    [self.motionManager startGyroUpdates];

  [self.motionManager startGyroUpdatesToQueue:[NSOperationQueue mainQueue]
                                    withHandler:^(CMGyroData *gyroData, NSError *error)
  {
       double x = gyroData.rotationRate.x;
       double y = gyroData.rotationRate.y;
       double z = gyroData.rotationRate.z;
       double timestamp = gyroData.timestamp;
      
      if (self.onGyroChange) {
          self.onGyroChange(@{
            @"x" : [NSNumber numberWithDouble:x],
            @"y" : [NSNumber numberWithDouble:y],
            @"z" : [NSNumber numberWithDouble:z],
            @"timestamp" : [NSNumber numberWithDouble:timestamp]
          });
     }
   }];
}

@end
