package de.sportschulApp.client.presenter.admin;

import java.util.ArrayList;
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
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.Event;
import eu.maydu.gwt.validation.client.ValidationProcessor;
import eu.maydu.gwt.validation.client.actions.StyleAction;
import eu.maydu.gwt.validation.client.description.PopupDescription;
import eu.maydu.gwt.validation.client.i18n.ValidationMessages;
import eu.maydu.gwt.validation.client.validators.strings.StringLengthValidator;

public class CreateEventPresenter implements Presenter {
	public interface Display {

		Widget asWidget();

		Boolean checkExaminers();

		void fillForm(Event event);

		LocalizationConstants getConstants();

		HasClickHandlers getCreateEventButtonHandler();

		VerticalPanel getCreateEventPanel();

		TextBox getDateTextBox();

		TextBox getEventCostsTextBox();

		TextBox getEventNameTextBox();

		TextBox getFirstInstructorTextBox();

		Event getFormData();

		TextBox getLocationTextBox();

		ValidationProcessor getValidator();

		VerticalPanel getWrapper();
		
		void setCourses(ArrayList<Course> courses);
	}

	private final LocalizationConstants constants;
	private final Display display;
	private Boolean editItem = false;
	private String eventID;
	private PopupDescription popupDesc;
	private final AdminServiceAsync rpcService;
	private ValidationProcessor validator;

	public CreateEventPresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		constants = display.getConstants();
		bind();
		setupValidation();
	}

	public CreateEventPresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display, String eventID) {
		this.display = display;
		this.rpcService = rpcService;
		constants = display.getConstants();
		this.eventID = eventID;
		editItem = true;
		bind();
		setupValidation();
	}

	private void bind() {
		fetchCourseList();

		display.getCreateEventButtonHandler().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						if (display.getValidator().validate()) {
							if (display.checkExaminers()) {

								Event newEvent = display.getFormData();

								if (!(editItem)) {
									newEvent.setEventID(0);
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

	private void fetchCourseList() {
		rpcService.getCompleteCourseList(new AsyncCallback<ArrayList<Course>>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim abfragen der Kursinformationen.");
			}
			public void onSuccess(ArrayList<Course> result) {
				display.setCourses(result);
				getEventDetails(eventID);
			}
		});
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

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	public void setupValidation() {
		class CustomValidationMessages extends ValidationMessages {

			@Override
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

		validator = display.getValidator();
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

}
