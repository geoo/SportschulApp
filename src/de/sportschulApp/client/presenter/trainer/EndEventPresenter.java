package de.sportschulApp.client.presenter.trainer;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.TrainerServiceAsync;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Event;
import de.sportschulApp.shared.EventParticipant;

public class EndEventPresenter implements Presenter {

	public interface Display {
		Widget asWidget();
		void setMemberList(ArrayList<EventParticipant> memberList);
		CellTable<EventParticipant> getCellTable();
		ListDataProvider<EventParticipant> getListProvider();
		HasClickHandlers getAllPassedLabel();
		HasClickHandlers getSubmitLabel();
	}

	private final Display display;
	private final TrainerServiceAsync rpcService;
	private String eventID;

	public EndEventPresenter(TrainerServiceAsync rpcService,
			HandlerManager eventBus, Display display, String eventID, LocalizationConstants constants) {
		this.display = display;
		this.rpcService = rpcService;
		this.eventID = eventID;
		bind();
		fetchListData();
		fetchCourses();
	}

	private void bind() {
		display.getAllPassedLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				for (int i = 0; i < display.getListProvider().getList().size(); i++) {
					display.getListProvider().getList().get(i).setPassed("Ja");
					display.getCellTable().redraw();
				}
			}
		});
		display.getSubmitLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				setPassedValues();
			}
		});
	}
	
	public void fetchListData() {
		rpcService.getEventParticipants(Integer.valueOf(eventID) , new AsyncCallback<ArrayList<EventParticipant>>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim abrufen der Teilnehmerliste");
			}
			public void onSuccess(ArrayList<EventParticipant> result) {
				ArrayList<EventParticipant> participants = new ArrayList<EventParticipant>();
				for (int i = 0; i < result.size(); i++) {
					if (result.get(i).getAttended().equals("Ja")) {
						participants.add(result.get(i));
					}
				}
				display.getListProvider().getList().addAll(participants);
			}
		});
	}
	
	public void fetchCourses() {
		rpcService.getEvent(Integer.valueOf(eventID), new AsyncCallback<Event>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim Abrufen der Kursdaten");
			}
			public void onSuccess(Event result) {
				
			}
		});
	}
	
	public void setPassedValues() {
		rpcService.getEvent(Integer.valueOf(eventID), new AsyncCallback<Event>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim Abrufen der Eventinformationen");
			}
			public void onSuccess(Event result) {
				rpcService.setPassedValues(result, (ArrayList<EventParticipant>) display.getCellTable().getDisplayedItems(), new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						Window.alert("Abschließen des Events fehlgeschlagen");
					}
					public void onSuccess(Void result) {
						History.newItem("trainerNewEvent");
					}
				});
			}
		});
	}


	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}
