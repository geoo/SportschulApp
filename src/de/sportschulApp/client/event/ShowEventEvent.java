package de.sportschulApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowEventEvent extends GwtEvent<ShowEventEventHandler>{
	public static Type<ShowEventEventHandler> TYPE = new Type<ShowEventEventHandler>();
	private int eventID;

	public ShowEventEvent(int eventID) {
		this.eventID = eventID;
	}

	@Override
	protected void dispatch(ShowEventEventHandler handler) {
		handler.onShowEvent(this);
	}

	@Override
	public Type<ShowEventEventHandler> getAssociatedType() {
		return TYPE;
	}

	public int getEventID() {
		return eventID;
	}
}
