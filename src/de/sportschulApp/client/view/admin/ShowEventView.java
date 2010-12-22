package de.sportschulApp.client.view.admin;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.ShowEventPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Event;

public class ShowEventView extends Composite implements ShowEventPresenter.Display {

	private Label deleteLabel;
	private Label editLabel;
	private Label closeLabel;
	private Label showParticipantsLabel;
	private Label eventCostsLabel = new Label();
	private Label eventDateLabel = new Label();
	private Label eventExaminersLabel = new Label();
	private Label eventLocationLabel = new Label();
	private Label eventNameLabel = new Label();
	private Label eventTimeLabel = new Label();
	private Label eventTypeLabel = new Label();

	public ShowEventView(LocalizationConstants constants) {
		VerticalPanel detailsPanel = new VerticalPanel();
		detailsPanel.setWidth("450px");
		initWidget(detailsPanel);

		HorizontalPanel metaPanel = new HorizontalPanel();
		metaPanel.addStyleName("metaPanel");

		editLabel = new Label("Event bearbeiten");
		showParticipantsLabel = new Label("Teilnehmer bearbeiten");
		deleteLabel = new Label("Event löschen");
		closeLabel = new Label("Schließen");

		metaPanel.add(editLabel);
		metaPanel.add(showParticipantsLabel);
		metaPanel.add(deleteLabel);
		metaPanel.add(closeLabel);

		detailsPanel.add(metaPanel);

		FlexTable courseDetailsPanel = new FlexTable();
		courseDetailsPanel.setStyleName("detailsDataPanel");
		courseDetailsPanel.setWidget(0, 0, new Label(constants.eventName() + ":"));
		courseDetailsPanel.setWidget(0, 1, eventNameLabel);
		courseDetailsPanel.setWidget(1, 0, new Label(constants.eventType() + ":"));
		courseDetailsPanel.setWidget(1, 1, eventTypeLabel);
		courseDetailsPanel.setWidget(2, 0, new Label(constants.date() + ":"));
		courseDetailsPanel.setWidget(2, 1, eventDateLabel);
		courseDetailsPanel.setWidget(3, 0, new Label(constants.time() + ":"));
		courseDetailsPanel.setWidget(3, 1, eventTimeLabel);
		courseDetailsPanel.setWidget(4, 0, new Label(constants.costs() + ":"));
		courseDetailsPanel.setWidget(4, 1, eventCostsLabel);
		courseDetailsPanel.setWidget(5, 0, new Label(constants.location()+ ":"));
		courseDetailsPanel.setWidget(5, 1, eventLocationLabel);
		courseDetailsPanel.setWidget(6, 0, new Label(constants.examiner()+ ":"));
		courseDetailsPanel.setWidget(6, 1, eventExaminersLabel);

		detailsPanel.add(courseDetailsPanel);
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
	
	public HasClickHandlers getShowParticipantsLabel() {
		return showParticipantsLabel;
	}
	
	public HasClickHandlers getCloseLabel() {
		return closeLabel;
	}

	public void setData(Event event) {
		eventNameLabel.setText(event.getName());
		eventTypeLabel.setText(event.getType());
		eventDateLabel.setText(event.getDate());
		eventTimeLabel.setText(event.getStartTime() + " - " + event.getEndTime() + " Uhr");
		eventCostsLabel.setText(event.getCosts());
		eventLocationLabel.setText(event.getLocation());
		
		String examiners = new String();
		for (int i = 0; i < event.getExaminers().size(); i++) {
			examiners = examiners + event.getExaminers().get(i) + ", ";
		}
		eventExaminersLabel.setText(examiners.substring(0, examiners.length() -2));
	}
}
