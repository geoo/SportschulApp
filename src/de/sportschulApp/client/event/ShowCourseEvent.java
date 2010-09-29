package de.sportschulApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

import de.sportschulApp.shared.Member;

public class ShowCourseEvent extends GwtEvent<ShowCourseEventHandler>{
	public static Type<ShowCourseEventHandler> TYPE = new Type<ShowCourseEventHandler>();
	private int id;
		
	public ShowCourseEvent(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}

	@Override
	public Type<ShowCourseEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowCourseEventHandler handler) {
		handler.onShowCourse(this);
	}
}
