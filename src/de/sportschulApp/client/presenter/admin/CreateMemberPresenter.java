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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import eu.maydu.gwt.validation.client.ValidationProcessor;
import eu.maydu.gwt.validation.client.actions.LabelTextAction;
import eu.maydu.gwt.validation.client.actions.StyleAction;
import eu.maydu.gwt.validation.client.description.PopupDescription;
import eu.maydu.gwt.validation.client.i18n.ValidationMessages;
import eu.maydu.gwt.validation.client.validators.numeric.IntegerValidator;
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

		MultiUploader getUploadHandler();
		
		TextBox getForenameTextBox();
		
		TextBox getSurnameTextBox();

		TextBox getBarcodeTextBox();


		Widget asWidget();

	}

	private String imageUrl;
	private final Display display;
	private final AdminServiceAsync rpcService;
	private ValidationProcessor validator;

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
					// No validation errors found. We can submit the data to the
					// server!
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
		
		validator.addValidators( "forename",
			new StringLengthValidator(display.getForenameTextBox(), 3, 20)
				.addActionForFailure(new StyleAction("validationFailedBorder"))
				//.addActionForFailure(new LabelTextAction(forenameErrorLabel))
		);
		
		validator.addValidators( "surname",
				new StringLengthValidator(display.getSurnameTextBox(), 3, 20)
					.addActionForFailure(new StyleAction("validationFailedBorder"))
					//.addActionForFailure(new LabelTextAction(forenameErrorLabel))
			);
		
		validator.addValidators( "barcode",
				new IntegerValidator(display.getBarcodeTextBox(), 2, 4)
					.addActionForFailure(new StyleAction("validationFailedBorder"))
					//.addActionForFailure(new LabelTextAction(forenameErrorLabel))
			);

		popupDesc.addDescription("forenameHelp", display.getForenameTextBox());
	}
}
