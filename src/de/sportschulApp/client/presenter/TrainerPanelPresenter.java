package de.sportschulApp.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.AdminPanelPresenter.Display;
import de.sportschulApp.client.presenter.admin.CreateCoursePresenter;
import de.sportschulApp.client.presenter.admin.CreateEventPresenter;
import de.sportschulApp.client.presenter.admin.CreateMemberPresenter;
import de.sportschulApp.client.presenter.admin.ListEventPresenter;
import de.sportschulApp.client.presenter.admin.ListMemberPresenter;
import de.sportschulApp.client.presenter.admin.NavigationPresenter;
import de.sportschulApp.client.presenter.trainer.TrainerNavigationPresenter;
import de.sportschulApp.client.presenter.trainer.NewTrainingPresenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.services.TrainerService;
import de.sportschulApp.client.services.TrainerServiceAsync;
import de.sportschulApp.client.view.admin.CreateCourseView;
import de.sportschulApp.client.view.admin.CreateEventView;
import de.sportschulApp.client.view.admin.CreateMemberView;
import de.sportschulApp.client.view.admin.ListEventView;
import de.sportschulApp.client.view.admin.ListMemberView;
import de.sportschulApp.client.view.admin.NavigationView;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.client.view.trainer.TrainerNavigationView;
import de.sportschulApp.client.view.trainer.NewTrainingView;

public class TrainerPanelPresenter implements Presenter {
	public interface Display {
		HasWidgets getNavigationContainer();

		HasWidgets getContentContainer();

		Widget asWidget();
	}

	private final Display display;
	private final HandlerManager eventBus;
	private final TrainerServiceAsync rpcService;
	private LocalizationConstants constants;

	public TrainerPanelPresenter(HandlerManager eventBus, Display display,
			LocalizationConstants constants, String token) {
		this.display = display;
		this.eventBus = eventBus;
		this.rpcService = GWT.create(TrainerService.class);
		this.constants = constants;
		bind();
		buildTrainerPanel(token);

	}

	private void bind() {
		// TODO Auto-generated method stub

	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	private void buildTrainerPanel(String token) {
		Presenter navigationPresenter = null;
		Presenter contentPresenter = null;		
		if (token.equals("trainerPanel")) {
			navigationPresenter = new TrainerNavigationPresenter(eventBus, new TrainerNavigationView(0, constants));
			contentPresenter =  new NewTrainingPresenter(rpcService, eventBus, new NewTrainingView(constants));
		}else {
			navigationPresenter = new TrainerNavigationPresenter(eventBus, new TrainerNavigationView(0, constants));
			contentPresenter =  new NewTrainingPresenter(rpcService, eventBus, new NewTrainingView(constants));
			History.newItem("trainerPanel");
		}
		 
		navigationPresenter.go(display.getNavigationContainer());
		contentPresenter.go(display.getContentContainer());
	}
}
