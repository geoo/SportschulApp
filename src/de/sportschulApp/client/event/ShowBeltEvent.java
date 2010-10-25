package de.sportschulApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowBeltEvent extends GwtEvent<ShowBeltEventHandler>{
	public static Type<ShowBeltEventHandler> TYPE = new Type<ShowBeltEventHandler>();
	private int id;
		
	public ShowBeltEvent(int id) {
		this.id = id;
	}
	
	public int getBeltID() {
		return id;
	}

	@Override
	public Type<ShowBeltEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowBeltEventHandler handler) {
		handler.onShowBelt(this);
	}
}
