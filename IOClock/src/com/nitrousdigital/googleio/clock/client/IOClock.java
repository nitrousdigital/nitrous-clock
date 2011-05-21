package com.nitrousdigital.googleio.clock.client;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class IOClock implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		ClockPanel panel = new ClockPanel();
		
		// determine target date from URL arguments
		Date targetDate = getTargetDate();
		CountDown count = new CountDown(targetDate);
		panel.setValue(count);
		
		Ticker ticker = new Ticker(count, panel);
		ticker.start();
		
		VerticalPanel background = new VerticalPanel();
		background.setStyleName("canvas-background");
		background.setWidth("100%");
		background.setHeight("100%");
		background.add(panel.getWidget());
		RootPanel.get().add(background);
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
