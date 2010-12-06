package de.sportschulApp.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CourseDate implements Serializable {

	private String time;
	private String weekDay;

	public CourseDate() {

	}

	public CourseDate(String weekDay, String time) {
		setWeekDay(weekDay);
		setTime(time);
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
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
	 * @param weekDay the weekDay to set
	 */
	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}
}
