package de.sportschulApp.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CourseDate implements Serializable {
	
	private String weekDay;
	private String time;
	
	public CourseDate(String weekDay, String time) {
		this.setWeekDay(weekDay);
		this.setTime(time);
	}
	
	public CourseDate() {
		
	}

	/**
	 * @param weekDay the weekDay to set
	 */
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	/**
	 * @return the weekDay
	 */
	public String getWeekDay() {
		return weekDay;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
}
