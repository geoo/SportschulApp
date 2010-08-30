package de.sportschulApp.client.view.admin;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.CreateMemberPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class CreateMemberView extends Composite implements
		CreateMemberPresenter.Display {
	public class CourseSelectorWidget {
		VerticalPanel courseVerticalPanel;
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
	private Widget faxLabel;
	private TextBox faxTextBox;
	private Label emailLabel;
	private TextBox emailTextBox;
	private Widget homepageLabel;
	private Widget homepageTextBox;
	private Widget birthTextBox;
	private Widget birthLabel;
	private Widget diseasesTextBox;
	private Widget diseasesLabel;
	private Widget beltsizeTextBox;
	private Widget beltsizeLabel;
	private Widget noteLabel;
	private Widget noteTextBox;
	private Widget trainingunitsLabel;
	private Widget trainingunitsTextBox;
	private ListBox trainingunitsListBox;
	private Label grade01Label;
	private Label course01Label;
	private ListBox course01ListBox;
	private ListBox grade01ListBox;
	VerticalPanel createMemberPanel = new VerticalPanel();

	ArrayList<CourseSelectorWidget> courseList = new ArrayList<CourseSelectorWidget>();

	public CreateMemberView(LocalizationConstants constants) {

		this.constants = constants;

		createMemberPanel.addStyleName("memberCreateWrapper");
		createMemberPanel.setWidth("700px");

		initWidget(createMemberPanel);

		HorizontalPanel forenameInputPanel = new HorizontalPanel();
		forenameLabel = new Label(constants.forename() + ": ");
		forenameTextBox = new TextBox();
		forenameInputPanel.add(forenameLabel);
		forenameInputPanel.add(forenameTextBox);

		HorizontalPanel surnameInputPanel = new HorizontalPanel();
		surnameLabel = new Label(constants.surname() + ": ");
		surnameTextBox = new TextBox();
		surnameInputPanel.add(surnameLabel);
		surnameInputPanel.add(surnameTextBox);

		HorizontalPanel barcodeInputPanel = new HorizontalPanel();
		barcodeLabel = new Label(constants.barcode() + ": ");
		barcodeTextBox = new TextBox();
		barcodeInputPanel.add(barcodeLabel);
		barcodeInputPanel.add(barcodeTextBox);

		HorizontalPanel streetInputPanel = new HorizontalPanel();
		streetLabel = new Label(constants.street() + ": ");
		streetTextBox = new TextBox();
		streetInputPanel.add(streetLabel);
		streetInputPanel.add(streetTextBox);

		HorizontalPanel zipcodeInputPanel = new HorizontalPanel();
		zipcodeLabel = new Label(constants.zipcode() + ": ");
		zipcodeTextBox = new TextBox();
		zipcodeInputPanel.add(zipcodeLabel);
		zipcodeInputPanel.add(zipcodeTextBox);

		HorizontalPanel cityInputPanel = new HorizontalPanel();
		cityLabel = new Label(constants.city() + ": ");
		cityTextBox = new TextBox();
		cityInputPanel.add(cityLabel);
		cityInputPanel.add(cityTextBox);

		HorizontalPanel phoneInputPanel = new HorizontalPanel();
		phoneLabel = new Label(constants.phone() + ": ");
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
		birthLabel = new Label(constants.birth() + ": ");
		birthTextBox = new TextBox();
		birthInputPanel.add(birthLabel);
		birthInputPanel.add(birthTextBox);

		HorizontalPanel diseasesInputPanel = new HorizontalPanel();
		diseasesLabel = new Label(constants.diseases() + ": ");
		diseasesTextBox = new TextBox();
		diseasesInputPanel.add(diseasesLabel);
		diseasesInputPanel.add(diseasesTextBox);

		HorizontalPanel beltsizeInputPanel = new HorizontalPanel();
		beltsizeLabel = new Label(constants.beltsize() + ": ");
		beltsizeTextBox = new TextBox();
		beltsizeInputPanel.add(beltsizeLabel);
		beltsizeInputPanel.add(beltsizeTextBox);

		HorizontalPanel noteInputPanel = new HorizontalPanel();
		noteLabel = new Label(constants.note() + ": ");
		noteTextBox = new TextBox();
		noteInputPanel.add(noteLabel);
		noteInputPanel.add(noteTextBox);

		HorizontalPanel trainingunitsInputPanel = new HorizontalPanel();
		trainingunitsLabel = new Label(constants.trainingunits() + ": ");
		trainingunitsTextBox = new TextBox();
		trainingunitsInputPanel.add(trainingunitsLabel);
		trainingunitsInputPanel.add(trainingunitsTextBox);

		/*
		 * HorizontalPanel course01InputPanel = new HorizontalPanel();
		 * course01Label = new Label(constants.course() + ": "); course01ListBox
		 * = new ListBox(); course01ListBox.insertItem("<" + constants.select()
		 * + ">", 0); course01InputPanel.add(course01Label);
		 * course01InputPanel.add(course01ListBox);
		 * 
		 * HorizontalPanel grade01InputPanel = new HorizontalPanel();
		 * grade01Label = new Label(constants.grade() + ": "); grade01ListBox =
		 * new ListBox(); grade01ListBox.insertItem("<" + constants.select() +
		 * ">", 0); grade01InputPanel.add(grade01Label);
		 * grade01InputPanel.add(grade01ListBox);
		 */

		for (int i = 0; i < 10; i++) {
			courseList.add(new CourseSelectorWidget());
		}

		createMemberPanel.add(forenameInputPanel);
		createMemberPanel.add(surnameInputPanel);
		createMemberPanel.add(barcodeInputPanel);
		createMemberPanel.add(streetInputPanel);
		createMemberPanel.add(zipcodeInputPanel);
		createMemberPanel.add(cityInputPanel);
		createMemberPanel.add(phoneInputPanel);
		createMemberPanel.add(mobilephoneInputPanel);
		createMemberPanel.add(faxInputPanel);
		createMemberPanel.add(emailInputPanel);
		createMemberPanel.add(homepageInputPanel);
		createMemberPanel.add(homepageInputPanel);
		createMemberPanel.add(birthInputPanel);
		createMemberPanel.add(diseasesInputPanel);
		createMemberPanel.add(beltsizeInputPanel);
		createMemberPanel.add(noteInputPanel);
		createMemberPanel.add(trainingunitsInputPanel);
		createMemberPanel.add(courseList.get(1).getCourseSelector());

		// createMemberPanel.add(course01InputPanel);
		// createMemberPanel.add(grade01InputPanel);

		// TODO Picture

	}

	public Widget asWidget() {
		return this;
	}

	public void setCourseList(ArrayList<String> courseList) {
		for (int i = 0; i < 10; i++) {
			this.courseList.get(i).setCourseList(courseList);
		}
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

	public void setBeltList(int index, ArrayList<String> beltList) {
		this.courseList.get(index).setBeltList(beltList);
	}

	public void addNewCourseSelector() {
		for (int i = 0; i < 10; i++) {
			if (!courseList.get(i).getCourseSelector().isAttached()) {
				createMemberPanel.add(courseList.get(i).getCourseSelector());
				break;
			}
		}
	}

}
