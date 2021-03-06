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
import de.sportschulApp.shared.Belt;
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

		void fillForm(Course course);

		LocalizationConstants getConstants();

		ArrayList<CourseDate> getCourseDates();

		TextBox getCourseNameTextBox();

		ArrayList<CourseTariff> getCourseTariffs();

		DualListBox getDualListBox();

		TextBox getInstructorTextBox();

		TextBox getLocationTextBox();

		HasClickHandlers getSendButtonHandler();

		ValidationProcessor getValidator();

	}

	private LocalizationConstants constants;
	private ArrayList<String> courseBelts = new ArrayList<String>();
	private String courseID;
	private final Display display;
	private Boolean editItem = false;
	private PopupDescription popupDesc;
	private final AdminServiceAsync rpcService;
	private ValidationProcessor validator;


	public CreateCoursePresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		constants = display.getConstants();
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
		constants = display.getConstants();
		editItem = true;
		this.courseID = courseID;
		bind();
		setupValidation();
		getCourseDetails(courseID);
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

	private void fillDualListBox() {
		rpcService.getAvailableBelts(new AsyncCallback<ArrayList<Belt>>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim laden der Gürtelfarben");
			}
			public void onSuccess(ArrayList<Belt> result) {
				ArrayList<Belt> leftTemp = result;
				for(int i = 0; i < leftTemp.size(); i++) {
					for(int j = 0; j < courseBelts.size(); j++) {
						if(leftTemp.get(i) != null) {
							if (result.get(i).getName().equals(courseBelts.get(j))) {
								leftTemp.set(i, null);
							}
						}
					}
				}
				for (int i = 0; i < leftTemp.size(); i++) {
					if(leftTemp.get(i) != null) {
						display.getDualListBox().addLeft(leftTemp.get(i).getName());
					}
				}

				for (int i = 0; i < courseBelts.size(); i++) {
					display.getDualListBox().addRight(courseBelts.get(i));
				}
			}
		});



	}

	private void fillForm() {
		Course course = new Course(0, display.getCourseNameTextBox().getText(), display
				.getInstructorTextBox().getText(), display
				.getLocationTextBox().getText(), display.getCourseDates(), display.getCourseTariffs(), display
				.getDualListBox().getWidgetListRight());

		if (!(editItem)) {
			course.setCourseID(0);
			rpcService.createCourse(course, new AsyncCallback<String>() {
				public void onFailure(Throwable caught) {
					System.out.println("rpc errror");
				}

				public void onSuccess(String result) {
					System.out.println("result: " + result);

					if (result.equals("name alrady used")) {
						display.getCourseNameTextBox().setStyleName(
						"validationFailedBorderCourseName");
						Window.alert(display.getConstants().courseNameUsed());

					} else {
						History.newItem("adminCourseShowCourses");
					}
				}
			});
		} else {
			course.setCourseID(Integer.valueOf(courseID));
			rpcService.updateCourse(course, new AsyncCallback<Void>() {
				public void onFailure(Throwable caught) {
					Window.alert("Editieren des Kurses fehlgeschlagen.");

				}
				public void onSuccess(Void result) {
					History.newItem("adminCourseShowCourses");
				}
			});
		}
	}

	public void getCourseDetails(String courseID) {
		rpcService.getCourseByID(Integer.valueOf(courseID), new AsyncCallback<Course>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim abfragen der Kursdaten.");
			}
			public void onSuccess(Course result) {
				display.fillForm(result);
				courseBelts = result.getBeltColours();
				fillDualListBox();
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
				msgMap.put("courseName", constants.popupHelpCourseName());
				msgMap.put("instructor", constants.popupHelpInstructor());
				msgMap.put("location", constants.popupHelpLocation());

				String temp = msgMap.get(msgKey.trim());
				return temp;
			}
		}

		validator = display.getValidator();
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
}
