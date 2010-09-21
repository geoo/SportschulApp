package de.sportschulApp.client.view.trainer;

import java.util.*;

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

	public MemberTrainingEntryView(LocalizationConstants constants) {
		this.constants = constants;

		HorizontalPanel memberTrainingEntryWrapper = new HorizontalPanel();
		memberTrainingEntryWrapper.setStyleName("memberTrainingEntryWrapper");
		initWidget(memberTrainingEntryWrapper);
		
		VerticalPanel memberDetailPanel = new VerticalPanel();
		
		deleteButton = new Image("imgs/closeButton.png");
		deleteButton.setStyleName("deleteButton");
		
		membernameLabel = new Label();
		picture = new Image();

		memberDetailPanel.add(membernameLabel);
		
		memberTrainingEntryWrapper.add(picture);
		memberTrainingEntryWrapper.add(memberDetailPanel);
		memberTrainingEntryWrapper.add(deleteButton);

	}

	public Widget asWidget() {
		return this;
	}

	public LocalizationConstants getConstants() {
		return constants;
	}

	public void fillEntry(Member member) {
		membernameLabel.setText("Name: "+member.getForename() + " " + member.getSurname());
		
		
		try {
			if (member.getPicture() == null) {
				picture.setUrl("imgs/standartMember.jpg");
			} else {
				picture.setUrl(member.getPicture());

			}
		} catch (NullPointerException e) {
		}
		
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
