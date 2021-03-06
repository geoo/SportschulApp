package de.sportschulApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowMemberEvent extends GwtEvent<ShowMemberEventHandler>{
	public static Type<ShowMemberEventHandler> TYPE = new Type<ShowMemberEventHandler>();
	private int barcode;

	public ShowMemberEvent(int barcode) {
		this.barcode = barcode;
	}

	@Override
	protected void dispatch(ShowMemberEventHandler handler) {
		handler.onShowMember(this);
	}

	@Override
	public Type<ShowMemberEventHandler> getAssociatedType() {
		return TYPE;
	}

	public int getBarcode() {
		return barcode;
	}
}
