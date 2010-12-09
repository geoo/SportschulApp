package de.sportschulApp.server.databanker;

import java.util.ArrayList;

import de.sportschulApp.shared.Event;

public interface DataBankerEventInterface {

	public boolean deleteEvent(int Event_id);

	public Event getEvent(int eventID);

	public ArrayList<Event> getEventList();

	public Integer updateEvent(Event event);
}
