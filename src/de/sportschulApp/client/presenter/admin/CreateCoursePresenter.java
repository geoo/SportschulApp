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
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.admin.dualListBox.DualListBox;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Course;
import eu.maydu.gwt.validation.client.ValidationProcessor;
import eu.maydu.gwt.validation.client.actions.StyleAction;
import eu.maydu.gwt.validation.client.description.PopupDescription;
import eu.maydu.gwt.validation.client.i18n.ValidationMessages;
import eu.maydu.gwt.validation.client.validators.strings.StringLengthValidator;

public class CreateCoursePresenter implements Presenter {
	public interface Display {
		Widget asWidget();

		DualListBox getDualListBox();

		LocalizationConstants getConstants();

		HasClickHandlers getSendButtonHandler();

		ValidationProcessor getValidator();

		TextBox getCourseNameTextBox();

		TextBox getTimeTextBox();

		TextBox getInstructorTextBox();

		TextBox getLocationTextBox();

	}

	private final Display display;
	private final AdminServiceAsync rpcService;
	private LocalizationConstants constants;
	private ValidationProcessor validator;
	private PopupDescription popupDesc;
	private boolean succ = true;

	public CreateCoursePresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		this.constants = display.getConstants();
		bind();
		fillDualListBox();
		setupValidation();
	}

	private void fillDualListBox() {
		display.getDualListBox().addLeft(constants.white());
		display.getDualListBox().addLeft(constants.whiteYellow());
		display.getDualListBox().addLeft(constants.yellowOrange());
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
		Course course = new Course(0, display.getCourseNameTextBox().getText(),
				display.getTimeTextBox().getText(), display
						.getInstructorTextBox().getText(), display
						.getLocationTextBox().getText(), display
						.getDualListBox().getWidgetListRight());

		rpcService.createCourse(course, new AsyncCallback<String>() {

			public void onSuccess(String result) {
				System.out.println("result: " + result);

				if (result.equals("name alrady used")) {
					display.getCourseNameTextBox().setStyleName(
							"validationFailedBorderCourseName");
					Window.alert(display.getConstants().courseNameUsed());

				} else {
					Window.alert(constants.courseCreated());
					History.newItem("adminMembersShowMembers");
				}
			}

			public void onFailure(Throwable caught) {
				System.out.println("rpc errror");
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
				msgMap.put("courseName", constants.popupHelpCourseName());
				msgMap.put("time", constants.popupHelpTime());
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
		popupDesc.addDescription("time", display.getTimeTextBox());
		popupDesc.addDescription("instructor", display.getInstructorTextBox());
		popupDesc.addDescription("location", display.getLocationTextBox());
	}
}
