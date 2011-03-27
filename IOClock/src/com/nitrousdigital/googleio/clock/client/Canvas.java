package com.nitrousdigital.googleio.clock.client;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

public class Canvas extends GWTCanvas {
	 private MouseListener listener;

	    public Canvas(int width, int height) {
	        super(width, height);
	        sinkEvents(Event.MOUSEEVENTS);
	    }

	    @Override
	    public void onBrowserEvent(Event event) {
	        super.onBrowserEvent(event);
	        if (listener != null) {
	            int x = event.getClientX() - getAbsoluteLeft();
	            int y = event.getClientY() - getAbsoluteTop();
	            switch (event.getTypeInt()) {
	                case Event.ONMOUSEDOWN:
	                    listener.onMouseDown(this, x, y);
	                    break;
	                case Event.ONMOUSEMOVE:
	                    listener.onMouseMove(this, x, y);
	                    break;
	                case Event.ONMOUSEUP:
	                    listener.onMouseUp(this, x, y);
	                    break;
	            }                           
	        }
	    }

	    public void setListener(MouseListener listener) {
	        this.listener = listener;
	    }
}
