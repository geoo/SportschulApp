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

	private Label deleteLabel;
	private Label editLabel;
	private Label closeLabel;
	private Label userForenameLabel = new Label();
	private Label userNameLabel = new Label();
	private Label userPermissionLabel = new Label();
	private Label userSurnameLabel = new Label();

	public ShowUserView(LocalizationConstants constants) {
		VerticalPanel detailsPanel = new VerticalPanel();
		detailsPanel.setWidth("350px");
		initWidget(detailsPanel);

		HorizontalPanel metaPanel = new HorizontalPanel();
		metaPanel.addStyleName("metaPanel");

		editLabel = new Label("Benutzer bearbeiten");
		deleteLabel = new Label("Benutzer löschen");
		closeLabel = new Label("Schließen");

		metaPanel.add(editLabel);
		metaPanel.add(deleteLabel);
		metaPanel.add(closeLabel);

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

	@Override
	public Widget asWidget() {
		return this;
	}

	public HasClickHandlers getDeleteLabel() {
		return deleteLabel;
	}

	public HasClickHandlers getEditLabel() {
		return editLabel;
	}
	
	public HasClickHandlers getCloseLabel() {
		return closeLabel;
	}

	public void setData(User user) {
		userNameLabel.setText(user.getUsername());
		userPermissionLabel.setText(user.getPermission());
		userForenameLabel.setText(user.getForename());
		userSurnameLabel.setText(user.getSurname());
	}
}
