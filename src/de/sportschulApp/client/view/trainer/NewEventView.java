package de.sportschulApp.client.view.trainer;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.trainer.NewEventPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class NewEventView extends Composite implements
NewEventPresenter.Display {

	private VerticalPanel newEventWrapper;
	private LocalizationConstants constants;

	public NewEventView(LocalizationConstants constants) {
		this.constants = constants;

		newEventWrapper = new VerticalPanel();
		initWidget(newEventWrapper);

		newEventWrapper.add(new Label("bla"));

	}

	@Override
	public Widget asWidget() {
		return this;
	}

}
