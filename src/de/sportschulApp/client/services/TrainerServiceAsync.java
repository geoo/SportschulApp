package de.sportschulApp.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.sportschulApp.shared.Event;
import de.sportschulApp.shared.EventParticipant;
import de.sportschulApp.shared.Member;

public interface TrainerServiceAsync {

	void getMemberByBarcodeID(int barcodeID, AsyncCallback<Member> callback);

	void getNote(int barcodeID, AsyncCallback<String> asyncCallback);

	void getTrainingspresence(int memberID, int month, int year, AsyncCallback<Integer> callback);

	void setNote(int barcodeID, String note, AsyncCallback<String> asyncCallback);

	void setTrainingsPresence(int barcodeID, int day, int month, int year,
			AsyncCallback<String> callback);

	void getEventList(AsyncCallback<ArrayList<Event>> callback);

	void startEvent(int eventID, String user, AsyncCallback<Void> callback);

	void getEventParticipants(int eventID,
			AsyncCallback<ArrayList<EventParticipant>> callback);

	void abortEvent(int eventID, AsyncCallback<Void> callback);

	void saveEvent(int eventID, ArrayList<EventParticipant> participants,
			AsyncCallback<Void> callback);

	void endEvent(int eventID, AsyncCallback<Void> callback);

	void getEvent(int eventID, AsyncCallback<Event> callback);

	void setPassedValues(Event event, ArrayList<EventParticipant> participants,
			AsyncCallback<Void> callback);

}
