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
import de.sportschulApp.shared.Event;

public class ShowEventPresenter implements Presenter{
	public interface Display{
		void setData(Event event);
		HasClickHandlers getEditLabel();
		HasClickHandlers getDeleteLabel();
		Widget asWidget();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	private Event event = new Event();
	
	public ShowEventPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display, int courseID) {
	    this.display = display;
	    this.rpcService = rpcService;
	    bind();
	    
//	    Time time = new Time(10, 10, 10);
//	    Date date = new Date(0);
//	    date.getTime();
//	    
//	    ArrayList<String> exam = new ArrayList<String>();
//	    exam.add("test");
//	    exam.add("test22");
//	    
//	    Event event2 = new Event(1,2,"das","12",date,time,"eh", exam);
//	    System.out.println("dasdoif");
//	    rpcService.createEvent(event2, new AsyncCallback<Void>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void onSuccess(Void result) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	    fetchData(courseID);
	}

	public void fetchData(int eventID) {
		rpcService.getEventByEventID(eventID, new AsyncCallback<Event>() {
			public void onSuccess(Event result) {
				event = result;
				display.setData(result);
			}

			public void onFailure(Throwable caught) {
				Window.alert("Fehler beim laden der Eventdaten");
			}
		});
	}
	

	private void bind() {
		this.display.getEditLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				History.newItem("adminEventsEditEvent:" + event.getEventID());
			}
		});
		
		this.display.getDeleteLabel().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent clickEvent) {
				if(Window.confirm("Bestätigen sie mit OK wenn das Event wirklich gelöscht werden soll.")) {
					rpcService.deleteEventByEventID(event.getEventID(), new AsyncCallback<Void>() {
						public void onSuccess(Void result) {
							History.fireCurrentHistoryState();
						}
						public void onFailure(Throwable caught) {
							Window.alert("Das löschen des Events ist Fehlgeschlagen");	
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
