package de.sportschulApp.client.view.admin;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.MemberListPresenter;

public class MemberListView extends Composite implements MemberListPresenter.Display {

	public MemberListView() {
		VerticalPanel dummyPanel = new VerticalPanel();
		initWidget(dummyPanel);
		
		Label dummyLabel = new Label("Mitglieder anzeigen");
		
		dummyPanel.add(dummyLabel);
		
	}
	
	public Widget asWidget() {
		return this;
	}

}
