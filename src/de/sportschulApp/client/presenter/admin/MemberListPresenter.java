package de.sportschulApp.client.presenter.admin;

import java.util.ArrayList;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionModel.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel.SelectionChangeHandler;

import de.sportschulApp.client.event.ShowMemberEvent;
import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.admin.MemberListView;
import de.sportschulApp.shared.Member;

public class MemberListPresenter implements Presenter{
	public interface Display{
		void setMemberList(ArrayList<Member> memberList);
		void setSelectionModel(SingleSelectionModel selectionModel);
		Widget asWidget();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	private final HandlerManager eventBus;
	
	public MemberListPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display) {
	    this.display = display;
	    this.rpcService = rpcService;
	    this.eventBus = eventBus;
	    bind();
	    getMemberList();
	  }

	private void bind() {
		setSelectionModel();
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
	
	public void setSelectionModel() {
		final SingleSelectionModel<Member> selectionModel = new SingleSelectionModel<Member>();
		SelectionChangeHandler selectionHandler = new SelectionChangeHandler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Member member = selectionModel.getSelectedObject();
				eventBus.fireEvent(new ShowMemberEvent(member.getBarcodeID()));
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);
		this.display.setSelectionModel(selectionModel);
	}
	
	public Display getDisplay(){
		return display;
	}
	
}
