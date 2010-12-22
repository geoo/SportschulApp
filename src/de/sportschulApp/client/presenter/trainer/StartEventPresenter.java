package de.sportschulApp.client.presenter.trainer;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.TrainerServiceAsync;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Event;
import de.sportschulApp.shared.EventParticipant;

public class StartEventPresenter implements Presenter {

	public interface Display {
		Widget asWidget();
		void setMemberList(ArrayList<EventParticipant> memberList);
		TextBox getBarcodeTextBox();
		HasClickHandlers getScanButton();
		HasClickHandlers getSaveLabel();
		HasClickHandlers getAbortLabel();
		HasClickHandlers getEndLabel();
		Image getScanImage();
		CellTable<EventParticipant> getCellTable();
		Column<EventParticipant, Boolean> getAttendedColumn();
		ListDataProvider<EventParticipant> getListProvider();
	}

	private final Display display;
	private final TrainerServiceAsync rpcService;
	private String eventID;
	private DialogBox participantEditor;
	private LocalizationConstants constants;
	private ArrayList<EventParticipant> allParticipants = new ArrayList<EventParticipant>();

	public StartEventPresenter(TrainerServiceAsync rpcService,
			HandlerManager eventBus, Display display, String eventID, LocalizationConstants constants) {
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
				allParticipants.addAll(result);
				ArrayList<EventParticipant> participants = new ArrayList<EventParticipant>();
				for (int i = 0; i < result.size(); i++) {
					if (result.get(i).getParticipant().equals("Ja")) {
						participants.add(result.get(i));
					}
				}
				display.setMemberList(participants);
				
				display.getBarcodeTextBox().addKeyUpHandler(new KeyUpHandler() {
					public void onKeyUp(KeyUpEvent event) {
						if (event.getNativeKeyCode() == 13) {
							for (int i = 0 ; i < display.getCellTable().getDisplayedItems().size(); i++) {
								if (display.getCellTable().getDisplayedItems().get(i).getBarcodeID().equals(display.getBarcodeTextBox().getText())) {
										display.getAttendedColumn().getFieldUpdater().update(i, display.getCellTable().getDisplayedItem(i), true);
										display.getCellTable().redraw();
										display.getBarcodeTextBox().setText("");
										display.getBarcodeTextBox().setFocus(true);
										return;
								} 
							}
							for (int i = 0; i < allParticipants.size(); i++) {
								if (display.getBarcodeTextBox().getText().equals(allParticipants.get(i).getBarcodeID())) {
									participationPopup(allParticipants.get(i));
									return;
								}
							}
							Window.alert("Mitglied nicht gefunden");
							display.getBarcodeTextBox().setText("");
							display.getBarcodeTextBox().setFocus(true);
						}
					}
				});
				
				display.getScanButton().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						display.getBarcodeTextBox().setFocus(true);
					}
				});

				display.getBarcodeTextBox().addFocusHandler(new FocusHandler() {
					public void onFocus(FocusEvent event) {
						display.getScanImage().setUrl("imgs/greenlight.png");
					}
				});
				
				display.getBarcodeTextBox().addBlurHandler(new BlurHandler() {

					public void onBlur(BlurEvent event) {
						display.getScanImage().setUrl("imgs/redlight.png");

					}
				});
				display.getBarcodeTextBox().addClickHandler(new ClickHandler() {

					public void onClick(ClickEvent event) {
						display.getBarcodeTextBox().setSelectionRange(0,
								display.getBarcodeTextBox().getText().length());
					}
				});
			}
		});
		
		display.getSaveLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ArrayList<EventParticipant> participants = new ArrayList<EventParticipant>();
				for (int i = 0; i < display.getListProvider().getList().size(); i++) {
					display.getListProvider().getList().get(i).setParticipant("Ja");
					participants.add(display.getListProvider().getList().get(i));
				}
				rpcService.saveEvent(Integer.valueOf(eventID), participants, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						Window.alert("Fehler beim speichern!");
					}
					public void onSuccess(Void result) {
						Window.alert("Speichern erfolgreich!");
					}
				});
			}
		});
		
		display.getAbortLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (Window.confirm("Möchten sie das Event wirklich abbrechen? Sie können es zu einem späteren Zeitpunkt fortführen.")) {
					if (Window.confirm("Klicken sie auf 'Ok' wenn sie den aktuellen Stand speichern wollen. Bei abbruch wird das Event auf den letzten Stand zurück gesetzt!")) {
						ArrayList<EventParticipant> participants = new ArrayList<EventParticipant>();
						for (int i = 0; i < display.getListProvider().getList().size(); i++) {
							display.getListProvider().getList().get(i).setParticipant("Ja");
							participants.add(display.getListProvider().getList().get(i));
						}
						rpcService.saveEvent(Integer.valueOf(eventID), participants, new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {
								Window.alert("Fehler beim speichern!");
							}
							public void onSuccess(Void result) {
							}
						});
					}
					History.newItem("trainerContinueEvent");							
				}
			}
		});
		
		display.getEndLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (Window.confirm("Klicken sie auf 'Ok' wenn sie das Event abschließen möchten")) {
					ArrayList<EventParticipant> participants = new ArrayList<EventParticipant>();
					for (int i = 0; i < display.getListProvider().getList().size(); i++) {
						display.getListProvider().getList().get(i).setParticipant("Ja");
						participants.add(display.getListProvider().getList().get(i));
					}
					rpcService.saveEvent(Integer.valueOf(eventID), participants, new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							Window.alert("Fehler beim speichern!");
						}
						public void onSuccess(Void result) {
							rpcService.endEvent(Integer.valueOf(eventID), new AsyncCallback<Void>() {
								public void onFailure(Throwable caught) {
									Window.alert("Fehler beim beenden des Events");
								}
								public void onSuccess(Void result) {
									rpcService.getEvent(Integer.valueOf(eventID), new AsyncCallback<Event>() {
										public void onFailure(Throwable caught) {
											Window.alert("Ein fehler ist aufgetreten");
										}
										public void onSuccess(Event result) {
											if (result.getType().equals("Prüfung")) {
												History.newItem("trainerEndEvent:" + eventID);												
											} else {
												History.newItem("trainerNewEvent");
											}
										}
									});
								}
							});
						}
					});
				}
			}
		});
	}
	
	private void participationPopup(final EventParticipant participant) {
		final DialogBox participationPopup = new DialogBox(true);
		participationPopup.setAnimationEnabled(true);
		participationPopup.setText("Teilnehmer hinzufügen");
		participationPopup.setGlassEnabled(true);
		participationPopup.center();
		participationPopup.setPopupPosition(participationPopup.getAbsoluteLeft() - participationPopup.getOffsetWidth(), participationPopup.getAbsoluteTop() - participationPopup.getOffsetHeight());
		participationPopup.setWidth("auto");
		
		VerticalPanel editor = new VerticalPanel();
		editor.add(new Label("Achtung: " + participant.getForename() + " " + participant.getSurname() + " ist nicht als Teilnehmer eingetragen! Jetzt als Teilnehmer hinzufügen?"));
		
		participationPopup.add(editor);
		
		final CheckBox paidCheckbox = new CheckBox();
		paidCheckbox.setText("Als 'bezahlt' markieren");
		
		HorizontalPanel labelPanel = new HorizontalPanel();
		Label continueLabel = new Label("Teilnehmer hinzufügen");
		continueLabel.setStyleName("clickableLabel");
		
		Label abortLabel = new Label("Abbrechen");
		abortLabel.setStyleName("clickableLabel");
		
		labelPanel.add(continueLabel);
		labelPanel.add(abortLabel);
		labelPanel.setSpacing(5);
		
		editor.add(paidCheckbox);
		editor.add(labelPanel);
		editor.setSpacing(5);
		
		continueLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (paidCheckbox.getValue()) {
					participant.setPaid("Ja");
				}
				participant.setAttended("Ja");
				display.getListProvider().getList().add(participant);
				participationPopup.hide();
				display.getBarcodeTextBox().setText("");
				display.getBarcodeTextBox().setFocus(true);
			}
		});
		
		abortLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				participationPopup.hide();
				display.getBarcodeTextBox().setText("");
				display.getBarcodeTextBox().setFocus(true);
			}
		});
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}
