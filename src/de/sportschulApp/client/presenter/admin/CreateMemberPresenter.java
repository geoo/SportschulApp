package de.sportschulApp.client.presenter.admin;

import gwtupload.client.IUploader;
import gwtupload.client.MultiUploader;
import gwtupload.client.PreloadedImage;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader.Utils;
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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;

public class CreateMemberPresenter implements Presenter {
	public interface Display {
		void setCourseList(ArrayList<String> courseList);

		void setBeltList(int index, ArrayList<String> beltList);

		void addNewCourseSelector();
		
		void setImage(PreloadedImage image);

		HasClickHandlers getSendButton();

		HasChangeHandlers getCourseHandler(int index);

		HasChangeHandlers getGradeHandler(int index);

		String getSelectedCourseName(int index);

		MultiUploader getUploadHandler();

		Widget asWidget();

	}

	private final Display display;
	private final AdminServiceAsync rpcService;

	public CreateMemberPresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		bind();
		getCourseList();
	}

	private void bind() {
		this.display.getSendButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.alert("lala");
				// TODO
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
				System.out.println("Image URL: " + uploader.getFileName());

				// The server can send information to the client.
				// You can parse this information using XML or JSON libraries
				/*
				 * Document doc = XMLParser.parse(uploader.getServerResponse());
				 * String size = Utils.getXmlNodeValue(doc, "file-1-size");
				 * String type = Utils.getXmlNodeValue(doc, "file-1-type");
				 * System.out .println(
				 * "Client-Side: The server sent information about the uploaded file -> size: "
				 * + size + " Bytes, type: " + type);
				 */}
		}
	};

	// Attach an image to the pictures viewer
	private OnLoadPreloadedImageHandler showImage = new OnLoadPreloadedImageHandler() {
		public void onLoad(PreloadedImage image) {
			image.setWidth("100px");
			System.out.println("image2222URL: "+image.getUrl());
			display.setImage(image);
			// panelImages.add(image);
		}
	};

}
