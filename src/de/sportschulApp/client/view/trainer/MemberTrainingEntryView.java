package de.sportschulApp.client.view.trainer;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.trainer.MemberTrainingEntryPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Member;

public class MemberTrainingEntryView extends Composite implements
MemberTrainingEntryPresenter.Display {

	private Label birthdayLabel;
	private Button cancelButton;
	private LocalizationConstants constants;
	private Image deleteButton;
	private HorizontalPanel diseasesPanel;
	private Member member;
	private Label membernameLabel1;
	private Label membernameLabel2;
	private VerticalPanel memberTrainingEntryWrapper1;
	private Image noteButton;
	private Image picture;
	private DecoratedPopupPanel popup;
	private TextArea popupTextBox;
	private Button saveNoteButton;
	private Label trainingsPresenceLabel1;
	private Label trainingsPresenceLabel2;

	public MemberTrainingEntryView(LocalizationConstants constants) {
		this.constants = constants;

		popup = new DecoratedPopupPanel(true);
		saveNoteButton = new Button(constants.save());
		cancelButton = new Button(constants.cancel());

		memberTrainingEntryWrapper1 = new VerticalPanel();

		HorizontalPanel memberTrainingEntryWrapper2 = new HorizontalPanel();
		memberTrainingEntryWrapper2.setStyleName("memberTrainingEntryWrapper");
		memberTrainingEntryWrapper1.add(memberTrainingEntryWrapper2);
		initWidget(memberTrainingEntryWrapper1);

		VerticalPanel memberDetailPanel1 = new VerticalPanel();
		memberDetailPanel1.setStyleName("memberDetailPanel1");
		VerticalPanel memberDetailPanel2 = new VerticalPanel();
		memberDetailPanel2.setStyleName("memberDetailPanel2");

		noteButton = new Image("imgs/note.png");
		noteButton.setStyleName("noteButton");

		deleteButton = new Image("imgs/closeButton.png");
		deleteButton.setStyleName("deleteButton");

		membernameLabel1 = new Label(constants.name() + ": ");
		membernameLabel2 = new Label();
		trainingsPresenceLabel1 = new Label();
		trainingsPresenceLabel2 = new Label();
		birthdayLabel = new Label();

		picture = new Image();

		memberDetailPanel1.add(membernameLabel1);
		memberDetailPanel1.add(trainingsPresenceLabel1);
		memberDetailPanel1.add(birthdayLabel);

		memberDetailPanel2.add(membernameLabel2);
		memberDetailPanel2.add(trainingsPresenceLabel2);

		memberTrainingEntryWrapper2.add(picture);
		memberTrainingEntryWrapper2.add(memberDetailPanel1);
		memberTrainingEntryWrapper2.add(memberDetailPanel2);
		memberTrainingEntryWrapper2.add(noteButton);
		memberTrainingEntryWrapper2.add(deleteButton);

		diseasesPanel = new HorizontalPanel();
		diseasesPanel.setStyleName("diseasesPanel");

	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public void buildPopup() {
	}

	public void closePopup() {
		popup.hide();
	}

	public void fillEntry(Member member, Integer trainingspresence) {

		this.member = member;
		Date today = new Date();
		HashMap<Integer, String> months = new HashMap<Integer, String>();
		months.put(0, constants.january());
		months.put(1, constants.february());
		months.put(2, constants.march());
		months.put(3, constants.april());
		months.put(4, constants.may());
		months.put(5, constants.june());
		months.put(6, constants.july());
		months.put(7, constants.august());
		months.put(8, constants.september());
		months.put(9, constants.october());
		months.put(10, constants.november());
		months.put(11, constants.december());

		// member details
		membernameLabel2.setText(member.getForename() + " "
				+ member.getSurname());
		trainingsPresenceLabel1.setText(constants.presence() + " "
				+ months.get(today.getMonth()) + ": ");
		trainingsPresenceLabel2.setText(trainingspresence + " "
				+ constants.from() + " " + member.getTrainingunits());
		if (trainingspresence <= member.getTrainingunits()) {

			// TODO
			trainingsPresenceLabel2.setStyleName("trainingspresenceGreen");
			// thumbs up
		} else {

			// TODO
			trainingsPresenceLabel2.setStyleName("trainingspresenceRed");

			// thumbs down
		}

		// Image

		picture.setUrl(member.getPicture());

		// krankheiten
		try {
			if (member.getDiseases() != null) {
				if (member.getDiseases().length() > 0) {
					memberTrainingEntryWrapper1.add(diseasesPanel);
					Label diseasesLabel = new Label("Beschwerden: "
							+ member.getDiseases());
					diseasesPanel.add(diseasesLabel);
				}
			} else {

			}
		} catch (NullPointerException e) {
		}

		// geburtstags erinnerung
		Date memberBirthDate = new Date();

		memberBirthDate
		.setYear(Integer.parseInt((member.getBirthYear())) - 1900);
		memberBirthDate.setMonth(Integer.parseInt(member.getBirthMonth()) - 1);
		memberBirthDate.setDate(Integer.parseInt(member.getBirthDay()));

		// TODO
		if (((memberBirthDate.getDate() == today.getDate()))
				&& memberBirthDate.getMonth() == today.getMonth()) {
			System.out.println("Member hat heute Geburtstag!");
			showBirthdayLabel(0);
		} else if (((memberBirthDate.getDate() - today.getDate()) >= -7)
				&& ((memberBirthDate.getDate() - today.getDate()) <= 0)
				&& memberBirthDate.getMonth() == today.getMonth()) {
			showBirthdayLabel(1);
			System.out.println("Member hatte am " + member.getBirthDay() + "."
					+ member.getBirthMonth() + "." + member.getBirthYear()
					+ " Geburtstag");
		} else if (((today.getDate() - memberBirthDate.getDate()) <= -23)
				&& memberBirthDate.getMonth() == today.getMonth() - 1) {
			showBirthdayLabel(1);
			System.out.println("Member hatte am " + member.getBirthDay() + "."
					+ member.getBirthMonth() + "." + member.getBirthYear()
					+ " Geburtstag");
		} else if (((memberBirthDate.getDate() - today.getDate()) <= 7)
				&& memberBirthDate.getMonth() == today.getMonth()) {
			showBirthdayLabel(2);
			System.out.println("Member hat am " + member.getBirthDay() + "."
					+ member.getBirthMonth() + "." + member.getBirthYear()
					+ " Geburtstag");
		} else if (((memberBirthDate.getDate() - today.getDate()) <= -24)
				&& memberBirthDate.getMonth() == today.getMonth() + 1) {
			showBirthdayLabel(2);
			System.out.println("Member hat am " + member.getBirthDay() + "."
					+ member.getBirthMonth() + "." + member.getBirthYear()
					+ " Geburtstag");
		}
	}

	public LocalizationConstants getConstants() {
		return constants;
	}

	public Image getDeleteButton() {
		return deleteButton;
	}

	public Image getNoteButton() {
		return noteButton;
	}

	public HasClickHandlers getPopupCancelButton() {
		return cancelButton;
	}

	public HasClickHandlers getPopupSendButton() {
		return saveNoteButton;
	}

	public String getPopupTextArea() {
		return popupTextBox.getText();
	}

	public void showBirthdayLabel(int index) {
		if (index == 0) {
			birthdayLabel.setText(member.getForename()
					+ " hat heute Geburtstag");
		} else if (index == 1) {
			birthdayLabel.setText(member.getForename() + " hatte am "
					+ member.getBirthDay() + "." + member.getBirthMonth()
					+ ". Geburtstag");
		} else if (index == 2) {
			birthdayLabel.setText(member.getForename() + " hat am "
					+ member.getBirthDay() + "." + member.getBirthMonth()
					+ ". Geburtstag");
		}
	}

	public void showPopup(int left, int top, String note) {

		popup.setStyleName("showNotePopup");

		VerticalPanel popupVerticalPanel = new VerticalPanel();

		popupTextBox = new TextArea();
		popupTextBox.setText(note);
		popupVerticalPanel.add(popupTextBox);

		HorizontalPanel popupButtonPanel = new HorizontalPanel();

		popupButtonPanel.add(saveNoteButton);
		popupButtonPanel.add(cancelButton);

		popupVerticalPanel.add(popupButtonPanel);

		popup.setWidget(popupVerticalPanel);

		popup.setPopupPosition(left, top);

		popup.show();

	}
}
