package de.sportschulApp.client.presenter.admin;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

import de.sportschulApp.client.event.ShowBeltEvent;
import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.shared.Belt;

@SuppressWarnings("unchecked")
public class BeltEditorPresenter implements Presenter{
	public interface Display{
		void setListData(ArrayList<Belt> listData);
		void setSelectionModel(SingleSelectionModel selectionModel);
		HasValue<String> getAddBeltTextBox();
		HasClickHandlers getAddBeltLabel();
		HasKeyUpHandlers getBeltTextBoxOnKeyUp();
		Widget asWidget();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	private final HandlerManager eventBus;
	
	public BeltEditorPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display) {
	    this.display = display;
	    this.rpcService = rpcService;
	    this.eventBus = eventBus;
	    bind();
	    fetchBeltList();
	  }

	private void bind() {
		this.display.getAddBeltLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doCreateBelt();
			}
		});
		
		this.display.getBeltTextBoxOnKeyUp().addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == 13) {
					doCreateBelt();
				}	
			}
		});
		
		setSelectionModel();
	}
	
	private void doCreateBelt() {
		rpcService.createBelt(display.getAddBeltTextBox().getValue(), new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim anlegen der Gurtfarbe");						
			}
			public void onSuccess(Void result) {
				History.fireCurrentHistoryState();	
			}
		});
	}

	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}

	
	public void fetchBeltList() {
		rpcService.getAvailableBelts(new AsyncCallback<ArrayList<Belt>>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim laden der Gürteldaten");
			}
			public void onSuccess(ArrayList<Belt> result) {
				display.setListData(result);
			}
		});
		
	}
	
	public void setSelectionModel() {
		final SingleSelectionModel<Belt> selectionModel = new SingleSelectionModel<Belt>();
		Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Belt selection = selectionModel.getSelectedObject();
				eventBus.fireEvent(new ShowBeltEvent(selection.getBeltID()));
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
