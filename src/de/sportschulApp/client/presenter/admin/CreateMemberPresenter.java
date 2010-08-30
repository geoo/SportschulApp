package de.sportschulApp.client.presenter.admin;

import java.util.ArrayList;

import com.gargoylesoftware.htmlunit.AlertHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;

public class CreateMemberPresenter implements Presenter {
	public interface Display {
		void setCourseList(ArrayList<String> courseList);

		void setBeltList(int index, ArrayList<String> beltList);

		void addNewCourseSelector();

		HasChangeHandlers getCourseHandler(int index);

		HasChangeHandlers getGradeHandler(int index);

		String getSelectedCourseName(int index);

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

}
