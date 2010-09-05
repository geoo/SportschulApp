package de.sportschulApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class LanguageChangeEvent extends GwtEvent<LanguageChangeHandler>{
	public static Type<LanguageChangeHandler> TYPE = new Type<LanguageChangeHandler>();
	private String value;
	
	public LanguageChangeEvent(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public Type<LanguageChangeHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LanguageChangeHandler handler) {
		handler.onLanguageChange(this);
	}
	
	
}
