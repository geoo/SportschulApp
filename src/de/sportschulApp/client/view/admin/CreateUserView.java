package de.sportschulApp.client.view.admin;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.CreateUserPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import eu.maydu.gwt.validation.client.DefaultValidationProcessor;
import eu.maydu.gwt.validation.client.ValidationProcessor;

public class CreateUserView extends Composite implements
		CreateUserPresenter.Display {

	/**
	 * datenfelder
	 */
	// hauptfelder
	private LocalizationConstants constants;
	private VerticalPanel mainPanel;
	private Button saveButton;
	// benutzername
	private Label userNameLabel;
	private TextBox systemUserForenameTextBox;
	// passwort
	private TextBox systemUserPasswortTextBox;
	private Label userPasswordLabel;
	private Label userPermissionLabel;
	private ListBox userPermissionListBox;
	private ValidationProcessor validator;
	private TextBox systemUserSurnameTextBox;
	private PasswordTextBox systemUserPasswortConfirmTextBox;
	private Label userPasswordConfirmLabel;
	private Label userForenameLabel;
	private TextBox systemUsernameTextBox;

	/**
	 * Konstruktor
	 */
	public CreateUserView(LocalizationConstants constants) {
		this.constants = constants;
		validator = new DefaultValidationProcessor();

		// hauptpanel generieren
		mainPanel = new VerticalPanel();
		mainPanel.setStyleName("createUserWrapper");
		initWidget(mainPanel);
		saveButton = new Button(constants.save());

		// benutzername panel generieren
		HorizontalPanel systemUsernameInputPanel = new HorizontalPanel();
		systemUsernameTextBox = new TextBox();
		userNameLabel = new Label(constants.username() + ":*");
		systemUsernameInputPanel.add(userNameLabel);
		systemUsernameInputPanel.add(systemUsernameTextBox);

		HorizontalPanel systemUserForenameInputPanel = new HorizontalPanel();
		systemUserForenameTextBox = new TextBox();
		userForenameLabel = new Label(constants.forename() + ":*");
		systemUserForenameInputPanel.add(userForenameLabel);
		systemUserForenameInputPanel.add(systemUserForenameTextBox);

		HorizontalPanel systemUserSurnameInputPanel = new HorizontalPanel();
		systemUserSurnameTextBox = new TextBox();
		userNameLabel = new Label(constants.surname() + ":*");
		systemUserSurnameInputPanel.add(userNameLabel);
		systemUserSurnameInputPanel.add(systemUserSurnameTextBox);

		// passwort panel generieren
		HorizontalPanel systemUserPasswortInputPanel = new HorizontalPanel();
		systemUserPasswortTextBox = new PasswordTextBox();
		userPasswordLabel = new Label(constants.password() + ":*");
		systemUserPasswortInputPanel.add(userPasswordLabel);
		systemUserPasswortInputPanel.add(systemUserPasswortTextBox);

		HorizontalPanel systemUserPasswortInputPanelConfirm = new HorizontalPanel();
		systemUserPasswortConfirmTextBox = new PasswordTextBox();
		userPasswordConfirmLabel = new Label(constants.passwordConfirm() + ":*");
		systemUserPasswortInputPanelConfirm.add(userPasswordConfirmLabel);
		systemUserPasswortInputPanelConfirm
				.add(systemUserPasswortConfirmTextBox);

		HorizontalPanel systemUserPermissionInputPanel = new HorizontalPanel();
		userPermissionLabel = new Label(constants.permission() + ":*");
		userPermissionListBox = new ListBox();
		userPermissionListBox.insertItem("<" + constants.select() + ">", 0);
		userPermissionListBox.insertItem(constants.admin(), 2);
		userPermissionListBox.insertItem(constants.trainer(), 1);
		systemUserPermissionInputPanel.add(userPermissionLabel);
		systemUserPermissionInputPanel.add(userPermissionListBox);

		// alles aufs hauptpanel adden
		mainPanel.add(systemUsernameInputPanel);
		mainPanel.add(systemUserForenameInputPanel);
		mainPanel.add(systemUserSurnameInputPanel);
		mainPanel.add(systemUserPasswortInputPanel);
		mainPanel.add(systemUserPasswortInputPanelConfirm);
		mainPanel.add(systemUserPermissionInputPanel);
		mainPanel.add(saveButton);
	}

	/**
	 * gibt die komplette view zurück
	 * 
	 * @return die view
	 */
	public Widget asWidget() {
		return this;
	}

	/**
	 * gibt den save button
	 * 
	 * @return save button
	 */
	public HasClickHandlers getSaveButton() {
		return saveButton;
	}

	/**
	 * gibt die sprachinformationen
	 */
	public LocalizationConstants getConstants() {
		return constants;
	}

	public ValidationProcessor getValidator() {
		return validator;
	}

	public TextBox getForenameTextBox() {
		return systemUserForenameTextBox;
	}

	public TextBox getSurnameTextBox() {
		return systemUserSurnameTextBox;
	}

	public TextBox getPasswordTextBox() {
		return systemUserPasswortTextBox;
	}

	public TextBox getPasswordConfirmTextBox() {
		return systemUserPasswortConfirmTextBox;
	}

	public TextBox getUsernameTextBox() {
		return systemUsernameTextBox;
	}

	public ListBox getPermissionListbox() {
		return userPermissionListBox;
	}

}
