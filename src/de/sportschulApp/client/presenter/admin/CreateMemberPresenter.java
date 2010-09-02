package de.sportschulApp.client.presenter.admin;

import gwtupload.client.IUploader;
import gwtupload.client.MultiUploader;
import gwtupload.client.PreloadedImage;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.PreloadedImage.OnLoadPreloadedImageHandler;

import java.util.ArrayList;


import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.admin.CreateMemberView.CourseSelectorWidget;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Member;
import eu.maydu.gwt.validation.client.ValidationProcessor;
import eu.maydu.gwt.validation.client.actions.StyleAction;
import eu.maydu.gwt.validation.client.description.PopupDescription;
import eu.maydu.gwt.validation.client.i18n.ValidationMessages;
import eu.maydu.gwt.validation.client.validators.numeric.IntegerValidator;
import eu.maydu.gwt.validation.client.validators.standard.NotEmptyValidator;
import eu.maydu.gwt.validation.client.validators.strings.StringLengthValidator;

public class CreateMemberPresenter implements Presenter {
	public interface Display {
		void setCourseList(ArrayList<String> courseList);

		void setBeltList(int index, ArrayList<String> beltList);

		void addNewCourseSelector();

		void setImage(PreloadedImage image, String imageUrl);

		ValidationProcessor getValidator();

		HasClickHandlers getSendButton();

		HasChangeHandlers getCourseHandler(int index);

		HasChangeHandlers getGradeHandler(int index);

		String getSelectedCourseName(int index);

		int getSelectedBeltNr(int index);

		String getPictureUrl();

		MultiUploader getUploadHandler();

		TextBox getForenameTextBox();

		TextBox getSurnameTextBox();

		TextBox getBarcodeTextBox();

		TextBox getStreetTextBox();

		TextBox getZipcodeTextBox();

		TextBox getCityTextBox();

		TextBox getPhoneTextBox();

		TextBox getmobilephoneTextBox();

		TextBox getEmailTextBox();

		TextBox getFaxTextBox();

		TextBox getHomepageTextBox();

		TextBox getBirthTextBox();

		TextArea getDiseasesTextBox();

		TextBox getBeltsizeTextBox();

		TextArea getNoteTextBox();

		TextBox getTrainingunitsTextBox();

		ListBox getCourseListBox();

		ListBox getGradeListBox();

		Widget asWidget();

		ArrayList<CourseSelectorWidget> getCourseList();

		String getListBoxString();
		
		LocalizationConstants getConstants();

	}

	private String imageUrl;
	private final Display display;
	private final AdminServiceAsync rpcService;
	private ValidationProcessor validator;
	private ArrayList<Integer> courses;
	private ArrayList<Integer> grades;
	private ArrayList<Integer> courseNumbers;

	public CreateMemberPresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		bind();
		getCourseList();
		setupValidation();
	}

	private void bind() {
		this.display.getSendButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boolean success = display.getValidator().validate();
				if (success) {
					System.out.println("validation succes");
					fillForm();
				} else {
					System.out.println("validation error");

					// One (or more) validations failed. The actions will have
					// been
					// already invoked by the validator.validate() call.
				}
			}
		});

		this.display.getUploadHandler().addOnFinishUploadHandler(
				onFinishUploaderHandler);
		for (int i = 0; i < 10; i++) {
			final int test = i;
			this.display.getCourseHandler(i).addChangeHandler(
					new ChangeHandler() {

						public void onChange(ChangeEvent event) {
							getBeltList(test);
						}
					});
			this.display.getGradeHandler(i).addChangeHandler(
					new ChangeHandler() {

						public void onChange(ChangeEvent event) {

							display.addNewCourseSelector();
						}
					});
		}
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	public void getCourseList() {
		rpcService.getCourseList(new AsyncCallback<ArrayList<String>>() {
			public void onSuccess(ArrayList<String> result) {
				display.setCourseList(result);
			}

			public void onFailure(Throwable caught) {

			}
		});

	}

	public void getBeltList(int index) {
		final int test = index;
		rpcService.getBeltList(display.getSelectedCourseName(index),
				new AsyncCallback<ArrayList<String>>() {
					public void onSuccess(ArrayList<String> result) {
						display.setBeltList(test, result);
					}

					public void onFailure(Throwable caught) {

					}
				});

	}

	public void fillForm() {
		courses = new ArrayList<Integer>();
		grades = new ArrayList<Integer>();

		ArrayList<CourseSelectorWidget> courseListWidget = display
				.getCourseList();

		ArrayList<String> courseNames = new ArrayList<String>();

		for (int index = 0; index < 10; index++) {
			if (!courseListWidget.get(index).getSelectedCourseName()
					.equals(display.getListBoxString())) {
				if (!courseListWidget.get(index).getSelectedBeltName()
						.equals(display.getListBoxString())) {
					courseNames.add(courseListWidget.get(index)
							.getSelectedCourseName());
					grades.add(display.getSelectedBeltNr(index));
				} else {
					Window.alert("Sie haben einen Kurs ohne Gürtelfarbe angegeben");
				}
			}
		}

		System.out.println("courseNames: " + courseNames);

		rpcService.getCourseIDs(courseNames,
				new AsyncCallback<ArrayList<Integer>>() {

					public void onSuccess(ArrayList<Integer> result) {
						// courseNumbers = result;
						// System.out.println("NUMBERS:" + result);
						courses = result;

					}

					public void onFailure(Throwable caught) {
						System.out.println("rpc errror");
					}
				});
		Timer timer = new Timer() {
			public void run() {

				// System.out.println("courses: " + courses);
				// System.out.println("grades: " + grades);

				Member member = new Member(
						0,
						new Integer(display.getBarcodeTextBox().getText()),
						display.getForenameTextBox().getText(),
						display.getSurnameTextBox().getText(),
						new Integer(display.getZipcodeTextBox().getText()),
						display.getCityTextBox().getText(),
						display.getStreetTextBox().getText(),
						display.getPhoneTextBox().getText(),
						display.getmobilephoneTextBox().getText(),
						display.getFaxTextBox().getText(),
						display.getEmailTextBox().getText(),
						display.getHomepageTextBox().getText(),
						display.getBirthTextBox().getText(),
						display.getPictureUrl(),
						display.getDiseasesTextBox().getText(),
						display.getBeltsizeTextBox().getText(),
						display.getNoteTextBox().getText(),
						new Integer(display.getTrainingunitsTextBox().getText()),
						courses, grades);

				rpcService.saveMember(member, new AsyncCallback<String>() {

					public void onSuccess(String result) {
						System.out.println("result: " + result);
						if (result.equals("barcode_id already used")) {
							Window.alert(display.getConstants().barcodeUsed());
						}
					}

					public void onFailure(Throwable caught) {
						System.out.println("rpc errror");
					}
				});
			}
		};
		timer.schedule(2000);

	}

	// Load the image in the document and in the case of success attach it to
	// the viewer
	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {

		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {

				new PreloadedImage(uploader.fileUrl(), showImage);
				imageUrl = uploader.getFileName();
			}
		}
	};

	// Attach an image to the pictures viewer
	private OnLoadPreloadedImageHandler showImage = new OnLoadPreloadedImageHandler() {
		public void onLoad(PreloadedImage image) {
			image.setWidth("100px");
			display.setImage(image, "uploads/" + imageUrl);
		}
	};
	private PopupDescription popupDesc;

	private void setupValidation() {
		this.validator = display.getValidator();
		ValidationMessages messages = new ValidationMessages();
		popupDesc = new PopupDescription(messages);

		validator.addValidators("forename",
				new StringLengthValidator(display.getForenameTextBox(), 3, 30)
						.addActionForFailure(new StyleAction(
								"validationFailedBorder"))
		// .addActionForFailure(new LabelTextAction(forenameErrorLabel))
				);

		validator.addValidators("surname",
				new StringLengthValidator(display.getSurnameTextBox(), 3, 30)
						.addActionForFailure(new StyleAction(
								"validationFailedBorder"))
		// .addActionForFailure(new LabelTextAction(forenameErrorLabel))
				);

		validator.addValidators("barcode",
				new IntegerValidator(display.getBarcodeTextBox(), 0,
						Integer.MAX_VALUE).addActionForFailure(new StyleAction(
						"validationFailedBorder"))
		// .addActionForFailure(new LabelTextAction(forenameErrorLabel))
				);

		validator.addValidators("street",
				new StringLengthValidator(display.getStreetTextBox(), 3, 30)
						.addActionForFailure(new StyleAction(
								"validationFailedBorder"))
		// .addActionForFailure(new LabelTextAction(forenameErrorLabel))
				);

		validator.addValidators("zipcodeInt",
				new IntegerValidator(display.getZipcodeTextBox(), 0,
						Integer.MAX_VALUE).addActionForFailure(new StyleAction(
						"validationFailedBorder"))
		// .addActionForFailure(new LabelTextAction(forenameErrorLabel))
				);

		validator.addValidators("zipcodeLength", new StringLengthValidator(
				display.getZipcodeTextBox(), 5, 5)
				.addActionForFailure(new StyleAction("validationFailedBorder"))
		// .addActionForFailure(new LabelTextAction(forenameErrorLabel))
				);

		validator.addValidators("city",
				new StringLengthValidator(display.getCityTextBox(), 2, 30)
						.addActionForFailure(new StyleAction(
								"validationFailedBorder"))
		// .addActionForFailure(new LabelTextAction(forenameErrorLabel))
				);

		validator.addValidators("phone",
				new StringLengthValidator(display.getPhoneTextBox(), 1, 30)
						.addActionForFailure(new StyleAction(
								"validationFailedBorder"))
		// .addActionForFailure(new LabelTextAction(forenameErrorLabel))
				);

		validator.addValidators("birth",
				new StringLengthValidator(display.getBirthTextBox(), 1, 30)
						.addActionForFailure(new StyleAction(
								"validationFailedBorder"))
		// .addActionForFailure(new LabelTextAction(forenameErrorLabel))
				);

		validator.addValidators("beltsize",
				new NotEmptyValidator(display.getBeltsizeTextBox())
						.addActionForFailure(new StyleAction(
								"validationFailedBorder"))
		// .addActionForFailure(new LabelTextAction(forenameErrorLabel))
				);

		validator.addValidators("trainingunits",
				new IntegerValidator(display.getTrainingunitsTextBox(), 1,
						Integer.MAX_VALUE).addActionForFailure(new StyleAction(
						"validationFailedBorder"))
		// .addActionForFailure(new LabelTextAction(forenameErrorLabel))
				);

		popupDesc.addDescription("forenameHelp", display.getForenameTextBox());
	}
}
