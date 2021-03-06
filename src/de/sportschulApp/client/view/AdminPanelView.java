package de.sportschulApp.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.AdminPanelPresenter;

public class AdminPanelView extends Composite implements AdminPanelPresenter.Display {

	VerticalPanel adminPanelContainer;
	VerticalPanel contentContainer;
	VerticalPanel navigationContainer;

	public AdminPanelView() {

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

	@Override
	public Widget asWidget() {
		return this;
	}

	public HasWidgets getContentContainer() {
		return contentContainer;
	}

	public HasWidgets getNavigationContainer() {
		return navigationContainer;
	}
}
