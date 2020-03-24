package com.reactnativesolutionsgyrohandler;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class RNGyroHandlerEvent extends Event<RNGyroHandlerEvent> {

    public static final String EVENT_NAME = "onGyroChange";

    private final double x;
    private final double y;
    private final double z;
    private final double timestamp;

    public RNGyroHandlerEvent(int viewId, double x, double y, double z, double timestamp) {
        super(viewId);
        this.x = x;
        this.y = y;
        this.z = z;
        this.timestamp = timestamp;

    }

    public Double getX() {
        return this.x;
    }
    public Double getY() {
        return this.y;
    }
    public Double getZ() {
        return this.z;
    }
    public Double getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }

    @Override
    public short getCoalescingKey() {
        // All checkbox events for a given view can be coalesced.
        return 0;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        eventData.putInt("target", getViewTag());
        eventData.putDouble("x", getX());
        eventData.putDouble("y", getY());
        eventData.putDouble("z", getZ());
        eventData.putDouble("timestamp", getTimestamp());
        return eventData;
    }
}