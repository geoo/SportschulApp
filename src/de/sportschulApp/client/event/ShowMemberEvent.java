package de.sportschulApp.client.event;

import com.google.gwt.event.shared.GwtEvent;

import de.sportschulApp.shared.Member;

public class ShowMemberEvent extends GwtEvent<ShowMemberEventHandler>{
	public static Type<ShowMemberEventHandler> TYPE = new Type<ShowMemberEventHandler>();
	private Member member;
	
	public ShowMemberEvent(Member member) {
		this.member = member;
	}
	
	public Member getMember() {
		return member;
	}

	@Override
	public Type<ShowMemberEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ShowMemberEventHandler handler) {
		handler.onShowMember(this);
	}
}
