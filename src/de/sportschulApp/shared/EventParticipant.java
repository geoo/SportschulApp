package de.sportschulApp.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EventParticipant implements Serializable {
	
	private int eventID;
	private String barcodeID;
	private String forename;
	private String surname;
	private String passed;
	private String paid;
	private String note;
	private String participant;
	private String picUrl;
	
	
	public EventParticipant(int eventID, String barcodeID, String forename, String surname, String passed, String paid, String note, String participant, String picUrl) {
		this.eventID = eventID;
		this.barcodeID = barcodeID;
		this.forename = forename;
		this.surname = surname;
		this.passed = passed;
		this.paid = paid;
		this.note = note;
		this.participant = participant;
		this.picUrl = picUrl;
	}
	
	public EventParticipant() {
		
	}
	
	public int getEventID() {
		return eventID;
	}
	
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getBarcodeID() {
		return barcodeID;
	}

	public void setBarcodeID(String memberID) {
		this.barcodeID = memberID;
	}

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getPassed() {
		return passed;
	}

	public void setPassed(String passed) {
		this.passed = passed;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param praticipant the praticipant to set
	 */
	public void setParticipant(String participant) {
		this.participant = participant;
	}

	/**
	 * @return the praticipant
	 */
	public String getParticipant() {
		return participant;
	}

	/**
	 * @param picUrl the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}


	
}
