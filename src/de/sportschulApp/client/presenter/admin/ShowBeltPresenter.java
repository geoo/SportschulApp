package de.sportschulApp.client.presenter.admin;

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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.shared.Belt;

public class ShowBeltPresenter implements Presenter{
	public interface Display{
		Widget asWidget();
		HasValue<String> getBeltTextBox();
		HasKeyUpHandlers getBeltTextBoxOnKeyUp();
		HasClickHandlers getDeleteLabel();
		HasClickHandlers getRenameLabel();
		HasClickHandlers getCloseLabel();
		void setData(Belt belt);
	}

	private Belt belt = new Belt();
	private final Display display;
	private final AdminServiceAsync rpcService;
	private DialogBox popup;

	public ShowBeltPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display, int beltID, DialogBox popup) {
		this.display = display;
		this.rpcService = rpcService;
		this.popup = popup;
		bind();
		fetchData(beltID);
	}

	private void bind() {
		display.getDeleteLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(Window.confirm("Bestätigen sie mit OK wenn die Gurtfarbe wirklich gelöscht werden soll.")) {
					rpcService.deleteBeltByID(belt.getBeltID(), new AsyncCallback<Void>() {
						public void onFailure(Throwable caught) {
							Window.alert("Das löschen der Gurtfarbe ist Fehlgeschlagen");
						}
						public void onSuccess(Void result) {
							History.fireCurrentHistoryState();
						}
					});
				}
			}
		});

		display.getBeltTextBoxOnKeyUp().addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == 13) {
					doRenameBelt();
				}
			}
		});

		display.getRenameLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doRenameBelt();
			}
		});
		
		display.getCloseLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				popup.hide();
			}
		});
	}


	private void doRenameBelt() {
		rpcService.renameBeltByID(new Belt(belt.getBeltID(), display.getBeltTextBox().getValue()), new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onSuccess(Void result) {
				History.fireCurrentHistoryState();

			}
		});
	}

	public void fetchData(int beltID) {
		rpcService.getBeltByID(beltID, new AsyncCallback<Belt>() {
			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim laden der Gurtfarbe");
			}
			@Override
			public void onSuccess(Belt result) {
				belt = result;
				display.setData(result);
			}
		});
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}
