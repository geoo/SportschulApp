package de.sportschulApp.client.view.admin;

import com.google.gwt.user.client.Window;
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
	private Label birthContainer = new Label();
	private Label beltSizeLabel = new Label();
	private Label noteLabel = new Label();
	private Image memberPicture = new Image("");
	
	public ShowMemberView(LocalizationConstants constants) {
		VerticalPanel memberPanel = new VerticalPanel();
		initWidget(memberPanel);
		
		memberPicture.setHeight("200px");
		memberPicture.setStyleName("showMember_Picture");
		
		HorizontalPanel primaryDetailsPanel = new HorizontalPanel();
		primaryDetailsPanel.add(memberPicture);
		
		FlexTable primaryDetailsData = new FlexTable();
		primaryDetailsData.setStyleName("primaryDetailsData");
		primaryDetailsData.setWidget(0, 0, new Label(constants.barcode() + ":"));
		primaryDetailsData.setWidget(0, 1, barcodeIDLabel);
		primaryDetailsData.setWidget(1, 0, new Label(constants.forename() + ":"));
		primaryDetailsData.setWidget(1, 1, forenameLabel);
		primaryDetailsData.setWidget(2, 0, new Label(constants.surname() + ":"));
		primaryDetailsData.setWidget(2, 1, surnameLabel);
		primaryDetailsData.setWidget(3, 0, new Label(constants.birth() + ":"));
		primaryDetailsData.setWidget(3, 1, birthContainer);
		primaryDetailsData.setWidget(4, 0, new Label(constants.city() + ":"));
		primaryDetailsData.setWidget(4, 1, cityLabel);
		primaryDetailsData.setWidget(5, 0, new Label(constants.street() + ":"));
		primaryDetailsData.setWidget(5, 1, streetLabel);
		primaryDetailsData.setWidget(6, 0, new Label(constants.phone() + ":"));
		primaryDetailsData.setWidget(6, 1, phoneLabel);
		
		primaryDetailsPanel.add(primaryDetailsData);
		
		FlexTable secondaryDetailsData = new FlexTable();
		secondaryDetailsData.setStyleName("secondaryDetailsData");
		secondaryDetailsData.setWidget(0, 0, new Label(constants.mobilephone() + ":"));
		secondaryDetailsData.setWidget(0, 1, mobilePhoneLabel);
		secondaryDetailsData.setWidget(1, 0, new Label(constants.fax() + ":"));
		secondaryDetailsData.setWidget(1, 1, faxLabel);
		secondaryDetailsData.setWidget(2, 0, new Label(constants.email() + ":"));
		secondaryDetailsData.setWidget(2, 1, emailLabel);
		secondaryDetailsData.setWidget(3, 0, new Label(constants.homepage() + ":"));
		secondaryDetailsData.setWidget(3, 1, homepageLabel);
		secondaryDetailsData.setWidget(4, 0, new Label(constants.beltsize() + ":"));
		secondaryDetailsData.setWidget(4, 1, beltSizeLabel);
		secondaryDetailsData.setWidget(5, 0, new Label(constants.note() + ":"));
		secondaryDetailsData.setWidget(5, 1, noteLabel);
		
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
		this.birthContainer.setText(member.getBirthDay() + "." + member.getBirthMonth() + "." + member.getBirthYear());
		this.beltSizeLabel.setText(member.getBeltsize());
		this.noteLabel.setText(member.getNote());
		
		try {
			if(!member.getPicture().equals(null)) {
				this.memberPicture.setUrl(member.getPicture());
			}
		} catch (NullPointerException e) {
			this.memberPicture.setUrl("imgs/standartMember.jpg");
		}
	}

}
