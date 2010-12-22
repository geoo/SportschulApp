package de.sportschulApp.client.view.admin;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.ShowMemberPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Member;

public class ShowMemberView extends Composite implements
ShowMemberPresenter.Display {

	private Label barcodeIDLabel = new Label();
	private Label beltSizeLabel = new Label();
	private Label birthContainer = new Label();
	private Label cityLabel = new Label();
	private Label coursesLabel = new Label();
	private Label deleteLabel;
	private Label editLabel;
	private Label emailLabel = new Label();
	private Label faxLabel = new Label();
	private Label forenameLabel = new Label();
	private Label homepageLabel = new Label();
	private Image memberPicture = new Image("");
	private Label mobilePhoneLabel = new Label();
	private Label noteLabel = new Label();
	private Label phoneLabel = new Label();
	FlexTable secondaryDetailsData = new FlexTable();
	private Label streetLabel = new Label();
	private Label surnameLabel = new Label();
	private Label trainingUnitsLabel = new Label();
	private Label zipcodeLabel = new Label();
	private Label closeLabel;

	public ShowMemberView(LocalizationConstants constants) {
		VerticalPanel memberPanel = new VerticalPanel();
		memberPanel.setWidth("450px");
		initWidget(memberPanel);

		HorizontalPanel metaPanel = new HorizontalPanel();
		metaPanel.addStyleName("metaPanel");

		editLabel = new Label("Mitglied bearbeiten");
		deleteLabel = new Label("Mitglied löschen");
		closeLabel = new Label("Schließen");

		metaPanel.add(editLabel);
		metaPanel.add(deleteLabel);
		metaPanel.add(closeLabel);

		memberPanel.add(metaPanel);

		memberPicture.setHeight("200px");
		memberPicture.setStyleName("showMember_Picture");

		HorizontalPanel primaryDetailsPanel = new HorizontalPanel();
		primaryDetailsPanel.add(memberPicture);

		FlexTable primaryDetailsData = new FlexTable();
		primaryDetailsData.setStyleName("primaryDetailsData");
		primaryDetailsData
		.setWidget(0, 0, new Label(constants.barcode() + ":"));
		primaryDetailsData.setWidget(0, 1, barcodeIDLabel);
		primaryDetailsData.setWidget(1, 0,
				new Label(constants.forename() + ":"));
		primaryDetailsData.setWidget(1, 1, forenameLabel);
		primaryDetailsData
		.setWidget(2, 0, new Label(constants.surname() + ":"));
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

		secondaryDetailsData.setStyleName("secondaryDetailsData");
		secondaryDetailsData.setWidget(0, 0,
				new Label(constants.course() + ":"));
		secondaryDetailsData.setWidget(0, 1, coursesLabel);
		secondaryDetailsData.setWidget(1, 0,
				new Label(constants.trainingunits() + ":"));
		secondaryDetailsData.setWidget(1, 1, trainingUnitsLabel);
		secondaryDetailsData.setWidget(2, 0, new Label(constants.mobilephone()
				+ ":"));
		secondaryDetailsData.setWidget(2, 1, mobilePhoneLabel);
		secondaryDetailsData.setWidget(3, 0, new Label(constants.fax() + ":"));
		secondaryDetailsData.setWidget(3, 1, faxLabel);
		secondaryDetailsData
		.setWidget(4, 0, new Label(constants.email() + ":"));
		secondaryDetailsData.setWidget(4, 1, emailLabel);
		secondaryDetailsData.setWidget(5, 0, new Label(constants.homepage()
				+ ":"));
		secondaryDetailsData.setWidget(5, 1, homepageLabel);
		secondaryDetailsData.setWidget(6, 0, new Label(constants.beltsize()
				+ ":"));
		secondaryDetailsData.setWidget(6, 1, beltSizeLabel);
		secondaryDetailsData.setWidget(7, 0, new Label(constants.note() + ":"));
		secondaryDetailsData.setWidget(7, 1, noteLabel);

		memberPanel.add(primaryDetailsPanel);
		memberPanel.add(secondaryDetailsData);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public HasClickHandlers getDeleteLabel() {
		return deleteLabel;
	}

	public HasClickHandlers getEditLabel() {
		return editLabel;
	}
	
	public HasClickHandlers getCloseLabel() {
		return closeLabel;
	}

	public void setMemberCourses(String courses) {
		coursesLabel.setText(courses);
	}

	public void setMemberData(Member member) {
		barcodeIDLabel.setText(member.getBarcodeID() + "");
		forenameLabel.setText(member.getForename());
		surnameLabel.setText(member.getSurname());
		zipcodeLabel.setText(member.getZipcode() + "");
		cityLabel.setText(member.getCity());
		streetLabel.setText(member.getStreet());
		phoneLabel.setText(member.getPhone());
		mobilePhoneLabel.setText(member.getMobilephone());
		faxLabel.setText(member.getFax());
		emailLabel.setText(member.getEmail());
		homepageLabel.setText(member.getHomepage());
		birthContainer.setText(member.getBirthDay() + "."
				+ member.getBirthMonth() + "." + member.getBirthYear());
		beltSizeLabel.setText(member.getBeltsize());
		noteLabel.setText(member.getNote());
		trainingUnitsLabel.setText(member.getTrainingunits() + "");

		memberPicture.setUrl(member.getPicture());

	}
}
