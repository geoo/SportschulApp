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
		Widget asWidget();
		void setListData(ArrayList<Event> listData);
		void setSelectionModel(SingleSelectionModel selectionModel);
	}

	private final Display display;
	private final HandlerManager eventBus;
	private final AdminServiceAsync rpcService;

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

	public void fetchListData() {
		rpcService.getEventList(new AsyncCallback<ArrayList<Event>>() {
			public void onFailure(Throwable caught) {
				Window.alert("Abrufen der Benutzerdaten fehlgeschlagen.");
			}
			public void onSuccess(ArrayList<Event> result) {
				display.setListData(result);
			}
		});

	}


	public Display getDisplay(){
		return display;
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
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
		display.setSelectionModel(selectionModel);
	}

}
