package com.nitrousdigital.googleio.clock.client;


public class Pixel extends AbstractPixel {
	private static int[] SIN_TABLE, COS_TABLE;
	private int vx, vy;
	private int gravity = 1;
	
	static {
		SIN_TABLE = new int[360];
		COS_TABLE = new int[360];
		for (int i = 0; i < 360; i++) {
			double angle = 2*Math.PI*i/360;
			SIN_TABLE[i] = (int)(256 * Math.sin(angle));
			COS_TABLE[i] = (int)(256 * Math.cos(angle));
		}
	}	
	
	public double dx;
	public double dy;
	public boolean isDead = false;
	
	private static final int MIN_VELOCITY = 1;
	
	public Pixel(double x, double y, PixelColor color) {
		super(x,y,color);
		this.dx = (Math.random() * 30 * 2) - 30;
		this.dy = (Math.random() * 30 * 2) - 30;
		int angle = (0 + 450 - 90/2 + (int)(Math.random() * 90)) % 360;
		if (angle < 30) {
			angle = 45;
		} else if (angle > 330) {
			angle = 315;
		}
		
		int speed = 10;
		this.vx = ((COS_TABLE[angle] * speed) >> 8);
		this.vy = -((SIN_TABLE[angle] * speed) >> 8);
		
		if (vx < MIN_VELOCITY && vx >= 0) {
			vx = MIN_VELOCITY;
		} else if (vx < 0 && vx > -MIN_VELOCITY) {
			vx = -MIN_VELOCITY;
		}
	}
	
	public void move(int canvasWidth, int canvasHeight) {
		x += vx;
		y += vy;
		vy += gravity;
		
		if (y + DigitPanel.CIRCLE_RADIUS > canvasHeight) {
			y = canvasHeight - DigitPanel.CIRCLE_RADIUS;
			vy = -(vy/2);
		}
		
		if (x < 0 || x > canvasWidth) {
			isDead = true;
		}
	}
	
}

