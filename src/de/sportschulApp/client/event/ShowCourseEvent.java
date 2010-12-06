package de.sportschulApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ShowCourseEvent extends GwtEvent<ShowCourseEventHandler>{
	public static Type<ShowCourseEventHandler> TYPE = new Type<ShowCourseEventHandler>();
	private int id;

	public ShowCourseEvent(int id) {
		this.id = id;
	}

	@Override
	protected void dispatch(ShowCourseEventHandler handler) {
		handler.onShowCourse(this);
	}

	@Override
	public Type<ShowCourseEventHandler> getAssociatedType() {
		return TYPE;
	}

	public int getCourseID() {
		return id;
	}
}
