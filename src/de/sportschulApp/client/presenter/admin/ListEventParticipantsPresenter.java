package de.sportschulApp.client.presenter.admin;

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
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SingleSelectionModel;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.EventParticipant;

@SuppressWarnings({ "unchecked", "deprecation" })
public class ListEventParticipantsPresenter implements Presenter{
	public interface Display{
		Widget asWidget();
		CellTable<EventParticipant> getCellTable();
		HasClickHandlers getSearchButton();
		TextBox getSearchInput();
		HasValue<String> getSearchQuery();
		HasClickHandlers getShowAllButton();
		HasClickHandlers getSubmitButton();
		void setMemberList(ArrayList<EventParticipant> memberList);
		void setSelectionModel(SingleSelectionModel selectionModel);
		TextBox getBarcodeTextBox();
		HasClickHandlers getScanButton();
		Image getScanImage();
	}

	private final Display display;
	private String eventID;
	private final AdminServiceAsync rpcService;
	private LocalizationConstants constants;
	private DialogBox participantEditor;

	public ListEventParticipantsPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display, String eventID, LocalizationConstants constants) {
		this.display = display;
		this.rpcService = rpcService;
		this.eventID = eventID;
		this.constants = constants;
		bind();
		fetchListData();
	}

	private void bind() {

	}
	
	public void showEventParticipantEditor(String barcode) {
		for (int i = 0; i < display.getCellTable().getDisplayedItems().size(); i++) {
			if (display.getCellTable().getDisplayedItems().get(i).getBarcodeID().equals(barcode)) {
				participantEditor = new DialogBox(true);
				participantEditor.setAnimationEnabled(true);
				participantEditor.setText("Teilnehmer bearbeiten");
				participantEditor.setGlassEnabled(true);
				participantEditor.center();
				participantEditor.setPopupPosition(participantEditor.getAbsoluteLeft() , participantEditor.getAbsoluteTop());
				participantEditor.setWidth("auto");
				
				HorizontalPanel participantNamePanel = new HorizontalPanel();
				Label participantNameLabel = new Label(constants.name() + ": ");
				participantNameLabel.setWidth("43px");
				Label participantName = new Label(display.getCellTable().getDisplayedItems().get(i).getForename() + " " + display.getCellTable().getDisplayedItems().get(i).getSurname());

				participantNamePanel.add(participantNameLabel);
				participantNamePanel.add(participantName);

				HorizontalPanel participantParticipatesPanel = new HorizontalPanel();
				Label participantParticipatesLabel = new Label("Teilnahme: ");
				participantParticipatesLabel.setWidth("190px");
				final CheckBox participatesCheckBox = new CheckBox();
				
				if (display.getCellTable().getDisplayedItems().get(i).getParticipant().equals("Ja")) {
					participatesCheckBox.setValue(true);
				}
				
				participantParticipatesPanel.add(participantParticipatesLabel);
				participantParticipatesPanel.add(participatesCheckBox);
				
				HorizontalPanel participantPaidPanel = new HorizontalPanel();
				Label participantPaidLabel = new Label("Bezahlt? : ");
				participantPaidLabel.setWidth("190px");
				final CheckBox participantPaidCheckBox = new CheckBox();
				
				if (display.getCellTable().getDisplayedItems().get(i).getPaid().equals("Ja")) {
					participantPaidCheckBox.setValue(true);
				}
				
				participantPaidPanel.add(participantPaidLabel);
				participantPaidPanel.add(participantPaidCheckBox);
				
				VerticalPanel participantNotePanel = new VerticalPanel();
				Label participantNoteLabel = new Label(constants.note() + ": ");
				final TextBox participantNoteTextBox = new TextBox();
				participantNoteTextBox.setWidth("200px");
				participantNoteTextBox.addStyleName("participantNote");
				participantNoteTextBox.setText(display.getCellTable().getDisplayedItems().get(i).getNote());
				
				participantNotePanel.add(participantNoteLabel);
				participantNotePanel.add(participantNoteTextBox);
				
				Label saveParticipantButton = new Label(constants.save());
				saveParticipantButton.addStyleName("clickableLabel");
				
				saveParticipantButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						for (int i = 0; i < display.getCellTable().getDisplayedItems().size(); i++) {
							if (display.getCellTable().getDisplayedItems().get(i).getBarcodeID().equals(display.getBarcodeTextBox().getText())) {
								if (participatesCheckBox.getValue() == true) {
									display.getCellTable().getDisplayedItems().get(i).setParticipant("Ja");
								} else {
									display.getCellTable().getDisplayedItems().get(i).setParticipant("Nein");
								}
								
								if (participantPaidCheckBox.getValue() == true) {
									display.getCellTable().getDisplayedItems().get(i).setPaid("Ja");
								} else {
									display.getCellTable().getDisplayedItems().get(i).setPaid("Nein");
								}
								
								if (participantNoteTextBox.getText().length() > 1) {
									display.getCellTable().getDisplayedItems().get(i).setNote(participantNoteTextBox.getText());
								}
							}	
						}
						participantEditor.hide();
						display.getCellTable().redraw();
					}
				});
				
				VerticalPanel participantEditorWrapper = new VerticalPanel();
				
				participantEditorWrapper.add(participantNamePanel);
				participantEditorWrapper.add(participantParticipatesPanel);
				participantEditorWrapper.add(participantPaidPanel);
				participantEditorWrapper.add(participantNotePanel);
				participantEditorWrapper.add(saveParticipantButton);
				
				participantEditor.add(participantEditorWrapper);
				participantEditor.show();
			}
		}
	}

	public void fetchListData() {
		rpcService.getEventParticipants(Integer.valueOf(eventID) , new AsyncCallback<ArrayList<EventParticipant>>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim abrufen der Teilnehmerliste");
			}
			public void onSuccess(ArrayList<EventParticipant> result) {
				display.setMemberList(result);

				display.getSubmitButton().addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						submitParticipants();
					}
				});
				
				display.getBarcodeTextBox().addKeyUpHandler(new KeyUpHandler() {
					public void onKeyUp(KeyUpEvent event) {
						if (event.getNativeKeyCode() == 13) {
							showEventParticipantEditor(display.getBarcodeTextBox().getText());
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
	}

	public Display getDisplay(){
		return display;
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}


	public void submitParticipants() {
		ArrayList<EventParticipant> participants = new ArrayList<EventParticipant>();
		for (int i = 0; i < display.getCellTable().getDisplayedItems().size(); i++) {
			if ((display.getCellTable().getDisplayedItems().get(i).getParticipant().equals("Ja")) || (display.getCellTable().getDisplayedItems().get(i).getPaid().equals("Ja")) || (display.getCellTable().getDisplayedItems().get(i).getNote().length() > 1)) {
				participants.add(display.getCellTable().getDisplayedItems().get(i));
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

}
