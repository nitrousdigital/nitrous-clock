package com.nitrousdigital.googleio.clock.client;

import java.util.Date;

import com.google.gwt.user.client.Timer;

public class Ticker {
	private CountDown count;
	private ClockPanel clock;
	private int lastSecond;
	public Ticker(CountDown count, ClockPanel clock) {
		this.count = count;
		this.clock = clock;
	}
	public void start() {
		lastSecond = new Date(System.currentTimeMillis()).getSeconds();
		Timer t = new com.google.gwt.user.client.Timer(){
			public void run() {
				int second = new Date(System.currentTimeMillis()).getSeconds();
				if (second != lastSecond) {
					count.tick();
					clock.setValue(count);
				}
				lastSecond = second;
				clock.animate();
				clock.paint();
			}
		};
		t.scheduleRepeating(33);
	}
}
