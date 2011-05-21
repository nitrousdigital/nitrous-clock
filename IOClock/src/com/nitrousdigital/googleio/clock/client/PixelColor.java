package com.nitrousdigital.googleio.clock.client;

import com.google.gwt.core.client.GWT;


public enum PixelColor {
	/**
	 * Blue Days #265897
	 */
	DAY("#265897", GWT.getHostPageBaseURL()+"images/blue.png"),// days
	
	/**
	 * Light Blue Hours #13acfa
	 */
	HOUR("#13acfa", GWT.getHostPageBaseURL()+"images/light_blue.png"), // hours
	
	/**
	 * Red minutes #c0000b
	 */
	MINUTE("#c0000b", GWT.getHostPageBaseURL()+"images/red.png"), //minutes
	
	/**
	 * Green Seconds #009a49 
	 */
	SECOND("#009a49", GWT.getHostPageBaseURL()+"images/green.png"),// seconds
	
	/**
	 * Gray Off #d9d9d9
	 */
	//OFF("#d9d9d9", GWT.getHostPageBaseURL()+"images/gray.png"),// off pixel
	OFF("#000000", GWT.getHostPageBaseURL()+"images/gray.png"),// off pixel
	
	/**
	 * Light Gray Colons #eeeeee
	 */
	COLON("#eeeeee", GWT.getHostPageBaseURL()+"images/light_gray.png"); // colons
	
	
	private String htmlColor;
	private String imgUrl;
	private PixelColor(String rgbHex, String imgUrl) {
		this.htmlColor = rgbHex;
		this.imgUrl = imgUrl;
	}
	public String getHtmlColor() {
		return htmlColor;
	}
	public String getImgUrl() {
		return imgUrl;
	}
}
