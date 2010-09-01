package de.sportschulApp.client.presenter.admin;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.shared.Member;

public class ShowMemberPresenter implements Presenter{
	public interface Display{
		void setMemberData(Member member);
		Widget asWidget();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	private final int memberID;
	
	public ShowMemberPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display, int memberID) {
	    this.display = display;
	    this.rpcService = rpcService;
	    this.memberID = memberID;
	    bind();
	    fetchMemberData(memberID);
	}
	
	public void fetchMemberData(int memberID) {
		rpcService.getMemberByMemberID(memberID, new AsyncCallback<Member>() {
			public void onSuccess(Member result) {
				buildShowMemberView(result);
				System.out.println(result.getForename());
			}
			public void onFailure(Throwable caught) {
			}
		});
	}
	
	public void buildShowMemberView(Member member) {
		this.display.setMemberData(member);
	}

	private void bind() {
		
	}

	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}

}
