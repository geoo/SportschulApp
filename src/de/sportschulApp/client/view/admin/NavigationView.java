package de.sportschulApp.client.view.admin;


import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;

import de.sportschulApp.client.CookieManager;
import de.sportschulApp.client.presenter.admin.NavigationPresenter;

public class NavigationView extends Composite implements NavigationPresenter.Display {
	
	private Label logOutLabel;
	private Label menuHomeShowSummary;
	private Label menuHomeSettings;
	private Label menuMembersShowMembers;
	private Label menuMembersCreateMember;
	private Label menuEventsShowEvents;
	private Label menuEventsCreateEvent;
	

	public NavigationView(int tabIndex) {
		VerticalPanel adminHeadPanel = new VerticalPanel();
		adminHeadPanel.setWidth("100%");
		initWidget(adminHeadPanel);
		
		VerticalPanel metaHeadPanel = new VerticalPanel();
		metaHeadPanel.setStyleName("metaHeadPanel");
		metaHeadPanel.setSize("100%", "28px");
		HorizontalPanel metaHeadLoginDetailsPanel = new HorizontalPanel();
		metaHeadLoginDetailsPanel.setStyleName("metaHeadLoginDetailsPanel");
		
		Label loggedAs = new Label("Angemeldet als: ");
		Label loginName = new Label(CookieManager.getUsername());
		logOutLabel = new Label("Abmelden");
		logOutLabel.addStyleName("logOutLabel");
		
		metaHeadLoginDetailsPanel.add(loggedAs);
		metaHeadLoginDetailsPanel.add(loginName);
		metaHeadLoginDetailsPanel.add(logOutLabel);
		metaHeadPanel.add(metaHeadLoginDetailsPanel);
		
		Image naviLogo = new Image("imgs/mm-logo-navi.png");
		naviLogo.addStyleName("naviLogo");
		
		TabLayoutPanel navigationPanel = new TabLayoutPanel(1.5, Unit.EM);
		navigationPanel.addStyleName("navigationPanel");
		navigationPanel.add(getSubNavigationElements(0), "Home");
		navigationPanel.add(getSubNavigationElements(1), "Mitgliedsverwaltung");
		navigationPanel.add(getSubNavigationElements(2), "Eventverwaltung");
		navigationPanel.add(getSubNavigationElements(3), "Kursverwaltung");
		navigationPanel.add(getSubNavigationElements(4), "Guerteleditor");
		navigationPanel.add(getSubNavigationElements(5), "Statistiken");
		navigationPanel.add(getSubNavigationElements(6), "Urkunden");
		navigationPanel.selectTab(tabIndex);
		
		HorizontalPanel mainHeadPanel = new HorizontalPanel();
		mainHeadPanel.setStyleName("mainHeadPanel");
		mainHeadPanel.setHeight("54px");
		mainHeadPanel.setWidth("100%");
		HorizontalPanel spacer = new HorizontalPanel();
		
		mainHeadPanel.add(naviLogo);
		mainHeadPanel.add(navigationPanel);


		adminHeadPanel.add(metaHeadPanel);
		adminHeadPanel.add(mainHeadPanel);
		
	}
	
	public HorizontalPanel getSubNavigationElements(int navigationID) {
		HorizontalPanel subNavPanel = new HorizontalPanel();
		subNavPanel.addStyleName("subNavigationPanel");
		
		if (navigationID == 0){
			menuHomeShowSummary = new Label("Uebersicht");
			menuHomeSettings = new Label("Systemverwaltung");
			subNavPanel.add(menuHomeShowSummary);
			subNavPanel.add(menuHomeSettings);
		} 
		
		if (navigationID == 1){
			menuMembersShowMembers = new Label("Mitglieder anzeigen");
			menuMembersCreateMember = new Label("Mitglied aufnehmen");
			subNavPanel.add(menuMembersShowMembers);
			subNavPanel.add(menuMembersCreateMember);
		} 
		
		if (navigationID == 2){
			menuEventsShowEvents = new Label("Events anzeigen");
			menuEventsCreateEvent = new Label("Event erstellen");
			subNavPanel.add(menuEventsShowEvents);
			subNavPanel.add(menuEventsCreateEvent);
		} 
		
		if (navigationID == 3){
		} 
		
		if (navigationID == 4){
		} 
		
		if (navigationID == 5){
		} 
		
		return subNavPanel;
	}
	
	public HasClickHandlers getMenuHomeShowSummary() {
		return menuHomeShowSummary;
	}
	
	public HasClickHandlers getMenuHomeSettings() {
		return menuHomeSettings;
	}
	
	public HasClickHandlers getMenuMembersShowMembers() {
		return menuMembersShowMembers;
	}
	
	public HasClickHandlers getMenuMembersCreateMember() {
		return menuMembersCreateMember;
	}

	public HasClickHandlers getMenuEventsShowEvents() {
		return menuEventsShowEvents;
	}
	
	public HasClickHandlers getMenuEventsCreateEvent() {
		return menuEventsCreateEvent;
	}
	
	public HasClickHandlers getLogoutButton() {
		return logOutLabel;
	}
	
	public Widget asWidget() {
		return this;
	}
}
