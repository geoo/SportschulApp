package de.sportschulApp.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.AdminPanelPresenter.Display;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.services.TrainerService;
import de.sportschulApp.client.services.TrainerServiceAsync;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class TrainerPanelPresenter implements Presenter {
	public interface Display {
		HasWidgets getNavigationContainer();

		HasWidgets getContentContainer();

		Widget asWidget();
	}

	private final Display display;
	private final HandlerManager eventBus;
	private final AdminServiceAsync rpcService;
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
		// TODO Auto-generated method stub

	}
}
