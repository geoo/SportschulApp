package de.sportschulApp.shared;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@SuppressWarnings("serial")
public class Event implements Serializable {
	private int eventID;
	private int courseID;
	private String name;
	private String costs;
	private Date date;
	private Time time;
	private String location;
	private String examiner1;
	private String examiner2;
	private String examiner3;
	private String examiner4;
	private String examiner5;

	
	public Event(int eventID, int courseID, String name, String costs, Date date, Time time, String location, String examiner1, String examiner2, String examiner3, String examiner4, String examiner5) {
	this.eventID = eventID;
	this.courseID = courseID;
	this.name = name;
	this.costs = costs;
	this.date = date;
	this.time = time;
	this.location = location;
	this.examiner1 = examiner1;
	this.examiner2 = examiner2;
	this.examiner3 = examiner3;
	this.examiner4 = examiner4;
	this.examiner5 = examiner5;
	
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
	 * @param examiner1 the examiner1 to set
	 */
	public void setExaminer1(String examiner1) {
		this.examiner1 = examiner1;
	}

	/**
	 * @return the examiner1
	 */
	public String getExaminer1() {
		return examiner1;
	}

	/**
	 * @param examiner2 the examiner2 to set
	 */
	public void setExaminer2(String examiner2) {
		this.examiner2 = examiner2;
	}

	/**
	 * @return the examiner2
	 */
	public String getExaminer2() {
		return examiner2;
	}

	/**
	 * @param examiner3 the examiner3 to set
	 */
	public void setExaminer3(String examiner3) {
		this.examiner3 = examiner3;
	}

	/**
	 * @return the examiner3
	 */
	public String getExaminer3() {
		return examiner3;
	}

	/**
	 * @param examiner4 the examiner4 to set
	 */
	public void setExaminer4(String examiner4) {
		this.examiner4 = examiner4;
	}

	/**
	 * @return the examiner4
	 */
	public String getExaminer4() {
		return examiner4;
	}

	/**
	 * @param examiner5 the examiner5 to set
	 */
	public void setExaminer5(String examiner5) {
		this.examiner5 = examiner5;
	}

	/**
	 * @return the examiner5
	 */
	public String getExaminer5() {
		return examiner5;
	}
}