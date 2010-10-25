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

import de.sportschulApp.client.event.ShowEventEvent;
import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.shared.Event;

@SuppressWarnings("unchecked")
public class ListEventPresenter implements Presenter{
	public interface Display{
		void setListData(ArrayList<Event> listData);
		void setSelectionModel(SingleSelectionModel selectionModel);
		Widget asWidget();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	private final HandlerManager eventBus;
	
	public ListEventPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display) {
	    this.display = display;
	    this.rpcService = rpcService;
	    this.eventBus = eventBus;
	    bind();
	    fetchListData();
	  }

	private void bind() {
		setSelectionModel();
	}

	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}

	
	public void fetchListData() {
		rpcService.getEventList(new AsyncCallback<ArrayList<Event>>() {
			public void onSuccess(ArrayList<Event> result) {
				display.setListData(result);
			}
			public void onFailure(Throwable caught) {
				Window.alert("Abrufen der Benutzerdaten fehlgeschlagen.");
			}
		});
		
	}
	
	public void setSelectionModel() {
		final SingleSelectionModel<Event> selectionModel = new SingleSelectionModel<Event>();
		Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Event selection = selectionModel.getSelectedObject();
				eventBus.fireEvent(new ShowEventEvent(selection.getEventID()));
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
