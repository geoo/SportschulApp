package de.sportschulApp.client.presenter.admin;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.shared.Event;

public class ShowEventPresenter implements Presenter{
	public interface Display{
		Widget asWidget();
		HasClickHandlers getDeleteLabel();
		HasClickHandlers getEditLabel();
		HasClickHandlers getShowParticipantsLabel();
		HasClickHandlers getCloseLabel();
		void setData(Event event);
	}

	private final Display display;
	private Event event = new Event();
	private final AdminServiceAsync rpcService;
	private DialogBox popup;

	public ShowEventPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display, int eventID, DialogBox popup) {
		this.display = display;
		this.rpcService = rpcService;
		this.popup = popup;
		bind();
		fetchData(eventID);
	}

	private void bind() {
		display.getEditLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				History.newItem("adminEventsEditEvent:" + event.getEventID());
			}
		});
		
		display.getShowParticipantsLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e) {
				History.newItem("adminEventsEditParticipants:" + event.getEventID());
			}
		});

		display.getDeleteLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				if(Window.confirm("Bestätigen sie mit OK wenn das Event wirklich gelöscht werden soll.")) {
					rpcService.deleteEventByEventID(event.getEventID(), new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							Window.alert("Das löschen des Events ist Fehlgeschlagen");
						}
						public void onSuccess(Void result) {
							History.fireCurrentHistoryState();
						}
					});
				}
			}
		});
		
		display.getCloseLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				popup.hide();
			}
		});
	}


	public void fetchData(int eventID) {
		rpcService.getEventByEventID(eventID, new AsyncCallback<Event>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim laden der Eventdaten");
			}

			public void onSuccess(Event result) {
				event = result;
				display.setData(result);
			}
		});
	}


	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}
