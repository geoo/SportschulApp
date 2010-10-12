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
	private ArrayList<String> weekDays = new ArrayList<String>();
	private ArrayList<String> times = new ArrayList<String>();
	private ArrayList<String> tariffNames = new ArrayList<String>();
	private ArrayList<String> tariffCosts = new ArrayList<String>();
	private VerticalPanel createCourseWrapper = new VerticalPanel();
	private FlexTable dateInputFieldsTable = new FlexTable();


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
		VerticalPanel courseForm = new VerticalPanel();
		
		dualListBox.setStyleName("dualListBox");
		
		HorizontalPanel courseNameInputPanel = new HorizontalPanel();
		courseNameLabel = new Label(constants.courseName() + ":* ");
		courseNameInputPanel.add(courseNameLabel);
		courseNameInputPanel.add(courseNameTextBox);
		
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
		courseForm.add(initDateInputArea());
		courseForm.add(initTariffInputArea());
		courseForm.add(instructorInputPanel);
		courseForm.add(locationInputPanel);
		courseForm.add(beltInputPanel);
		
		sendButton.setText(constants.send());
		courseForm.add(sendButton);
		
		return courseForm;
	}
	
	/**
	 * Erstellt den Eingabebereich für Termine.
	 * 
	 * @return Den DateInputfield Wrapper.
	 */
	private VerticalPanel initDateInputArea() {
		VerticalPanel dateInputFieldWrapper = new VerticalPanel();
		dateInputFieldWrapper.addStyleName("dateInputFieldWrapper");
		
		dateInputFieldsTable.setCellPadding(0);
		dateInputFieldsTable.setCellSpacing(0);
		dateInputFieldsTable.addStyleName("dateInputFieldsTable");
		
		addDateInpuRow(dateInputFieldsTable, true);

		for (int i = 1; i < weekDays.size(); i++) {
			addDateInpuRow(dateInputFieldsTable, false);
		}
		
		Label addDateFieldLabel = new Label("Weiteren Termin hinzufügen");
		addDateFieldLabel.addStyleName("clickableLabel");
		
		addDateFieldLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addDateInpuRow(dateInputFieldsTable, false);
			}
		});
		
		dateInputFieldWrapper.add(dateInputFieldsTable);
		dateInputFieldWrapper.add(addDateFieldLabel);
		
		return dateInputFieldWrapper;
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
		
		if ((numRow < this.weekDays.size()) && !(this.weekDays.isEmpty())) {
			for (int i = 0; i < weekDayListBox.getItemCount(); i++) {
				if (weekDayListBox.getValue(i).equals(this.weekDays.get(numRow))) {
					weekDayListBox.setSelectedIndex(i);
				}
			}
		} else {
			this.weekDays.add(numRow, weekDayListBox.getValue(weekDayListBox.getSelectedIndex()));
		}
				
		weekDayListBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				weekDays.set(numRow, weekDayListBox.getValue(weekDayListBox.getSelectedIndex()));
			}
		});
		
		final ListBox timeListBox = new ListBox();
		for(int i = 0; i < 24; i++) {
			timeListBox.addItem(i + ":00 " + constants.clock());
			timeListBox.addItem(i + ":15 " + constants.clock());
			timeListBox.addItem(i + ":30 " + constants.clock());
			timeListBox.addItem(i + ":45 " + constants.clock());
		}
		
		if ((numRow < this.times.size()) && !(this.times.isEmpty())) {
			for (int i = 0; i < timeListBox.getItemCount(); i++) {
				if (timeListBox.getValue(i).equals(this.times.get(numRow))) {
					timeListBox.setSelectedIndex(i);
				}
			}
		} else {
			this.times.add(numRow, timeListBox.getValue(timeListBox.getSelectedIndex()));
		}

		timeListBox.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				times.set(numRow, timeListBox.getValue(timeListBox.getSelectedIndex()));
			}
		});
		
		if (firstField) {
			dateInputFieldsTable.setWidget(numRow, 0, new Label(constants.trainingTime() + ": "));
		}
			
		dateInputFieldsTable.setWidget(numRow, 1, weekDayListBox);
		dateInputFieldsTable.setWidget(numRow, 2, timeListBox);
		
		if (!firstField) {
			Image deleteButton = new Image("/imgs/Symbol_Delete.png");
			deleteButton.setStyleName("clickable");
			
			deleteButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					dateInputFieldsTable.removeCells(numRow, 0,4);
					
					weekDays.set(numRow, null);
					times.set(numRow, null);
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
	 * Erstellt den Eingabebereich für Tarife.
	 * 
	 * @return Den TarifInputfield Wrapper.
	 */
	private VerticalPanel initTariffInputArea() {
		VerticalPanel tariffInputFieldWrapper = new VerticalPanel();
		tariffInputFieldWrapper.addStyleName("tariffInputFieldWrapper");
		
		final FlexTable tariffInputFieldsTable = new FlexTable();
		tariffInputFieldsTable.setCellPadding(0);
		tariffInputFieldsTable.setCellSpacing(0);
		tariffInputFieldsTable.addStyleName("tariffInputFieldsTable");
		
		addTariffInpuRow(tariffInputFieldsTable, true);

		for (int i = 1; i < tariffNames.size(); i++) {
			addTariffInpuRow(tariffInputFieldsTable, false);
		}
		
		Label addDateFieldLabel = new Label("Weiteren Termin hinzufügen");
		addDateFieldLabel.addStyleName("clickableLabel");
		
		addDateFieldLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				addTariffInpuRow(tariffInputFieldsTable, false);
			}
		});
		
		tariffInputFieldWrapper.add(tariffInputFieldsTable);
		tariffInputFieldWrapper.add(addDateFieldLabel);
		
		return tariffInputFieldWrapper;
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
		
		final TextBox tariffNameTextBox = new TextBox();
		tariffNameTextBox.setWidth("150px");
		
		final TextBox tariffCostsTextBox = new TextBox();
		tariffCostsTextBox.setWidth("50px");
		
		tariffNameTextBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				if (tariffNameTextBox.getValue().length() > 0) {
					tariffNames.add(numRow, tariffNameTextBox.getValue());
				}
			}
		});
		
		tariffCostsTextBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				if (tariffCostsTextBox.getValue().length() > 0) {
					tariffCosts.add(numRow, tariffNameTextBox.getValue());
				}
			}
		});
		
		if (firstField) {
			tariffInputFieldsTable.setWidget(numRow, 0, new Label(constants.tariffs() + ": "));
		}
			
		tariffInputFieldsTable.setWidget(numRow, 1, tariffNameTextBox);
		tariffInputFieldsTable.setWidget(numRow, 2, tariffCostsTextBox);
		tariffInputFieldsTable.setWidget(numRow, 3, euroLabel);
		
		if (!firstField) {
			Image deleteButton = new Image("/imgs/Symbol_Delete.png");
			deleteButton.setStyleName("clickable");
			
			deleteButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					tariffInputFieldsTable.removeCells(numRow, 0,4);
					
					tariffNames.set(numRow, null);
					tariffCosts.set(numRow, null);
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
	
	
	public ArrayList<String> getWeekDays() {
		return this.weekDays;
	}
	
	public ArrayList<String> getTimes() {
		return this.times;
	}
	
	public ArrayList<String> getTariffNames() {
		return this.tariffNames;
	}
	
	public ArrayList<String> getTariffCosts() {
		return this.tariffCosts;
	}
	
	public void fillForm(Course course) {
		this.courseNameTextBox.setText(course.getName());
		this.instructorTextBox.setText(course.getInstructor());
		this.locationTextBox.setText(course.getLocation());
		this.weekDays = course.getWeekDays();
		this.times = course.getTimes();
		
		
		this.createCourseWrapper.add(buildForm());
	}
}
