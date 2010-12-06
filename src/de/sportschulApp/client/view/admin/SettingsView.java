package de.sportschulApp.client.view.admin;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.SettingsPresenter;

public class SettingsView extends Composite implements SettingsPresenter.Display {

	public SettingsView() {
		VerticalPanel dummyPanel = new VerticalPanel();
		initWidget(dummyPanel);

		Label dummyLabel = new Label("Systemverwaltung");

		dummyPanel.add(dummyLabel);

	}

	@Override
	public Widget asWidget() {
		return this;
	}

}
