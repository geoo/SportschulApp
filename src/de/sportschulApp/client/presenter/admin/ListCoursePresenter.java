package de.sportschulApp.client.presenter.admin;

import java.util.ArrayList;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

import de.sportschulApp.client.event.ShowCourseEvent;
import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.shared.Course;

@SuppressWarnings("unchecked")
public class ListCoursePresenter implements Presenter{
	public interface Display{
		void setCourseList(ArrayList<Course> memberList);
		void setSelectionModel(SingleSelectionModel selectionModel);
		Widget asWidget();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	private final HandlerManager eventBus;
	
	public ListCoursePresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display) {
	    this.display = display;
	    this.rpcService = rpcService;
	    this.eventBus = eventBus;
	    bind();
	    fetchCourseList();
	  }

	private void bind() {
		setSelectionModel();
	}

	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}

	
	public void fetchCourseList() {
		rpcService.getCompleteCourseList(new AsyncCallback<ArrayList<Course>>() {
			public void onSuccess(ArrayList<Course> result) {
				display.setCourseList(result);
			}
			public void onFailure(Throwable caught) {
				Window.alert("Abrufen der Kursdaten fehlgeschlagen.");
			}
		});
		
	}
	
	public void setSelectionModel() {
		final SingleSelectionModel<Course> selectionModel = new SingleSelectionModel<Course>();
		Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Course selection = selectionModel.getSelectedObject();
				eventBus.fireEvent(new ShowCourseEvent(selection.getCourseID()));
				selectionModel.setSelected(selection, false);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);
		this.display.setSelectionModel(selectionModel);
	}
	
	public Display getDisplay(){
		return display;
	}
	
}
