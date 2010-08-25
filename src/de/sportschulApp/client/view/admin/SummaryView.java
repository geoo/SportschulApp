package de.sportschulApp.client.view.admin;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.SummaryPresenter;

public class SummaryView extends Composite implements SummaryPresenter.Display {

	public SummaryView() {
		VerticalPanel dummyPanel = new VerticalPanel();
		initWidget(dummyPanel);
		
		Label dummyLabel = new Label("Uebersicht");
		
		dummyPanel.add(dummyLabel);
		
	}
	
	public Widget asWidget() {
		return this;
	}

}
