package de.sportschulApp.client.view.admin;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

import de.sportschulApp.client.presenter.admin.CreateEventPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class CreateEventView extends Composite implements
		CreateEventPresenter.Display {

	private VerticalPanel wrapper;
	private VerticalPanel createEventPanel;
	private VerticalPanel addMemberToEventPanel;
	private LocalizationConstants constants;
	private Button addMemberButton;
	private Button createEventButton;
	private Label eventNameLabel;
	private TextBox eventNameTextBox;
	private Label dateLabel;
	private DatePicker dateDatePicker;
	private Label locationLabel;
	private TextBox locationTextBox;
	private Label timeLabel;
	private ListBox timeListBoxHour;
	private ListBox timeListBoxMinutes;
	private Label costsLabel;
	private TextBox costsTextBox;

	public CreateEventView(LocalizationConstants constants) {
		this.constants = constants;

		wrapper = new VerticalPanel();
		wrapper.addStyleName("createEventWrapper");

		createEventPanel = new VerticalPanel();
		addMemberToEventPanel = new VerticalPanel();

		wrapper.add(createEventPanel);
		initWidget(wrapper);

		addMemberButton = new Button("Test");
		createEventButton = new Button("Test22222");

		HorizontalPanel eventNameInputPanel = new HorizontalPanel();
		eventNameLabel = new Label(constants.eventName() + ":* ");
		eventNameTextBox = new TextBox();
		eventNameInputPanel.add(eventNameLabel);
		eventNameInputPanel.add(eventNameTextBox);

		HorizontalPanel dateInputPanel = new HorizontalPanel();
		dateLabel = new Label(constants.date() + ": ");
		dateDatePicker = new DatePicker();
		dateInputPanel.add(dateLabel);
		dateInputPanel.add(dateDatePicker);

		HorizontalPanel timeInputPanel = new HorizontalPanel();
		timeLabel = new Label(constants.time() + ": ");
		timeListBoxHour = new ListBox();
		timeListBoxHour.addItem(constants.hour());
		for (int i = 0; i < 24; i++) {
			if (i < 10) {
				timeListBoxHour.addItem("0" + i);
			} else {
				timeListBoxHour.addItem("" + i);
			}
		}
		timeListBoxMinutes = new ListBox();
		timeListBoxMinutes.addItem(constants.minutes());
		for (int i = 0; i < 4; i++) {
			if (i == 0) {
				timeListBoxMinutes.addItem("0" + i);
			} else {
				timeListBoxMinutes.addItem("" + (i * 15));
			}
		}
		timeInputPanel.add(timeLabel);
		timeInputPanel.add(timeListBoxHour);
		timeInputPanel.add(timeListBoxMinutes);

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

		
		createEventPanel.add(eventNameInputPanel);
		createEventPanel.add(dateInputPanel);
		createEventPanel.add(timeInputPanel);
		createEventPanel.add(locationInputPanel);
		createEventPanel.add(costsInputPanel);
		createEventPanel.add(addMemberButton);
		addMemberToEventPanel.add(createEventButton);
		
	}

	public Widget asWidget() {
		return this;
	}

	public HasClickHandlers getAddMemberButtonHandler() {
		return addMemberButton;
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

	public VerticalPanel getAddMemberPanel() {
		return addMemberToEventPanel;
	}

	public void addMemberlist(MemberListView presenter) {
		addMemberToEventPanel.add(presenter);

	}

	public LocalizationConstants getConstants() {
		return constants;
	}


}
