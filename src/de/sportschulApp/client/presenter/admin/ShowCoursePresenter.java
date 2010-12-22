package de.sportschulApp.client.presenter.admin;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.shared.Course;

public class ShowCoursePresenter implements Presenter{
	public interface Display{
		Widget asWidget();
		HasClickHandlers getDeleteLabel();
		HasClickHandlers getEditLabel();
		HasClickHandlers getCloseLabel();
		void setData(Course course);
	}

	private Course course = new Course();
	private final Display display;
	private final AdminServiceAsync rpcService;
	private DialogBox popup;

	public ShowCoursePresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display, int courseID, DialogBox popup) {
		this.display = display;
		this.rpcService = rpcService;
		this.popup = popup;
		bind();
		fetchCourseData(courseID);
	}

	private void bind() {
		display.getEditLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminCourseEditCourse:" + course.getCourseID());
			}
		});

		display.getDeleteLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(Window.confirm("Bestätigen sie mit OK wenn der Kurs wirklich gelöscht werden soll.")) {
					rpcService.deleteCourseByID(course.getCourseID(), new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							Window.alert("Das löschen des Kurses ist Fehlgeschlagen");
						}
						public void onSuccess(Void result) {
							History.fireCurrentHistoryState();
						}
					});
				}
			}
		});
		
		display.getCloseLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				popup.hide();
			}
		});
	}


	public void fetchCourseData(int courseID) {
		rpcService.getCourseByID(courseID, new AsyncCallback<Course>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim laden der Kursdaten");
			}

			public void onSuccess(Course result) {
				course = result;
				display.setData(result);
			}
		});
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}
