package de.sportschulApp.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.sportschulApp.shared.Event;
import de.sportschulApp.shared.EventParticipant;
import de.sportschulApp.shared.Member;


@RemoteServiceRelativePath("TrainerService")
public interface TrainerService extends RemoteService {
	Member getMemberByBarcodeID(int barcodeID);


	String getNote(int barcodeID);


	int getTrainingspresence(int memberID, int month, int year);


	String setNote(int barcodeID, String note);

	String setTrainingsPresence(int barcodeID, int day, int month, int year);
	
	ArrayList<Event> getEventList();

	void startEvent(int eventID, String user);
	
	ArrayList<EventParticipant> getEventParticipants(int eventID);
	
	void abortEvent(int eventID);
	
	void endEvent(int eventID);

	void saveEvent(int eventID, ArrayList<EventParticipant> participants);
	
	Event getEvent(int eventID);
	
	void setPassedValues(Event event, ArrayList<EventParticipant> participants);
}
