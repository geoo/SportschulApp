package de.sportschulApp.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.LoginPresenter;

public class LoginView extends Composite implements LoginPresenter.Display{
	
	private Label userNameLabel;
	private Label passwordLabel;
    private TextBox userNameTextBox;
	private PasswordTextBox passwordTextBox;
	private Button loginButton;
    
	public LoginView() {
    	DecoratorPanel loginDecorator = new DecoratorPanel();
    	loginDecorator.setWidth("18em");
        initWidget(loginDecorator);

        VerticalPanel loginPanel = new VerticalPanel();
        loginPanel.setWidth("100%");
        
        HorizontalPanel userNameInputPanel = new HorizontalPanel();
        userNameLabel = new Label("Benutzername: ");
        userNameLabel.setWidth("100px");
        userNameTextBox = new TextBox();
        userNameInputPanel.add(userNameLabel);
        userNameInputPanel.add(userNameTextBox);
        
        HorizontalPanel passwordInputPanel = new HorizontalPanel();
        passwordLabel = new Label("Passwort: ");
        passwordLabel.setWidth("100px");
        passwordTextBox = new PasswordTextBox();
        passwordInputPanel.add(passwordLabel);
        passwordInputPanel.add(passwordTextBox);
        
        loginButton = new Button("Anmelden");
        
        loginPanel.add(userNameInputPanel);
        loginPanel.add(passwordInputPanel);
        loginPanel.add(loginButton);
        loginDecorator.add(loginPanel);
    }
    
   	public HasClickHandlers getLoginButton() {
		return loginButton;
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
}
	
