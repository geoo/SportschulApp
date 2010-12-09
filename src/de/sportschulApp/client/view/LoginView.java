package de.sportschulApp.client.view;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.LoginPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class LoginView extends Composite implements LoginPresenter.Display{

	private ListBox languagePicker = new ListBox();
	private Label loginErrorLabel = new Label("Login fehlgeschlagen. Der Benutzername und das Passwort stimmen nich überein.");
	private Label loginLabel;
	private Label passwordLabel;
	private PasswordTextBox passwordTextBox;
	private Label userNameLabel;
	private TextBox userNameTextBox;

	public LoginView(LocalizationConstants constants) {

		VerticalPanel loginPanel = new VerticalPanel();
		loginPanel.setSize("100%", "100%");
		initWidget(loginPanel);

		HorizontalPanel metaHeadPanel = new HorizontalPanel();
		metaHeadPanel.setStyleName("loginMetaHeadPanel");
		metaHeadPanel.setSize("100%", "28px");

		HorizontalPanel languagePanel = new HorizontalPanel();
		languagePanel.addStyleName("languagePanel");
		Label languageLabel = new Label(constants.language() + ": ");
		languagePicker.addItem("Deutsch");
		languagePicker.addItem("English");
		languagePanel.add(languageLabel);
		languagePanel.add(languagePicker);

		try {
			if (!Cookies.getCookie("SportschuleLanguage").isEmpty()) {
				if (Cookies.getCookie("SportschuleLanguage").equals("Deutsch")) {
					languagePicker.setSelectedIndex(0);
				}
				if (Cookies.getCookie("SportschuleLanguage").equals("English")) {
					languagePicker.setSelectedIndex(1);
				}
			}
		} catch (NullPointerException e) {
			languagePicker.setSelectedIndex(0);
		}

		loginErrorLabel.setStyleName("loginErrorLabel");
		loginErrorLabel.setVisible(false);
		metaHeadPanel.add(loginErrorLabel);
//		metaHeadPanel.add(languagePanel);

		HorizontalPanel loginContainer = new HorizontalPanel();
		loginContainer.setStyleName("loginContainer");

		userNameLabel = new Label(constants.username()+": ");
		userNameTextBox = new TextBox();
		userNameTextBox.setStyleName("loginInput");

		passwordLabel = new Label(constants.password()+": ");
		passwordTextBox = new PasswordTextBox();
		passwordTextBox.setStyleName("loginInput");

		loginLabel = new Label(constants.login());
		loginLabel.setStyleName("loginLabel");

		loginContainer.add(userNameLabel);
		loginContainer.add(userNameTextBox);
		loginContainer.add(passwordLabel);
		loginContainer.add(passwordTextBox);

		loginContainer.add(loginLabel);

		HorizontalPanel mainHeadPanel = new HorizontalPanel();
		mainHeadPanel.setStyleName("loginMainHeadPanel");
		mainHeadPanel.setHeight("54px");
		mainHeadPanel.setWidth("100%");

		mainHeadPanel.add(loginContainer);

		Image mmLogo = new Image("imgs/mm-logo.jpg");
		mmLogo.addStyleName("mm-logo");

		loginPanel.add(metaHeadPanel);
		loginPanel.add(mainHeadPanel);
		loginPanel.add(mmLogo);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public HasChangeHandlers getLanguagePickerOnChange() {
		return languagePicker;
	}


	public String getLanguagePickerValue() {
		return languagePicker.getValue(languagePicker.getSelectedIndex());
	}

	public HasClickHandlers getLoginButton() {
		return loginLabel;
	}

	public HasValue<String> getPassword() {
		return passwordTextBox;
	}

	public HasKeyUpHandlers getPasswordOnKeyUp() {
		return passwordTextBox;
	}

	public HasValue<String> getUserName() {
		return userNameTextBox;
	}

	public HasKeyUpHandlers getUserNameOnKeyUp() {
		return userNameTextBox;
	}

	public void setErrorLabel() {
		loginErrorLabel.setVisible(true);
	}
}

