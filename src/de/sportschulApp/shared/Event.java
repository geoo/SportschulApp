package de.sportschulApp.shared;


import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Event implements Serializable {
	private int eventID;
	private String type;
	private String name;
	private String costs;
	private String date;
	private String time;
	private String location;
	private ArrayList<String> examiners;
	private ArrayList<EventParticipant> participants;

	
	public Event(int eventID, String type, String name, String costs, String date, String time, String location, ArrayList<String> examiners, ArrayList<EventParticipant> participants) {
	this.eventID = eventID;
	this.type = type;
	this.name = name;
	this.costs = costs;
	this.date = date;
	this.time = time;
	this.location = location;
	this.examiners = examiners;
	this.participants = participants;
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
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the courseID
	 */
	public String getType() {
		return type;
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
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
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

	/**
	 * @param participants the participants to set
	 */
	public void setParticipants(ArrayList<EventParticipant> participants) {
		this.participants = participants;
	}

	/**
	 * @return the participants
	 */
	public ArrayList<EventParticipant> getParticipants() {
		return participants;
	}

}