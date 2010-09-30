package de.sportschulApp.client.view.admin;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.ShowUserPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.User;

public class ShowUserView extends Composite implements ShowUserPresenter.Display {
	
	private Label userNameLabel = new Label();
	private Label userPermissionLabel = new Label();
	private Label userForenameLabel = new Label();
	private Label userSurnameLabel = new Label();
	private Label editLabel;
	private Label deleteLabel;

	public ShowUserView(LocalizationConstants constants) {
		VerticalPanel detailsPanel = new VerticalPanel();
		detailsPanel.setWidth("350px");
		initWidget(detailsPanel);
		
		HorizontalPanel metaPanel = new HorizontalPanel();
		metaPanel.addStyleName("metaPanel");
		
		editLabel = new Label("Benutzer bearbeiten");
		deleteLabel = new Label("Benutzer löschen");
		
		metaPanel.add(editLabel);
		metaPanel.add(deleteLabel);
		
		detailsPanel.add(metaPanel);

		FlexTable detailsDataPanel = new FlexTable();
		detailsDataPanel.setStyleName("detailsDataPanel");
		
		detailsDataPanel.setWidget(0, 0, new Label(constants.username() + ":"));
		detailsDataPanel.setWidget(0, 1, userNameLabel);
		detailsDataPanel.setWidget(1, 0, new Label(constants.type() + ":"));
		detailsDataPanel.setWidget(1, 1, userPermissionLabel);
		detailsDataPanel.setWidget(2, 0, new Label(constants.forename() + ":"));
		detailsDataPanel.setWidget(2, 1, userForenameLabel);
		detailsDataPanel.setWidget(3, 0, new Label(constants.surname() + ":"));
		detailsDataPanel.setWidget(3, 1, userSurnameLabel);
		
		detailsPanel.add(detailsDataPanel);
	}
	
	public void setData(User user) {
		this.userNameLabel.setText(user.getUsername());
		this.userPermissionLabel.setText(user.getPermission());
		this.userForenameLabel.setText(user.getForename());
		this.userSurnameLabel.setText(user.getSurname());
	}
	
	public HasClickHandlers getEditLabel() {
		return this.editLabel;
	}
	
	public HasClickHandlers getDeleteLabel() {
		return this.deleteLabel;
	}
	
	public Widget asWidget() {
		return this;
	}
}
