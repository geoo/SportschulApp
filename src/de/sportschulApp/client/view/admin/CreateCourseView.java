package de.sportschulApp.client.view.admin;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.CreateCoursePresenter;

public class CreateCourseView extends Composite implements CreateCoursePresenter.Display {

	public CreateCourseView() {
		VerticalPanel dummyPanel = new VerticalPanel();
		initWidget(dummyPanel);
		
		Label dummyLabel = new Label("Kurs erstellen");
		
		dummyPanel.add(dummyLabel);
		
	}
	
	public Widget asWidget() {
		return this;
	}

}
