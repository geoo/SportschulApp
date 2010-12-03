package de.sportschulApp.client.presenter.admin;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
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
import de.sportschulApp.shared.EventParticipant;
import de.sportschulApp.shared.Member;

@SuppressWarnings("unchecked")
public class ListEventParticipantsPresenter implements Presenter{
	public interface Display{
		void setMemberList(ArrayList<EventParticipant> memberList);
		void setSelectionModel(SingleSelectionModel selectionModel);
		HasClickHandlers getSearchButton();
		HasClickHandlers getShowAllButton();
		TextBox getSearchInput();
		HasValue<String> getSearchQuery();
		Widget asWidget();
		CellTable<EventParticipant> getCellTable();
		HasClickHandlers getSubmitButton();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	private final HandlerManager eventBus;
	private String eventID;
	
	public ListEventParticipantsPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display, String eventID) {
	    this.display = display;
	    this.rpcService = rpcService;
	    this.eventBus = eventBus;
	    this.eventID = eventID;
	    bind();
	    fetchListData();
	  }

	private void bind() {
		
	}

	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}
	
	public void fetchListData() {
		rpcService.getEventParticipants(Integer.valueOf(eventID) , new AsyncCallback<ArrayList<EventParticipant>>() {
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
			public void onSuccess(ArrayList<EventParticipant> result) {
				display.setMemberList(result);
				
				display.getSubmitButton().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						submitParticipants();
					}
				});
			}
		});
		
	}
	
	public void submitParticipants() {
		ArrayList<EventParticipant> participants = new ArrayList<EventParticipant>();
		for (int i = 0; i < this.display.getCellTable().getDisplayedItems().size(); i++) {
			if (this.display.getCellTable().getDisplayedItems().get(i).getParticipant().equals("Ja")) {
				participants.add(this.display.getCellTable().getDisplayedItems().get(i));
			}
		}
		
		rpcService.setEventParticipants(Integer.valueOf(eventID), participants, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim speichern der Teilnehmer");
				
			}

			@Override
			public void onSuccess(Void result) {
				History.newItem("adminEventsShowEvents");
			}
		});
	}
	
	
	public Display getDisplay(){
		return display;
	}
	
}
