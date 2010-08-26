package de.sportschulApp.client.view.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.CreateMemberPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class CreateMemberView extends Composite implements
		CreateMemberPresenter.Display {

	private LocalizationConstants constants;
	private Label forenameLabel;
	private TextBox forenameTextBox;
	private Label surnameLabel;
	private TextBox surnameTextBox;
	private Label barcodeLabel;
	private TextBox barcodeTextBox;
	private Label streetLabel;
	private TextBox streetTextBox;
	private Label zipcodeLabel;
	private TextBox zipcodeTextBox;
	private Label cityLabel;
	private TextBox cityTextBox;
	private Label phoneLabel;
	private TextBox phoneTextBox;
	private Label mobilephoneLabel;
	private TextBox mobilephoneTextBox;

	public CreateMemberView(LocalizationConstants constants) {

		this.constants = constants;
		VerticalPanel createMemberPanel = new VerticalPanel();
		createMemberPanel.setWidth("100%");
		initWidget(createMemberPanel);

		HorizontalPanel forenameInputPanel = new HorizontalPanel();
		forenameLabel = new Label(constants.forename() + ": ");
		forenameLabel.setWidth("100px");
		forenameTextBox = new TextBox();
		forenameInputPanel.add(forenameLabel);
		forenameInputPanel.add(forenameTextBox);

		HorizontalPanel surnameInputPanel = new HorizontalPanel();
		surnameLabel = new Label(constants.surname() + ": ");
		surnameLabel.setWidth("100px");
		surnameTextBox = new TextBox();
		surnameInputPanel.add(surnameLabel);
		surnameInputPanel.add(surnameTextBox);

		HorizontalPanel barcodeInputPanel = new HorizontalPanel();
		barcodeLabel = new Label(constants.barcode() + ": ");
		barcodeLabel.setWidth("100px");
		barcodeTextBox = new TextBox();
		barcodeInputPanel.add(barcodeLabel);
		barcodeInputPanel.add(barcodeTextBox);

		HorizontalPanel streetInputPanel = new HorizontalPanel();
		streetLabel = new Label(constants.street() + ": ");
		streetLabel.setWidth("100px");
		streetTextBox = new TextBox();
		streetInputPanel.add(streetLabel);
		streetInputPanel.add(streetTextBox);

		HorizontalPanel zipcodeInputPanel = new HorizontalPanel();
		zipcodeLabel = new Label(constants.zipcode() + ": ");
		zipcodeLabel.setWidth("100px");
		zipcodeTextBox = new TextBox();
		zipcodeInputPanel.add(zipcodeLabel);
		zipcodeInputPanel.add(zipcodeTextBox);
		
		HorizontalPanel cityInputPanel = new HorizontalPanel();
		cityLabel = new Label(constants.city() + ": ");
		cityLabel.setWidth("100px");
		cityTextBox = new TextBox();
		cityInputPanel.add(cityLabel);
		cityInputPanel.add(cityTextBox);
		
		HorizontalPanel phoneInputPanel = new HorizontalPanel();
		phoneLabel = new Label(constants.phone() + ": ");
		phoneLabel.setWidth("100px");
		phoneTextBox = new TextBox();
		phoneInputPanel.add(phoneLabel);
		phoneInputPanel.add(phoneTextBox);
		
		HorizontalPanel mobilephoneInputPanel = new HorizontalPanel();
		mobilephoneLabel = new Label(constants.mobilephone() + ": ");
		mobilephoneLabel.setWidth("100px");
		mobilephoneTextBox = new TextBox();
		mobilephoneInputPanel.add(mobilephoneLabel);
		mobilephoneInputPanel.add(mobilephoneTextBox);

		createMemberPanel.add(forenameInputPanel);
		createMemberPanel.add(surnameInputPanel);
		createMemberPanel.add(barcodeInputPanel);
		createMemberPanel.add(streetInputPanel);
		createMemberPanel.add(zipcodeInputPanel);
		createMemberPanel.add(cityInputPanel);
		createMemberPanel.add(phoneInputPanel);


		

	}

	public Widget asWidget() {
		return this;
	}

}
