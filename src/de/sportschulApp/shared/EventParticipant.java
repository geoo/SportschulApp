package de.sportschulApp.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EventParticipant implements Serializable {

	private String barcodeID;
	private int eventID;
	private String forename;
	private String note;
	private String paid;
	private String participant;
	private String passed;
	private String picUrl;
	private String surname;


	public EventParticipant() {

	}

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

	public String getBarcodeID() {
		return barcodeID;
	}

	public int getEventID() {
		return eventID;
	}

	public String getForename() {
		return forename;
	}

	public String getNote() {
		return note;
	}

	public String getPaid() {
		return paid;
	}

	/**
	 * @return the praticipant
	 */
	public String getParticipant() {
		return participant;
	}

	public String getPassed() {
		return passed;
	}

	/**
	 * @return the picUrl
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	public void setBarcodeID(String memberID) {
		barcodeID = memberID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	/**
	 * @param praticipant the praticipant to set
	 */
	public void setParticipant(String participant) {
		this.participant = participant;
	}

	public void setPassed(String passed) {
		this.passed = passed;
	}

	/**
	 * @param picUrl the picUrl to set
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}



}
