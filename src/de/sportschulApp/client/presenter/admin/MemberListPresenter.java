package de.sportschulApp.client.presenter.admin;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

import de.sportschulApp.client.event.ShowMemberEvent;
import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.shared.Member;

@SuppressWarnings("unchecked")
public class MemberListPresenter implements Presenter{
	public interface Display{
		void setMemberList(ArrayList<Member> memberList);
		void setSelectionModel(SingleSelectionModel selectionModel);
		HasClickHandlers getSearchButton();
		HasClickHandlers getShowAllButton();
		HasKeyUpHandlers getSearchInput();
		HasValue<String> getSearchQuery();
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
		
		this.display.getSearchButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				executeSearch();
			}
		});
		
		this.display.getSearchInput().addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == 13) {
					executeSearch();
				}	
			}
		});
		
		this.display.getShowAllButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				getMemberList();
			}
		});
	}

	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}
	
	public void executeSearch() {
		String searchQuery = new String(display.getSearchQuery().getValue());
		rpcService.searchMember(searchQuery, new AsyncCallback<ArrayList<Member>>() {
			public void onSuccess(ArrayList<Member> result) {
				display.setMemberList(result);
			}
			public void onFailure(Throwable caught) {
			}
		});
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
		Handler selectionHandler = new SelectionChangeEvent.Handler() {
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
