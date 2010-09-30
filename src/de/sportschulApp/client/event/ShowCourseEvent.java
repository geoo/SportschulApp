package de.sportschulApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowCourseEvent extends GwtEvent<ShowCourseEventHandler>{
	public static Type<ShowCourseEventHandler> TYPE = new Type<ShowCourseEventHandler>();
	private int id;
		
	public ShowCourseEvent(int id) {
		this.id = id;
	}
	
	public int getCourseID() {
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
