package de.sportschulApp.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.trainer.NewEventPresenter;
import de.sportschulApp.client.presenter.trainer.NewTrainingPresenter;
import de.sportschulApp.client.presenter.trainer.TrainerNavigationPresenter;
import de.sportschulApp.client.services.TrainerService;
import de.sportschulApp.client.services.TrainerServiceAsync;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.client.view.trainer.NewEventView;
import de.sportschulApp.client.view.trainer.NewTrainingView;
import de.sportschulApp.client.view.trainer.TrainerNavigationView;

public class TrainerPanelPresenter implements Presenter {
	public interface Display {
		Widget asWidget();

		HasWidgets getContentContainer();

		HasWidgets getNavigationContainer();
	}

	private LocalizationConstants constants;
	private final Display display;
	private final HandlerManager eventBus;
	private final TrainerServiceAsync rpcService;

	public TrainerPanelPresenter(HandlerManager eventBus, Display display,
			LocalizationConstants constants, String token) {
		this.display = display;
		this.eventBus = eventBus;
		rpcService = GWT.create(TrainerService.class);
		this.constants = constants;
		bind();
		buildTrainerPanel(token);

	}

	private void bind() {
		// TODO Auto-generated method stub

	}

	private void buildTrainerPanel(String token) {
		Presenter navigationPresenter = null;
		Presenter contentPresenter = null;
		if (token.equals("trainerNewTraining")) {
			navigationPresenter = new TrainerNavigationPresenter(eventBus, new TrainerNavigationView(0, constants));
			contentPresenter =  new NewTrainingPresenter(rpcService, eventBus, new NewTrainingView(constants));
		} else if(token.equals("trainerNewEvent")) {
			navigationPresenter = new TrainerNavigationPresenter(eventBus, new TrainerNavigationView(0, constants));
			contentPresenter =  new NewEventPresenter(rpcService, eventBus, new NewEventView(constants));
		} else {
			navigationPresenter = new TrainerNavigationPresenter(eventBus, new TrainerNavigationView(0, constants));
			contentPresenter =  new NewTrainingPresenter(rpcService, eventBus, new NewTrainingView(constants));
			History.newItem("trainerPanel");
		}

		navigationPresenter.go(display.getNavigationContainer());
		contentPresenter.go(display.getContentContainer());
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}
}
