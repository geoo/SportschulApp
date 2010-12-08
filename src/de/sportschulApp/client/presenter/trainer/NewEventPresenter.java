package de.sportschulApp.client.presenter.trainer;

import java.util.Date;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.TrainerServiceAsync;

public class NewEventPresenter implements Presenter {

	public interface Display {
		Widget asWidget();
	}

	private final Display display;
	private final TrainerServiceAsync rpcService;
	Date today = new Date();

	public NewEventPresenter(TrainerServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		bind();

	}

	private void bind() {
		
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}
