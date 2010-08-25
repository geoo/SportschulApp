package de.sportschulApp.client.presenter.admin;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;

public class CreateEventPresenter implements Presenter{
	public interface Display{
		Widget asWidget();
	}
	
	private final Display display;
	private final AdminServiceAsync rpcService;
	
	public CreateEventPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display) {
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
