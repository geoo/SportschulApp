package de.sportschulApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LogoutEvent extends GwtEvent<LogoutEventHandler>{
	public static Type<LogoutEventHandler> TYPE = new Type<LogoutEventHandler>();

	@Override
	protected void dispatch(LogoutEventHandler handler) {
		handler.onLogout(this);
	}

	@Override
	public Type<LogoutEventHandler> getAssociatedType() {
		return TYPE;
	}

}
