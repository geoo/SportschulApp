package de.sportschulApp.server.databanker;

import java.util.ArrayList;

import de.sportschulApp.shared.Event;

public interface DataBankerEventInterface {

	public boolean createEvent(Event event);

	public boolean deleteEvent(int Event_id);

	public boolean updateEvent(Event event);

	public boolean addMemberToEvent(int memberID, int eventID);
	
	public Event getEvent(int eventID);
	
	public ArrayList<Integer> getEventParticipants(int eventID);
}
