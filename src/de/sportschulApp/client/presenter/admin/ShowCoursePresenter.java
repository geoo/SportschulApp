package de.sportschulApp.client.presenter.admin;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.admin.ShowCourseView;
import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.Member;

public class ShowCoursePresenter implements Presenter{
	public interface Display{
		void setCourseData(Course course);
		HasClickHandlers getEditCourseLabel();
		String getCourseID();
		Widget asWidget();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	private String courseData = new String();
	
	public ShowCoursePresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display, int courseID) {
	    this.display = display;
	    this.rpcService = rpcService;
	    bind();
	    fetchCourseData(courseID);
	}

	public void fetchCourseData(int courseID) {
		rpcService.getCourseByID(courseID, new AsyncCallback<Course>() {
			public void onSuccess(Course result) {
				display.setCourseData(result);
			}

			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim laden der Kursdaten");
			}
		});
	}
	

	private void bind() {
		this.display.getEditCourseLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminCourseEditCourse:" + display.getCourseID());
			}
		});
	}

	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}

}
