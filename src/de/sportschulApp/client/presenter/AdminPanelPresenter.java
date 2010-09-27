package de.sportschulApp.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.event.ShowMemberEvent;
import de.sportschulApp.client.event.ShowMemberEventHandler;
import de.sportschulApp.client.presenter.admin.CreateCoursePresenter;
import de.sportschulApp.client.presenter.admin.CreateEventPresenter;
import de.sportschulApp.client.presenter.admin.CreateMemberPresenter;
import de.sportschulApp.client.presenter.admin.CreateUserPresenter;
import de.sportschulApp.client.presenter.admin.EventListPresenter;
import de.sportschulApp.client.presenter.admin.MemberListPresenter;
import de.sportschulApp.client.presenter.admin.NavigationPresenter;
import de.sportschulApp.client.presenter.admin.ShowMemberPresenter;
import de.sportschulApp.client.presenter.admin.ShowUsersPresenter;
import de.sportschulApp.client.services.AdminService;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.admin.CreateCourseView;
import de.sportschulApp.client.view.admin.CreateEventView;
import de.sportschulApp.client.view.admin.CreateMemberView;
import de.sportschulApp.client.view.admin.CreateUserView;
import de.sportschulApp.client.view.admin.EventListView;
import de.sportschulApp.client.view.admin.MemberListView;
import de.sportschulApp.client.view.admin.NavigationView;
import de.sportschulApp.client.view.admin.ShowMemberView;
import de.sportschulApp.client.view.admin.ShowUsersView;
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
	}
		
	public void doShowMember(int barcodeID) {
		DialogBox memberPopup = new DialogBox(true);
		memberPopup.setAnimationEnabled(true);
		memberPopup.setText("Detailansicht");
		memberPopup.setGlassEnabled(true);
		memberPopup.center();
		memberPopup.setPopupPosition(memberPopup.getAbsoluteLeft() - 200, 100);
		memberPopup.setWidth("auto");
		Presenter showMemberPresenter = null;
		showMemberPresenter =  new ShowMemberPresenter(rpcService, eventBus, new ShowMemberView(constants), barcodeID);
		showMemberPresenter.go(memberPopup);
		memberPopup.show();
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
			contentPresenter =  new MemberListPresenter(rpcService, eventBus, new MemberListView());
		} else if (token.equals("adminMembersCreateMember")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(0, constants));
			contentPresenter =  new CreateMemberPresenter(rpcService, eventBus, new CreateMemberView(constants));
		} else if (token.equals("adminEventsShowEvents")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(1, constants));
			contentPresenter =  new EventListPresenter(rpcService, eventBus, new EventListView());
		} else if (token.equals("adminEventsCreateEvent")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(1, constants));
			contentPresenter =  new CreateEventPresenter(rpcService, eventBus, new CreateEventView(constants));
		} else if (token.equals("adminCourseCreateCourse")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(2, constants));
			contentPresenter =  new CreateCoursePresenter(rpcService, eventBus, new CreateCourseView(constants));
		}
		/*
		//muss noch implementiert werden - klasse ShowCoursePresenter noch nicht vorhanden
		else if (token.equals("adminCourseShowCourses")) {
			//navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(2, constants));
			//contentPresenter = new ShowCoursePresenter(rpcService, eventBus, new ShowCourseView(constants)); 
		}
		*/
		else if (token.equals("adminSystemCreateUser")){
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(3, constants));
			contentPresenter = new CreateUserPresenter(rpcService, eventBus, new CreateUserView(constants));
		}
		
		else if (token.equals("adminSystemShowUsers")){
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(3, constants));
			//hier wird die exception erzeugt - vielleicht ein import fehler oder fehler bei der einbindung...
			contentPresenter = new ShowUsersPresenter(rpcService, eventBus, new ShowUsersView(constants));
		}
		
		else {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(0, constants));
			contentPresenter =  new MemberListPresenter(rpcService, eventBus, new MemberListView());
			History.newItem("adminMembersShowMembers");
		}
		 
		navigationPresenter.go(display.getNavigationContainer());
		contentPresenter.go(display.getContentContainer());
	}
}