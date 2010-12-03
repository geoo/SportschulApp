package de.sportschulApp.server.databanker;

import java.util.ArrayList;

import de.sportschulApp.shared.Event;
import de.sportschulApp.shared.EventParticipant;

public interface DataBankerEventInterface {

	public Integer createEvent(Event event);

	public boolean deleteEvent(int Event_id);

	public Integer updateEvent(Event event);

	public boolean addMemberToEvent(int memberID, int eventID);
	
	public Event getEvent(int eventID);
		
	public ArrayList<Event> getEventList();
}
