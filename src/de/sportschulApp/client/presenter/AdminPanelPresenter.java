package de.sportschulApp.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.event.ShowCourseEvent;
import de.sportschulApp.client.event.ShowCourseEventHandler;
import de.sportschulApp.client.event.ShowEventEvent;
import de.sportschulApp.client.event.ShowEventEventHandler;
import de.sportschulApp.client.event.ShowMemberEvent;
import de.sportschulApp.client.event.ShowMemberEventHandler;
import de.sportschulApp.client.event.ShowUserEvent;
import de.sportschulApp.client.event.ShowUserEventHandler;
import de.sportschulApp.client.presenter.admin.CreateCoursePresenter;
import de.sportschulApp.client.presenter.admin.CreateEventPresenter;
import de.sportschulApp.client.presenter.admin.CreateMemberPresenter;
import de.sportschulApp.client.presenter.admin.CreateUserPresenter;
import de.sportschulApp.client.presenter.admin.ListEventPresenter;
import de.sportschulApp.client.presenter.admin.ListCoursePresenter;
import de.sportschulApp.client.presenter.admin.ListMemberPresenter;
import de.sportschulApp.client.presenter.admin.NavigationPresenter;
import de.sportschulApp.client.presenter.admin.ShowMemberPresenter;
import de.sportschulApp.client.presenter.admin.ShowUserPresenter;
import de.sportschulApp.client.presenter.admin.ShowCoursePresenter;
import de.sportschulApp.client.presenter.admin.ShowEventPresenter;
import de.sportschulApp.client.presenter.admin.ListUserPresenter;
import de.sportschulApp.client.services.AdminService;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.admin.ListCourseView;
import de.sportschulApp.client.view.admin.CreateCourseView;
import de.sportschulApp.client.view.admin.CreateEventView;
import de.sportschulApp.client.view.admin.CreateMemberView;
import de.sportschulApp.client.view.admin.CreateUserView;
import de.sportschulApp.client.view.admin.ListEventView;
import de.sportschulApp.client.view.admin.ListMemberView;
import de.sportschulApp.client.view.admin.NavigationView;
import de.sportschulApp.client.view.admin.ShowMemberView;
import de.sportschulApp.client.view.admin.ShowUserView;
import de.sportschulApp.client.view.admin.ShowEventView;
import de.sportschulApp.client.view.admin.ListUserView;
import de.sportschulApp.client.view.admin.ShowCourseView;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class AdminPanelPresenter implements Presenter {
	public interface Display{
		HasWidgets getNavigationContainer();
		HasWidgets getContentContainer();
		Widget asWidget();
	}
	
	private final Display display;
	private final HandlerManager eventBus;
	private final AdminServiceAsync rpcService;
	private LocalizationConstants constants;
	
	public AdminPanelPresenter( HandlerManager eventBus, Display display, 
			LocalizationConstants constants, String token) {
		this.eventBus = eventBus;
		this.display = display;
		this.rpcService = GWT.create(AdminService.class);
		this.constants = constants;
		bind();
		buildAdminPanel(token);
	}
	
	private void bind() {
		if (!eventBus.isEventHandled(ShowMemberEvent.TYPE)) 
		{
			eventBus.addHandler(ShowMemberEvent.TYPE, new ShowMemberEventHandler() {
				public void onShowMember(ShowMemberEvent event) {
					doShowMember(event.getBarcode());	
				}
			});	
		}
		
		if (!eventBus.isEventHandled(ShowCourseEvent.TYPE)) 
		{
			eventBus.addHandler(ShowCourseEvent.TYPE, new ShowCourseEventHandler() {
				public void onShowCourse(ShowCourseEvent event) {
					doShowCourse(event.getCourseID());	
				}
			});	
		}
		
		if (!eventBus.isEventHandled(ShowUserEvent.TYPE)) 
		{
			eventBus.addHandler(ShowUserEvent.TYPE, new ShowUserEventHandler() {
				public void onShowUser(ShowUserEvent event) {
					doShowUser(event.getUserID());	
				}
			});	
		}
		
		if (!eventBus.isEventHandled(ShowEventEvent.TYPE)) 
		{
			eventBus.addHandler(ShowEventEvent.TYPE, new ShowEventEventHandler() {
				public void onShowEvent(ShowEventEvent event) {
					doShowEvent(event.getEventID());	
				}
			});	
		}
	}
	
	public void doShowMember(int barcodeID) {
		DialogBox memberPopup = new DialogBox(true);
		memberPopup.setAnimationEnabled(true);
		memberPopup.setText("Detailansicht");
		memberPopup.setGlassEnabled(true);
		memberPopup.center();
		memberPopup.setPopupPosition(memberPopup.getAbsoluteLeft() - 200, memberPopup.getAbsoluteTop() - 150);
		memberPopup.setWidth("auto");
		Presenter showMemberPresenter = null;
		showMemberPresenter =  new ShowMemberPresenter(rpcService, eventBus, new ShowMemberView(constants), barcodeID);
		showMemberPresenter.go(memberPopup);
		memberPopup.show();
	}
	
	public void doShowCourse(int courseID) {
		DialogBox coursePopup = new DialogBox(true);
		coursePopup.setAnimationEnabled(true);
		coursePopup.setText("Detailansicht");
		coursePopup.setGlassEnabled(true);
		coursePopup.center();
		coursePopup.setPopupPosition(coursePopup.getAbsoluteLeft() - 200, coursePopup.getAbsoluteTop() - 150);
		coursePopup.setWidth("auto");
		Presenter showCoursePresenter = null;
		showCoursePresenter =  new ShowCoursePresenter(rpcService, eventBus, new ShowCourseView(constants), courseID);
		showCoursePresenter.go(coursePopup);
		coursePopup.show();
	}
	
	public void doShowUser(int userID) {
		DialogBox userPopup = new DialogBox(true);
		userPopup.setAnimationEnabled(true);
		userPopup.setText("Detailansicht");
		userPopup.setGlassEnabled(true);
		userPopup.center();
		userPopup.setPopupPosition(userPopup.getAbsoluteLeft() - 200, userPopup.getAbsoluteTop() - 150);
		userPopup.setWidth("auto");
		Presenter showUserPresenter = null;
		showUserPresenter =  new ShowUserPresenter(rpcService, eventBus, new ShowUserView(constants), userID);
		showUserPresenter.go(userPopup);
		userPopup.show();
	}
	
	public void doShowEvent(int eventID) {
		DialogBox eventPopup = new DialogBox(true);
		eventPopup.setAnimationEnabled(true);
		eventPopup.setText("Detailansicht");
		eventPopup.setGlassEnabled(true);
		eventPopup.center();
		eventPopup.setPopupPosition(eventPopup.getAbsoluteLeft() - 200, eventPopup.getAbsoluteTop() - 150);
		eventPopup.setWidth("auto");
		Presenter showEventPresenter = null;
		showEventPresenter =  new ShowEventPresenter(rpcService, eventBus, new ShowEventView(constants), eventID);
		showEventPresenter.go(eventPopup);
		eventPopup.show();
	}
	
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}
	
	private void buildAdminPanel(String token) {
		Presenter navigationPresenter = null;
		Presenter contentPresenter = null;
		
		if (token.equals("adminMembersShowMembers")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(0, constants));
			contentPresenter =  new ListMemberPresenter(rpcService, eventBus, new ListMemberView());
		} else if (token.equals("adminMembersCreateMember")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(0, constants));
			contentPresenter =  new CreateMemberPresenter(rpcService, eventBus, new CreateMemberView(constants));
		} else if ((token.length() >= "adminMembersEditMember".length()) && (token.subSequence(0, 22).equals("adminMembersEditMember"))) {
			String barcodeID = token.substring(23);
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(0, constants));
			contentPresenter =  new CreateMemberPresenter(rpcService, eventBus, new CreateMemberView(constants), barcodeID);	
		} else if (token.equals("adminEventsShowEvents")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(1, constants));
			contentPresenter =  new ListEventPresenter(rpcService, eventBus, new ListEventView());
		} else if (token.equals("adminEventsCreateEvent")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(1, constants));
			contentPresenter =  new CreateEventPresenter(rpcService, eventBus, new CreateEventView(constants));
		} else if (token.equals("adminCourseCreateCourse")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(2, constants));
			contentPresenter =  new CreateCoursePresenter(rpcService, eventBus, new CreateCourseView(constants, true));
		} else if (token.equals("adminCourseShowCourses")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(2, constants));
			contentPresenter = new ListCoursePresenter(rpcService, eventBus, new ListCourseView()); 
		} else if ((token.length() >= "adminCourseEditCourse".length()) && (token.subSequence(0, 21).equals("adminCourseEditCourse"))) {
			String courseID = token.substring(22);
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(2, constants));
			contentPresenter =  new CreateCoursePresenter(rpcService, eventBus, new CreateCourseView(constants, false), courseID);	
		} else if (token.equals("adminSystemCreateUser")){
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(3, constants));
			contentPresenter = new CreateUserPresenter(rpcService, eventBus, new CreateUserView(constants));
		} else if (token.equals("adminSystemShowUsers")){
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(3, constants));
			//hier wird die exception erzeugt - vielleicht ein import fehler oder fehler bei der einbindung...
			contentPresenter = new ListUserPresenter(rpcService, eventBus, new ListUserView());
		} else if ((token.length() >= "adminSystemEditUser".length()) && (token.subSequence(0, 19).equals("adminSystemEditUser"))) {
			String courseID = token.substring(20);
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(3, constants));
			contentPresenter =  new CreateUserPresenter(rpcService, eventBus, new CreateUserView(constants), courseID);	
		}
		
		else {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(0, constants));
			contentPresenter =  new ListMemberPresenter(rpcService, eventBus, new ListMemberView());
			History.newItem("adminMembersShowMembers");
		}
		 
		navigationPresenter.go(display.getNavigationContainer());
		contentPresenter.go(display.getContentContainer());
	}
}