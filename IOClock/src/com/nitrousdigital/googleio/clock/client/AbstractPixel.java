package com.nitrousdigital.googleio.clock.client;

import com.nitrousdigital.googleio.clock.client.canvas.Canvas;
import com.nitrousdigital.googleio.clock.client.canvas.Color;

public abstract class AbstractPixel {
	public double x;
	public double y;
	public PixelColor color;
	public Color paint;
	public AbstractPixel(double x, double y, PixelColor color) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
		this.paint = new Color(color.getHtmlColor());
	}
	
	public abstract void move(int canvasWidth, int canvasHeight);
	
	public void paint(Canvas canvas) {
		// paint based implementation
		canvas.beginPath();		
		canvas.setFillStyle(paint);
		canvas.arc(x, y, DigitPanel.CIRCLE_RADIUS, 0, 360, false);
		canvas.fill();
	}
	

}
