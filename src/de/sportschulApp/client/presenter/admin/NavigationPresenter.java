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
		HasChangeHandlers getLanguagePickerOnChange();
		String getLanguagePickerValue();
		HasClickHandlers getMenuMembersShowMembers();
		HasClickHandlers getMenuMembersCreateMember();
		HasClickHandlers getMenuEventsShowEvents();
		HasClickHandlers getMenuEventsCreateEvent();
		HasClickHandlers getMenuCourseCreateCourse();
		HasClickHandlers getMenuCourseBeltEditor();
		HasClickHandlers getMenuCourseShowCourses();
		HasClickHandlers getMenuSystemCreateUser();
		HasClickHandlers getMenuSystemShowUsers();
		HasClickHandlers getLogoutButton();
		Widget asWidget();
	}
	
	private final Display display;
	private final HandlerManager eventBus;
	
	public NavigationPresenter(HandlerManager eventBus, Display display) {
		this.eventBus = eventBus;
		this.display = display;
		bind();
	}
	
	private void bind() {
		this.display.getLogoutButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new LogoutEvent());
			}
		});
		
		this.display.getLanguagePickerOnChange().addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				changeLanguage();
			}
		});
		
		this.display.getMenuMembersShowMembers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminMembersShowMembers");
			}
		});
		
		this.display.getMenuMembersCreateMember().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminMembersCreateMember");
			}
		});
		
		this.display.getMenuEventsShowEvents().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminEventsShowEvents");
			}
		});
		
		this.display.getMenuEventsCreateEvent().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminEventsCreateEvent");
			}
		});
		
		this.display.getMenuCourseCreateCourse().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminCourseCreateCourse");
			}
		});
		
		this.display.getMenuCourseShowCourses().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminCourseShowCourses");	
			}
		});
		
		this.display.getMenuCourseBeltEditor().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminCourseBeltEditor");	
			}
		});
		
		this.display.getMenuSystemCreateUser().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminSystemCreateUser");
			}
		});
		
		this.display.getMenuSystemShowUsers().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				History.newItem("adminSystemShowUsers");
			}
		});
		
	}
	
	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}
	
	public void changeLanguage(){
		eventBus.fireEvent(new LanguageChangeEvent(this.display.getLanguagePickerValue()));
	}
}