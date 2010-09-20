package de.sportschulApp.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.TrainerPanelPresenter;

public class TrainerPanelView extends Composite implements TrainerPanelPresenter.Display {

	VerticalPanel adminPanelContainer;
	VerticalPanel navigationContainer;
	VerticalPanel contentContainer;
	
	public TrainerPanelView() {
		
		adminPanelContainer = new VerticalPanel();
		adminPanelContainer.setSize("100%", "100%");
		
		navigationContainer = new VerticalPanel();
		navigationContainer.setWidth("100%");
		
		contentContainer = new VerticalPanel();
		contentContainer.setSize("100%", "100%");
		
		initWidget(adminPanelContainer);
		
		adminPanelContainer.add(navigationContainer);
		adminPanelContainer.add(contentContainer);
			
	}
	
	public HasWidgets getNavigationContainer() {
		return navigationContainer;
	}
	
	public HasWidgets getContentContainer() {
		return contentContainer;
	}
	
	public Widget asWidget() {
		return this;
	}
}
