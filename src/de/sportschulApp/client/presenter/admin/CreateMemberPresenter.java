package de.sportschulApp.client.presenter.admin;

import gwtupload.client.IUploader;
import gwtupload.client.MultiUploader;
import gwtupload.client.PreloadedImage;
import gwtupload.client.IUploadStatus.Status;

import gwtupload.client.PreloadedImage.OnLoadPreloadedImageHandler;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
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

		ListBox getBirthTextBox1();

		ListBox getBirthTextBox2();

		ListBox getBirthTextBox3();

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

		void fillForm(Member result);

	}

	private String imageUrl;
	private final Display display;
	private final AdminServiceAsync rpcService;
	private ValidationProcessor validator;
	private ArrayList<Integer> courses;
	private ArrayList<Integer> grades;
	private LocalizationConstants constants;
	private HandlerManager eventBus;
	private boolean error = false;

	public CreateMemberPresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		this.rpcService = rpcService;
		this.constants = display.getConstants();
		bind();
		getCourseList();
		setupValidation();

	}

	/**
	 * Konstruktor für den EditView
	 * 
	 * @param rpcService
	 * @param eventBus
	 * @param display
	 * @param barcodeID
	 *            (Aus HistroyToken von ShowMember)
	 */
	public CreateMemberPresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display, String barcodeID) {
		this.eventBus = eventBus;
		this.display = display;
		this.rpcService = rpcService;
		this.constants = display.getConstants();
		getMember(barcodeID);
		bind();
		getCourseList();
		setupValidation();
		System.out.println(barcodeID);

	}

	private void getMember(String barcodeID) {
		rpcService.getMemberByBarcodeID(Integer.parseInt(barcodeID),
				new AsyncCallback<Member>() {

					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					public void onSuccess(Member result) {
						display.fillForm(result);
					}
				});
	}

	private void bind() {
		this.display.getSendButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boolean success = display.getValidator().validate();
				if (display.getBirthTextBox1().getSelectedIndex() == 0) {
					display.getBirthTextBox1().setStyleName(
							"validationFailedBorder");
					success = false;
				} else {
					display.getBirthTextBox1().removeStyleName(
							"validationFailedBorder");
				}
				if (display.getBirthTextBox2().getSelectedIndex() == 0) {
					display.getBirthTextBox2().setStyleName(
							"validationFailedBorder");
					success = false;
				} else {
					display.getBirthTextBox2().removeStyleName(
							"validationFailedBorder");
				}
				if (display.getBirthTextBox3().getSelectedIndex() == 0) {
					display.getBirthTextBox3().setStyleName(
							"validationFailedBorder");
					success = false;
				} else {
					display.getBirthTextBox3().removeStyleName(
							"validationFailedBorder");
				}

				if (success) {
					System.out.println("validation success");
					fillForm();

				} else {
					System.out.println("validation error");
					Window.alert("Bitte überprüfen Sie ihre Eingaben");

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
		error = false;
		// courses = new ArrayList<Integer>();
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
					error = true;
					Window.alert("Sie haben einen Kurs ohne Gürtelfarbe angegeben");
				}
			}
		}
		if (error == false) {

			System.out.println("courseNames: " + courseNames);

			rpcService.getCourseIDs(courseNames,
					new AsyncCallback<ArrayList<Integer>>() {

						public void onSuccess(ArrayList<Integer> result) {

							// courses = result;
							Integer selected = display.getBirthTextBox1()
									.getSelectedIndex();
							String birthDay = selected.toString();

							selected = display.getBirthTextBox2()
									.getSelectedIndex();
							String birthMonth = selected.toString();

							selected = display.getBirthTextBox3()
									.getSelectedIndex();
							String birthYear = display.getBirthTextBox3()
									.getItemText(selected);
							Member member = new Member(0, new Integer(display
									.getBarcodeTextBox().getText()), display
									.getForenameTextBox().getText(), display
									.getSurnameTextBox().getText(),
									new Integer(display.getZipcodeTextBox()
											.getText()), display
											.getCityTextBox().getText(),
									display.getStreetTextBox().getText(),
									display.getPhoneTextBox().getText(),
									display.getmobilephoneTextBox().getText(),
									display.getFaxTextBox().getText(), display
											.getEmailTextBox().getText(),
									display.getHomepageTextBox().getText(),
									birthDay, birthMonth, birthYear, display
											.getPictureUrl(), display
											.getDiseasesTextBox().getText(),
									display.getBeltsizeTextBox().getText(),
									display.getNoteTextBox().getText(),
									new Integer(display
											.getTrainingunitsTextBox()
											.getText()), result, grades);

							rpcService.saveMember(member,
									new AsyncCallback<String>() {

										public void onSuccess(String result) {
											System.out.println("result: "
													+ result);
											if (result
													.equals("barcode_id already used")) {
												display.getBarcodeTextBox()
														.setStyleName(
																"validationFailedBorderBarcode");
												Window.alert(display
														.getConstants()
														.barcodeUsed());
											} else {
												Window.alert(constants
														.memberCreated());
												History.newItem("adminMembersShowMembers");
											}
										}

										public void onFailure(Throwable caught) {
											System.out.println("rpc errror");
										}
									});

						}

						public void onFailure(Throwable caught) {
							System.out.println("rpc errror");
						}
					});
		}
	}

	// Load the image in the document and in the case of success attach it to
	// the viewer
	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {

		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {
				imageUrl = uploader.getServerResponse();
				new PreloadedImage(imageUrl, showImage);

			}
		}
	};

	// Attach an image to the pictures viewer
	private OnLoadPreloadedImageHandler showImage = new OnLoadPreloadedImageHandler() {
		public void onLoad(PreloadedImage image) {
			image.setWidth("100px");
			display.setImage(image, imageUrl);
		}
	};
	private PopupDescription popupDesc;

	private void setupValidation() {
		class CustomValidationMessages extends ValidationMessages {

			public String getDescriptionMessage(String msgKey) {
				HashMap<String, String> msgMap = new HashMap<String, String>();
				msgMap.put("forename", constants.popupHelpForename());
				msgMap.put("surname", constants.popupHelpSurname());
				msgMap.put("barcode", constants.popupHelpBarcode());
				msgMap.put("street", constants.popupHelpStreet());
				msgMap.put("zipcode", constants.popupHelpZipcode());
				msgMap.put("city", constants.popupHelpCity());
				msgMap.put("phone", constants.popupHelpPhone());
				msgMap.put("beltsize", constants.popupHelpBeltsize());
				msgMap.put("trainingunits", constants.popupHelpTrainingunits());
				msgMap.put("mobilephone", constants.popupHelpMobilephone());
				msgMap.put("fax", constants.popupHelpFax());
				msgMap.put("email", constants.popupHelpEmail());
				msgMap.put("homepage", constants.popupHelpHomepage());
				msgMap.put("diseases", constants.popupHelpDiseases());
				msgMap.put("note", constants.popupHelpNote());

				String temp = msgMap.get(msgKey.trim());
				return temp;
			}
		}

		this.validator = display.getValidator();
		ValidationMessages messages = new CustomValidationMessages();

		popupDesc = new PopupDescription(messages);

		validator.addValidators("forename",
				new StringLengthValidator(display.getForenameTextBox(), 2, 30)
						.addActionForFailure(new StyleAction(
								"validationFailedBorder")));

		validator.addValidators("surname",
				new StringLengthValidator(display.getSurnameTextBox(), 2, 30)
						.addActionForFailure(new StyleAction(
								"validationFailedBorder")));

		validator.addValidators("barcode",
				new IntegerValidator(display.getBarcodeTextBox(), 0,
						Integer.MAX_VALUE).addActionForFailure(new StyleAction(
						"validationFailedBorder")));

		validator.addValidators("street",
				new StringLengthValidator(display.getStreetTextBox(), 2, 30)
						.addActionForFailure(new StyleAction(
								"validationFailedBorder")));

		validator.addValidators("zipcodeInt",
				new IntegerValidator(display.getZipcodeTextBox(), 0,
						Integer.MAX_VALUE).addActionForFailure(new StyleAction(
						"validationFailedBorder")));

		validator.addValidators("zipcodeLength", new StringLengthValidator(
				display.getZipcodeTextBox(), 5, 5)
				.addActionForFailure(new StyleAction("validationFailedBorder"))
		// .addActionForFailure(new LabelTextAction(forenameErrorLabel))
				);

		validator.addValidators("city",
				new StringLengthValidator(display.getCityTextBox(), 2, 30)
						.addActionForFailure(new StyleAction(
								"validationFailedBorder")));

		validator.addValidators("phone",
				new StringLengthValidator(display.getPhoneTextBox(), 1, 30)
						.addActionForFailure(new StyleAction(
								"validationFailedBorder")));

		validator.addValidators("beltsize",
				new NotEmptyValidator(display.getBeltsizeTextBox())
						.addActionForFailure(new StyleAction(
								"validationFailedBorder")));

		validator.addValidators("trainingunits",
				new IntegerValidator(display.getTrainingunitsTextBox(), 1,
						Integer.MAX_VALUE).addActionForFailure(new StyleAction(
						"validationFailedBorder")));
		popupDesc.addDescription("forename ", display.getForenameTextBox());
		popupDesc.addDescription("surname ", display.getSurnameTextBox());
		popupDesc.addDescription("barcode ", display.getBarcodeTextBox());
		popupDesc.addDescription("street ", display.getStreetTextBox());
		popupDesc.addDescription("zipcode ", display.getZipcodeTextBox());
		popupDesc.addDescription("city ", display.getCityTextBox());
		popupDesc.addDescription("phone ", display.getPhoneTextBox());
		popupDesc.addDescription("beltsize ", display.getBeltsizeTextBox());
		popupDesc.addDescription("trainingunits ",
				display.getTrainingunitsTextBox());
		popupDesc.addDescription("mobilephone ",
				display.getmobilephoneTextBox());
		popupDesc.addDescription("fax ", display.getFaxTextBox());
		popupDesc.addDescription("email ", display.getEmailTextBox());
		popupDesc.addDescription("homepage ", display.getDiseasesTextBox());
		popupDesc.addDescription("note ", display.getNoteTextBox());

	}
}
