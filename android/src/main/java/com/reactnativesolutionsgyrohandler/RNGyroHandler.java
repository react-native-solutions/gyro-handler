package com.reactnativesolutionsgyrohandler;

import android.app.usage.UsageEvents;
import android.content.Context;

import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;

import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactApplicationContext;

import android.view.ViewGroup;

import com.facebook.react.uimanager.UIManagerHelper;

public class RNGyroHandler extends ViewGroup implements SensorEventListener {
    private SensorManager sensorManager;
    private ReactApplicationContext mReactContext;
    private Context context;
    private Sensor sensor;
    private long lastUpdate = 0;
    private int i = 0;
    private int interval = 1;


    public RNGyroHandler(Context context, ReactApplicationContext reactContext) {
        super(context);
        this.context = context;
        this.mReactContext = reactContext;
    }

    @Override
    protected void onAttachedToWindow() {
        System.out.println("onAttachedToWindow");
        super.onAttachedToWindow();
    }

    @Override
    protected void onLayout(boolean changed,
                            int l, int t, int r, int b) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.start();
        super.onDraw(canvas);
    }
    @Override
    protected void onDetachedFromWindow() {
        System.out.println("onDetachedFromWindow");
        this.stop();
        super.onDetachedFromWindow();
    }

    public void start() {
        this.sensorManager = (SensorManager) getContext().getSystemService(this.mReactContext.SENSOR_SERVICE);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void stop() {
        this.sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
            long curTime = System.currentTimeMillis();
            i++;
            if ((curTime - lastUpdate) > interval) {
                i = 0;
                ReactContext reactContext = (ReactContext)getContext();
                reactContext
                        .getNativeModule(UIManagerModule.class)
                        .getEventDispatcher()
                        .dispatchEvent(
                            new RNGyroHandlerEvent(
                                getId(),
                                sensorEvent.values[0],
                                sensorEvent.values[1],
                                sensorEvent.values[2],
                                System.currentTimeMillis()
                            )
                        );
                lastUpdate = curTime;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}