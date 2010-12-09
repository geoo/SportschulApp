package de.sportschulApp.client.view.admin;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
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
	private TextBox systemUserForenameTextBox;
	private TextBox systemUsernameTextBox;
	private PasswordTextBox systemUserPasswortConfirmTextBox;
	// passwort
	private TextBox systemUserPasswortTextBox;
	private TextBox systemUserSurnameTextBox;
	private Label userForenameLabel;
	// benutzername
	private Label userNameLabel;
	private Label userPasswordConfirmLabel;
	private Label userPasswordLabel;
	private Label userPermissionLabel;
	private ListBox userPermissionListBox;
	private ValidationProcessor validator;

	/**
	 * Konstruktor
	 */
	public CreateUserView(LocalizationConstants constants) {
		this.constants = constants;
		validator = new DefaultValidationProcessor();
		
		VerticalPanel wrapper = new VerticalPanel();
		wrapper.setStyleName("createUserWrapper");

		// hauptpanel generieren
		mainPanel = new VerticalPanel();
		initWidget(wrapper);
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
		
		wrapper.add(createHeadPanel());
		wrapper.add(mainPanel);
	}
	
	public VerticalPanel createHeadPanel() {
		VerticalPanel createUserHeader = new VerticalPanel();
		createUserHeader.addStyleName("createUserHeadWrapper");
		createUserHeader.setWidth("500px");

		HorizontalPanel formHeader = new HorizontalPanel();
		formHeader.addStyleName("formHeader");
		formHeader.add(new Label("Nutzer anlegen / bearbeiten"));

		createUserHeader.add(formHeader);

		return createUserHeader;
	}

	/**
	 * gibt die komplette view zurück
	 * 
	 * @return die view
	 */
	@Override
	public Widget asWidget() {
		return this;
	}

	/**
	 * gibt die sprachinformationen
	 */
	public LocalizationConstants getConstants() {
		return constants;
	}

	public TextBox getForenameTextBox() {
		return systemUserForenameTextBox;
	}

	public TextBox getPasswordConfirmTextBox() {
		return systemUserPasswortConfirmTextBox;
	}

	public TextBox getPasswordTextBox() {
		return systemUserPasswortTextBox;
	}

	public ListBox getPermissionListbox() {
		return userPermissionListBox;
	}

	/**
	 * gibt den save button
	 * 
	 * @return save button
	 */
	public HasClickHandlers getSaveButton() {
		return saveButton;
	}

	public TextBox getSurnameTextBox() {
		return systemUserSurnameTextBox;
	}

	public TextBox getUsernameTextBox() {
		return systemUsernameTextBox;
	}

	public ValidationProcessor getValidator() {
		return validator;
	}

}
