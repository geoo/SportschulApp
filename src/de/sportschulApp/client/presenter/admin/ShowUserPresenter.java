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

import de.sportschulApp.client.CookieManager;
import de.sportschulApp.client.event.LogoutEvent;
import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.User;

public class ShowUserPresenter implements Presenter{
	public interface Display{
		void setData(User user);
		HasClickHandlers getEditLabel();
		HasClickHandlers getDeleteLabel();
		Widget asWidget();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	private final HandlerManager eventBus;
	private User user = new User();
	
	public ShowUserPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display, int userID) {
		this.eventBus = eventBus;
	    this.display = display;
	    this.rpcService = rpcService;
	    bind();
	    fetchData(userID);
	}

	public void fetchData(int courseID) {
		rpcService.getUserByUserID(courseID, new AsyncCallback<User>() {
			public void onSuccess(User result) {
				user = result;
				display.setData(result);
			}

			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim laden der Benutzerdaten");
			}
		});
	}
	

	private void bind() {
		this.display.getEditLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminSystemEditUser:" + user.getUserID());
			}
		});
		
		this.display.getDeleteLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(!(CookieManager.getUsername().equals(user.getUsername()))) {
					if(Window.confirm("Bestätigen sie mit OK wenn der Benutzer wirklich gelöscht werden soll.")) {
						rpcService.deleteUserByUserID(user.getUserID(), new AsyncCallback<Void>() {
							public void onSuccess(Void result) {
								History.fireCurrentHistoryState();
							}
							public void onFailure(Throwable caught) {
								Window.alert("Das löschen des Benutzers ist Fehlgeschlagen");	
							}
						});
					} 
				} else if (Window.confirm("Achtung! Es handelt sich hierbei um ihren eigenen Account! Wirklich fortfahren?")) {
					rpcService.deleteUserByUserID(user.getUserID(), new AsyncCallback<Void>() {
						public void onSuccess(Void result) {
							eventBus.fireEvent(new LogoutEvent());
						}
						public void onFailure(Throwable caught) {
							Window.alert("Das löschen des Benutzers ist Fehlgeschlagen");	
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
