package de.sportschulApp.client.view.admin;

import gwtupload.client.MultiUploader;
import gwtupload.client.PreloadedImage;
import gwtupload.client.IFileInput.FileInputType;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.rpc.client.RpcService;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.CreateMemberPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.CourseTariff;
import de.sportschulApp.shared.Member;
import eu.maydu.gwt.validation.client.DefaultValidationProcessor;
import eu.maydu.gwt.validation.client.ValidationProcessor;

public class CreateMemberView extends Composite implements
		CreateMemberPresenter.Display {
	public class CourseSelectorWidget {
		private Label courseLabel;
		private ListBox courseListBox;
		private VerticalPanel courseVerticalPanel;
		private Label gradeLabel;
		private ListBox gradeListBox;
		private Label tariffLabel;
		private ListBox tariffListBox;
		private Label courseSelectLabel;
		int index;
		private Image deleteButton;
		private HorizontalPanel tariffInputPanel;
		private HorizontalPanel courseHorizontalPanel;

		public CourseSelectorWidget() {

			courseHorizontalPanel = new HorizontalPanel();
			courseVerticalPanel = new VerticalPanel();
			courseHorizontalPanel.add(courseVerticalPanel);

			courseHorizontalPanel.setStyleName("courseSelectorPanel");
			courseSelectLabel = new Label();
			courseSelectLabel.setStyleName("courseSelectLabel");

			HorizontalPanel courseInputPanel = new HorizontalPanel();
			courseLabel = new Label(constants.course() + ": ");
			courseListBox = new ListBox();
			courseListBox.insertItem("<" + constants.select() + ">", 0);
			courseInputPanel.add(courseLabel);
			courseInputPanel.add(courseListBox);

			tariffInputPanel = new HorizontalPanel();
			tariffLabel = new Label(constants.tariff() + ": ");
			tariffListBox = new ListBox();
			tariffListBox.insertItem("<" + constants.select() + ">", 0);
			tariffInputPanel.add(tariffLabel);
			tariffInputPanel.add(tariffListBox);

			HorizontalPanel gradeInputPanel = new HorizontalPanel();
			gradeLabel = new Label(constants.grade() + ": ");
			gradeListBox = new ListBox();
			gradeListBox.insertItem("<" + constants.select() + ">", 0);
			gradeInputPanel.add(gradeLabel);
			gradeInputPanel.add(gradeListBox);

			deleteButton = new Image("/imgs/Symbol_Delete.png");
			deleteButton.setStyleName("clickable");
			deleteButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					courseSelectorWrapper.remove(index - 1);

					int j = 0;
					for (int i = 0; i < 15; i++) {

						if (!courseList.get(i).getCourseSelector().isAttached()) {
							courseList.get(i).resetSelector();

						} else {
							courseList.get(i).setCourseLabel(j + 1);
							j++;

						}

					}

				}
			});

			courseVerticalPanel.add(courseSelectLabel);
			courseVerticalPanel.add(courseInputPanel);
			courseVerticalPanel.add(tariffInputPanel);
			courseVerticalPanel.add(gradeInputPanel);
			courseHorizontalPanel.add(deleteButton);

		}

		public void resetSelector() {
			courseListBox.setSelectedIndex(0);
			gradeListBox.setSelectedIndex(0);
			tariffListBox.setSelectedIndex(0);
		}

		public void noDeleteButton() {
			deleteButton.setVisible(false);
			// courseHorizontalPanel.remove(deleteButton);
		}

		public HasChangeHandlers getCourseHandler() {
			return courseListBox;
		}

		public ListBox getCourseListBox() {
			return courseListBox;
		}

		public ListBox getGraduationListBox() {
			return gradeListBox;
		}

		public ListBox getTariffListBox() {
			return tariffListBox;
		}

		public HorizontalPanel getCourseSelector() {
			return courseHorizontalPanel;
		}

		public HasChangeHandlers getGradeHandler() {
			return gradeListBox;
		}

		public String getSelectedBeltName() {
			int selected = gradeListBox.getSelectedIndex();
			return gradeListBox.getItemText(selected);
		}

		public int getSelectedBeltNr() {
			return gradeListBox.getSelectedIndex();
		}

		public VerticalPanel getWrapper() {
			return courseSelectorWrapper;
		}

		public String getSelectedCourseName() {
			int selected = courseListBox.getSelectedIndex();
			return courseListBox.getItemText(selected);
		}

		public String getSelectedTariff() {
			int selected = tariffListBox.getSelectedIndex();
			String temp = tariffListBox.getItemText(selected);
			temp = temp.substring((temp.indexOf("-") + 1), temp.length() - 2);
			System.out.println("temp= " + temp);
			return temp;
		}

		public String getSelectedTariffName() {
			int selected = tariffListBox.getSelectedIndex();
			return tariffListBox.getItemText(selected);
		}

		public void setBeltList(ArrayList<String> beltList) {
			gradeListBox.clear();
			gradeListBox.insertItem("<" + constants.select() + ">", 0);
			Iterator<String> itr = beltList.iterator();
			int i = 1;
			while (itr.hasNext()) {
				gradeListBox.insertItem(itr.next(), i);
				i++;
			}
			// gradeListBox.setItemSelected(0, true);

		}

		public void setCourseList(ArrayList<String> courseList) {
			Iterator<String> itr = courseList.iterator();
			int i = 1;
			while (itr.hasNext()) {
				courseListBox.insertItem(itr.next(), i);
				i++;
			}

		}

		public void setTariffList(ArrayList<CourseTariff> tariffList) {
			tariffListBox.clear();
			tariffListBox.insertItem("<" + constants.select() + ">", 0);
			for (int j = 0; j < tariffList.size(); j++) {
				tariffListBox.insertItem(tariffList.get(j).getName() + " - "
						+ tariffList.get(j).getCosts() + " €", j + 1);
			}
			gradeListBox.setItemSelected(0, true);
		}

		public void setCourseLabel(int i) {
			courseSelectLabel.setText("Kurs " + i);
			index = i;
		}

	}

	private Label accountNumberLabel;
	private TextBox accountNumberTextBox;
	private Label accountOwnerForenameLabel;
	private TextBox accountOwnerForenameTextBox;
	private Label accountOwnerSurnameLabel;
	private TextBox accountOwnerSurnameTextBox;
	private DisclosurePanel additionalDisclosurePanel;
	private DisclosurePanel bankAccountDisclosurePanel;
	private VerticalPanel bankAccountPanel = new VerticalPanel();
	private Label bankNameLabel;
	private TextBox bankNameTextBox;
	private Label bankNumberLabel;
	private TextBox bankNumberTextBox;
	private HorizontalPanel barcodeInputPanel;
	private Label barcodeLabel;
	private TextBox barcodeTextBox;
	private Label beltsizeLabel;
	private Label birthLabel;
	private ListBox birthTextBox1;
	private ListBox birthTextBox2;
	private ListBox birthTextBox3;
	private Label cityLabel;
	private TextBox cityTextBox;
	private LocalizationConstants constants;
	ArrayList<CourseSelectorWidget> courseList = new ArrayList<CourseSelectorWidget>();
	private VerticalPanel createMemberPanel = new VerticalPanel();
	private VerticalPanel createMemberPanel2 = new VerticalPanel();
	private MultiUploader defaultUploader;
	private Widget diseasesLabel;
	private TextArea diseasesTextBox;
	private Label emailLabel;
	private TextBox emailTextBox;
	private Label faxLabel;
	private TextBox faxTextBox;
	private Label forenameLabel;
	private TextBox forenameTextBox;
	private Label homepageLabel;
	private TextBox homepageTextBox;
	private PreloadedImage image;
	private String imageUrl;
	private DisclosurePanel importantDisclosurePanel;
	private CheckBox likeAbove;
	private CheckBox likeAbove2;
	private Label mobilephoneLabel;
	private TextBox mobilephoneTextBox;
	private Label noteLabel;

	private TextArea noteTextBox;
	private Label phoneLabel;
	private TextBox phoneTextBox;
	private Label pictureUploadLabel;
	HorizontalPanel pictureUploadPanel;
	private Button sendButton = new Button();
	private Label streetLabel;
	private TextBox streetTextBox;
	private Label surnameLabel;
	private TextBox surnameTextBox;
	private DefaultValidationProcessor validator;
	private VerticalPanel wrapper = new VerticalPanel();
	private Label zipcodeLabel;
	private TextBox zipcodeTextBox;
	private VerticalPanel courseSelectorWrapper;
	private Label newCourseSelectorLabel;
	private ListBox beltsizeListBox;
	private Label header;

	public CreateMemberView(LocalizationConstants constants) {

		this.constants = constants;
		importantDisclosurePanel = new DisclosurePanel(
				constants.importantInformations());
		importantDisclosurePanel.setContent(createMemberPanel);
		importantDisclosurePanel.setOpen(true);
		importantDisclosurePanel.setStyleName("disclosurePanel");
		bankAccountDisclosurePanel = new DisclosurePanel(
				constants.accountInformations());
		bankAccountDisclosurePanel.setContent(bankAccountPanel);
		bankAccountDisclosurePanel.setOpen(true);
		bankAccountDisclosurePanel.setStyleName("disclosurePanel");
		additionalDisclosurePanel = new DisclosurePanel(
				constants.additionalInformations());
		additionalDisclosurePanel.setContent(createMemberPanel2);
		additionalDisclosurePanel.setStyleName("AdditionalisclosurePanel");

		header = new Label("Mitglied anlegen");
		header.setStyleName("formHeader2");

		wrapper.add(header);
		wrapper.add(importantDisclosurePanel);
		wrapper.add(bankAccountDisclosurePanel);
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

		barcodeInputPanel = new HorizontalPanel();
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

		HorizontalPanel accountOwnerForenameInputPanel = new HorizontalPanel();
		VerticalPanel likeAbovePanel = new VerticalPanel();
		likeAbovePanel.setStyleName("likeAbovePanel");
		accountOwnerForenameLabel = new Label(constants.forename() + ":* ");
		accountOwnerForenameTextBox = new TextBox();
		likeAbove = new CheckBox(constants.likeAbove());
		accountOwnerForenameInputPanel.add(accountOwnerForenameLabel);
		accountOwnerForenameInputPanel.add(likeAbovePanel);
		likeAbovePanel.add(accountOwnerForenameTextBox);
		likeAbovePanel.add(likeAbove);

		HorizontalPanel accountOwnerSurnameInputPanel = new HorizontalPanel();
		VerticalPanel likeAbovePanel2 = new VerticalPanel();
		likeAbovePanel2.setStyleName("likeAbovePanel");
		accountOwnerSurnameLabel = new Label(constants.surname() + ":* ");
		accountOwnerSurnameTextBox = new TextBox();
		likeAbove2 = new CheckBox(constants.likeAbove());
		accountOwnerSurnameInputPanel.add(accountOwnerSurnameLabel);
		accountOwnerSurnameInputPanel.add(likeAbovePanel2);
		likeAbovePanel2.add(accountOwnerSurnameTextBox);
		likeAbovePanel2.add(likeAbove2);

		HorizontalPanel accountNumberInputPanel = new HorizontalPanel();
		accountNumberLabel = new Label(constants.accountNumber() + ":* ");
		accountNumberTextBox = new TextBox();
		accountNumberInputPanel.add(accountNumberLabel);
		accountNumberInputPanel.add(accountNumberTextBox);

		HorizontalPanel bankNumberInputPanel = new HorizontalPanel();
		bankNumberLabel = new Label(constants.bankNumber() + ":* ");
		bankNumberTextBox = new TextBox();
		bankNumberInputPanel.add(bankNumberLabel);
		bankNumberInputPanel.add(bankNumberTextBox);

		HorizontalPanel bankNameInputPanel = new HorizontalPanel();
		bankNameLabel = new Label(constants.bankName() + ":* ");
		bankNameTextBox = new TextBox();
		bankNameInputPanel.add(bankNameLabel);
		bankNameInputPanel.add(bankNameTextBox);

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

		
		//TODO
		HorizontalPanel beltsizeInputPanel = new HorizontalPanel();
		beltsizeLabel = new Label(constants.beltsize() + ":* ");
		beltsizeListBox = new ListBox();
		beltsizeListBox.addItem("<auswählen>");
		beltsizeListBox.addItem("200");
		beltsizeListBox.addItem("220");
		beltsizeListBox.addItem("240");
		beltsizeListBox.addItem("260");
		beltsizeListBox.addItem("280");
		beltsizeListBox.addItem("300");

		beltsizeListBox.setWidth("255px");
		
		beltsizeInputPanel.add(beltsizeLabel);
		beltsizeInputPanel.add(beltsizeListBox);

		HorizontalPanel noteInputPanel = new HorizontalPanel();
		noteLabel = new Label(constants.note() + ": ");
		noteTextBox = new TextArea();
		noteInputPanel.add(noteLabel);
		noteInputPanel.add(noteTextBox);

		for (int i = 0; i < 15; i++) {
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
		createMemberPanel.add(mobilephoneInputPanel);

		bankAccountPanel.add(accountOwnerForenameInputPanel);
		bankAccountPanel.add(accountOwnerSurnameInputPanel);
		bankAccountPanel.add(accountNumberInputPanel);
		bankAccountPanel.add(bankNameInputPanel);
		bankAccountPanel.add(bankNumberInputPanel);

		createMemberPanel2.add(faxInputPanel);
		createMemberPanel2.add(emailInputPanel);
		createMemberPanel2.add(homepageInputPanel);
		createMemberPanel2.add(homepageInputPanel);
		createMemberPanel.add(beltsizeInputPanel);
		createMemberPanel2.add(diseasesInputPanel);
		createMemberPanel2.add(noteInputPanel);

		courseSelectorWrapper = new VerticalPanel();
		createMemberPanel.add(courseSelectorWrapper);
		courseSelectorWrapper.add(courseList.get(0).getCourseSelector());
		courseList.get(0).setCourseLabel(1);
		courseList.get(0).noDeleteButton();
		newCourseSelectorLabel = new Label("Weiteren Kurs hinzufügen");
		newCourseSelectorLabel.setStyleName("addNewCourseSelctorLabel");
		courseSelectorWrapper.add(newCourseSelectorLabel);

		createMemberPanel.add(pictureUploadPanel);

	}

	public void addNewCourseSelector() {
		for (int i = 0; i < 15; i++) {
			if (!courseList.get(i).getCourseSelector().isAttached()) {
				courseSelectorWrapper.insert(courseList.get(i)
						.getCourseSelector(), courseSelectorWrapper
						.getWidgetCount() - 1);
				courseList.get(i).setCourseLabel(courseSelectorWrapper
						.getWidgetCount() - 1);
				break;
			}
		}
	}

	public void addNewCourseSelector(String course, float tariff, int graduation) {
		System.out
				.println("LALALAL" + course + "," + tariff + "," + graduation);
		int temp2 = 0;
		for (int i = 0; i < 10; i++) {
			if (!courseList.get(i).getCourseSelector().isAttached()) {
				createMemberPanel.insert(courseList.get(i).getCourseSelector(),
						createMemberPanel.getWidgetCount() - 1);
				temp2 = i;
				break;
			}

		}
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public void fillForm(Member member) {
		
		header.setText("Mitglied bearbeiten");
		importantDisclosurePanel.setOpen(true);
		additionalDisclosurePanel.setOpen(true);
		forenameTextBox.setText(member.getForename());
		surnameTextBox.setText(member.getSurname());
		barcodeTextBox.setText("" + member.getBarcodeID());
		createMemberPanel.remove(barcodeInputPanel);
		streetTextBox.setText(member.getStreet());
		zipcodeTextBox.setText("" + member.getZipcode());
		cityTextBox.setText(member.getCity());
		birthTextBox1.setSelectedIndex(Integer.parseInt(""
				+ member.getBirthDay()));
		birthTextBox2.setSelectedIndex(Integer.parseInt(""
				+ member.getBirthMonth()));
		int temp = Integer.parseInt("" + (member.getBirthYear())) - 2009;
		birthTextBox3.setSelectedIndex(-temp);
		phoneTextBox.setText(member.getPhone());
		//TODO
		for(int i=0;i<beltsizeListBox.getItemCount();i++){
			if(beltsizeListBox.getItemText(i).equals(member.getBeltsize())){
				beltsizeListBox.setSelectedIndex(i);
			}
		
		}
		//beltsizeListBox.setSelectedIndex(i);
		//beltsizeTextBox.setText(member.getBeltsize());
		mobilephoneTextBox.setText(member.getMobilephone());
		faxTextBox.setText(member.getFax());
		emailTextBox.setText(member.getEmail());
		homepageTextBox.setText(member.getHomepage());
		diseasesTextBox.setText(member.getDiseases());
		noteTextBox.setText(member.getNote());
		imageUrl = member.getPicture();

		if (member.getAccountForename().equals(member.getForename())) {
			accountOwnerForenameTextBox.setText(constants.likeAbove());
			accountOwnerForenameTextBox.setReadOnly(true);
			likeAbove.setValue(true);
		} else {
			accountOwnerForenameTextBox.setText(member.getAccountForename());
		}

		if (member.getAccountSurname().equals(member.getSurname())) {
			accountOwnerSurnameTextBox.setText(constants.likeAbove());
			accountOwnerSurnameTextBox.setReadOnly(true);
			likeAbove2.setValue(true);
		} else {
			accountOwnerSurnameTextBox.setText(member.getAccountSurname());
		}

		accountNumberTextBox.setText(member.getAccountNumber());
		bankNameTextBox.setText(member.getBankName());
		bankNumberTextBox.setText(member.getBankNumber());


	}

	public TextBoxBase getAccountForenameTextBox() {
		return accountOwnerForenameTextBox;
	}

	public TextBoxBase getAccountNumberTextBox() {
		return accountNumberTextBox;
	}

	public TextBoxBase getAccountSurnameTextBox() {
		return accountOwnerSurnameTextBox;
	}

	public TextBox getAccoutForenameTextbox() {
		return accountOwnerForenameTextBox;
	}

	public TextBox getAccoutSurnameTextbox() {
		return accountOwnerSurnameTextBox;
	}

	public TextBoxBase getBankNameTextBox() {
		return bankNameTextBox;
	}

	public TextBoxBase getBankNumberTextBox() {
		return bankNumberTextBox;
	}

	public TextBox getBarcodeTextBox() {
		return barcodeTextBox;
	}

	public ListBox getBeltsizeTextBox() {
		return beltsizeListBox;
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

	public TextBox getCityTextBox() {
		return cityTextBox;
	}

	public LocalizationConstants getConstants() {
		return constants;
	}

	public HasChangeHandlers getCourseHandler(int index) {
		return courseList.get(index).getCourseHandler();
	}

	public ArrayList<CourseSelectorWidget> getCourseList() {
		return courseList;
	}

	public ListBox getCourseListBox() {
		return courseList.get(0).courseListBox;
	}

	public TextArea getDiseasesTextBox() {
		return diseasesTextBox;
	}

	public TextBox getEmailTextBox() {
		return emailTextBox;
	}

	public TextBox getFaxTextBox() {
		return faxTextBox;
	}

	public CheckBox getForenameCheckBox() {
		return likeAbove;
	}

	public TextBox getForenameTextBox() {
		return forenameTextBox;
	}

	public HasChangeHandlers getGradeHandler(int index) {
		return courseList.get(index).getGradeHandler();
	}

	public ListBox getGradeListBox() {
		return null;
	}

	public TextBox getHomepageTextBox() {
		return homepageTextBox;
	}

	public String getListBoxString() {
		return "<" + constants.select() + ">";
	}

	public TextBox getmobilephoneTextBox() {
		return mobilephoneTextBox;
	}

	public TextArea getNoteTextBox() {
		return noteTextBox;
	}

	public TextBox getPhoneTextBox() {
		return phoneTextBox;
	}

	public String getPictureUrl() {
		return imageUrl;
	}

	public int getSelectedBeltNr(int index) {
		return courseList.get(index).getSelectedBeltNr();
	}

	public String getSelectedCourseName(int index) {
		return courseList.get(index).getSelectedCourseName();

	}

	public float getSelectedTariff(int index) {
		return Float.valueOf(courseList.get(index).getSelectedTariff());

		// Integer.parseInt(courseList.get(index).getSelectedTariff());
	}

	public HasClickHandlers getSendButton() {
		return sendButton;
	}

	public TextBox getStreetTextBox() {
		return streetTextBox;
	}

	public CheckBox getSurnameCheckBox() {
		return likeAbove2;
	}

	public TextBox getSurnameTextBox() {
		return surnameTextBox;
	}

	public MultiUploader getUploadHandler() {
		return defaultUploader;
	}

	public ValidationProcessor getValidator() {
		return validator;
	}

	public TextBox getZipcodeTextBox() {
		return zipcodeTextBox;
	}

	public void setBeltList(int index, ArrayList<String> beltList) {
		courseList.get(index).setBeltList(beltList);
	}

	public void setTariffList(int index, ArrayList<CourseTariff> tariffList) {
		courseList.get(index).setTariffList(tariffList);
	}

	public void setCourseList(ArrayList<String> courseList) {
		for (int i = 0; i < 10; i++) {
			this.courseList.get(i).setCourseList(courseList);
		}
	}

	public void setImage(PreloadedImage image, String imageUrl) {

		pictureUploadPanel.remove(defaultUploader);
		pictureUploadPanel.add(image);
		this.imageUrl = imageUrl;
	}

	public int calculateTrainingUnits() {
		int temp3 = 0;
		try {
			for (int i = 0; i < courseList.size(); i++) {
				String temp = courseList.get(i).getSelectedTariffName();
				int temp2 = temp.indexOf(" ");
				temp3 = temp3 + Integer.parseInt(temp.substring(0, temp2));

			}
		} catch (IndexOutOfBoundsException e) {
		}
		return temp3;
	}

	public HasClickHandlers getNewCourseSelectorLabel() {
		return newCourseSelectorLabel;
	}

	public void removeLastCourseSelector() {
		courseSelectorWrapper.remove(courseSelectorWrapper.getWidgetCount()-2);
	}

}
