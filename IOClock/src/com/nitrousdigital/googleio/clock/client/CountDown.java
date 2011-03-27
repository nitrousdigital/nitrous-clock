package com.nitrousdigital.googleio.clock.client;

import java.util.Date;

public class CountDown {
	private static final long SECOND = 1000;
	private static final long MINUTE = 60 * SECOND;
	private static final long HOUR = 60 * MINUTE;
	private static final long DAY = 24 * HOUR;
	
	private int days;
	private int hours;
	private int minutes;
	private int seconds;
	
	public CountDown(Date target) {
		long now = System.currentTimeMillis();
		long targetMillis = target.getTime();
		if (targetMillis > now) {
			long diffMillis = targetMillis - now;
			fromMillis(diffMillis);
			
			// enforce 365 day maximum as per requirements of contest
			if (days > 365 || 
					(days == 365 && (hours > 0 || minutes > 0 || seconds > 0))) {
				days = 365;
				hours = 0;
				minutes = 0;
				seconds = 0;
			}
		}
	}
	
	public CountDown(int days, int hours, int minutes, int seconds) {
		super();
		this.days = days;
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	
	private long toMillis() {
		long millis = 0;
		millis += seconds * SECOND;
		millis += minutes * MINUTE;
		millis += hours * HOUR;
		millis += days * DAY;
		return millis;
	}
	
	private void fromMillis(long millis) {
		days = (int)(millis / DAY);
		millis -= (DAY * days);
		
		hours = (int)(millis / HOUR);
		millis -= (HOUR * hours);
		
		minutes = (int)(millis / MINUTE);
		millis -= (MINUTE * minutes);

		seconds = (int)(millis / 1000);
	}

	/**
	 * Reduce this count down by 1 second
	 */
	public void tick() {
		long millis = toMillis();
		millis -= 1000;
		if (millis < 1000) {
			millis = 0;
		}
		fromMillis(millis);
	}
	
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

}
