package de.sportschulApp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

import de.sportschulApp.client.services.LoginService;
import de.sportschulApp.client.services.LoginServiceAsync;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class SportschulApp implements EntryPoint {
	// start
	
	private LocalizationConstants constants = GWT.create(LocalizationConstants.class);
	
	public void onModuleLoad() {
		LoginServiceAsync rpcService = GWT.create(LoginService.class);
	    HandlerManager eventBus = new HandlerManager(null);
	    AppController appViewer = new AppController(rpcService, eventBus, constants);
	    appViewer.go(RootPanel.get());
	}
}
