package de.sportschulApp.client.presenter.admin;

import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.admin.ListMemberView;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.Event;
import eu.maydu.gwt.validation.client.DefaultValidationProcessor;
import eu.maydu.gwt.validation.client.ValidationProcessor;
import eu.maydu.gwt.validation.client.actions.StyleAction;
import eu.maydu.gwt.validation.client.description.PopupDescription;
import eu.maydu.gwt.validation.client.i18n.ValidationMessages;
import eu.maydu.gwt.validation.client.validators.strings.StringLengthValidator;

public class CreateEventPresenter implements Presenter {
	public interface Display {

		Widget asWidget();

		VerticalPanel getWrapper();

		VerticalPanel getCreateEventPanel();

		HasClickHandlers getCreateEventButtonHandler();
		
		LocalizationConstants getConstants();
		
		ValidationProcessor getValidator();
		
		TextBox getEventNameTextBox();
		
		TextBox getDateTextBox();
		
		TextBox getLocationTextBox();
		
		TextBox getEventCostsTextBox();
		
		TextBox getFirstInstructorTextBox();
		
		Boolean checkExaminers();
		
		Event getFormData();
		
		void fillForm(Event event);
	}

	private final Display display;
	private final AdminServiceAsync rpcService;
	private final HandlerManager eventBus;
	private ValidationProcessor validator;
	private PopupDescription popupDesc;
	private final LocalizationConstants constants;
	private String eventID;
	private Boolean editItem = false;

	public CreateEventPresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.constants = display.getConstants();
		bind();		
		setupValidation();
	}

	public CreateEventPresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display, String eventID) {
		this.display = display;
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.constants = display.getConstants();
		this.eventID = eventID;
		this.editItem = true;
		bind();		
		setupValidation();
		getEventDetails(eventID);
	}

	private void bind() {

		display.getCreateEventButtonHandler().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						if (display.getValidator().validate()) {
							if (display.checkExaminers()) {
								
								Event newEvent = display.getFormData();
		
								if (!(editItem)) {
									rpcService.createEvent(newEvent, new AsyncCallback<Integer>() {								
										@Override
										public void onFailure(Throwable caught) {
											Window.alert("Anlegen des Events fehlgeschlagen!");
										}
										@Override
										public void onSuccess(Integer result) {
											History.newItem("adminEventsEditParticipants:" + result);
										}
									});
								} else {
									newEvent.setEventID(Integer.valueOf(eventID));
									
									rpcService.updateEvent(newEvent, new AsyncCallback<Integer>() {
										@Override
										public void onFailure(Throwable caught) {
											Window.alert("Bearbeiten des Events fehlgeschlagen!");
										}
										@Override
										public void onSuccess(Integer result) {
											History.newItem("adminEventsEditParticipants:" + result);
										}
									});
								}
							} else {
								Window.alert("Geben sie mindestens einen Betreuer an");
							}
						} else {
							Window.alert("Bitte überprüfen Sie ihre Eingaben");
						}
					}
				});
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	public void setupValidation() {
		class CustomValidationMessages extends ValidationMessages {

			public String getDescriptionMessage(String msgKey) {
				HashMap<String, String> msgMap = new HashMap<String, String>();
				msgMap.put("eventName", constants.popupHelpEventName());
				msgMap.put("eventLocation", constants.popupHelpEventLocation());
				msgMap.put("eventDate", constants.popupHelpEventDate());
				msgMap.put("eventCosts", constants.popupHelpEventCosts());

				String temp = msgMap.get(msgKey.trim());
				return temp;
			}
		}

		this.validator = display.getValidator();
		ValidationMessages messages = new CustomValidationMessages();

		popupDesc = new PopupDescription(messages);

		validator.addValidators("eventName",new StringLengthValidator(display.getEventNameTextBox(), 2, 30).addActionForFailure(new StyleAction("validationFailedBorder")));
		popupDesc.addDescription("eventName", display.getEventNameTextBox());
		
		validator.addValidators("eventDate", new StringLengthValidator(display.getDateTextBox(), 2, 30).addActionForFailure(new StyleAction("validationFailedBorder")));
		
		validator.addValidators("eventLocation", new StringLengthValidator(display.getLocationTextBox(), 2, 30).addActionForFailure(new StyleAction("validationFailedBorder")));
		popupDesc.addDescription("eventLocation", display.getLocationTextBox());
		
		validator.addValidators("eventCosts", new StringLengthValidator(display.getEventCostsTextBox(), 2, 30).addActionForFailure(new StyleAction("validationFailedBorder")));
		popupDesc.addDescription("eventCosts", display.getEventCostsTextBox());
	}
	
	public void getEventDetails(String eventID) {
		rpcService.getEventByEventID(Integer.valueOf(eventID), new AsyncCallback<Event>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim abfragen der Eventdaten.");
			}
			@Override
			public void onSuccess(Event result) {
				display.fillForm(result);
			}
		});
	}

}
