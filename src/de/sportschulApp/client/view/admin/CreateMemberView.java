package de.sportschulApp.client.view.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.CreateMemberPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class CreateMemberView extends Composite implements
		CreateMemberPresenter.Display {

	private Label forenameLabel;
	private TextBox forenameTextBox;
	private Label surnameLabel;
	private TextBox surnameTextBox;


	public CreateMemberView() {

		VerticalPanel createMemberPanel = new VerticalPanel();
		createMemberPanel.setWidth("100%");
		initWidget(createMemberPanel);

		HorizontalPanel forenameInputPanel = new HorizontalPanel();
		forenameLabel = new Label("Vorname: ");
		forenameLabel.setWidth("100px");
		forenameTextBox = new TextBox();
		forenameInputPanel.add(forenameLabel);
		forenameInputPanel.add(forenameTextBox);

		HorizontalPanel surnameInputPanel = new HorizontalPanel();
		surnameLabel = new Label("Nachname: ");
		surnameLabel.setWidth("100px");
		surnameTextBox = new TextBox();
		surnameInputPanel.add(surnameLabel);
		surnameInputPanel.add(surnameTextBox);

		
		

		createMemberPanel.add(surnameInputPanel);

	}

	public Widget asWidget() {
		return this;
	}

}
