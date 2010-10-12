package de.sportschulApp.shared;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Event implements Serializable {
	private int eventID;
	private int courseID;
	private String name;
	private String costs;
	private Date date;
	private Time time;
	private String location;
	private ArrayList<String> examiners;

	
	public Event(int eventID, int courseID, String name, String costs, Date date, Time time, String location, ArrayList<String> examiners) {
	this.eventID = eventID;
	this.courseID = courseID;
	this.name = name;
	this.costs = costs;
	this.date = date;
	this.time = time;
	this.location = location;
	this.examiners = examiners;
	
	}

	public Event() {

	}

	/**
	 * @param eventID the eventID to set
	 */
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	/**
	 * @return the eventID
	 */
	public int getEventID() {
		return eventID;
	}

	/**
	 * @param courseID the courseID to set
	 */
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	/**
	 * @return the courseID
	 */
	public int getCourseID() {
		return courseID;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param costs the costs to set
	 */
	public void setCosts(String costs) {
		this.costs = costs;
	}

	/**
	 * @return the costs
	 */
	public String getCosts() {
		return costs;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Time time) {
		this.time = time;
	}

	/**
	 * @return the time
	 */
	public Time getTime() {
		return time;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param examiner the examiner to set
	 */
	public void setExaminers(ArrayList<String> examiner) {
		this.examiners = examiner;
	}

	/**
	 * @return the examiner
	 */
	public ArrayList<String> getExaminers() {
		return examiners;
	}

}