package de.sportschulApp.client.view.admin;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.ShowMemberPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Member;

public class ShowMemberView extends Composite implements ShowMemberPresenter.Display {
	
	private Label barcodeIDLabel = new Label();
	private Label forenameLabel = new Label();
	private Label surnameLabel = new Label();
	private Label zipcodeLabel = new Label();
	private Label cityLabel = new Label();
	private Label streetLabel = new Label();
	private Label phoneLabel = new Label();
	private Label mobilePhoneLabel = new Label();
	private Label faxLabel = new Label();
	private Label emailLabel = new Label();
	private Label homepageLabel = new Label();
	private Label birthDayLabel = new Label();
	private Label birthMonthLabel = new Label();
	private Label birthYearLabel = new Label();
	private Label beltSizeLabel = new Label();
	private Label noteLabel = new Label();
	private Image memberPicture = new Image();
	
	public ShowMemberView(LocalizationConstants constants) {
		VerticalPanel memberPanel = new VerticalPanel();
		initWidget(memberPanel);
		
		HorizontalPanel birthContainer = new HorizontalPanel();
		birthContainer.add(birthDayLabel);
		birthContainer.add(new Label("."));
		birthContainer.add(birthMonthLabel);
		birthContainer.add(new Label("."));
		birthContainer.add(birthYearLabel);
		
		HorizontalPanel primaryDetailsPanel = new HorizontalPanel();
		primaryDetailsPanel.add(memberPicture);
		
		FlexTable primaryDetailsData = new FlexTable();
		primaryDetailsData.setWidget(0, 0, new Label(constants.barcode()));
		primaryDetailsData.setWidget(0, 1, barcodeIDLabel);
		primaryDetailsData.setWidget(1, 0, new Label(constants.surname()));
		primaryDetailsData.setWidget(1, 1, surnameLabel);
		primaryDetailsData.setWidget(2, 0, new Label(constants.forename()));
		primaryDetailsData.setWidget(2, 1, forenameLabel);
		primaryDetailsData.setWidget(3, 0, new Label(constants.phone()));
		primaryDetailsData.setWidget(3, 1, phoneLabel);
		primaryDetailsData.setWidget(4, 0, new Label(constants.birth()));
		primaryDetailsData.setWidget(4, 1, birthContainer);
		
		primaryDetailsPanel.add(primaryDetailsData);
		
		FlexTable secondaryDetailsData = new FlexTable();
		secondaryDetailsData.setWidget(0, 0, new Label(constants.city()));
		secondaryDetailsData.setWidget(0, 1, cityLabel);
		secondaryDetailsData.setWidget(1, 0, new Label(constants.street()));
		secondaryDetailsData.setWidget(1, 1, streetLabel);
		secondaryDetailsData.setWidget(2, 0, new Label(constants.phone()));
		secondaryDetailsData.setWidget(2, 1, phoneLabel);
		secondaryDetailsData.setWidget(3, 0, new Label(constants.mobilephone()));
		secondaryDetailsData.setWidget(3, 1, mobilePhoneLabel);
		secondaryDetailsData.setWidget(4, 0, new Label(constants.fax()));
		secondaryDetailsData.setWidget(4, 1, faxLabel);
		secondaryDetailsData.setWidget(5, 0, new Label(constants.email()));
		secondaryDetailsData.setWidget(5, 1, emailLabel);
		secondaryDetailsData.setWidget(6, 0, new Label(constants.homepage()));
		secondaryDetailsData.setWidget(6, 1, homepageLabel);
		secondaryDetailsData.setWidget(7, 0, new Label(constants.beltsize()));
		secondaryDetailsData.setWidget(7, 1, beltSizeLabel);
		secondaryDetailsData.setWidget(8, 0, new Label(constants.note()));
		secondaryDetailsData.setWidget(8, 1, noteLabel);
		
		memberPanel.add(primaryDetailsPanel);
		memberPanel.add(secondaryDetailsData);
	}
	
	public Widget asWidget() {
		return this;
	}

	public void setMemberData(Member member) {
		this.barcodeIDLabel.setText(member.getBarcodeID() + "");
		this.forenameLabel.setText(member.getForename());
		this.surnameLabel.setText(member.getSurname());
		this.zipcodeLabel.setText(member.getZipcode() + "");
		this.cityLabel.setText(member.getCity());
		this.streetLabel.setText(member.getStreet());
		this.phoneLabel.setText(member.getPhone());
		this.mobilePhoneLabel.setText(member.getMobilephone());
		this.faxLabel.setText(member.getFax());
		this.emailLabel.setText(member.getEmail());
		this.homepageLabel.setText(member.getHomepage());
		this.birthDayLabel.setText(member.getBirthDay());
		this.birthMonthLabel.setText(member.getBirthMonth());
		this.birthYearLabel.setText(member.getBirthYear());
		this.beltSizeLabel.setText(member.getBeltsize());
		this.noteLabel.setText(member.getNote());
		this.memberPicture.setUrl(member.getPicture());
	}

}
