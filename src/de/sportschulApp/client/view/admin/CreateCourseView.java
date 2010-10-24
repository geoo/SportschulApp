package de.sportschulApp.client.view.admin;


import java.util.ArrayList;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.CreateCoursePresenter;
import de.sportschulApp.client.view.admin.dualListBox.DualListBox;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.CourseDate;
import de.sportschulApp.shared.CourseTariff;
import eu.maydu.gwt.validation.client.DefaultValidationProcessor;
import eu.maydu.gwt.validation.client.ValidationProcessor;

public class CreateCourseView extends Composite implements
		CreateCoursePresenter.Display {

	private LocalizationConstants constants;
	private DualListBox dualListBox = new DualListBox(10, "10em");;
	private Label courseNameLabel;
	private TextBox courseNameTextBox = new TextBox();
	private Label instructorLabel;
	private TextBox instructorTextBox = new TextBox();
	private Label locationLabel;
	private TextBox locationTextBox = new TextBox();
	private Label beltLabel;
	private Button sendButton = new Button();
	private DefaultValidationProcessor validator = new DefaultValidationProcessor();
	private ArrayList<CourseDate> courseDates = new ArrayList<CourseDate>();
	private ArrayList<CourseTariff> courseTariffs = new ArrayList<CourseTariff>();
	private VerticalPanel createCourseWrapper = new VerticalPanel();
	private FlexTable dateInputFieldsTable = new FlexTable();
	private FlexTable tariffInputFieldsTable = new FlexTable();

	public CreateCourseView(LocalizationConstants constants, Boolean newItem) {
		this.constants = constants;

		createCourseWrapper.setStyleName("createCourseWrapper");
		initWidget(createCourseWrapper);
		
		if (newItem) {
			createCourseWrapper.add(buildForm());
		}
	}
	
	/**
	 * Erstellt Das Formular für create/edit Course.
	 * 
	 * @return courseForm Das Formular.
	 */
	private VerticalPanel buildForm() {
		VerticalPanel courseFormWrapper = new VerticalPanel();
		VerticalPanel courseForm = new VerticalPanel();
		courseForm.addStyleName("createCourseForm");
		
		HorizontalPanel formHeader = new HorizontalPanel();
		formHeader.setStyleName("formHeader");
		
		formHeader.add(new Label(constants.createEditCourse()));
		
		dualListBox.setStyleName("dualListBox");
		
		HorizontalPanel courseNameInputPanel = new HorizontalPanel();
		courseNameLabel = new Label(constants.courseName() + ":* ");
		courseNameInputPanel.add(courseNameLabel);
		courseNameInputPanel.add(courseNameTextBox);
		
		VerticalPanel dateInputFieldWrapper = new VerticalPanel();
		dateInputFieldWrapper.addStyleName("dateInputFieldWrapper");
		
		dateInputFieldsTable.setCellPadding(0);
		dateInputFieldsTable.setCellSpacing(0);
		dateInputFieldsTable.addStyleName("dateInputFieldsTable");
		
		Label addDateFieldLabel = new Label("Weiteren Termin hinzufügen");
		addDateFieldLabel.addStyleName("clickableLabel");
		
		addDateFieldLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addDateInpuRow(dateInputFieldsTable, false);
			}
		});

		refreshDateInputTable();
		
		dateInputFieldWrapper.add(dateInputFieldsTable);
		dateInputFieldWrapper.add(addDateFieldLabel);
		
		VerticalPanel tariffInputFieldWrapper = new VerticalPanel();
		tariffInputFieldWrapper.addStyleName("tariffInputFieldWrapper");
		
		tariffInputFieldsTable.setCellPadding(0);
		tariffInputFieldsTable.setCellSpacing(0);
		tariffInputFieldsTable.addStyleName("tariffInputFieldsTable");
		
		Label addTariffFieldLabel = new Label("Weiteren Tarif hinzufügen");
		addTariffFieldLabel.addStyleName("clickableLabel");
		
		addTariffFieldLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addTariffInpuRow(tariffInputFieldsTable, false);
			}
		});
		
		refreshTariffInputTable();
		
		tariffInputFieldWrapper.add(tariffInputFieldsTable);
		tariffInputFieldWrapper.add(addTariffFieldLabel);

		
		HorizontalPanel instructorInputPanel = new HorizontalPanel();
		instructorLabel = new Label(constants.instructor() + ": ");
		instructorInputPanel.add(instructorLabel);
		instructorInputPanel.add(instructorTextBox);
		
		HorizontalPanel locationInputPanel = new HorizontalPanel();
		locationLabel = new Label(constants.location() + ": ");
		locationInputPanel.add(locationLabel);
		locationInputPanel.add(locationTextBox);
		
		HorizontalPanel beltInputPanel = new HorizontalPanel();
		beltLabel = new Label(constants.belts() + ": ");
		beltInputPanel.add(beltLabel);
		beltInputPanel.add(dualListBox);
		
		
		courseForm.add(courseNameInputPanel);
		courseForm.add(dateInputFieldWrapper);
		courseForm.add(tariffInputFieldWrapper);
		courseForm.add(instructorInputPanel);
		courseForm.add(locationInputPanel);
		courseForm.add(beltInputPanel);
		
		sendButton.setText(constants.send());
		courseForm.add(sendButton);
		
		courseFormWrapper.add(formHeader);
		courseFormWrapper.add(courseForm);
		
		
		return courseFormWrapper;
	}
	
	private void refreshDateInputTable() {
		dateInputFieldsTable.removeAllRows();
		
		addDateInpuRow(dateInputFieldsTable, true);

		for (int i = 1; i < courseDates.size(); i++) {
			addDateInpuRow(dateInputFieldsTable, false);
		}
	}
	
	private void refreshTariffInputTable() {
		tariffInputFieldsTable.removeAllRows();
		
		addTariffInpuRow(tariffInputFieldsTable, true);

		for (int i = 1; i < courseTariffs.size(); i++) {
			addTariffInpuRow(tariffInputFieldsTable, false);
		}
	}
	
	/**
	 * Erstellt eine neue Zeile zum auswählen einer Trainigszeit.
	 * 
	 * @param dateInputFieldsTable	Die FlexTable für die Eingabebereiche.
	 * @param firstField 		Gibt an ob es das erste Feld in der Tabelle ist.
	 * @return Die neue Trainingszeitzeile.
	 */
	private void addDateInpuRow(final FlexTable dateInputFieldsTable, Boolean firstField) {
		final int numRow = dateInputFieldsTable.getRowCount();
		
		final ListBox weekDayListBox = new ListBox();
		weekDayListBox.addItem(constants.monday());
		weekDayListBox.addItem(constants.tuesday());
		weekDayListBox.addItem(constants.wednesday());
		weekDayListBox.addItem(constants.thursday());
		weekDayListBox.addItem(constants.friday());
		weekDayListBox.addItem(constants.saturday());
		weekDayListBox.addItem(constants.sunday());

		final ListBox timeListBox = new ListBox();
		for(int i = 0; i < 24; i++) {
			timeListBox.addItem(i + ":00 " + constants.clock());
			timeListBox.addItem(i + ":15 " + constants.clock());
			timeListBox.addItem(i + ":30 " + constants.clock());
			timeListBox.addItem(i + ":45 " + constants.clock());
		}
		
		if ((numRow < this.courseDates.size()) && !(this.courseDates.isEmpty())) {
			for (int i = 0; i < weekDayListBox.getItemCount(); i++) {
				if (weekDayListBox.getValue(i).equals(this.courseDates.get(numRow).getWeekDay())) {
					weekDayListBox.setSelectedIndex(i);
				}
			}
			for (int i = 0; i < timeListBox.getItemCount(); i++) {
				if (timeListBox.getValue(i).equals(this.courseDates.get(numRow).getTime())) {
					timeListBox.setSelectedIndex(i);
				}
			}
		} else {
			this.courseDates.add(numRow, new CourseDate(weekDayListBox.getValue(weekDayListBox.getSelectedIndex()), 
					timeListBox.getValue(timeListBox.getSelectedIndex())));
		}
		
		weekDayListBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				courseDates.set(numRow, new CourseDate(weekDayListBox.getValue(weekDayListBox.getSelectedIndex()), 
						timeListBox.getValue(timeListBox.getSelectedIndex())));
			}
		});

		timeListBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				courseDates.set(numRow, new CourseDate(weekDayListBox.getValue(weekDayListBox.getSelectedIndex()), 
						timeListBox.getValue(timeListBox.getSelectedIndex())));
			}
		});
		
		if (firstField) {
			dateInputFieldsTable.setWidget(numRow, 0, new Label(constants.trainingTime() + ": "));
		}
			
		dateInputFieldsTable.setWidget(numRow, 1, weekDayListBox);
		dateInputFieldsTable.setWidget(numRow, 2, timeListBox);
		
		if (!firstField) {
			final Image deleteButton = new Image("/imgs/Symbol_Delete.png");
			deleteButton.setStyleName("clickable");
			
			deleteButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					courseDates.remove(numRow);
					refreshDateInputTable();
				}
			});

			dateInputFieldsTable.setWidget(numRow, 3, deleteButton);
		} else {
			HorizontalPanel placeholderPanel = new HorizontalPanel();
			placeholderPanel.setWidth("16px");
			dateInputFieldsTable.setWidget(numRow, 3, placeholderPanel);
		}
	}
	
	/**
	 * Erstellt eine neue Zeile zum anlegen eines Trainigstarifs.
	 * 
	 * @param tariffInputFieldsTable	Die FlexTable für die Eingabebereiche.
	 * @param firstField 		Gibt an ob es das erste Feld in der Tabelle ist.
	 * @return Die neue Tarifzeile.
	 */
	private void addTariffInpuRow(final FlexTable tariffInputFieldsTable, Boolean firstField) {
		final int numRow = tariffInputFieldsTable.getRowCount();
		
		Label euroLabel = new Label("€");
		euroLabel.setWidth("10px");
		
		final TextBox tariffCostsTextBox = new TextBox();
		tariffCostsTextBox.setValue("0");
		tariffCostsTextBox.setWidth("50px");
		
		final ListBox tariffPresenceListBox = new ListBox();
		for (int i = 1; i <= 20; i++) {
			tariffPresenceListBox.addItem(i + " mal / Monat");
		}
		
		if ((numRow < this.courseTariffs.size()) && !(this.courseTariffs.isEmpty())) {
			for (int i = 0; i < tariffPresenceListBox.getItemCount(); i++) {
				if (tariffPresenceListBox.getValue(i).equals(this.courseTariffs.get(numRow).getName())) {
					tariffPresenceListBox.setSelectedIndex(i);
				}
			}
			tariffCostsTextBox.setValue(this.courseTariffs.get(numRow).getCosts());
		} else {
			this.courseTariffs.add(numRow, new CourseTariff(tariffPresenceListBox.getValue(tariffPresenceListBox.getSelectedIndex()), 
					tariffCostsTextBox.getValue()));
		}
		
		tariffPresenceListBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				courseTariffs.set(numRow, new CourseTariff(tariffPresenceListBox.getValue(tariffPresenceListBox.getSelectedIndex()), 
						tariffCostsTextBox.getValue()));
			}
		});
		
		tariffCostsTextBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
					courseTariffs.set(numRow, new CourseTariff(tariffPresenceListBox.getValue(tariffPresenceListBox.getSelectedIndex()), 
							tariffCostsTextBox.getValue()));
			}
		});
		
		if (firstField) {
			tariffInputFieldsTable.setWidget(numRow, 0, new Label(constants.tariffs() + ": "));
		}
			
		tariffInputFieldsTable.setWidget(numRow, 1, tariffPresenceListBox);
		tariffInputFieldsTable.setWidget(numRow, 2, tariffCostsTextBox);
		tariffInputFieldsTable.setWidget(numRow, 3, euroLabel);
		
		if (!firstField) {
			Image deleteButton = new Image("/imgs/Symbol_Delete.png");
			deleteButton.setStyleName("clickable");
			
			deleteButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					courseTariffs.remove(numRow);
					refreshTariffInputTable();
				}
			});
			tariffInputFieldsTable.setWidget(numRow, 4, deleteButton);
		} else {
			HorizontalPanel placeholderPanel = new HorizontalPanel();
			placeholderPanel.setWidth("16px");
			tariffInputFieldsTable.setWidget(numRow, 4, placeholderPanel);
		}
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

	public TextBox getInstructorTextBox() {
		return instructorTextBox;
	}

	public TextBox getLocationTextBox() {
		return locationTextBox;
	}
	
	
	public ArrayList<CourseDate> getCourseDates() {
		return this.courseDates;
	}
	
	public ArrayList<CourseTariff> getCourseTariffs() {
		return this.courseTariffs;
	}
	
	public void fillForm(Course course) {
		this.courseNameTextBox.setText(course.getName());
		this.instructorTextBox.setText(course.getInstructor());
		this.locationTextBox.setText(course.getLocation());
		this.courseDates = course.getCourseDates();
		this.courseTariffs = course.getCourseTariffs();
		
		
		this.createCourseWrapper.add(buildForm());
	}
}
