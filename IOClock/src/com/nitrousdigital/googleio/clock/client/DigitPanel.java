package com.nitrousdigital.googleio.clock.client;

import java.util.ArrayList;

import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;


public class DigitPanel {
	public static final double CIRCLE_RADIUS = 7;
	public static final double SPACE = 5;
	public static final double STEP_X = (CIRCLE_RADIUS * 2) + SPACE;
	public static final double STEP_Y = STEP_X;
	public static final int WIDTH = (int)(CIRCLE_RADIUS * 2 + SPACE) * 4;
	public static final int HEIGHT = (int)(CIRCLE_RADIUS * 2 + SPACE) * 7;
	
	private Color color = DEFAULT_COLOR;
	private static final Color DEFAULT_COLOR = Color.BLACK;
	
	private Color emptyColor = new Color("#d9d9d9");
	
	private int value;
	private int panelX;
	private int panelY;
	
	public DigitPanel() {
	}
	
	public void setPosition(int panelX, int panelY) {
		this.panelX = panelX;
		this.panelY = panelY;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	private static int[][] getShape(int value) {
		int[][] shape = null;
		switch(value) {
		case 0:
			shape = ZERO;
			break;
		case 1:
			shape = ONE;
			break;
		case 2:
			shape = TWO;
			break;
		case 3:
			shape = THREE;
			break;
		case 4:
			shape = FOUR;
			break;
		case 5: 
			shape = FIVE;
			break;
		case 6:
			shape = SIX;
			break;
		case 7:
			shape = SEVEN;
			break;
		case 8 :
			shape = EIGHT;
			break;
		case 9:
			shape = NINE;
			break;
		}
		return shape;
	}
	
	public void paint(GWTCanvas canvas) {
		paint(canvas, panelX, panelY);
	}
	
	private void paint(GWTCanvas canvas, double x, double y) {
		int[][] shape = getShape(value);
		if (shape != null) {
			paint(shape, canvas, x, y);
		}
	}
	private void paint(int[][] shape, GWTCanvas canvas, double panelX, double panelY) {
		for (int row = 0 ; row < shape.length; row++) {
			double y = panelY + (STEP_Y * row);
			for (int col = 0 ; col < shape[row].length; col++) {
				double x = panelX + (STEP_X * col);
				canvas.beginPath();
				canvas.arc(x, y, CIRCLE_RADIUS, 0, 360, false);
				Color paintColor = emptyColor;
				if (shape[row][col] != 0) {
					if (color != null) {
						paintColor = color;
					} else {
						paintColor = DEFAULT_COLOR;
					}
				} else {
					paintColor = emptyColor;
				}
				canvas.setStrokeStyle(paintColor);
				canvas.setFillStyle(paintColor);
				canvas.fill();
			}
		}
	}
	
	public Pixel[] getColoredPixels() {
		int[][] bits = getShape(value);
		ArrayList<Pixel> pixels = new ArrayList<Pixel>();
		for (int row = 0; row < bits.length; row++) {
			for (int col = 0 ; col < bits[row].length; col++) {
				if (bits[row][col] != 0) {
					// found a colored pixel
					double y = panelY + (STEP_Y * row);
					double x = panelX + (STEP_X * col);
					Pixel pixel = new Pixel(x, y, color != null ? color : DEFAULT_COLOR);
					pixels.add(pixel);
				}
			}
		}
		return pixels.toArray(new Pixel[pixels.size()]);
	}
	
	public Pixel[] getDyingPixels(int oldValue, int newValue) {
		if (oldValue != newValue) {
			int[][] oldBits = getShape(oldValue);
			int[][] newBits = getShape(newValue);
			if (oldBits != null && newBits != null) {
				ArrayList<Pixel> pixels = new ArrayList<Pixel>();
				for (int row = 0; row < oldBits.length; row++) {
					for (int col = 0 ; col < oldBits[row].length; col++) {
						if (oldBits[row][col] != 0 && newBits[row][col] == 0) {
							// found a dying pixel
							double y = panelY + (STEP_Y * row);
							double x = panelX + (STEP_X * col);
							Pixel pixel = new Pixel(x, y, color != null ? color : DEFAULT_COLOR);
							pixels.add(pixel);
						}
					}
				}
				return pixels.toArray(new Pixel[pixels.size()]);
			}
		}
		return new Pixel[0];
	}
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	private static final int [][] ZERO = {
		{1,1,1,1},
		{1,0,0,1},
		{1,0,0,1},
		{1,0,0,1},
		{1,0,0,1},
		{1,0,0,1},
		{1,1,1,1}
	};
	private static final int [][] ONE = {
		{0,0,0,1},
		{0,0,0,1},
		{0,0,0,1},
		{0,0,0,1},
		{0,0,0,1},
		{0,0,0,1},
		{0,0,0,1}
	};
	private static final int [][] TWO = {
		{1,1,1,1},
		{0,0,0,1},
		{0,0,0,1},
		{1,1,1,1},
		{1,0,0,0},
		{1,0,0,0},
		{1,1,1,1}
	};
	private static final int [][] THREE = {
		{1,1,1,1},
		{0,0,0,1},
		{0,0,0,1},
		{1,1,1,1},
		{0,0,0,1},
		{0,0,0,1},
		{1,1,1,1}
	};
	private static final int [][] FOUR = {
		{1,0,0,1},
		{1,0,0,1},
		{1,0,0,1},
		{1,1,1,1},
		{0,0,0,1},
		{0,0,0,1},
		{0,0,0,1}
	};
	private static final int [][] FIVE = {
		{1,1,1,1},
		{1,0,0,0},
		{1,0,0,0},
		{1,1,1,1},
		{0,0,0,1},
		{0,0,0,1},
		{1,1,1,1}
	};
	private static final int [][] SIX = {
		{1,1,1,1},
		{1,0,0,0},
		{1,0,0,0},
		{1,1,1,1},
		{1,0,0,1},
		{1,0,0,1},
		{1,1,1,1}
	};
	private static final int [][] SEVEN = {
		{1,1,1,1},
		{0,0,0,1},
		{0,0,0,1},
		{0,0,0,1},
		{0,0,0,1},
		{0,0,0,1},
		{0,0,0,1}
	};
	private static final int [][] EIGHT = {
		{1,1,1,1},
		{1,0,0,1},
		{1,0,0,1},
		{1,1,1,1},
		{1,0,0,1},
		{1,0,0,1},
		{1,1,1,1}
	};
	private static final int [][] NINE = {
		{1,1,1,1},
		{1,0,0,1},
		{1,0,0,1},
		{1,1,1,1},
		{0,0,0,1},
		{0,0,0,1},
		{1,1,1,1}
	};	
}
