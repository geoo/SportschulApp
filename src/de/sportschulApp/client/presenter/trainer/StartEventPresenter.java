package de.sportschulApp.client.presenter.trainer;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.TrainerServiceAsync;
import de.sportschulApp.shared.EventParticipant;

public class StartEventPresenter implements Presenter {

	public interface Display {
		Widget asWidget();
		void setMemberList(ArrayList<EventParticipant> memberList);
	}

	private final Display display;
	private final TrainerServiceAsync rpcService;
	private String eventID;

	public StartEventPresenter(TrainerServiceAsync rpcService,
			HandlerManager eventBus, Display display, String eventID) {
		this.display = display;
		this.rpcService = rpcService;
		this.eventID = eventID;
		bind();
		fetchListData();
	}

	private void bind() {
		
	}
	
	public void fetchListData() {
		rpcService.getEventParticipants(Integer.valueOf(eventID) , new AsyncCallback<ArrayList<EventParticipant>>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim abrufen der Teilnehmerliste");
			}
			public void onSuccess(ArrayList<EventParticipant> result) {
				display.setMemberList(result);
			}
		});
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}
