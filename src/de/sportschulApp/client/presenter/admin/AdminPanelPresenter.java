package de.sportschulApp.client.presenter.admin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminService;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.admin.CreateEventView;
import de.sportschulApp.client.view.admin.CreateMemberView;
import de.sportschulApp.client.view.admin.EventListView;
import de.sportschulApp.client.view.admin.MemberListView;
import de.sportschulApp.client.view.admin.NavigationView;
import de.sportschulApp.client.view.admin.SettingsView;
import de.sportschulApp.client.view.admin.SummaryView;

public class AdminPanelPresenter implements Presenter {
	public interface Display{
		HasWidgets getNavigationContainer();
		HasWidgets getContentContainer();
		Widget asWidget();
	}
	
	private final Display display;
	private final HandlerManager eventBus;
	private final AdminServiceAsync rpcService;
	
	
	public AdminPanelPresenter( HandlerManager eventBus, Display display, String token) {
		this.eventBus = eventBus;
		this.display = display;
		this.rpcService = GWT.create(AdminService.class);
		bind();
		buildAdminPanel(token);
	}
	

	private void bind() {
		
	}
	
	
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}
	
	private void buildAdminPanel(String token) {
		Presenter navigationPresenter = null;
		Presenter contentPresenter = null;
		
		if (token.equals("adminHomeShowSummary")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(0));
			contentPresenter =  new SummaryPresenter(rpcService, eventBus, new SummaryView());
		} else if (token.equals("adminHomeSettings")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(0));
			contentPresenter =  new SettingsPresenter(rpcService, eventBus, new SettingsView());
		} else if (token.equals("adminMembersShowMembers")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(1));
			contentPresenter =  new MemberListPresenter(rpcService, eventBus, new MemberListView());
		} else if (token.equals("adminMembersCreateMember")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(1));
			contentPresenter =  new CreateMemberPresenter(rpcService, eventBus, new CreateMemberView());
		} else if (token.equals("adminEventsShowEvents")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(2));
			contentPresenter =  new EventListPresenter(rpcService, eventBus, new EventListView());
		} else if (token.equals("adminEventsCreateEvent")) {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(2));
			contentPresenter =  new CreateEventPresenter(rpcService, eventBus, new CreateEventView());
		} else {
			navigationPresenter = new NavigationPresenter(eventBus, new NavigationView(0));
			contentPresenter =  new SummaryPresenter(rpcService, eventBus, new SummaryView());
		}
		
		navigationPresenter.go(display.getNavigationContainer());
		contentPresenter.go(display.getContentContainer());
	}


}