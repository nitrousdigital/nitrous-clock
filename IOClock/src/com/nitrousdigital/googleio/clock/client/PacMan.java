package com.nitrousdigital.googleio.clock.client;

import com.nitrousdigital.googleio.clock.client.canvas.Canvas;
import com.nitrousdigital.googleio.clock.client.canvas.Color;

public class PacMan {
	private int x;
	private int y;
	private int radius = (int)(DigitPanel.CIRCLE_RADIUS * 2);
	private int mouthSize = 0;
	private int maxMouthSize = 6; 
	private boolean mouthOpening = true;
	public PacMan() {
		this.x = -radius;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getRadius() {
		return radius;
	}
	public void paint(Canvas canvas) {
		canvas.beginPath();		
		canvas.setFillStyle(Color.YELLOW);
		canvas.setStrokeStyle(Color.BLACK);
		
		canvas.arc(x, y, radius, 0, 360, false);		
		canvas.stroke();
		canvas.fill();
		
		canvas.beginPath();
		canvas.arc(x + (radius / 2.0D), y - (radius / 2.0D), 2D, 0D, 360D, false);
		canvas.stroke();
		
		
		if (mouthSize > 0) {
			canvas.beginPath();
			canvas.setFillStyle(Color.BLACK);
			canvas.moveTo(x,y);
			canvas.lineTo(x + radius, y - mouthSize);
			canvas.lineTo(x + radius, y + mouthSize);
			canvas.closePath();
			canvas.fill();
		}
	}
	public void move() {
		x += 5;
		if (mouthOpening) {
			mouthSize++;
			if (mouthSize == maxMouthSize) {
				mouthOpening = false;
			}
		} else {
			mouthSize--;
			if (mouthSize == 0) {
				mouthOpening = true;
			}
		}
	}
	public int getX() {
		return x;
	}
	public int getMinX() {
		return x - radius;
	}
	public boolean isOver(Pixel pixel) {
		int minX = x - radius;
		int minY = y - radius;
		int maxX = x + radius;
		int maxY = y + radius;
		return (minX < pixel.x && maxX > pixel.x && minY < pixel.y && maxY > pixel.y); 
	}
}
