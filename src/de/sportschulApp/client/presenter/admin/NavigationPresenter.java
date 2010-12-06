package de.sportschulApp.client.presenter.admin;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.event.LanguageChangeEvent;
import de.sportschulApp.client.event.LogoutEvent;
import de.sportschulApp.client.presenter.Presenter;

public class NavigationPresenter implements Presenter{
	public interface Display{
		Widget asWidget();
		HasChangeHandlers getLanguagePickerOnChange();
		String getLanguagePickerValue();
		HasClickHandlers getLogoutButton();
		HasClickHandlers getMenuCourseBeltEditor();
		HasClickHandlers getMenuCourseCreateCourse();
		HasClickHandlers getMenuCourseShowCourses();
		HasClickHandlers getMenuEventsCreateEvent();
		HasClickHandlers getMenuEventsShowEvents();
		HasClickHandlers getMenuMembersCreateMember();
		HasClickHandlers getMenuMembersShowMembers();
		HasClickHandlers getMenuSystemCreateUser();
		HasClickHandlers getMenuSystemShowUsers();
	}

	private final Display display;
	private final HandlerManager eventBus;

	public NavigationPresenter(HandlerManager eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		bind();
	}

	private void bind() {
		display.getLogoutButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new LogoutEvent());
			}
		});

		display.getLanguagePickerOnChange().addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				changeLanguage();
			}
		});

		display.getMenuMembersShowMembers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminMembersShowMembers");
			}
		});

		display.getMenuMembersCreateMember().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminMembersCreateMember");
			}
		});

		display.getMenuEventsShowEvents().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminEventsShowEvents");
			}
		});

		display.getMenuEventsCreateEvent().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminEventsCreateEvent");
			}
		});

		display.getMenuCourseCreateCourse().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminCourseCreateCourse");
			}
		});

		display.getMenuCourseShowCourses().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminCourseShowCourses");
			}
		});

		display.getMenuCourseBeltEditor().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminCourseBeltEditor");
			}
		});

		display.getMenuSystemCreateUser().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminSystemCreateUser");
			}
		});

		display.getMenuSystemShowUsers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminSystemShowUsers");
			}
		});

	}

	public void changeLanguage(){
		eventBus.fireEvent(new LanguageChangeEvent(display.getLanguagePickerValue()));
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}
}