import * as React from 'react';
import { StyleSheet, View } from 'react-native';
import { REAGyroHandler } from '@react-native-solutions/gyro-handler';
import { Value, event, interpolate } from 'react-native-reanimated';

export default function App() {
  const translationX = new Value(0);
  const translationY = new Value(0);

  const onGyroEvent = event(
    [
      {
        nativeEvent: {
          x: translationX,
          y: translationY,
        },
      },
    ],
    {
      useNativeDriver: true,
    }
  );

  return (
    <View style={styles.container}>
      <REAGyroHandler
        style={{
          height: 100,
          width: '100%',
          backgroundColor: '#000',
          transform: [
            {
              translateX: interpolate(translationX, {
                inputRange: [-1, 1],
                outputRange: [-100, 100],
              }),
            },
            {
              translateY: interpolate(translationY, {
                inputRange: [-1, 1],
                outputRange: [-100, 100],
              }),
            },
          ],
        }}
        onGyroChange={onGyroEvent}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
