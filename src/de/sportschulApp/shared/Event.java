package de.sportschulApp.shared;


import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Event implements Serializable {
	private String costs;
	private String date;
	private int eventID;
	private ArrayList<String> examiners;
	private String location;
	private String name;
	private ArrayList<EventParticipant> participants;
	private String time;
	private String type;


	public Event() {

	}

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

	/**
	 * @return the costs
	 */
	public String getCosts() {
		return costs;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @return the eventID
	 */
	public int getEventID() {
		return eventID;
	}

	/**
	 * @return the examiner
	 */
	public ArrayList<String> getExaminers() {
		return examiners;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the participants
	 */
	public ArrayList<EventParticipant> getParticipants() {
		return participants;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @return the courseID
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param costs the costs to set
	 */
	public void setCosts(String costs) {
		this.costs = costs;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @param eventID the eventID to set
	 */
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	/**
	 * @param examiner the examiner to set
	 */
	public void setExaminers(ArrayList<String> examiner) {
		examiners = examiner;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param participants the participants to set
	 */
	public void setParticipants(ArrayList<EventParticipant> participants) {
		this.participants = participants;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @param courseID the courseID to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}