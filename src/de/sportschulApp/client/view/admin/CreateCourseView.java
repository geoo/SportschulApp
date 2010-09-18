package de.sportschulApp.client.view.admin;


import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.CreateCoursePresenter;
import de.sportschulApp.client.view.admin.dualListBox.DualListBox;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import eu.maydu.gwt.validation.client.DefaultValidationProcessor;
import eu.maydu.gwt.validation.client.ValidationProcessor;

public class CreateCourseView extends Composite implements
		CreateCoursePresenter.Display {

	private LocalizationConstants constants;
	private DualListBox dualListBox;
	private Label courseNameLabel;
	private TextBox courseNameTextBox;
	private Label timeLabel;
	private TextBox timeTextBox;
	private Label instructorLabel;
	private TextBox instructorTextBox;
	private Label locationLabel;
	private TextBox locationTextBox;
	private Label beltLabel;
	private Button sendButton;
	private DefaultValidationProcessor validator;


	public CreateCourseView(LocalizationConstants constants) {
		this.constants = constants;
		validator = new DefaultValidationProcessor();

		VerticalPanel createCourseWrapper = new VerticalPanel();
		createCourseWrapper.setStyleName("createCourseWrapper");
		initWidget(createCourseWrapper);

		dualListBox = new DualListBox(10, "10em");
		dualListBox.setStyleName("dualListBox");
		
		HorizontalPanel courseNameInputPanel = new HorizontalPanel();
		courseNameLabel = new Label(constants.courseName() + ":* ");
		courseNameTextBox = new TextBox();
		courseNameInputPanel.add(courseNameLabel);
		courseNameInputPanel.add(courseNameTextBox);
		
		HorizontalPanel timeInputPanel = new HorizontalPanel();
		timeLabel = new Label(constants.time() + ": ");
		timeTextBox = new TextBox();
		timeInputPanel.add(timeLabel);
		timeInputPanel.add(timeTextBox);
		
		
		HorizontalPanel instructorInputPanel = new HorizontalPanel();
		instructorLabel = new Label(constants.instructor() + ": ");
		instructorTextBox = new TextBox();
		instructorInputPanel.add(instructorLabel);
		instructorInputPanel.add(instructorTextBox);
		
		
		HorizontalPanel locationInputPanel = new HorizontalPanel();
		locationLabel = new Label(constants.location() + ": ");
		locationTextBox = new TextBox();
		locationInputPanel.add(locationLabel);
		locationInputPanel.add(locationTextBox);
		
		HorizontalPanel beltInputPanel = new HorizontalPanel();
		beltLabel = new Label(constants.belts() + ": ");
		beltInputPanel.add(beltLabel);
		beltInputPanel.add(dualListBox);
		
		createCourseWrapper.add(courseNameInputPanel);
		createCourseWrapper.add(timeInputPanel);
		createCourseWrapper.add(instructorInputPanel);
		createCourseWrapper.add(locationInputPanel);
		createCourseWrapper.add(beltInputPanel);

		
		
		
		
		
		sendButton = new Button(constants.send());
		createCourseWrapper.add(sendButton);


		
	}

	public Widget asWidget() {
		return this;
	}

	public DualListBox getDualListBox() {
		return dualListBox;
	}

	public LocalizationConstants getConstants() {
		return constants;
	}

	public HasClickHandlers getSendButtonHandler() {
		return sendButton;
	}

	public ValidationProcessor getValidator() {
		return validator;
	}

	public TextBox getCourseNameTextBox() {
		return courseNameTextBox;
	}

	public TextBox getTimeTextBox() {
		return timeTextBox;
	}

	public TextBox getInstructorTextBox() {
		return instructorTextBox;
	}

	public TextBox getLocationTextBox() {
		return locationTextBox;
	}

}
