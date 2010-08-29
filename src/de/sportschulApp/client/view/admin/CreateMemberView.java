package de.sportschulApp.client.view.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
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
	private Widget faxLabel;
	private TextBox faxTextBox;
	private Label emailLabel;
	private TextBox emailTextBox;
	private Widget homepageLabel;
	private Widget homepageTextBox;
	private Widget birthTextBox;
	private Widget birthLabel;
	private Widget diseasesTextBox;
	private Widget diseasesLabel;
	private Widget beltsizeTextBox;
	private Widget beltsizeLabel;
	private Widget noteLabel;
	private Widget noteTextBox;
	private Widget trainingunitsLabel;
	private Widget trainingunitsTextBox;
	private ListBox trainingunitsListBox;
	private Label grade01Label;
	private Label course01Label;
	private ListBox course01ListBox;
	private ListBox grade01ListBox;

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
		
		HorizontalPanel faxInputPanel = new HorizontalPanel();
		faxLabel = new Label(constants.fax() + ": ");
		faxLabel.setWidth("100px");
		faxTextBox = new TextBox();
		faxInputPanel.add(faxLabel);
		faxInputPanel.add(faxTextBox);
		
		HorizontalPanel emailInputPanel = new HorizontalPanel();
		emailLabel = new Label(constants.email() + ": ");
		emailLabel.setWidth("100px");
		emailTextBox = new TextBox();
		emailInputPanel.add(emailLabel);
		emailInputPanel.add(emailTextBox);
		
		HorizontalPanel homepageInputPanel = new HorizontalPanel();
		homepageLabel = new Label(constants.homepage() + ": ");
		homepageLabel.setWidth("100px");
		homepageTextBox = new TextBox();
		homepageInputPanel.add(homepageLabel);
		homepageInputPanel.add(homepageTextBox);
		
		HorizontalPanel birthInputPanel = new HorizontalPanel();
		birthLabel = new Label(constants.birth() + ": ");
		birthLabel.setWidth("100px");
		birthTextBox = new TextBox();
		birthInputPanel.add(birthLabel);
		birthInputPanel.add(birthTextBox);
		
		HorizontalPanel diseasesInputPanel = new HorizontalPanel();
		diseasesLabel = new Label(constants.diseases() + ": ");
		diseasesLabel.setWidth("100px");
		diseasesTextBox = new TextBox();
		diseasesInputPanel.add(diseasesLabel);
		diseasesInputPanel.add(diseasesTextBox);
		
		HorizontalPanel beltsizeInputPanel = new HorizontalPanel();
		beltsizeLabel = new Label(constants.beltsize() + ": ");
		beltsizeLabel.setWidth("100px");
		beltsizeTextBox = new TextBox();
		beltsizeInputPanel.add(beltsizeLabel);
		beltsizeInputPanel.add(beltsizeTextBox);
		
		HorizontalPanel noteInputPanel = new HorizontalPanel();
		noteLabel = new Label(constants.note() + ": ");
		noteLabel.setWidth("100px");
		noteTextBox = new TextBox();
		noteInputPanel.add(noteLabel);
		noteInputPanel.add(noteTextBox);
		
		HorizontalPanel trainingunitsInputPanel = new HorizontalPanel();
		trainingunitsLabel = new Label(constants.trainingunits() + ": ");
		trainingunitsLabel.setWidth("100px");
		trainingunitsTextBox = new TextBox();
		trainingunitsInputPanel.add(trainingunitsLabel);
		trainingunitsInputPanel.add(trainingunitsTextBox);
		
		HorizontalPanel course01InputPanel = new HorizontalPanel();
		course01Label = new Label(constants.course() + ": ");
		course01Label.setWidth("100px");	
		course01ListBox = new ListBox();
		grade01Label = new Label(constants.grade() + ": ");
		grade01Label.setWidth("100px");
		grade01ListBox = new ListBox();
		course01InputPanel.add(course01Label);
		course01InputPanel.add(course01ListBox);
		course01InputPanel.add(grade01Label);
		course01InputPanel.add(grade01ListBox);

		createMemberPanel.add(forenameInputPanel);
		createMemberPanel.add(surnameInputPanel);
		createMemberPanel.add(barcodeInputPanel);
		createMemberPanel.add(streetInputPanel);
		createMemberPanel.add(zipcodeInputPanel);
		createMemberPanel.add(cityInputPanel);
		createMemberPanel.add(phoneInputPanel);
		createMemberPanel.add(mobilephoneInputPanel);
		createMemberPanel.add(faxInputPanel);
		createMemberPanel.add(emailInputPanel);
		createMemberPanel.add(homepageInputPanel);
		createMemberPanel.add(homepageInputPanel);
		createMemberPanel.add(birthInputPanel);
		createMemberPanel.add(diseasesInputPanel);
		createMemberPanel.add(beltsizeInputPanel);
		createMemberPanel.add(noteInputPanel);
		createMemberPanel.add(trainingunitsInputPanel);
		createMemberPanel.add(course01InputPanel);




		
		
		//TODO Picture


		
		

	}

	public Widget asWidget() {
		return this;
	}

}
