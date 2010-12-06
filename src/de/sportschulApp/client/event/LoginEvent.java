package de.sportschulApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LoginEvent extends GwtEvent<LoginEventHandler>{
	public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();

	@Override
	protected void dispatch(LoginEventHandler handler) {
		handler.onLogin(this);
	}

	@Override
	public Type<LoginEventHandler> getAssociatedType() {
		return TYPE;
	}


}
