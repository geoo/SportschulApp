package de.sportschulApp.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EventParticipant implements Serializable {
	
	private int eventID;
	private int memberID;
	private String memberName;
	private int passed;
	private String paid;
	private String note;
	
	
	public EventParticipant(int eventID, int memberID, String memberName, int passed, String paid, String note) {
		this.eventID = eventID;
		this.memberID = memberID;
		this.memberName = memberName;
		this.passed = passed;
		this.paid = paid;
		this.note = note;
	}
	
	public EventParticipant() {
		
	}
	
	public int getEventID() {
		return eventID;
	}
	
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public int getMemberID() {
		return memberID;
	}

	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public int getPassed() {
		return passed;
	}

	public void setPassed(int passed) {
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


	
}
