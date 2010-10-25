package de.sportschulApp.client.presenter.trainer;

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

public class TrainerNavigationPresenter implements Presenter {
	public interface Display {
		HasChangeHandlers getLanguagePickerOnChange();

		String getLanguagePickerValue();

		HasClickHandlers getMenuTrainingNewTraining();

		HasClickHandlers getLogoutButton();

		Widget asWidget();

	}

	private final Display display;
	private final HandlerManager eventBus;

	public TrainerNavigationPresenter(HandlerManager eventBus,
			Display trainerNavigationView) {
		this.eventBus = eventBus;
		this.display = trainerNavigationView;
		bind();
	}

	private void bind() {
		this.display.getLogoutButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				eventBus.fireEvent(new LogoutEvent());
			}
		});

		this.display.getLanguagePickerOnChange().addChangeHandler(
				new ChangeHandler() {
					public void onChange(ChangeEvent event) {
						changeLanguage();
					}
				});

		this.display.getMenuTrainingNewTraining().addClickHandler(
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						History.newItem("trainerNewTraining");
					}
				});
		
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	public void changeLanguage() {
		eventBus.fireEvent(new LanguageChangeEvent(this.display
				.getLanguagePickerValue()));
	}
}