package de.sportschulApp.client.view.admin;

import java.sql.Time;
import java.util.ArrayList;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
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
import com.google.gwt.user.datepicker.client.DatePicker;

import de.sportschulApp.shared.CourseTariff;
import de.sportschulApp.shared.Event;

import de.sportschulApp.client.presenter.admin.CreateEventPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import eu.maydu.gwt.validation.client.DefaultValidationProcessor;
import eu.maydu.gwt.validation.client.ValidationProcessor;

public class CreateEventView extends Composite implements
		CreateEventPresenter.Display {

	private VerticalPanel wrapper = new VerticalPanel();
	private VerticalPanel createEventPanel;
	private LocalizationConstants constants;
	private Button createEventButton;
	private Label eventNameLabel;
	private TextBox eventNameTextBox;
	private Label dateLabel;
	private DateBox dateBox;
	private Label locationLabel;
	private TextBox locationTextBox;
	private Label timeLabel;
	private ListBox timeListBoxHour;
	private ListBox timeListBoxMinutes;
	private String eventHour = null;
	private String eventMinutes = null;
	private Label costsLabel;
	private TextBox costsTextBox;
	private RadioButton rb0;
	private RadioButton rb1;
	private DefaultValidationProcessor validator = new DefaultValidationProcessor();
	private ArrayList<String> examiners = new ArrayList<String>();
	private FlexTable examinersInputFieldsTable = new FlexTable();

	public CreateEventView(LocalizationConstants constants) {
		this.constants = constants;

		wrapper.addStyleName("createEventWrapper");
		initWidget(wrapper);
		
		wrapper.add(buildForm());
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
		
		DateTimeFormat dateFormat = DateTimeFormat.getLongDateFormat();
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
	
	private void refreshExaminersInputTable() {
		examinersInputFieldsTable.removeAllRows();
		
		addExaminersInpuRow(examinersInputFieldsTable, true);

		for (int i = 1; i < examiners.size(); i++) {
			addExaminersInpuRow(examinersInputFieldsTable, false);
		}
	}
	
	private void addExaminersInpuRow(final FlexTable examinersInputFieldsTable, Boolean firstField) {
		final int numRow = examinersInputFieldsTable.getRowCount();
		
		final TextBox examinerTextBox = new TextBox();
		examinerTextBox.setWidth("210px");
		
		if ((numRow < this.examiners.size()) && !(this.examiners.isEmpty())) {
			examinerTextBox.setValue(this.examiners.get(numRow));
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

	public Widget asWidget() {
		return this;
	}

	public HasClickHandlers getCreateEventButtonHandler() {
		return createEventButton;
	}

	public VerticalPanel getWrapper() {
		return wrapper;
	}

	public VerticalPanel getCreateEventPanel() {
		return createEventPanel;
	}

	public LocalizationConstants getConstants() {
		return constants;
	}
	
	public ValidationProcessor getValidator() {
		return this.validator;
	}
	
	public TextBox getEventNameTextBox() {
		return this.eventNameTextBox;
	}
	
	public TextBox getDateTextBox() {
		return this.dateBox.getTextBox();
	}
	
	public TextBox getLocationTextBox() {
		return this.locationTextBox;
	}
	
	public TextBox getEventCostsTextBox() {
		return this.costsTextBox;
	}
	
	public TextBox getFirstInstructorTextBox() {
		return (TextBox) this.examinersInputFieldsTable.getWidget(0, 1);
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
	
	public Event getFormData() {
		Event event = new Event();
		event.setName(this.eventNameTextBox.getText());
		event.setCosts(this.costsTextBox.getText());
		event.setLocation(this.locationTextBox.getText());
		event.setDate(this.dateBox.getTextBox().getValue());
		event.setTime(this.timeListBoxHour.getValue(this.timeListBoxHour.getSelectedIndex()) + ":" + this.timeListBoxMinutes.getValue(this.timeListBoxMinutes.getSelectedIndex()));
		
		ArrayList<String> examinersTemp = new ArrayList<String>();
		for (int i = 0; i < examiners.size(); i++) {
			if (examiners.get(i).length() >= 1) {
				examinersTemp.add(examiners.get(i));
			}
		}
		event.setExaminers(examinersTemp);
		
		if(this.rb0.getValue()) {
			event.setType("exam");
		}
		if(this.rb1.getValue()) {
			event.setType("event");
		}	
		
		return event;
	}
	
	public void fillForm(Event event) {
		this.eventNameTextBox.setText(event.getName());
		this.costsTextBox.setText(event.getCosts());
		this.locationTextBox.setText(event.getLocation());
		this.dateBox.getTextBox().setText(event.getDate());
		this.examiners = event.getExaminers();
			
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
		
		if (event.getType().equals("exam")) {
			rb0.setValue(true);
		}
		if (event.getType().equals("event")) {
			rb1.setValue(true);
		}
	}

}
