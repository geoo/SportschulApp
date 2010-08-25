package de.sportschulApp.client.presenter.trainer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.TrainerService;
import de.sportschulApp.client.services.TrainerServiceAsync;

public class TrainerPanelPresenter implements Presenter{
	public interface Display{
		Widget asWidget();
	}
	
	private final Display display;
	private final TrainerServiceAsync rpcService;
	
	public TrainerPanelPresenter(HandlerManager eventBus, Display display) {
	    this.display = display;
	    this.rpcService = GWT.create(TrainerService.class);
	    bind();
	  }

	private void bind() {
		// TODO Auto-generated method stub
		
	}

	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}

}
