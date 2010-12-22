package de.sportschulApp.client.view.admin;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.ShowBeltPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Belt;

public class ShowBeltView extends Composite implements ShowBeltPresenter.Display {

	private TextBox beltTextBox = new TextBox();
	private Label deleteLabel;
	private Label renameLabel;
	private Label closeLabel;

	public ShowBeltView(LocalizationConstants constants) {
		VerticalPanel detailsPanel = new VerticalPanel();
		detailsPanel.setWidth("350px");
		initWidget(detailsPanel);

		HorizontalPanel metaPanel = new HorizontalPanel();
		metaPanel.addStyleName("metaPanel");

		deleteLabel = new Label("Gurtfarbe löschen");
		renameLabel = new Label("umbenennen");
		renameLabel.setStyleName("clickableLabel");
		closeLabel = new Label("Schließen");

		metaPanel.add(deleteLabel);
		metaPanel.add(closeLabel);
		detailsPanel.add(metaPanel);

		HorizontalPanel renamePanel = new HorizontalPanel();
		renamePanel.add(new Label("Gurtfarbe: "));
		beltTextBox.setStyleName("formInput");
		renamePanel.add(beltTextBox);
		renamePanel.add(renameLabel);


		detailsPanel.add(renamePanel);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public HasValue<String> getBeltTextBox() {
		return beltTextBox;
	}

	public HasKeyUpHandlers getBeltTextBoxOnKeyUp() {
		return beltTextBox;
	}

	public HasClickHandlers getDeleteLabel() {
		return deleteLabel;
	}

	public HasClickHandlers getRenameLabel() {
		return renameLabel;
	}
	
	public HasClickHandlers getCloseLabel() {
		return closeLabel;
	}

	public void setData(Belt belt) {
		beltTextBox.setValue(belt.getName());
	}
}
