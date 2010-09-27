package de.sportschulApp.client.view.admin;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.ShowUsersPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class ShowUsersView extends Composite implements ShowUsersPresenter.Display{
	
	//Datenfelder
	private VerticalPanel mainPanel;
	private Label dummy;
	private LocalizationConstants constants;
	
	/**
	 * Konstruktor
	 * @param constants
	 */
	public ShowUsersView(LocalizationConstants constants){
		this.constants = constants;
		
		//...
		mainPanel = new VerticalPanel();
		dummy = new Label("hier soll eine Anzeige für alle User hin wie bei Member");
		mainPanel.add(dummy);
	}

	/**
	 * gibt sich selbst zurück
	 */
	public Widget asWidget() {
		return this;
	}

	/**
	 * gibt die Spracheinstellungen zurück
	 */
	public LocalizationConstants getConstants() {
		return constants;
	}

}
