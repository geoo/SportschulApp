package de.sportschulApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowUserEvent extends GwtEvent<ShowUserEventHandler>{
	public static Type<ShowUserEventHandler> TYPE = new Type<ShowUserEventHandler>();
	private int userID;
		
	public ShowUserEvent(int userID) {
		this.userID = userID;
	}
	
	public int getUserID() {
		return userID;
	}

	@Override
	public Type<ShowUserEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowUserEventHandler handler) {
		handler.onShowUser(this);
	}
}
