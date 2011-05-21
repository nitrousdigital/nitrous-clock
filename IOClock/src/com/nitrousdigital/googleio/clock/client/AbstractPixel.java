package com.nitrousdigital.googleio.clock.client;

import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public abstract class AbstractPixel {
	public double x;
	public double y;
	public Color color;
	public AbstractPixel(double x, double y, Color color) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public abstract void move(int canvasWidth, int canvasHeight);
	
	public void paint(GWTCanvas canvas) {
		canvas.beginPath();		
		canvas.setFillStyle(color);
		canvas.arc(x, y, DigitPanel.CIRCLE_RADIUS, 0, 360, false);
		canvas.fill();
	}
	

}
