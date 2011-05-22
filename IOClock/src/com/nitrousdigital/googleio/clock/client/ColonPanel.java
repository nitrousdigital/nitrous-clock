package com.nitrousdigital.googleio.clock.client;

import com.nitrousdigital.googleio.clock.client.canvas.Canvas;
import com.nitrousdigital.googleio.clock.client.canvas.Color;

public class ColonPanel {
	public static final int WIDTH = (int)(DigitPanel.STEP_X * 3);
	public static final int HEIGHT = DigitPanel.HEIGHT;
	private int panelX;
	private int panelY;
	private Color color;
	public ColonPanel() {
		this.color = new Color(PixelColor.COLON.getHtmlColor());
	}
	public void setPosition(int panelX, int panelY) {
		this.panelX = panelX;
		this.panelY = panelY;
	}
	
	/**
	 * 
	 * @return True if the specified co-ordinates are within the circles painted by this ColonPanel
	 */
	public boolean isPacTrigger(int x, int y) {
		return isOverSpot(2,1,x,y);
	}
	
	public boolean isMessageTrigger(int x, int y) {
		return isOverSpot(4,1,x,y);
	}
	
	private boolean isOverSpot(int row, int col, int x, int y) {
		int centerX = (int)(panelX + (DigitPanel.STEP_X * col));
		int centerY = (int)(panelY + (DigitPanel.STEP_Y * row));
		int minX = (int)(centerX - DigitPanel.CIRCLE_RADIUS);
		int minY = (int)(centerY - DigitPanel.CIRCLE_RADIUS);
		int maxX = (int)(centerX + DigitPanel.CIRCLE_RADIUS);
		int maxY = (int)(centerY + DigitPanel.CIRCLE_RADIUS);
		return x >= minX && x <= maxX && y >= minY && y<= maxY;
	}
	
	
	public void paint(Canvas canvas) {
		paint(canvas, panelX, panelY);
	}
	
	private void paint(Canvas canvas, double panelX, double panelY) {
		paint(2,1, canvas, panelX, panelY);
		paint(4,1, canvas, panelX, panelY);
	}
	
	private void paint(int row, int col, Canvas canvas, double panelX, double panelY) {
		double x = panelX + (DigitPanel.STEP_X * col);
		double y = panelY + (DigitPanel.STEP_Y * row);
		canvas.beginPath();
		canvas.setFillStyle(color);
		canvas.setStrokeStyle(color);
		canvas.arc(x, y, DigitPanel.CIRCLE_RADIUS, 0, 360, false);
		canvas.fill();
	}
}
