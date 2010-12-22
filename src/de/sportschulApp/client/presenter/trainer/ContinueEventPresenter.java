package de.sportschulApp.client.presenter.trainer;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.CookieManager;
import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.TrainerServiceAsync;
import de.sportschulApp.shared.Event;

public class ContinueEventPresenter implements Presenter {

	public interface Display {
		Widget asWidget();
		void setEventListBox(ArrayList<Event> events);
		HasClickHandlers getContinueButton();
		Event getSelectedItem();
	}

	private final Display display;
	private final TrainerServiceAsync rpcService;
	Date today = new Date();

	public ContinueEventPresenter(TrainerServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		bind();
		fillEventListBox();
	}

	private void bind() {
		this.display.getContinueButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent e) {
				final Event event = display.getSelectedItem();
				String user = CookieManager.getUsername();
				rpcService.startEvent(event.getEventID(), user, new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							Window.alert("Fehler beim starten des Events");
						}
						public void onSuccess(Void result) {
							History.newItem("trainerStartEvent:" + event.getEventID());
						}
				});
			}
		});
	}
	
	public void fillEventListBox() {
		rpcService.getEventList(new AsyncCallback<ArrayList<Event>>() {
			public void onSuccess(ArrayList<Event> result) {
				display.setEventListBox(result);
			}
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim laden der Events");
			}
		});
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}
