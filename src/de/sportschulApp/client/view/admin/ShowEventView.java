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
import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.Event;

public class ShowEventView extends Composite implements ShowEventPresenter.Display {
	
	private Label eventNameLabel = new Label();
	private Label eventCoursesLabel = new Label();
	private Label eventDateLabel = new Label();
	private Label eventTimeLabel = new Label();
	private Label eventCostsLabel = new Label();
	private Label eventInsructorsLabel = new Label();
	private Label eventLocationLabel = new Label();
	private Label editLabel;
	private Label deleteLabel;

	public ShowEventView(LocalizationConstants constants) {
		VerticalPanel detailsPanel = new VerticalPanel();
		detailsPanel.setWidth("450px");
		initWidget(detailsPanel);
		
		HorizontalPanel metaPanel = new HorizontalPanel();
		metaPanel.addStyleName("metaPanel");
		
		editLabel = new Label("Event bearbeiten");
		deleteLabel = new Label("Event löschen");
		
		metaPanel.add(editLabel);
		metaPanel.add(deleteLabel);
		
		detailsPanel.add(metaPanel);

		FlexTable courseDetailsPanel = new FlexTable();
		courseDetailsPanel.setStyleName("detailsDataPanel");
		courseDetailsPanel.setWidget(0, 0, new Label(constants.eventName() + ":"));
		courseDetailsPanel.setWidget(0, 1, eventNameLabel);
//		courseDetailsPanel.setWidget(1, 0, new Label(constants.course() + ":"));
//		courseDetailsPanel.setWidget(1, 1, eventCoursesLabel);
		courseDetailsPanel.setWidget(2, 0, new Label(constants.date() + ":"));
		courseDetailsPanel.setWidget(2, 1, eventDateLabel);
		courseDetailsPanel.setWidget(3, 0, new Label(constants.time() + ":"));
		courseDetailsPanel.setWidget(3, 1, eventTimeLabel);
		courseDetailsPanel.setWidget(4, 0, new Label(constants.costs() + ":"));
		courseDetailsPanel.setWidget(4, 1, eventCostsLabel);
		courseDetailsPanel.setWidget(5, 0, new Label(constants.location()+ ":"));
		courseDetailsPanel.setWidget(5, 1, eventLocationLabel);
		
		detailsPanel.add(courseDetailsPanel);
	}
	
	public void setData(Event event) {
		this.eventNameLabel.setText(event.getName());
//		this.eventCoursesLabel.setText(event.getCourseID());
//		this.eventDateLabel.setText(event.getDate().toString());
//		this.eventTimeLabel.setText(event.getTime().toString());
		this.eventCostsLabel.setText(event.getCosts());
		this.eventLocationLabel.setText(event.getLocation());
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
