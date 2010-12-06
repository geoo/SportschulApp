package de.sportschulApp.server.databanker;

import java.util.ArrayList;

import de.sportschulApp.shared.Event;

public interface DataBankerEventInterface {

	public boolean addMemberToEvent(int memberID, int eventID);

	public Integer createEvent(Event event);

	public boolean deleteEvent(int Event_id);

	public Event getEvent(int eventID);

	public ArrayList<Event> getEventList();

	public Integer updateEvent(Event event);
}
