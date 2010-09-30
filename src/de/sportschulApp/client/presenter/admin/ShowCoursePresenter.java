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
import de.sportschulApp.shared.Course;

public class ShowCoursePresenter implements Presenter{
	public interface Display{
		void setData(Course course);
		HasClickHandlers getEditLabel();
		HasClickHandlers getDeleteLabel();
		Widget asWidget();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	private Course course = new Course();
	
	public ShowCoursePresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display, int courseID) {
	    this.display = display;
	    this.rpcService = rpcService;
	    bind();
	    fetchCourseData(courseID);
	}

	public void fetchCourseData(int courseID) {
		rpcService.getCourseByID(courseID, new AsyncCallback<Course>() {
			public void onSuccess(Course result) {
				course = result;
				display.setData(result);
			}

			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim laden der Kursdaten");
			}
		});
	}
	

	private void bind() {
		this.display.getEditLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminCourseEditCourse:" + course.getCourseID());
			}
		});
		
		this.display.getDeleteLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(Window.confirm("Bestätigen sie mit OK wenn der Kurs wirklich gelöscht werden soll.")) {
					rpcService.deleteCourseByID(course.getCourseID(), new AsyncCallback<Void>() {
						public void onSuccess(Void result) {
							History.fireCurrentHistoryState();
						}
						public void onFailure(Throwable caught) {
							Window.alert("Das löschen des Kurses ist Fehlgeschlagen");	
						}
					});
				}
			}
		});
	}

	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}

}
