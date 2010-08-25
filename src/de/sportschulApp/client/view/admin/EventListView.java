package de.sportschulApp.client.view.admin;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.EventListPresenter;

public class EventListView extends Composite implements EventListPresenter.Display {

	public EventListView() {
		VerticalPanel dummyPanel = new VerticalPanel();
		initWidget(dummyPanel);
		
		Label dummyLabel = new Label("Events");
		
		dummyPanel.add(dummyLabel);
		
	}
	
	public Widget asWidget() {
		return this;
	}

}
