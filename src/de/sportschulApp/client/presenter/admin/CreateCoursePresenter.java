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
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.admin.dualListBox.DualListBox;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.CourseDate;
import de.sportschulApp.shared.CourseTariff;
import eu.maydu.gwt.validation.client.ValidationProcessor;
import eu.maydu.gwt.validation.client.actions.StyleAction;
import eu.maydu.gwt.validation.client.description.PopupDescription;
import eu.maydu.gwt.validation.client.i18n.ValidationMessages;
import eu.maydu.gwt.validation.client.validators.strings.StringLengthValidator;

@SuppressWarnings("unchecked")
public class CreateCoursePresenter implements Presenter {
	public interface Display {
		Widget asWidget();

		DualListBox getDualListBox();

		LocalizationConstants getConstants();

		HasClickHandlers getSendButtonHandler();

		ValidationProcessor getValidator();

		TextBox getCourseNameTextBox();

		TextBox getInstructorTextBox();

		TextBox getLocationTextBox();
		
		ArrayList<CourseDate> getCourseDates();
		
		ArrayList<CourseTariff> getCourseTariffs();
		
		void fillForm(Course course);

	}

	private final Display display;
	private final AdminServiceAsync rpcService;
	private LocalizationConstants constants;
	private ValidationProcessor validator;
	private PopupDescription popupDesc;
	private Boolean editItem = false;
	private String courseID;
	
	public CreateCoursePresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		this.constants = display.getConstants();
		bind();
		fillDualListBox();
		setupValidation();
	}
	
	/**
	 * Konstruktor für den EditCourseView
	 * 
	 * @param rpcService
	 * @param eventBus
	 * @param display
	 * @param courseID
	 */
	public CreateCoursePresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display, String courseID) {
		this.display = display;
		this.rpcService = rpcService;
		this.constants = display.getConstants();
		this.editItem = true;
		this.courseID = courseID;
		bind();
		fillDualListBox();
		setupValidation();
		getCourseDetails(courseID);
	}

	private void fillDualListBox() {
		display.getDualListBox().addLeft(constants.orangeGreen());
		display.getDualListBox().addLeft(constants.green() + "1");
		display.getDualListBox().addLeft(constants.green() + "2");
		display.getDualListBox().addLeft(constants.blue() + "1");
		display.getDualListBox().addLeft(constants.blue() + "2");
		display.getDualListBox().addLeft(constants.brown() + "1");
		display.getDualListBox().addLeft(constants.brown() + "2");
		display.getDualListBox().addLeft(constants.black());
		display.getDualListBox().addLeft(constants.brown());
		display.getDualListBox().addLeft(constants.red());
		display.getDualListBox().addRight("test");
	}

	private void bind() {

		display.getSendButtonHandler().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				boolean success = display.getValidator().validate();
				if (success) {
					System.out.println("validation success");
					fillForm();
				} else {
					System.out.println("validation error");
					Window.alert("Bitte überprüfen Sie ihre Eingaben");

					// One (or more) validations failed. The actions will have
					// been
					// already invoked by the validator.validate() call.
				}
			}

		});
	}

	private void fillForm() {
		Course course = new Course(0, display.getCourseNameTextBox().getText(), display
						.getInstructorTextBox().getText(), display
						.getLocationTextBox().getText(), this.display.getCourseDates(), this.display.getCourseTariffs(), display
						.getDualListBox().getWidgetListRight());
		
		if (!(this.editItem)) {
			rpcService.createCourse(course, new AsyncCallback<String>() {
				public void onSuccess(String result) {
					System.out.println("result: " + result);
	
					if (result.equals("name alrady used")) {
						display.getCourseNameTextBox().setStyleName(
								"validationFailedBorderCourseName");
						Window.alert(display.getConstants().courseNameUsed());
	
					} else {
						Window.alert(constants.courseCreated());
						History.newItem("adminCourseShowCourses");
					}
				}
	
				public void onFailure(Throwable caught) {
					System.out.println("rpc errror");
				}
			});
		} else {
			course.setCourseID(Integer.valueOf(this.courseID));
			rpcService.updateCourse(course, new AsyncCallback<Void>() {
				public void onSuccess(Void result) {
					Window.alert("Editieren des Kurses erfolgreich");
					History.newItem("adminCourseShowCourses");
				}
				public void onFailure(Throwable caught) {
					Window.alert("Editieren des Kurses fehlgeschlagen.");
					
				}
			});
		}
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	
	public void setupValidation() {
		class CustomValidationMessages extends ValidationMessages {

			public String getDescriptionMessage(String msgKey) {
				HashMap<String, String> msgMap = new HashMap<String, String>();
				msgMap.put("courseName", constants.popupHelpCourseName());
				msgMap.put("instructor", constants.popupHelpInstructor());
				msgMap.put("location", constants.popupHelpLocation());

				String temp = msgMap.get(msgKey.trim());
				return temp;
			}
		}

		this.validator = display.getValidator();
		ValidationMessages messages = new CustomValidationMessages();

		popupDesc = new PopupDescription(messages);

		validator
				.addValidators(
						"courseName",
						new StringLengthValidator(display
								.getCourseNameTextBox(), 2, 30)
								.addActionForFailure(new StyleAction(
										"validationFailedBorder")));

		popupDesc.addDescription("courseName", display.getCourseNameTextBox());
		popupDesc.addDescription("instructor", display.getInstructorTextBox());
		popupDesc.addDescription("location", display.getLocationTextBox());
	}
	
	public void getCourseDetails(String courseID) {
		rpcService.getCourseByID(Integer.valueOf(courseID), new AsyncCallback<Course>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim abfragen der Kursdaten.");
			}
			public void onSuccess(Course result) {
				display.fillForm(result);
			}
		});
	}
}
