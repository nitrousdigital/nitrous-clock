package com.nitrousdigital.googleio.clock.client.canvas;

import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.FillStrokeStyle;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MouseListener;

public class Canvas extends Composite {
	 private MouseListener listener;

	 	private com.google.gwt.canvas.client.Canvas impl;
	 	private int width;
	 	private int height;
	    public Canvas(int width, int height) {
	    	impl = com.google.gwt.canvas.client.Canvas.createIfSupported();
	    	initWidget(impl);
	    	setSize(width, height);
	    	impl.addMouseDownHandler(new MouseDownHandler(){
				public void onMouseDown(MouseDownEvent event) {
					if (listener != null) {
			            int x = event.getX();
			            int y = event.getY();
			            listener.onMouseDown(impl, x, y);
					}
				}
	    	});
	    	impl.addMouseUpHandler(new MouseUpHandler(){
				public void onMouseUp(MouseUpEvent event) {
					if (listener != null) {
			            int x = event.getX();
			            int y = event.getY();
			            listener.onMouseUp(impl, x, y);
					}
				}
	    	});
	    	impl.addMouseMoveHandler(new MouseMoveHandler(){
				public void onMouseMove(MouseMoveEvent event) {
					if (listener != null) {
			            int x = event.getX();
			            int y = event.getY();
			            listener.onMouseMove(impl, x, y);
					}
				}
	    	});
	    }

	    
	    /**
	     * Clears the entire canvas.
	     */
	    public void clear() {
	    	impl.getContext2d().clearRect(0, 0, width, height);
	    }
	    
	    public void beginPath() {
	    	impl.getContext2d().beginPath();
	    }
	    /**
	     * Draws an arc. If a current subpath exists, a line segment is added from the
	     * current point to the starting point of the arc. If {@code endAngle -
	     * startAngle} is equal to or greater than {@code 2 * Math.Pi}, the arc is the
	     * whole circumference of the circle.
	     *
	     * @param x the x coordinate of the center of the arc
	     * @param y the y coordinate of the center of the arc
	     * @param radius the radius of the arc
	     * @param startAngle the start angle, measured in radians clockwise from the
	     *          positive x-axis
	     * @param endAngle the end angle, measured in radians clockwise from the
	     *          positive x-axis
	     */
	    public void arc(double x, double y, double radius, double startAngle, double endAngle){
	    	impl.getContext2d().arc(x, y, radius, startAngle, endAngle);
	    }
	    
	    /**
	     * Convenience method to set the context's fillStyle to a {@link CssColor},
	     * specified in String form.
	     *
	     * @param fillStyleColor the color as a String
	     */
	    public void setFillStyle(String color) {
	    	impl.getContext2d().setFillStyle(color);
	    }
	    
	    public void setFillStyle(Color color) {
	    	impl.getContext2d().setFillStyle(color.toString());
	    }
	    
	    public void setStrokeStyle(Color color) {
	    	impl.getContext2d().setStrokeStyle(color.toString());
	    }
	    
	    /**
	     * Draws an arc. If a current subpath exists, a line segment is added from the
	     * current point to the starting point of the arc. If {@code anticlockwise} is
	     * false and {@code endAngle - startAngle} is equal to or greater than {@code
	     * 2 * Math.PI}, or if {@code anticlockwise} is {@code true} and {@code
	     * startAngle - endAngle} is equal to or greater than {@code 2 * Math.PI},
	     * then the arc is the whole circumference of the circle.
	     *
	     * @param x the x coordinate of the center of the arc
	     * @param y the y coordinate of the center of the arc
	     * @param radius the radius of the arc
	     * @param startAngle the start angle, measured in radians clockwise from the
	     *          positive x-axis
	     * @param endAngle the end angle, measured in radians clockwise from the
	     *          positive x-axis
	     * @param anticlockwise if {@code true}, the arc is drawn in an anticlockwise
	     *       direction
	     */
	    public void arc(double x, double y, double radius, double startAngle, double endAngle, boolean anticlockwise) {
	    	impl.getContext2d().arc(x, y, radius, startAngle, endAngle, anticlockwise);
	    }
	    public void stroke() {
	    	impl.getContext2d().stroke();
	    }
	    public void closePath() {
	    	impl.getContext2d().closePath();
	    }
	    public void fill() {
	    	impl.getContext2d().fill();
	    }
	    public void moveTo(double x, double y) {
	    	impl.getContext2d().moveTo(x, y);
	    }
	    public void lineTo(double x, double y) {
	    	impl.getContext2d().lineTo(x, y);
	    }
	    /**
	     * Sets the context's fillStyle.
	     *
	     * @param fillStyle the fill style to set.
	     * @see #getFillStyle()
	     * @see CssColor
	     */
	    public void setFillStyle(FillStrokeStyle fillStyle) {
	    	impl.getContext2d().setFillStyle(fillStyle);
	    }
	    
	    public void setStyleName(String name) {
	    	impl.setStyleName(name);
	    }
	    public void setSize(int width, int height) {
	    	this.width = width;
	    	this.height = height;
	    	impl.setCoordinateSpaceWidth(width);
	        impl.setCoordinateSpaceHeight(height);	    	
	    	super.setSize(width + "px", height+"px");
	    }
	    public void resize(int width, int height) {
	    	setSize(width, height);
	    }

	    public void setListener(MouseListener listener) {
	        this.listener = listener;
	    }
}
