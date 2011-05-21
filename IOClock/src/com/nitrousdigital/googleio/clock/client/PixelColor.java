package com.nitrousdigital.googleio.clock.client;



public enum PixelColor {
	/**
	 * Blue Days #265897
	 */
	DAY(getDayColor(), "#265897"),// days
	
	/**
	 * Light Blue Hours #13acfa
	 */
	HOUR(getHourColor(), "#13acfa"), // hours
	
	/**
	 * Red minutes #c0000b
	 */
	MINUTE(getMinuteColor(), "#c0000b"), //minutes
	
	/**
	 * Green Seconds #009a49 
	 */
	SECOND(getSecondColor(), "#009a49"),// seconds
	
	/**
	 * Gray Off #d9d9d9
	 */
	OFF(getOffColor(), "#d9d9d9"),// off pixel
	
	/**
	 * Light Gray Colons #eeeeee
	 */
	COLON(getColonColor(), "#eeeeee"); // colons
	
	
	private String htmlColor;
	private PixelColor(String rgbHex, String defaultColor) {
		this.htmlColor = rgbHex == null ? defaultColor : rgbHex;
	}
	public String getHtmlColor() {
		return htmlColor;
	}
	
	private native static String getDayColor() /*-{
		return $wnd.dayColor;
	}-*/;
	private native static String getHourColor() /*-{
		return $wnd.hourColor;
	}-*/;
	private native static String getMinuteColor() /*-{
		return $wnd.minuteColor;
	}-*/;
	private native static String getSecondColor() /*-{
		return $wnd.secondColor;
	}-*/;
	private native static String getOffColor() /*-{
		return $wnd.offColor;
	}-*/;
	private native static String getColonColor() /*-{
		return $wnd.colonColor;
	}-*/;
}
