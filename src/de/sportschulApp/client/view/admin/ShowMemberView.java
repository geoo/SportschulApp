package de.sportschulApp.client.view.admin;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.ShowMemberPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Member;

public class ShowMemberView extends Composite implements ShowMemberPresenter.Display {
	
	public ShowMemberView(LocalizationConstants constants) {
		VerticalPanel memberPanel = new VerticalPanel();
		initWidget(memberPanel);
		
		
		
	}
	
	public Widget asWidget() {
		return this;
	}

	public void setMemberData(Member member) {
	}

}
