package de.sportschulApp.client.view.admin;


import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.CookieManager;
import de.sportschulApp.client.presenter.admin.NavigationPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class NavigationView extends Composite implements NavigationPresenter.Display {

	private ListBox languagePicker = new ListBox();
	private Label logOutLabel;
	private Label menuCourseBeltEditor;
	private Label menuCourseCreateCourse;
	private Label menuCourseShowCourses;
	private Label menuEventsCreateEvent;
	private Label menuEventsShowEvents;
	private Label menuMembersCreateMember;
	private Label menuMembersShowMembers;
	private Label menuSystemCreateUser;
	private Label menuSystemShowUsers;


	public NavigationView(int tabIndex, LocalizationConstants constants) {
		VerticalPanel adminHeadPanel = new VerticalPanel();
		adminHeadPanel.setWidth("100%");
		initWidget(adminHeadPanel);

		HorizontalPanel metaHeadPanel = new HorizontalPanel();
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

		HorizontalPanel languagePanel = new HorizontalPanel();
		languagePanel.addStyleName("languagePanel");
		Label languageLabel = new Label(constants.language() + ": ");
		languagePicker.addItem("Deutsch");
		languagePicker.addItem("English");

		try {
			if (!Cookies.getCookie("SportschuleLanguage").isEmpty()) {
				if (Cookies.getCookie("SportschuleLanguage").equals("Deutsch")) {
					languagePicker.setSelectedIndex(0);
				}
				if (Cookies.getCookie("SportschuleLanguage").equals("English")) {
					languagePicker.setSelectedIndex(1);
				}
			}
		} catch (NullPointerException e) {
			languagePicker.setSelectedIndex(0);
		}
		
		languagePanel.add(languageLabel);
		languagePanel.add(languagePicker);

		metaHeadPanel.add(metaHeadLoginDetailsPanel);
//		metaHeadPanel.add(languagePanel);


		Image naviLogo = new Image("imgs/mm-logo-navi.png");
		naviLogo.addStyleName("naviLogo");

		TabLayoutPanel navigationPanel = new TabLayoutPanel(1.5, Unit.EM);
		navigationPanel.addStyleName("navigationPanel");
		navigationPanel.add(getSubNavigationElements(0), "Mitgliedsverwaltung");
		navigationPanel.add(getSubNavigationElements(1), "Eventverwaltung");
		navigationPanel.add(getSubNavigationElements(2), "Kursverwaltung");
		navigationPanel.add(getSubNavigationElements(3), "Systemverwaltung");
		navigationPanel.selectTab(tabIndex);

		HorizontalPanel mainHeadPanel = new HorizontalPanel();
		mainHeadPanel.setStyleName("mainHeadPanel");
		mainHeadPanel.setHeight("54px");
		mainHeadPanel.setWidth("100%");

		mainHeadPanel.add(naviLogo);
		mainHeadPanel.add(navigationPanel);

		adminHeadPanel.add(metaHeadPanel);
		adminHeadPanel.add(mainHeadPanel);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public HasChangeHandlers getLanguagePickerOnChange() {
		return languagePicker;
	}

	public String getLanguagePickerValue() {
		return languagePicker.getValue(languagePicker.getSelectedIndex());
	}

	public HasClickHandlers getLogoutButton() {
		return logOutLabel;
	}

	public HasClickHandlers getMenuCourseBeltEditor() {
		return menuCourseBeltEditor;
	}

	public HasClickHandlers getMenuCourseCreateCourse() {
		return menuCourseCreateCourse;
	}

	public HasClickHandlers getMenuCourseShowCourses() {
		return menuCourseShowCourses;
	}

	public HasClickHandlers getMenuEventsCreateEvent() {
		return menuEventsCreateEvent;
	}

	public HasClickHandlers getMenuEventsShowEvents() {
		return menuEventsShowEvents;
	}

	public HasClickHandlers getMenuMembersCreateMember() {
		return menuMembersCreateMember;
	}

	public HasClickHandlers getMenuMembersShowMembers() {
		return menuMembersShowMembers;
	}


	public HasClickHandlers getMenuSystemCreateUser() {
		return menuSystemCreateUser;
	}

	public HasClickHandlers getMenuSystemShowUsers() {
		return menuSystemShowUsers;
	}

	public HorizontalPanel getSubNavigationElements(int navigationID) {
		HorizontalPanel subNavPanel = new HorizontalPanel();
		subNavPanel.addStyleName("subNavigationPanel");

		if (navigationID == 0){
			menuMembersShowMembers = new Label("Mitglieder anzeigen");
			menuMembersCreateMember = new Label("Mitglied aufnehmen");
			subNavPanel.add(menuMembersShowMembers);
			subNavPanel.add(menuMembersCreateMember);
		}

		if (navigationID == 1){
			menuEventsShowEvents = new Label("Events anzeigen");
			menuEventsCreateEvent = new Label("Event erstellen");
			subNavPanel.add(menuEventsShowEvents);
			subNavPanel.add(menuEventsCreateEvent);
		}

		if (navigationID == 2){
			menuCourseShowCourses = new Label("Kurse anzeigen");
			menuCourseCreateCourse = new Label("Kurs erstellen");
			menuCourseBeltEditor = new Label("Gürtel Editor");
			subNavPanel.add(menuCourseShowCourses);
			subNavPanel.add(menuCourseCreateCourse);
			subNavPanel.add(menuCourseBeltEditor);
		}

		if (navigationID == 3){
			menuSystemShowUsers = new Label("Nutzer anzeigen");
			menuSystemCreateUser = new Label("Nutzer erstellen");
			subNavPanel.add(menuSystemShowUsers);
			subNavPanel.add(menuSystemCreateUser);
		}

		return subNavPanel;
	}
}
