package com.nitrousdigital.googleio.clock.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.widgetideas.graphics.client.ImageLoader;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IOClock implements EntryPoint {
//	private static final String[] IMG_URLS = {
//		PixelColor.DAY.getImgUrl(),
//		PixelColor.OFF.getImgUrl(),
//		PixelColor.SECOND.getImgUrl(),
//		PixelColor.HOUR.getImgUrl(),
//		PixelColor.COLON.getImgUrl(),
//		PixelColor.MINUTE.getImgUrl()
//	};
//
//	public static ImageElement BLUE;
//	public static ImageElement GRAY;
//	public static ImageElement GREEN;
//	public static ImageElement LIGHT_BLUE;
//	public static ImageElement LIGHT_GRAY;
//	public static ImageElement RED;
//	
//	private Label message;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
//		message = new Label("Loading Images, Please Wait...");
//		RootPanel.get().add(message);
//		
//		ImageLoader.loadImages(IMG_URLS, new ImageLoader.CallBack() {
//			@Override
//			public void onImagesLoaded(ImageElement[] imageElements) {
//				BLUE = imageElements[0];
//				GRAY = imageElements[1];
//				GREEN = imageElements[2];
//				LIGHT_BLUE = imageElements[3];
//				LIGHT_GRAY= imageElements[4];
//				RED = imageElements[5];
				onStart();
//			}
//		});
	}
	private void onStart() {
//		RootPanel.get().remove(message);
		
		ClockPanel panel = new ClockPanel();
		
		// determine target date from URL arguments
		Date targetDate = getTargetDate();
		CountDown count = new CountDown(targetDate);
		panel.setValue(count);
		
		Ticker ticker = new Ticker(count, panel);
		ticker.start();
		
		RootPanel.get().add(panel.getWidget());
	}

	/**
	 * Retrieve the countdown target date from either the URL argument 'targetdate' or the JavaScript global variable 'targetdate'.
	 * The value of the argument is expected to be in the format "MMddyyyyHHmm"
	 * If both are undefined, then a default date is returned.
	 * @return The countdown target date
	 */
	private Date getTargetDate() {
		String dateStr = null;
		Map<String, List<String>> args = Window.Location.getParameterMap();
		if (args != null) {
			List<String> dates = args.get("targetdate");
			if (dates != null && dates.size() > 0) {
				 dateStr = dates.get(0); 
			}
		}
		if (dateStr == null) {
			dateStr = getNativeDate();
		}
		if (dateStr == null || dateStr.trim().length() == 0) {
			dateStr = "051020110900";
		}
		
		DateTimeFormat format = DateTimeFormat.getFormat("MMddyyyyHHmm");
		return format.parse(dateStr);
	}
	
	private native String getNativeDate()/*-{
		if ($wnd.targetdate) {
			return $wnd.targetdate;
		} else {
			return null;
		}
	}-*/;
}
