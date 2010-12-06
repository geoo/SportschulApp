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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.User;
import eu.maydu.gwt.validation.client.ValidationProcessor;
import eu.maydu.gwt.validation.client.actions.StyleAction;
import eu.maydu.gwt.validation.client.description.PopupDescription;
import eu.maydu.gwt.validation.client.i18n.ValidationMessages;
import eu.maydu.gwt.validation.client.validators.strings.StringLengthValidator;

/**
 * Die Presenter Klasse für die zugehörige View
 * 
 * 
 */
public class CreateUserPresenter implements Presenter {

	/**
	 * Das Interface für die zugehörige View Hier müssen alle Methoden angegeben
	 * werden die die View zur Verfügung stellen muss
	 * 
	 * 
	 */
	public interface Display {
		// standard
		Widget asWidget();

		LocalizationConstants getConstants();

		TextBox getForenameTextBox();

		TextBox getPasswordConfirmTextBox();

		TextBox getPasswordTextBox();

		ListBox getPermissionListbox();

		HasClickHandlers getSaveButton();

		TextBox getSurnameTextBox();

		TextBox getUsernameTextBox();

		ValidationProcessor getValidator();

	}

	private LocalizationConstants constants;
	// datenfelder - presenter
	private final Display display;
	private boolean editable = false;
	private PopupDescription popupDesc;
	private final AdminServiceAsync rpcService;
	private int userID;
	private ValidationProcessor validator;

	// konstruktor - presenter
	public CreateUserPresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		constants = display.getConstants();
		bind();
		setupValidation();
	}

	/**
	 * Konstruktor für den EditUserView
	 * 
	 * @param rpcService
	 * @param eventBus
	 * @param display
	 * @param userID
	 */
	public CreateUserPresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display, String userID) {
		this.display = display;
		this.rpcService = rpcService;
		constants = display.getConstants();
		bind();
		setupValidation();
		editable = true;
		this.userID = Integer.parseInt(userID);
		getUser(Integer.parseInt(userID));
	}

	/**
	 * versieht die relevanten view komponenten mit handlern und gibt der view
	 * somit funktionalität
	 */
	private void bind() {
		// save button
		display.getSaveButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boolean success = display.getValidator().validate();
				if (success) {
					if (!(display.getPasswordTextBox().getText().equals(display
							.getPasswordConfirmTextBox().getText()))) {
						display.getPasswordTextBox().setStyleName(
						"userValidationFailedBorder");
						display.getPasswordConfirmTextBox().setStyleName(
						"userValidationFailedBorder");

					}
					if ((display.getPasswordTextBox().getText().equals(display
							.getPasswordConfirmTextBox().getText()))) {
						display.getPasswordTextBox().setStyleName(
						"userValidationPassedBorder");
						display.getPasswordConfirmTextBox().setStyleName(
						"userValidationPassedBorder");
					}
					if (display.getPermissionListbox().getSelectedIndex() == 0) {
						Window.alert(constants.selectPermission());
					} else {
						fillForm();
					}
				}
			}

		});
	}

	private void fillForm() {
		User user = new User();
		user.setForename(display.getForenameTextBox().getText());
		user.setSurname(display.getSurnameTextBox().getText());
		user.setPassword(display.getPasswordTextBox().getText());
		user.setUsername(display.getUsernameTextBox().getText());

		if (display.getPermissionListbox().getSelectedIndex() == 1) {
			user.setPermission("trainer");
		} else {
			user.setPermission("admin");
		}

		if (!editable) {
			rpcService.saveUser(user, new AsyncCallback<String>() {

				public void onFailure(Throwable caught) {
				}

				public void onSuccess(String result) {
					if (result.equals("Benutzername schon vorhanden")) {
						Window.alert(constants.usernameAlreadyTaken());
						display.getUsernameTextBox().setStyleName(
						"userValidationFailedBorder");
					}
					History.newItem("adminSystemShowUsers");

				}
			});
		} else {

			user.setUserID(userID);

			rpcService.changeUser(user, new AsyncCallback<String>() {

				public void onFailure(Throwable caught) {
				}

				public void onSuccess(String result) {
					if (result.equals("Benutzername schon vorhanden")) {
						Window.alert(constants.usernameAlreadyTaken());
						display.getUsernameTextBox().setStyleName(
						"userValidationFailedBorder");
					}
					History.newItem("adminSystemShowUsers");

				}
			});
		}
	}

	private void fillTextBoxes(User user) {
		display.getUsernameTextBox().setText(user.getUsername());
		display.getForenameTextBox().setText(user.getForename());
		display.getSurnameTextBox().setText(user.getSurname());
		if (user.getPermission().equals("admin")) {
			display.getPermissionListbox().setItemSelected(2, true);
		} else {
			display.getPermissionListbox().setItemSelected(1, true);
		}

	}

	private void getUser(int userID) {
		rpcService.getUserByUserID(userID, new AsyncCallback<User>() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			public void onSuccess(User result) {
				fillTextBoxes(result);
			}
		});
	}

	/**
	 * bringt die View zur Anzeige
	 */
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	private void setupValidation() {
		class CustomValidationMessages extends ValidationMessages {

			@Override
			public String getDescriptionMessage(String msgKey) {
				HashMap<String, String> msgMap = new HashMap<String, String>();
				msgMap.put("username", constants.popupHelpUsername());
				msgMap.put("forename", constants.popupHelpUserForename());
				msgMap.put("surname", constants.popupHelpUserSurname());
				msgMap.put("password", constants.popupHelpUserPassword());
				msgMap.put("passwordConfirm",
						constants.popupHelpUserPasswordConfirm());

				String temp = msgMap.get(msgKey.trim());
				return temp;
			}
		}

		validator = display.getValidator();
		ValidationMessages messages = new CustomValidationMessages();

		popupDesc = new PopupDescription(messages);

		validator.addValidators("username",
				new StringLengthValidator(display.getUsernameTextBox(), 2, 30)
		.addActionForFailure(new StyleAction(
		"validationFailedBorder")));

		validator.addValidators("forename",
				new StringLengthValidator(display.getForenameTextBox(), 2, 30)
		.addActionForFailure(new StyleAction(
		"validationFailedBorder")));

		validator.addValidators("surname",
				new StringLengthValidator(display.getSurnameTextBox(), 2, 30)
		.addActionForFailure(new StyleAction(
		"validationFailedBorder")));

		validator.addValidators("password",
				new StringLengthValidator(display.getPasswordTextBox(), 6, 30)
		.addActionForFailure(new StyleAction(
		"validationFailedBorder")));

		validator
		.addValidators("passwordConfirm", new StringLengthValidator(
				display.getPasswordConfirmTextBox(), 6, 30)
		.addActionForFailure(new StyleAction(
		"validationFailedBorder")));

		popupDesc.addDescription("username ", display.getUsernameTextBox());
		popupDesc.addDescription("forename ", display.getForenameTextBox());
		popupDesc.addDescription("surname ", display.getSurnameTextBox());
		popupDesc.addDescription("password ", display.getPasswordTextBox());
		popupDesc.addDescription("passwordConfirm ",
				display.getPasswordConfirmTextBox());

	}
}
