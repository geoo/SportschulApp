package de.sportschulApp.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.CreateEventPresenter;
import de.sportschulApp.client.presenter.admin.NavigationPresenter;
import de.sportschulApp.client.presenter.trainer.ContinueEventPresenter;
import de.sportschulApp.client.presenter.trainer.NewEventPresenter;
import de.sportschulApp.client.presenter.trainer.EndEventPresenter;
import de.sportschulApp.client.presenter.trainer.NewTrainingPresenter;
import de.sportschulApp.client.presenter.trainer.StartEventPresenter;
import de.sportschulApp.client.presenter.trainer.TrainerNavigationPresenter;
import de.sportschulApp.client.services.TrainerService;
import de.sportschulApp.client.services.TrainerServiceAsync;
import de.sportschulApp.client.view.admin.CreateEventView;
import de.sportschulApp.client.view.admin.NavigationView;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.client.view.trainer.ContinueEventView;
import de.sportschulApp.client.view.trainer.EndEventView;
import de.sportschulApp.client.view.trainer.NewEventView;
import de.sportschulApp.client.view.trainer.NewTrainingView;
import de.sportschulApp.client.view.trainer.StartEventView;
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
		} else if(token.equals("trainerContinueEvent")) {
			navigationPresenter = new TrainerNavigationPresenter(eventBus, new TrainerNavigationView(0, constants));
			contentPresenter =  new ContinueEventPresenter(rpcService, eventBus, new ContinueEventView(constants));
		} else if ((token.length() >= "trainerStartEvent".length()) && (token.subSequence(0, 17).equals("trainerStartEvent"))) {
			String eventID = token.substring(18);
			navigationPresenter = new TrainerNavigationPresenter(eventBus, new TrainerNavigationView(0, constants));
			contentPresenter =  new StartEventPresenter(rpcService, eventBus, new StartEventView(constants), eventID, constants);
		} else if ((token.length() >= "trainerEndEvent".length()) && (token.subSequence(0, 15).equals("trainerEndEvent"))) {
			String eventID = token.substring(16);
			navigationPresenter = new TrainerNavigationPresenter(eventBus, new TrainerNavigationView(0, constants));
			contentPresenter =  new EndEventPresenter(rpcService, eventBus, new EndEventView(constants), eventID, constants);
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
