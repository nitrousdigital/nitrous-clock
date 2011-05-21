package com.nitrousdigital.googleio.clock.client;

//import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;
//import com.google.gwt.widgetideas.graphics.client.ImageLoader;

public abstract class AbstractPixel {
	public double x;
	public double y;
	public PixelColor color;
	public Color paint;
//	private ImageElement pixelImg;
	public AbstractPixel(double x, double y, PixelColor color) {
		super();
		this.x = x;
		this.y = y;
		this.color = color;
		this.paint = new Color(color.getHtmlColor());
		
//		switch (color){
//		case DAY:
//			this.pixelImg = IOClock.BLUE;
//			break;
//		case OFF:
//			this.pixelImg = IOClock.GRAY;
//			break;
//		case SECOND:
//			this.pixelImg = IOClock.GREEN;
//			break;
//		case HOUR:
//			this.pixelImg = IOClock.LIGHT_BLUE;
//			break;
//		case COLON:
//			this.pixelImg = IOClock.LIGHT_GRAY;
//			break;
//		case MINUTE:
//			this.pixelImg = IOClock.RED;
//			break;
//		}
	}
	
	public abstract void move(int canvasWidth, int canvasHeight);
	
	public void paint(GWTCanvas canvas) {
		// image base implementation
//		canvas.drawImage(pixelImg, x-DigitPanel.CIRCLE_RADIUS, y-DigitPanel.CIRCLE_RADIUS);
		
		// paint based implementation
		canvas.beginPath();		
		canvas.setFillStyle(paint);
		canvas.arc(x, y, DigitPanel.CIRCLE_RADIUS, 0, 360, false);
		canvas.fill();
	}
	

}
