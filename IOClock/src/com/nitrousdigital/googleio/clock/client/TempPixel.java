package com.nitrousdigital.googleio.clock.client;

import com.google.gwt.widgetideas.graphics.client.Color;

public class TempPixel extends AbstractPixel {
	private double targetX;
	private double targetY;
	
	public TempPixel(double startX, double startY, double endX, double endY, Color color) {
		super(startX, startY, color);
		this.targetX = endX;
		this.targetY = endY;
	}

	private static final int STEP = 10;
	public void move(int canvasWidth, int canvasHeight) {
		if (this.x < targetX) {
			this.x+=STEP;
			if (x > targetX) {
				x = targetX;
			}
		} else if (x > targetX) {
			this.x-=STEP;
			if (x < targetX) {
				x = targetX;
			}
		}
		if (this.y > targetY) {
			this.y-=STEP;
			if (y < targetY) {
				y = targetY;
			}
		} else if (this.y < targetY) {
			y+=STEP;
			if (y > targetY) {
				y = targetY;
			}
		}
	}
}
