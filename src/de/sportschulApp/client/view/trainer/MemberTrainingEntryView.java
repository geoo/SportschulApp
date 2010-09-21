package de.sportschulApp.client.view.trainer;

import java.util.*;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.trainer.MemberTrainingEntryPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Member;

public class MemberTrainingEntryView extends Composite implements
		MemberTrainingEntryPresenter.Display {

	private LocalizationConstants constants;
	private Label membernameLabel;
	private Image picture;
	private Image deleteButton;
	private HorizontalPanel diseasesPanel;
	private VerticalPanel memberTrainingEntryWrapper1;

	public MemberTrainingEntryView(LocalizationConstants constants) {
		this.constants = constants;

		memberTrainingEntryWrapper1 = new VerticalPanel();

		HorizontalPanel memberTrainingEntryWrapper2 = new HorizontalPanel();
		memberTrainingEntryWrapper2.setStyleName("memberTrainingEntryWrapper");
		memberTrainingEntryWrapper1.add(memberTrainingEntryWrapper2);
		initWidget(memberTrainingEntryWrapper1);

		VerticalPanel memberDetailPanel = new VerticalPanel();

		deleteButton = new Image("imgs/closeButton.png");
		deleteButton.setStyleName("deleteButton");

		membernameLabel = new Label();
		picture = new Image();

		memberDetailPanel.add(membernameLabel);

		memberTrainingEntryWrapper2.add(picture);
		memberTrainingEntryWrapper2.add(memberDetailPanel);
		memberTrainingEntryWrapper2.add(deleteButton);

		diseasesPanel = new HorizontalPanel();
		diseasesPanel.setStyleName("diseasesPanel");

	}

	public Widget asWidget() {
		return this;
	}

	public LocalizationConstants getConstants() {
		return constants;
	}

	public void fillEntry(Member member) {

		// member details
		membernameLabel.setText("Name: " + member.getForename() + " "
				+ member.getSurname());

		// Image
		try {
			if (member.getPicture() == null) {
				picture.setUrl("imgs/standartMember.jpg");
			} else {
				picture.setUrl(member.getPicture());

			}
		} catch (NullPointerException e) {
		}

		// krankheiten
		try {
			//TODO
			if ((member.getDiseases() != null)
					|| (!member.getDiseases().equals(""))) {
				memberTrainingEntryWrapper1.add(diseasesPanel);
				Label diseasesLabel = new Label("Beschwerden: "
						+ member.getDiseases());
				diseasesPanel.add(diseasesLabel);
			} else {

			}
		} catch (NullPointerException e) {
		}

		// geburtstags erinnerung
		Date today = new Date();
		Date memberBirthDate = new Date();

		memberBirthDate
				.setYear(Integer.parseInt((member.getBirthYear())) - 1900);
		memberBirthDate.setMonth(Integer.parseInt(member.getBirthMonth()) - 1);
		memberBirthDate.setDate(Integer.parseInt(member.getBirthDay()));

		if (((memberBirthDate.getDate() == today.getDate()))
				&& memberBirthDate.getMonth() == today.getMonth()) {
			System.out.println("Member hat heute Geburtstag!");
		} else if (((memberBirthDate.getDate() - today.getDate()) >= -7)
				&& ((memberBirthDate.getDate() - today.getDate()) <= 0)
				&& memberBirthDate.getMonth() == today.getMonth()) {
			System.out.println("Member hatte am " + member.getBirthDay() + "."
					+ member.getBirthMonth() + "." + member.getBirthYear()
					+ " Geburtstag");
		} else if (((today.getDate() - memberBirthDate.getDate()) <= -23)
				&& memberBirthDate.getMonth() == today.getMonth() - 1) {
			System.out.println("Member hatte am " + member.getBirthDay() + "."
					+ member.getBirthMonth() + "." + member.getBirthYear()
					+ " Geburtstag");
		}
	}

	public Image getDeleteButton() {
		return deleteButton;
	}
}
