package com.reactnativesolutionsgyrohandler;

import android.app.usage.UsageEvents;
import android.content.Context;

import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;

import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.annotations.ReactPropGroup;
import com.facebook.react.uimanager.annotations.ReactPropertyHolder;
import android.view.MotionEvent;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactApplicationContext;
import android.util.Log;
import androidx.annotation.Nullable;
import com.facebook.react.bridge.WritableMap;
import android.view.ViewGroup;

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
//        this.start();
        super.onAttachedToWindow();
    }

    @Override
    protected void onLayout(boolean changed,
                            int l, int t, int r, int b) {
//        this.start();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        this.start();
        super.onDraw(canvas);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        System.out.println("qwe");
//        super.onTouchEvent(e);
//        WritableMap event = Arguments.createMap();
//            event.putDouble("x", 1.0);
//            event.putDouble("y", 1.0);
//            event.putDouble("z", 1.0);
//            event.putInt("target", getId());
//            event.putDouble("timestamp", System.currentTimeMillis());
//            sendEvent("onGyroChange", event);
//        return true;
//    }

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
//    @ReactProp(name = "onChangeEvent")
//    public void onChangeEvent(UsageEvents.Event event) {
//        System.out.println(event);
//    }

    private void sendEvent(String eventName, @Nullable WritableMap params)
    {
        try {
            ReactContext reactContext = (ReactContext)getContext();

            reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                    getId(),
                    eventName,
                    params
            );
        } catch (RuntimeException e) {
            Log.e("ERROR", "Some error");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
            WritableMap event = Arguments.createMap();
            long curTime = System.currentTimeMillis();
            i++;
            if ((curTime - lastUpdate) > interval) {
                i = 0;
                event.putDouble("x", sensorEvent.values[0]);
                event.putDouble("y",sensorEvent.values[1]);
                event.putDouble("z", sensorEvent.values[2]);
                event.putInt("target", getId());
                event.putDouble("timestamp", curTime);
//                sendEvent("onGyroChange", event);
                lastUpdate = curTime;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}