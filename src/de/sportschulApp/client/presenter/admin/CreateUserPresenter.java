package de.sportschulApp.client.presenter.admin;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.localization.LocalizationConstants;

/**
 * Die Presenter Klasse für die zugehörige View
 * @author flo
 *
 */
public class CreateUserPresenter implements Presenter {
	
	/**
	 * Das Interface für die zugehörige View
	 * Hier müssen alle Methoden angegeben werden die die View zur Verfügung stellen muss
	 * @author flo
	 *
	 */
	public interface Display{
		//standard
		Widget asWidget();
		LocalizationConstants getConstants();
		//getter methoden für alle textfelder buttons,... der view
		Label getUserNameLabel();
		Label getUserPasswortLabel();
		HasClickHandlers getSaveButton();
	}
	
	//datenfelder - presenter
	private final Display display;
	private final AdminServiceAsync rpcService;
	private HandlerManager eventBus;
	
	//konstruktor - presenter
	public CreateUserPresenter(AdminServiceAsync rpcService, 
			HandlerManager eventBus, Display display) {
		this.display = display;
	    this.rpcService = rpcService;
	    this.eventBus = eventBus;
	    bind();
	}
	
	/**
	 * versieht die relevanten view komponenten mit handlern 
	 * und gibt der view somit funktionalität
	 */
	private void bind() {
		//save button
		display.getSaveButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				Window.alert("save button gedrückt");
			}
		});
	}
	
	/**
	 * bringt die View zur Anzeige
	 */
	public void go(HasWidgets container) {
		container.clear();
	    container.add(display.asWidget());
	}

}
