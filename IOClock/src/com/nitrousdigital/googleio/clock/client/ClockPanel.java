package com.nitrousdigital.googleio.clock.client;

import java.util.Vector;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class ClockPanel {
	private Canvas canvas;
	
	private DigitPanel dayHundreds;
	private DigitPanel dayTens;
	private DigitPanel dayUnits;
	
	private ColonPanel dayHourSep;
	
	private DigitPanel hourTens;
	private DigitPanel hourUnits;
	
	private ColonPanel hourMinSep;
	
	private DigitPanel minuteTens;
	private DigitPanel minuteUnits;
	
	private ColonPanel minSecSep;
	
	private DigitPanel secondTens;
	private DigitPanel secondUnits;

	private DigitPanel[] digitPanels;
	
	private Vector<Pixel> movingPixels;
	private Vector<TempPixel> tempPixels;
	
	private static final int MIN_WIDTH = (int)(DigitPanel.CIRCLE_RADIUS +
			((DigitPanel.WIDTH+20) * 6) +
			(DigitPanel.WIDTH * 3) + 
			(ColonPanel.WIDTH * 3));
	
	private static final int MIN_HEIGHT = DigitPanel.HEIGHT * 2;
	
	public ClockPanel() {
		movingPixels = new Vector<Pixel>();
		
		dayHundreds = new DigitPanel();
		dayHundreds.setColor(new Color("#265897"));
		dayTens = new DigitPanel();
		dayTens.setColor(new Color("#265897"));
		dayUnits = new DigitPanel();
		dayUnits.setColor(new Color("#265897"));
		
		dayHourSep = new ColonPanel();

		hourTens = new DigitPanel();
		hourTens.setColor(new Color("#13acfa"));
		hourUnits = new DigitPanel();
		hourUnits.setColor(new Color("#13acfa"));

		hourMinSep = new ColonPanel();
		
		minuteTens = new DigitPanel();
		minuteTens.setColor(new Color("#c0000b"));
		minuteUnits = new DigitPanel();
		minuteUnits.setColor(new Color("#c0000b"));
		
		minSecSep = new ColonPanel();
		
		secondTens = new DigitPanel();
		secondTens.setColor(new Color("#009a49"));
		secondUnits = new DigitPanel();
		secondUnits.setColor(new Color("#009a49"));
		
		digitPanels = new DigitPanel[]{
				dayHundreds, dayTens, dayUnits, hourTens, hourUnits, minuteTens, minuteUnits, secondTens, secondUnits
		};
		
		onLayout();
		Window.addResizeHandler(new ResizeHandler(){
			public void onResize(ResizeEvent event) {
				onLayout();
			}
		});
	}
	
	private int canvasWidth = -1;
	private int canvasHeight = -1;
	public void onLayout() {
		int clientWidth = Window.getClientWidth();
		int clientHeight = Window.getClientHeight();
		if (clientWidth < MIN_WIDTH) {
			clientWidth = MIN_WIDTH;
		} else {
			clientWidth-=20;
		}
		if (clientHeight < MIN_HEIGHT) {
			clientHeight = MIN_HEIGHT;
		} else {
			clientHeight-=20;
		}
		if (clientWidth == canvasWidth && clientHeight == canvasHeight) {
			return;
		}
		
		if (canvas == null) {
			// initial layout
			canvas = new Canvas(clientWidth, clientHeight);
			canvas.setListener(new MouseListener(){
				public void onMouseDown(Widget sender, int x, int y) {
					handleMouseDown(x, y);
				}
				public void onMouseEnter(Widget sender) {
				}
				public void onMouseLeave(Widget sender) {
				}
				public void onMouseMove(Widget sender, int x, int y) {
				}
				public void onMouseUp(Widget sender, int x, int y) {
					endMessageAnim();
				}
			});	
		} else {
			canvas.resize(clientWidth, clientHeight);
			
			// ensure pac-man vertical position is updated
			if (pac != null) {
				pac.setY(clientHeight - pac.getRadius());
			}
		}
		
		int x = (int)(((clientWidth - MIN_WIDTH) / 2) + DigitPanel.CIRCLE_RADIUS);
		int y = (clientHeight / 2) - DigitPanel.HEIGHT;
		
		int blockWidth = DigitPanel.WIDTH+20;
		
		dayHundreds.setPosition(x, y);
		x+=blockWidth;
		dayTens.setPosition(x, y);
		x+=blockWidth;
		dayUnits.setPosition(x, y);
		x+=DigitPanel.WIDTH;
		
		dayHourSep.setPosition(x, y);
		x+=ColonPanel.WIDTH;
		
		hourTens.setPosition(x, y);
		x+=blockWidth;
		hourUnits.setPosition(x, y);
		x+=DigitPanel.WIDTH;
		hourMinSep.setPosition(x, y);
		x+=ColonPanel.WIDTH;
		
		minuteTens.setPosition(x, y);
		x+=blockWidth;
		minuteUnits.setPosition(x, y);
		x+=DigitPanel.WIDTH;
		
		minSecSep.setPosition(x, y);
		x+=ColonPanel.WIDTH;
		
		secondTens.setPosition(x, y);
		x+=blockWidth;
		secondUnits.setPosition(x, y);
		
		this.canvasWidth = clientWidth;
		this.canvasHeight = clientHeight;
	}
	
	private PacMan pac = null;
	private void handleMouseDown(int x, int y) {
		if (dayHourSep.isPacTrigger(x, y) || hourMinSep.isPacTrigger(x, y) || minSecSep.isPacTrigger(x, y)) {
			if (pac == null) {
				pac = new PacMan();	
				pac.setY(canvasHeight - pac.getRadius());
			}
		} else if (dayHourSep.isMessageTrigger(x, y) || hourMinSep.isMessageTrigger(x, y) || minSecSep.isMessageTrigger(x, y)) {
			if (tempPixels == null) {
				startMorphAnim();
			}
		}
	}
	private void endMessageAnim() {
		if (tempPixels != null) {
			tempPixels.clear();
			tempPixels = null;
			
			movingPixels.clear();
		}
	}
	private void addDyingPixels(Pixel[] dyingPixels) {
		if (dyingPixels == null || dyingPixels.length == 0) {
			return;
		}
		for (Pixel p : dyingPixels) {
			movingPixels.add(p);
		}
	}
	
	private CountDown count = null;
	public void setValue(CountDown count) {
		// update days-hundreds
		int oldValue = dayHundreds.getValue();
		int days = count.getDays();
		int newValue = days / 100;
		dayHundreds.setValue(newValue);		
		days %= 100;
		if (this.count != null && oldValue != newValue) {
			Pixel[] dyingPixels = dayHundreds.getDyingPixels(oldValue, newValue);
			addDyingPixels(dyingPixels);
		}		
		// update days-tens
		newValue = days / 10;
		oldValue = dayTens.getValue();
		dayTens.setValue(newValue);
		if (this.count != null && oldValue != newValue) {
			Pixel[] dyingPixels = dayTens.getDyingPixels(oldValue, newValue);
			addDyingPixels(dyingPixels);
		}		
		// update days-units
		newValue = days % 10;
		oldValue = dayUnits.getValue();
		dayUnits.setValue(newValue);
		if (this.count != null && oldValue != newValue) {
			Pixel[] dyingPixels = dayUnits.getDyingPixels(oldValue, newValue);
			addDyingPixels(dyingPixels);
		}
		
		
		// update hour-tens
		int hours = count.getHours();
		newValue = hours / 10;
		oldValue = hourTens.getValue();
		hourTens.setValue(newValue);
		if (this.count != null && oldValue != newValue) {
			Pixel[] dyingPixels = hourTens.getDyingPixels(oldValue, newValue);
			addDyingPixels(dyingPixels);
		}		
		// update hour-units
		newValue = hours % 10;
		oldValue = hourUnits.getValue();
		hourUnits.setValue(newValue);
		if (this.count != null && oldValue != newValue) {
			Pixel[] dyingPixels = hourUnits.getDyingPixels(oldValue, newValue);
			addDyingPixels(dyingPixels);
		}
		
		// update minute-tens
		int minutes = count.getMinutes();
		newValue = minutes / 10;
		oldValue = minuteTens.getValue();
		minuteTens.setValue(newValue);
		if (this.count != null && oldValue != newValue) {
			Pixel[] dyingPixels = minuteTens.getDyingPixels(oldValue, newValue);
			addDyingPixels(dyingPixels);
		}
		// update minute-units
		oldValue = minuteUnits.getValue();
		newValue = minutes % 10;
		minuteUnits.setValue(newValue);
		if (this.count != null && oldValue != newValue) {
			Pixel[] dyingPixels = minuteUnits.getDyingPixels(oldValue, newValue);
			addDyingPixels(dyingPixels);
		}
		
		// update second-tens
		int seconds = count.getSeconds();
		newValue = seconds / 10;
		oldValue = secondTens.getValue();
		secondTens.setValue(newValue);
		if (this.count != null && oldValue != newValue) {
			Pixel[] dyingPixels = secondTens.getDyingPixels(oldValue, newValue);
			addDyingPixels(dyingPixels);
		}
		
		// update second-units
		newValue = seconds % 10;
		oldValue = secondUnits.getValue();
		secondUnits.setValue(newValue);
		if (this.count != null && oldValue != newValue) {
			Pixel[] dyingPixels = secondUnits.getDyingPixels(oldValue, newValue);
			addDyingPixels(dyingPixels);
		}
		this.count = count;
	}
	
	/**
	 * Update moving objects
	 */
	public void animate() {
		// move or hide pac-man
		if (pac != null) {
			pac.move();
			if (pac.getX() > canvasWidth) {
				pac = null;
			}
		}
		
		if (pac != null) {
			// move/hide/eat pixels
			for (int i = movingPixels.size()-1; i>=0; i--) {
				Pixel p = movingPixels.get(i);
				if (pac.isOver(p)) {
					// eat pixel
					movingPixels.remove(i);
				} else {
					p.move(canvasWidth, canvasHeight);
					if (pac.isOver(p)) {
						// eat pixel
						movingPixels.remove(i);
					} else if (p.isDead) {
						movingPixels.remove(i);
					}
				}
			}
		} else {
			// pac-man not present
			for (int i = movingPixels.size()-1; i>=0; i--) {
				Pixel p = movingPixels.get(i);
				p.move(canvasWidth, canvasHeight);
				if (p.isDead) {
					movingPixels.remove(i);
				}
			}
		}	
		
		if (tempPixels != null) {
			for (TempPixel p : tempPixels) {
				p.move(canvasWidth, canvasHeight);
			}
		}
	}
	
	private void startMorphAnim() {
		Vector<Pixel> sourcePixels = new Vector<Pixel>();
		for (DigitPanel panel : digitPanels) {
			Pixel[] pixels = panel.getColoredPixels();
			for (Pixel p : pixels) {
				sourcePixels.add(p);
			}
		}
		sourcePixels.addAll(movingPixels);
		int srcIdx = 0;
		
		int blockSize = ((int)((DigitPanel.CIRCLE_RADIUS * 2) +  20));
		int messageWidth = MESSAGE_PIXELS[0].length * blockSize;
		int messageHeight = MESSAGE_PIXELS.length * blockSize;
		int left = (canvasWidth - messageWidth) / 2;
		int top = (canvasHeight - messageHeight) / 2;
		Vector<TempPixel> tempPixels = new Vector<TempPixel>();
		for (int row = 0; row < MESSAGE_PIXELS.length; row++) {
			for (int col = 0 ; col < MESSAGE_PIXELS[row].length; col++) {
				if (MESSAGE_PIXELS[row][col] != 0) {
					int x = left + (col * blockSize);
					int y = top + (row * blockSize);
					Pixel srcPixel = sourcePixels.get(srcIdx++ % sourcePixels.size());
					TempPixel temp = new TempPixel(srcPixel.x, srcPixel.y, x, y, srcPixel.color);
					tempPixels.add(temp);
				}
			}
		}
		this.tempPixels = tempPixels;
	}
	
	public void paint() {
		canvas.clear();

		if (tempPixels == null) {
			dayHundreds.paint(canvas);
			dayTens.paint(canvas);
			dayUnits.paint(canvas);
			dayHourSep.paint(canvas);
			hourTens.paint(canvas);
			hourUnits.paint(canvas);
			hourMinSep.paint(canvas);
			minuteTens.paint(canvas);
			minuteUnits.paint(canvas);
			minSecSep.paint(canvas);
			secondTens.paint(canvas);
			secondUnits.paint(canvas);
			
			for (Pixel p : movingPixels) {
				p.paint(canvas);
			}
		} else {
			for (TempPixel p : tempPixels) {
				p.paint(canvas);
			}
		}
		
		if (pac != null) {
			pac.paint(canvas);
		}
	}
	
	public Widget getWidget() {
		return canvas;
	}
	
	private int[][] MESSAGE_PIXELS = {
			{0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1},
			{0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0},
			{0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1},
			{0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
			{0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
			{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1}
	};
}

