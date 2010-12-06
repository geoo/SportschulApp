package de.sportschulApp.client.view.admin;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

import de.sportschulApp.client.presenter.admin.CreateEventPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Event;
import eu.maydu.gwt.validation.client.DefaultValidationProcessor;
import eu.maydu.gwt.validation.client.ValidationProcessor;

public class CreateEventView extends Composite implements
CreateEventPresenter.Display {

	private LocalizationConstants constants;
	private Label costsLabel;
	private TextBox costsTextBox;
	private Button createEventButton;
	private VerticalPanel createEventPanel;
	private DateBox dateBox;
	private Label dateLabel;
	private Label eventNameLabel;
	private TextBox eventNameTextBox;
	private ArrayList<String> examiners = new ArrayList<String>();
	private FlexTable examinersInputFieldsTable = new FlexTable();
	private Label locationLabel;
	private TextBox locationTextBox;
	private RadioButton rb0;
	private RadioButton rb1;
	private Label timeLabel;
	private ListBox timeListBoxHour;
	private ListBox timeListBoxMinutes;
	private DefaultValidationProcessor validator = new DefaultValidationProcessor();
	private VerticalPanel wrapper = new VerticalPanel();

	public CreateEventView(LocalizationConstants constants) {
		this.constants = constants;

		wrapper.addStyleName("createEventWrapper");
		initWidget(wrapper);

		wrapper.add(buildForm());
	}

	private void addExaminersInpuRow(final FlexTable examinersInputFieldsTable, Boolean firstField) {
		final int numRow = examinersInputFieldsTable.getRowCount();

		final TextBox examinerTextBox = new TextBox();
		examinerTextBox.setWidth("210px");

		if ((numRow < examiners.size()) && !(examiners.isEmpty())) {
			examinerTextBox.setValue(examiners.get(numRow));
		} else {
			examiners.add(numRow, "");
		}

		examinerTextBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				examiners.set(numRow, examinerTextBox.getValue());
			}
		});

		if (firstField) {
			examinersInputFieldsTable.setWidget(numRow, 0, new Label(constants.examiner() + ": "));
		}

		examinersInputFieldsTable.setWidget(numRow, 1, examinerTextBox);

		if (!firstField) {
			Image deleteButton = new Image("/imgs/Symbol_Delete.png");
			deleteButton.setStyleName("clickable");

			deleteButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					examiners.remove(numRow);
					refreshExaminersInputTable();
				}
			});
			examinersInputFieldsTable.setWidget(numRow, 2, deleteButton);
		} else {
			HorizontalPanel placeholderPanel = new HorizontalPanel();
			placeholderPanel.setWidth("16px");
			examinersInputFieldsTable.setWidget(numRow, 2, placeholderPanel);
		}
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public VerticalPanel buildForm() {

		HorizontalPanel formHeader = new HorizontalPanel();
		formHeader.addStyleName("formHeader");
		formHeader.add(new Label("Event anlegen"));

		createEventPanel = new VerticalPanel();

		createEventButton = new Button("Event Speichern / Teilnehmer bearbeiten");

		HorizontalPanel eventNameInputPanel = new HorizontalPanel();
		eventNameLabel = new Label(constants.eventName() + ":* ");
		eventNameTextBox = new TextBox();
		eventNameInputPanel.add(eventNameLabel);
		eventNameInputPanel.add(eventNameTextBox);

		HorizontalPanel eventTypePanel = new HorizontalPanel();

		Label eventTypeLabel = new Label(constants.eventType() + ": ");

		rb0 = new RadioButton("eventType", "Prüfung");
		rb1 = new RadioButton("eventType", "Event");
		rb0.setValue(true);

		eventTypePanel.add(eventTypeLabel);
		eventTypePanel.add(rb0);
		eventTypePanel.add(rb1);

		HorizontalPanel dateInputPanel = new HorizontalPanel();
		dateLabel = new Label(constants.date() + ": ");

		DateTimeFormat dateFormat = DateTimeFormat.getFormat("E dd. MMMM yyyy");
		dateBox = new DateBox();
		dateBox.addStyleName("gwt-TextBox");
		dateBox.setFormat(new DateBox.DefaultFormat(dateFormat));

		dateInputPanel.add(dateLabel);
		dateInputPanel.add(dateBox);

		HorizontalPanel timeInputPanel = new HorizontalPanel();
		timeLabel = new Label(constants.time() + " (Stunde/Minute): ");
		timeListBoxHour = new ListBox();
		for (int i = 0; i < 24; i++) {
			if (i < 10) {
				timeListBoxHour.addItem("0" + i);
			} else {
				timeListBoxHour.addItem("" + i);
			}
		}
		timeListBoxMinutes = new ListBox();
		for (int i = 0; i < 60; i++) {
			if (i < 10) {
				timeListBoxMinutes.addItem("0" + i);
			} else {
				timeListBoxMinutes.addItem("" + i);
			}
		}
		timeInputPanel.add(timeLabel);
		timeInputPanel.add(timeListBoxHour);
		timeInputPanel.add(timeListBoxMinutes);

		VerticalPanel examinersInputFieldWrapper = new VerticalPanel();
		examinersInputFieldWrapper.addStyleName("examinersInputFieldWrapper");

		examinersInputFieldsTable.setCellPadding(0);
		examinersInputFieldsTable.setCellSpacing(0);
		examinersInputFieldsTable.addStyleName("tariffInputFieldsTable");

		Label addExaminerFieldLabel = new Label("Weiteren Betreuer hinzufügen");
		addExaminerFieldLabel.addStyleName("clickableLabel");

		addExaminerFieldLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addExaminersInpuRow(examinersInputFieldsTable, false);
			}
		});

		refreshExaminersInputTable();

		examinersInputFieldWrapper.add(examinersInputFieldsTable);
		examinersInputFieldWrapper.add(addExaminerFieldLabel);

		HorizontalPanel locationInputPanel = new HorizontalPanel();
		locationLabel = new Label(constants.location() + ": ");
		locationTextBox = new TextBox();
		locationInputPanel.add(locationLabel);
		locationInputPanel.add(locationTextBox);

		HorizontalPanel costsInputPanel = new HorizontalPanel();
		costsLabel = new Label(constants.costs() + ": ");
		costsTextBox = new TextBox();
		costsInputPanel.add(costsLabel);
		costsInputPanel.add(costsTextBox);

		createEventPanel.add(formHeader);
		createEventPanel.add(eventNameInputPanel);
		createEventPanel.add(eventTypePanel);
		createEventPanel.add(dateInputPanel);
		createEventPanel.add(timeInputPanel);
		createEventPanel.add(locationInputPanel);
		createEventPanel.add(costsInputPanel);
		createEventPanel.add(examinersInputFieldWrapper);
		createEventPanel.add(createEventButton);

		VerticalPanel createEventWrapper = new VerticalPanel();

		createEventWrapper.add(formHeader);
		createEventWrapper.add(createEventPanel);

		return createEventWrapper;
	}

	public Boolean checkExaminers() {
		if (!(examiners.isEmpty())) {
			for (int i = 0; i < examiners.size(); i++) {
				if (examiners.get(i).length() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	public void fillForm(Event event) {
		eventNameTextBox.setText(event.getName());
		costsTextBox.setText(event.getCosts());
		locationTextBox.setText(event.getLocation());
		dateBox.getTextBox().setText(event.getDate());
		examiners = event.getExaminers();

		for (int i = 0; i < timeListBoxHour.getItemCount(); i++) {
			if (timeListBoxHour.getValue(i).equals(event.getTime().substring(0, 2))) {
				timeListBoxHour.setSelectedIndex(i);
			}
		}

		for (int i = 0; i < timeListBoxMinutes.getItemCount(); i++) {
			if (timeListBoxMinutes.getValue(i).equals(event.getTime().substring(3, 5))) {
				timeListBoxMinutes.setSelectedIndex(i);
			}
		}

		refreshExaminersInputTable();

		if (event.getType().equals("Prüfung")) {
			rb0.setValue(true);
		}
		if (event.getType().equals("Event")) {
			rb1.setValue(true);
		}
	}

	public LocalizationConstants getConstants() {
		return constants;
	}

	public HasClickHandlers getCreateEventButtonHandler() {
		return createEventButton;
	}

	public VerticalPanel getCreateEventPanel() {
		return createEventPanel;
	}

	public TextBox getDateTextBox() {
		return dateBox.getTextBox();
	}

	public TextBox getEventCostsTextBox() {
		return costsTextBox;
	}

	public TextBox getEventNameTextBox() {
		return eventNameTextBox;
	}

	public TextBox getFirstInstructorTextBox() {
		return (TextBox) examinersInputFieldsTable.getWidget(0, 1);
	}

	public Event getFormData() {
		Event event = new Event();
		event.setName(eventNameTextBox.getText());
		event.setCosts(costsTextBox.getText());
		event.setLocation(locationTextBox.getText());
		event.setDate(dateBox.getTextBox().getValue());
		event.setTime(timeListBoxHour.getValue(timeListBoxHour.getSelectedIndex()) + ":" + timeListBoxMinutes.getValue(timeListBoxMinutes.getSelectedIndex()));

		ArrayList<String> examinersTemp = new ArrayList<String>();
		for (int i = 0; i < examiners.size(); i++) {
			if (examiners.get(i).length() >= 1) {
				examinersTemp.add(examiners.get(i));
			}
		}
		event.setExaminers(examinersTemp);

		if(rb0.getValue()) {
			event.setType("Prüfung");
		}
		if(rb1.getValue()) {
			event.setType("Event");
		}

		return event;
	}

	public TextBox getLocationTextBox() {
		return locationTextBox;
	}

	public ValidationProcessor getValidator() {
		return validator;
	}

	public VerticalPanel getWrapper() {
		return wrapper;
	}

	private void refreshExaminersInputTable() {
		examinersInputFieldsTable.removeAllRows();

		addExaminersInpuRow(examinersInputFieldsTable, true);

		for (int i = 1; i < examiners.size(); i++) {
			addExaminersInpuRow(examinersInputFieldsTable, false);
		}
	}

}
