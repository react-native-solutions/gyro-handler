import { ReactNode } from 'react';
import { requireNativeComponent, ViewProps, NativeSyntheticEvent, Platform, View } from 'react-native';
import Animated from 'react-native-reanimated';

type GyroEvent = {
  x?: number,
  y?: number,
  z?: number,
  timestamp?: number
};

interface GyroHandlerProps extends ViewProps {
  onGyroChange?: (event: NativeSyntheticEvent<GyroEvent>) => void,
  children?: ReactNode
};

const GyroHandler = Platform.OS === 'ios' ? requireNativeComponent('RNGyroHandler') : View;
const REAGyroHandler = Animated.createAnimatedComponent(GyroHandler);

export { GyroHandler, REAGyroHandler, GyroHandlerProps };