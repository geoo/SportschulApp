package de.sportschulApp.client.view.trainer;

import com.google.gwt.user.client.Window;
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
	private Label test;
	private Image picture;

	public MemberTrainingEntryView(LocalizationConstants constants) {
		this.constants = constants;

		VerticalPanel memberTrainingEntryWrapper = new VerticalPanel();
		memberTrainingEntryWrapper.setStyleName("memberTrainingEntryWrapper");
		initWidget(memberTrainingEntryWrapper);

		test = new Label();
		picture = new Image();

		memberTrainingEntryWrapper.add(test);
		memberTrainingEntryWrapper.add(picture);

	}

	public Widget asWidget() {
		return this;
	}

	public LocalizationConstants getConstants() {
		return constants;
	}

	public void fillEntry(Member member) {
		test.setText(member.getForename() + " " + member.getSurname());
		try {
			if (member.getPicture() == null) {
				picture.setUrl("imgs/standartMember.jpg");
			} else {
				picture.setUrl(member.getPicture());

			}
		} catch (NullPointerException e) {
		}

	}
}
