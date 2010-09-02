package de.sportschulApp.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.LoginPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class LoginView extends Composite implements LoginPresenter.Display{
	
	private Label userNameLabel;
	private Label passwordLabel;
    private TextBox userNameTextBox;
	private PasswordTextBox passwordTextBox;
	private Label loginLabel;
	private Label loginErrorLabel = new Label("Login fehlgeschlagen. Der Benutzername und das Passwort stimmen nich überein.");
    
	public LoginView(LocalizationConstants constants) {
		
		VerticalPanel loginPanel = new VerticalPanel();
		loginPanel.setSize("100%", "100%");
		initWidget(loginPanel);
		
		VerticalPanel metaHeadPanel = new VerticalPanel();
		metaHeadPanel.setStyleName("metaHeadPanel");
		metaHeadPanel.setSize("100%", "28px");
		
		loginErrorLabel.setStyleName("loginErrorLabel");
		loginErrorLabel.setVisible(false);
		metaHeadPanel.add(loginErrorLabel);
		
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
		mainHeadPanel.setStyleName("mainHeadPanel");
		mainHeadPanel.setHeight("54px");
		mainHeadPanel.setWidth("100%");
		
		mainHeadPanel.add(loginContainer);
		
		Image mmLogo = new Image("imgs/mm-logo.jpg");
		mmLogo.addStyleName("mm-logo");
		
		loginPanel.add(metaHeadPanel);
		loginPanel.add(mainHeadPanel);
		loginPanel.add(mmLogo);
    }
    
   	public HasClickHandlers getLoginButton() {
		return loginLabel;
	}
	
	public Widget asWidget() {
		return this;
	}
	
	public HasValue<String> getUserName() {
		return userNameTextBox;
	}
	
	public HasValue<String> getPassword() {
		return passwordTextBox;
	}
	
	public void setErrorLabel() {
		loginErrorLabel.setVisible(true);
	}
}
	
