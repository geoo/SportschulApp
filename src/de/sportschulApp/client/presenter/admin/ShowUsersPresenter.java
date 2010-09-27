package de.sportschulApp.client.presenter.admin;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class ShowUsersPresenter implements Presenter{
	
	public interface Display{
		Widget asWidget();
		LocalizationConstants getConstants();
	}
	
	//Datenfelder
	private final Display display;
	private final AdminServiceAsync rpcService;
	//private HandlerManager eventBus;
	
	/**
	 * Konstruktor
	 * @param rpcService
	 * @param eventBus
	 * @param display
	 */
	public ShowUsersPresenter(AdminServiceAsync rpcService, 
			HandlerManager eventBus, Display display){
		this.display = display;
		this.rpcService = rpcService;
		//this.eventBus = eventBus;
		bind();
	}

	/**
	 * versieht die relevanten view komponenten mit handlern 
	 * und gibt der view somit funktionalität
	 */
	private void bind() {
		// TODO Auto-generated method stub
	}
	
	/**
	 * bringt die View zur Anzeige
	 */ 
	public void go(HasWidgets container){
		container.clear();
		container.add(display.asWidget());
	}

}
