package de.sportschulApp.client.view.admin;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.MultiUploader;
import gwtupload.client.PreloadedImage;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.CreateMemberPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import eu.maydu.gwt.validation.client.DefaultValidationProcessor;
import eu.maydu.gwt.validation.client.ValidationProcessor;
import eu.maydu.gwt.validation.client.description.PopupDescription;

public class CreateMemberView extends Composite implements
		CreateMemberPresenter.Display {
	public class CourseSelectorWidget {
		private VerticalPanel courseVerticalPanel;
		private Label courseLabel;
		private ListBox courseListBox;
		private Label gradeLabel;
		private ListBox gradeListBox;

		public CourseSelectorWidget() {
			courseVerticalPanel = new VerticalPanel();

			HorizontalPanel courseInputPanel = new HorizontalPanel();
			courseLabel = new Label(constants.course() + ": ");
			courseListBox = new ListBox();
			courseListBox.insertItem("<" + constants.select() + ">", 0);
			courseInputPanel.add(courseLabel);
			courseInputPanel.add(courseListBox);

			HorizontalPanel gradeInputPanel = new HorizontalPanel();
			gradeLabel = new Label(constants.grade() + ": ");
			gradeListBox = new ListBox();
			gradeListBox.insertItem("<" + constants.select() + ">", 0);
			gradeInputPanel.add(gradeLabel);
			gradeInputPanel.add(gradeListBox);

			courseVerticalPanel.add(courseInputPanel);
			courseVerticalPanel.add(gradeInputPanel);

		}

		public VerticalPanel getCourseSelector() {
			return courseVerticalPanel;
		}

		public HasChangeHandlers getCourseHandler() {
			return courseListBox;
		}

		public HasChangeHandlers getGradeHandler() {
			return gradeListBox;
		}

		public String getSelectedCourseName() {
			int selected = courseListBox.getSelectedIndex();
			return courseListBox.getItemText(selected);
		}

		public int getSelectedBeltNr() {
			return gradeListBox.getSelectedIndex();
		}

		public String getSelectedBeltName() {
			int selected = gradeListBox.getSelectedIndex();
			return gradeListBox.getItemText(selected);
		}

		public void setCourseList(ArrayList<String> courseList) {
			Iterator<String> itr = courseList.iterator();
			int i = 1;
			while (itr.hasNext()) {
				courseListBox.insertItem(itr.next(), i);
				i++;
			}

		}

		public void setBeltList(ArrayList<String> beltList) {
			Iterator<String> itr = beltList.iterator();
			int i = 1;
			while (itr.hasNext()) {
				gradeListBox.insertItem(itr.next(), i);
				i++;
			}
			gradeListBox.setItemSelected(0, true);

		}

	}

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
	private Label faxLabel;
	private TextBox faxTextBox;
	private Label emailLabel;
	private TextBox emailTextBox;
	private Label homepageLabel;
	private TextBox homepageTextBox;
	private ListBox birthTextBox1;
	private ListBox birthTextBox2;
	private ListBox birthTextBox3;

	private Label birthLabel;
	private TextArea diseasesTextBox;
	private Widget diseasesLabel;
	private TextBox beltsizeTextBox;
	private Label beltsizeLabel;
	private Label noteLabel;
	private TextArea noteTextBox;
	private Label trainingunitsLabel;
	private TextBox trainingunitsTextBox;
	private VerticalPanel createMemberPanel = new VerticalPanel();
	private VerticalPanel createMemberPanel2 = new VerticalPanel();
	private VerticalPanel wrapper = new VerticalPanel();
	private Button sendButton = new Button();
	private MultiUploader defaultUploader;
	ArrayList<CourseSelectorWidget> courseList = new ArrayList<CourseSelectorWidget>();
	private Label pictureUploadLabel;
	private PreloadedImage image;
	HorizontalPanel pictureUploadPanel;
	private String imageUrl;
	private DefaultValidationProcessor validator;

	private DisclosurePanel importantDisclosurePanel;
	private DisclosurePanel additionalDisclosurePanel;

	public CreateMemberView(LocalizationConstants constants) {

		this.constants = constants;
		importantDisclosurePanel = new DisclosurePanel(constants.importantInformations());
		importantDisclosurePanel.setContent(createMemberPanel);
		importantDisclosurePanel.setOpen(true);
		additionalDisclosurePanel = new DisclosurePanel(
				constants.additionalInformations());
		additionalDisclosurePanel.setContent(createMemberPanel2);
		wrapper.add(importantDisclosurePanel);
		wrapper.add(additionalDisclosurePanel);
		wrapper.addStyleName("memberCreateWrapper");
		initWidget(wrapper);

		sendButton.setText(constants.send());

		pictureUploadPanel = new HorizontalPanel();
		pictureUploadLabel = new Label(constants.picture() + ": ");
		defaultUploader = new MultiUploader(FileInputType.LABEL);
		pictureUploadPanel.add(pictureUploadLabel);
		pictureUploadPanel.add(defaultUploader);
		image = new PreloadedImage();
		image.setWidth("75px");

		wrapper.add(sendButton);

		HorizontalPanel forenameInputPanel = new HorizontalPanel();
		forenameLabel = new Label(constants.forename() + ":* ");
		forenameTextBox = new TextBox();
		forenameInputPanel.add(forenameLabel);
		forenameInputPanel.add(forenameTextBox);

		validator = new DefaultValidationProcessor();

		HorizontalPanel surnameInputPanel = new HorizontalPanel();
		surnameLabel = new Label(constants.surname() + ":* ");
		surnameTextBox = new TextBox();
		surnameInputPanel.add(surnameLabel);
		surnameInputPanel.add(surnameTextBox);

		HorizontalPanel barcodeInputPanel = new HorizontalPanel();
		barcodeLabel = new Label(constants.barcode() + ":* ");
		barcodeTextBox = new TextBox();
		barcodeInputPanel.add(barcodeLabel);
		barcodeInputPanel.add(barcodeTextBox);

		HorizontalPanel streetInputPanel = new HorizontalPanel();
		streetLabel = new Label(constants.street() + ":* ");
		streetTextBox = new TextBox();
		streetInputPanel.add(streetLabel);
		streetInputPanel.add(streetTextBox);

		HorizontalPanel zipcodeInputPanel = new HorizontalPanel();
		zipcodeLabel = new Label(constants.zipcode() + ":* ");
		zipcodeTextBox = new TextBox();
		zipcodeInputPanel.add(zipcodeLabel);
		zipcodeInputPanel.add(zipcodeTextBox);

		HorizontalPanel cityInputPanel = new HorizontalPanel();
		cityLabel = new Label(constants.city() + ":* ");
		cityTextBox = new TextBox();
		cityInputPanel.add(cityLabel);
		cityInputPanel.add(cityTextBox);

		HorizontalPanel phoneInputPanel = new HorizontalPanel();
		phoneLabel = new Label(constants.phone() + ":* ");
		phoneTextBox = new TextBox();
		phoneInputPanel.add(phoneLabel);
		phoneInputPanel.add(phoneTextBox);

		HorizontalPanel mobilephoneInputPanel = new HorizontalPanel();
		mobilephoneLabel = new Label(constants.mobilephone() + ": ");
		mobilephoneTextBox = new TextBox();
		mobilephoneInputPanel.add(mobilephoneLabel);
		mobilephoneInputPanel.add(mobilephoneTextBox);

		HorizontalPanel faxInputPanel = new HorizontalPanel();
		faxLabel = new Label(constants.fax() + ": ");
		faxTextBox = new TextBox();
		faxInputPanel.add(faxLabel);
		faxInputPanel.add(faxTextBox);

		HorizontalPanel emailInputPanel = new HorizontalPanel();
		emailLabel = new Label(constants.email() + ": ");
		emailTextBox = new TextBox();
		emailInputPanel.add(emailLabel);
		emailInputPanel.add(emailTextBox);

		HorizontalPanel homepageInputPanel = new HorizontalPanel();
		homepageLabel = new Label(constants.homepage() + ": ");
		homepageTextBox = new TextBox();
		homepageInputPanel.add(homepageLabel);
		homepageInputPanel.add(homepageTextBox);

		HorizontalPanel birthInputPanel = new HorizontalPanel();
		birthLabel = new Label(constants.birth() + ":* ");
		birthTextBox1 = new ListBox();
		birthTextBox2 = new ListBox();
		birthTextBox3 = new ListBox();

		birthTextBox1.addStyleName("birthDayTextBox");
		birthTextBox2.addStyleName("birthMonthTextBox");
		birthTextBox3.addStyleName("birthYearTextBox");

		birthTextBox1.addItem(constants.day());
		for (Integer i = 1; i < 32; i++) {
			if (i < 10) {
				birthTextBox1.addItem("0" + i.toString() + ".");
			} else {
				birthTextBox1.addItem(i.toString() + ".");
			}
		}

		birthTextBox2.addItem(constants.month());
		birthTextBox2.addItem(constants.january());
		birthTextBox2.addItem(constants.february());
		birthTextBox2.addItem(constants.march());
		birthTextBox2.addItem(constants.april());
		birthTextBox2.addItem(constants.may());
		birthTextBox2.addItem(constants.june());
		birthTextBox2.addItem(constants.july());
		birthTextBox2.addItem(constants.august());
		birthTextBox2.addItem(constants.september());
		birthTextBox2.addItem(constants.october());
		birthTextBox2.addItem(constants.november());
		birthTextBox2.addItem(constants.december());

		birthTextBox3.addItem(constants.year());
		for (Integer i = 2008; i > 1960; i--) {
			birthTextBox3.addItem(i.toString());
		}

		birthInputPanel.add(birthLabel);
		birthInputPanel.add(birthTextBox1);
		birthInputPanel.add(birthTextBox2);
		birthInputPanel.add(birthTextBox3);

		HorizontalPanel diseasesInputPanel = new HorizontalPanel();
		diseasesLabel = new Label(constants.diseases() + ": ");
		diseasesTextBox = new TextArea();
		diseasesInputPanel.add(diseasesLabel);
		diseasesInputPanel.add(diseasesTextBox);

		HorizontalPanel beltsizeInputPanel = new HorizontalPanel();
		beltsizeLabel = new Label(constants.beltsize() + ":* ");
		beltsizeTextBox = new TextBox();
		beltsizeInputPanel.add(beltsizeLabel);
		beltsizeInputPanel.add(beltsizeTextBox);

		HorizontalPanel noteInputPanel = new HorizontalPanel();
		noteLabel = new Label(constants.note() + ": ");
		noteTextBox = new TextArea();
		noteInputPanel.add(noteLabel);
		noteInputPanel.add(noteTextBox);

		HorizontalPanel trainingunitsInputPanel = new HorizontalPanel();
		trainingunitsLabel = new Label(constants.trainingunits() + ":* ");
		trainingunitsTextBox = new TextBox();
		trainingunitsInputPanel.add(trainingunitsLabel);
		trainingunitsInputPanel.add(trainingunitsTextBox);

		for (int i = 0; i < 10; i++) {
			courseList.add(new CourseSelectorWidget());
		}

		createMemberPanel.add(forenameInputPanel);
		createMemberPanel.add(surnameInputPanel);
		createMemberPanel.add(barcodeInputPanel);
		createMemberPanel.add(streetInputPanel);
		createMemberPanel.add(zipcodeInputPanel);
		createMemberPanel.add(cityInputPanel);
		createMemberPanel.add(birthInputPanel);
		createMemberPanel.add(phoneInputPanel);
		createMemberPanel2.add(mobilephoneInputPanel);
		createMemberPanel2.add(faxInputPanel);
		createMemberPanel2.add(emailInputPanel);
		createMemberPanel2.add(homepageInputPanel);
		createMemberPanel2.add(homepageInputPanel);
		createMemberPanel.add(beltsizeInputPanel);
		createMemberPanel.add(trainingunitsInputPanel);
		createMemberPanel2.add(diseasesInputPanel);
		createMemberPanel2.add(noteInputPanel);
		createMemberPanel.add(courseList.get(1).getCourseSelector());
		createMemberPanel.add(pictureUploadPanel);

	}

	public Widget asWidget() {
		return this;
	}

	public void setCourseList(ArrayList<String> courseList) {
		for (int i = 0; i < 10; i++) {
			this.courseList.get(i).setCourseList(courseList);
		}
	}

	public HasClickHandlers getSendButton() {
		return sendButton;
	}

	public HasChangeHandlers getCourseHandler(int index) {
		return courseList.get(index).getCourseHandler();
	}

	public HasChangeHandlers getGradeHandler(int index) {
		return courseList.get(index).getGradeHandler();
	}

	public String getSelectedCourseName(int index) {
		return courseList.get(index).getSelectedCourseName();

	}

	public int getSelectedBeltNr(int index) {
		return courseList.get(index).getSelectedBeltNr();
	}

	public void setBeltList(int index, ArrayList<String> beltList) {
		this.courseList.get(index).setBeltList(beltList);
	}

	public void addNewCourseSelector() {
		for (int i = 0; i < 10; i++) {
			if (!courseList.get(i).getCourseSelector().isAttached()) {
				createMemberPanel.insert(courseList.get(i).getCourseSelector(),
						createMemberPanel.getWidgetCount() - 1);
				break;
			}
		}
	}

	public MultiUploader getUploadHandler() {
		return defaultUploader;
	}

	public void setImage(PreloadedImage image, String imageUrl) {

		pictureUploadPanel.remove(defaultUploader);
		pictureUploadPanel.add(image);
		this.imageUrl = imageUrl;
	}

	public ValidationProcessor getValidator() {
		return validator;
	}

	public TextBox getForenameTextBox() {
		return forenameTextBox;
	}

	public TextBox getSurnameTextBox() {
		return surnameTextBox;
	}

	public TextBox getBarcodeTextBox() {
		return barcodeTextBox;
	}

	public TextBox getStreetTextBox() {
		return streetTextBox;
	}

	public TextBox getZipcodeTextBox() {
		return zipcodeTextBox;
	}

	public TextBox getCityTextBox() {
		return cityTextBox;
	}

	public TextBox getPhoneTextBox() {
		return phoneTextBox;
	}

	public TextBox getmobilephoneTextBox() {
		return mobilephoneTextBox;
	}

	public TextBox getEmailTextBox() {
		return emailTextBox;
	}

	public TextBox getFaxTextBox() {
		return faxTextBox;
	}

	public TextBox getHomepageTextBox() {
		return homepageTextBox;
	}

	public ListBox getBirthTextBox1() {
		return birthTextBox1;
	}

	public ListBox getBirthTextBox2() {
		return birthTextBox2;
	}

	public ListBox getBirthTextBox3() {
		return birthTextBox3;
	}

	public TextArea getDiseasesTextBox() {
		return diseasesTextBox;
	}

	public TextBox getBeltsizeTextBox() {
		return beltsizeTextBox;
	}

	public TextArea getNoteTextBox() {
		return noteTextBox;
	}

	public TextBox getTrainingunitsTextBox() {
		return trainingunitsTextBox;
	}

	public ListBox getCourseListBox() {
		return courseList.get(0).courseListBox;
	}

	public ListBox getGradeListBox() {
		return null;
	}

	public String getPictureUrl() {
		return imageUrl;
	}

	public ArrayList<CourseSelectorWidget> getCourseList() {
		return courseList;
	}

	public String getListBoxString() {
		return "<" + constants.select() + ">";
	}

	public LocalizationConstants getConstants() {
		return constants;
	}

}
