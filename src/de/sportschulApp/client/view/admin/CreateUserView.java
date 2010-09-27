package de.sportschulApp.client.view.admin;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.CreateUserPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class CreateUserView extends Composite implements CreateUserPresenter.Display {
	
	/**
	 * datenfelder
	 */
	//hauptfelder
	private LocalizationConstants constants; 
	private VerticalPanel mainPanel;
	private Button saveButton;
	//benutzername
	private Label userNameLabel;
	private TextBox systemUserNameTextBox;
	//passwort
	private TextBox systemUserPasswortTextBox;
	private Label userPasswordLabel;
	
	/**
	 * Konstruktor
	 */
	public CreateUserView(LocalizationConstants constants){
		this.constants = constants;
		
		//hauptpanel generieren
		mainPanel = new VerticalPanel();
		initWidget(mainPanel);
		saveButton = new Button(constants.save());
		
		//benutzername panel generieren
		HorizontalPanel systemUserNameInputPanel = new HorizontalPanel();
		systemUserNameTextBox = new TextBox();
		userNameLabel = new Label(constants.username());
		systemUserNameInputPanel.add(userNameLabel);
		systemUserNameInputPanel.add(systemUserNameTextBox);
		
		//passwort panel generieren
		HorizontalPanel systemUserPasswortInputPanel = new HorizontalPanel();
		systemUserPasswortTextBox = new TextBox();
		userPasswordLabel = new Label(constants.password());
		systemUserNameInputPanel.add(userPasswordLabel);
		systemUserNameInputPanel.add(systemUserPasswortTextBox);
		
		//alles aufs hauptpanel adden
		mainPanel.add(systemUserNameInputPanel);
		mainPanel.add(systemUserPasswortInputPanel);
		mainPanel.add(saveButton);
	}
	
	/**
	 * gibt die komplette view zurück
	 *  @return die view
	 */
	public Widget asWidget() {
		return this;
	}
	
	/**
	 * gibt das user name label zurück
	 * @return
	 */
	public Label getUserNameLabel() {
		return userNameLabel;
	}
	
	/**
	 * gibt das passwort label
	 * @return 
	 */
	public Label getUserPasswortLabel() {
		return userPasswordLabel;
	}
	
	/**
	 * gibt den save button
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
}
