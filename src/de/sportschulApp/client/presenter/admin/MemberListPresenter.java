package de.sportschulApp.client.presenter.admin;

import java.util.ArrayList;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.shared.Member;

public class MemberListPresenter implements Presenter{
	public interface Display{
		void setMemberList(ArrayList<Member> memberList);
		Widget asWidget();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	
	public MemberListPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display) {
	    this.display = display;
	    this.rpcService = rpcService;
	    bind();
	    getMemberList();
	  }

	private void bind() {
		
	}

	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}
	
	public void getMemberList() {
		rpcService.getMemberList(new AsyncCallback<ArrayList<Member>>() {
			public void onSuccess(ArrayList<Member> result) {
				display.setMemberList(result);
			}
			public void onFailure(Throwable caught) {
				
			}
		});
		
	}

}
