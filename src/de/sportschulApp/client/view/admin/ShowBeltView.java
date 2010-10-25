package de.sportschulApp.client.view.admin;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.ShowBeltPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Belt;
import de.sportschulApp.shared.User;

public class ShowBeltView extends Composite implements ShowBeltPresenter.Display {
	
	private TextBox beltTextBox = new TextBox();
	private Label renameLabel;
	private Label deleteLabel;

	public ShowBeltView(LocalizationConstants constants) {
		VerticalPanel detailsPanel = new VerticalPanel();
		detailsPanel.setWidth("350px");
		initWidget(detailsPanel);
		
		HorizontalPanel metaPanel = new HorizontalPanel();
		metaPanel.addStyleName("metaPanel");
		
		deleteLabel = new Label("Gurtfarbe löschen");
		renameLabel = new Label("umbenennen");
		renameLabel.setStyleName("clickableLabel");
		
		metaPanel.add(deleteLabel);
		
		detailsPanel.add(metaPanel);

		HorizontalPanel renamePanel = new HorizontalPanel();
		renamePanel.add(new Label("Gurtfarbe: "));
		beltTextBox.setStyleName("formInput");
		renamePanel.add(beltTextBox);
		renamePanel.add(renameLabel);
		
		
		detailsPanel.add(renamePanel);
	}
	
	public void setData(Belt belt) {
		this.beltTextBox.setValue(belt.getName());
	}

	public HasValue<String> getBeltTextBox() {
		return this.beltTextBox;
	}
	
	public HasClickHandlers getRenameLabel() {
		return this.renameLabel;
	}
	
	public HasClickHandlers getDeleteLabel() {
		return this.deleteLabel;
	}
	
	public HasKeyUpHandlers getBeltTextBoxOnKeyUp() {
		return this.beltTextBox;
	}
	
	public Widget asWidget() {
		return this;
	}
}
