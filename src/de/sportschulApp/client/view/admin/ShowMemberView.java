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
	
	private Label memberIDLabel = new Label();
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
		
		HorizontalPanel mainDetailsPanel = new HorizontalPanel();
		mainDetailsPanel.setHeight("200px");
		mainDetailsPanel.add(memberPicture);
		
		FlexTable mainDetailsData = new FlexTable();
//		mainDetailsData.set
		memberPanel.add(forenameLabel);
 memberPanel.add(surnameLabel);
memberPanel.add(cityLabel);
memberPanel.add(streetLabel);
 memberPanel.add(phoneLabel);
		
	}
	
	public Widget asWidget() {
		return this;
	}

	public void setMemberData(Member member) {
		this.forenameLabel.setText(member.getForename());
		this.surnameLabel.setText(member.getSurname());
		this.cityLabel.setText(member.getCity());
		this.streetLabel.setText(member.getStreet());
		this.phoneLabel.setText(member.getPhone());
	}

}
