package com.reactnativesolutionsgyrohandler;

import androidx.annotation.Nullable;

import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ThemedReactContext;
import java.util.Map;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.views.scroll.ScrollEventType;

public class GyroHandlerModule extends SimpleViewManager<RNGyroHandler>  {

	private ReactApplicationContext mReactContext;

	public GyroHandlerModule(ReactApplicationContext reactContext) {
		this.mReactContext = reactContext;
	}

	@Override
	public @Nullable Map getExportedCustomDirectEventTypeConstants() {
		return MapBuilder.<String, Object>builder()
			.put(
				"onGyroChange",
				MapBuilder.of("registrationName", "onGyroChange")
			).build();
	}

	@Override
	public String getName() {
			return "RNGyroHandler";
	}

	protected RNGyroHandler createViewInstance(ThemedReactContext reactContext) {
		return new RNGyroHandler(reactContext, this.mReactContext);
	}
}