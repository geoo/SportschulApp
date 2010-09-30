package de.sportschulApp.client.view.admin;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.ShowCoursePresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Course;

public class ShowCourseView extends Composite implements ShowCoursePresenter.Display {
	
	private Label courseNameLabel = new Label();
	private Label courseDateLabel = new Label();
	private Label courseInsructorLabel = new Label();
	private Label courseLocationLabel = new Label();
	private Label courseBeltsLabel = new Label();
	private Label editLabel;
	private Label deleteLabel;

	public ShowCourseView(LocalizationConstants constants) {
		VerticalPanel detailsPanel = new VerticalPanel();
		detailsPanel.setWidth("450px");
		initWidget(detailsPanel);
		
		HorizontalPanel metaPanel = new HorizontalPanel();
		metaPanel.addStyleName("metaPanel");
		
		editLabel = new Label("Kurs bearbeiten");
		deleteLabel = new Label("Kurs löschen");
		
		metaPanel.add(editLabel);
		metaPanel.add(deleteLabel);
		
		detailsPanel.add(metaPanel);

		FlexTable courseDetailsPanel = new FlexTable();
		courseDetailsPanel.setStyleName("courseDetailsPanel");
		courseDetailsPanel.setWidget(0, 0, new Label(constants.courseName() + ":"));
		courseDetailsPanel.setWidget(0, 1, courseNameLabel);
		courseDetailsPanel.setWidget(1, 0, new Label(constants.time() + ":"));
		courseDetailsPanel.setWidget(1, 1, courseDateLabel);
		courseDetailsPanel.setWidget(2, 0, new Label(constants.instructor() + ":"));
		courseDetailsPanel.setWidget(2, 1, courseInsructorLabel);
		courseDetailsPanel.setWidget(3, 0, new Label(constants.location() + ":"));
		courseDetailsPanel.setWidget(3, 1, courseLocationLabel);
		courseDetailsPanel.setWidget(4, 0, new Label(constants.belts() + ":"));
		courseDetailsPanel.setWidget(4, 1, courseBeltsLabel);
		
		detailsPanel.add(courseDetailsPanel);
	}
	
	public void setCourseData(Course course) {
		this.courseNameLabel.setText(course.getName());
		this.courseDateLabel.setText(course.getTime());
		this.courseInsructorLabel.setText(course.getInstructor());
		this.courseLocationLabel.setText(course.getLocation());
		
		String beltColors = new String();
		for (int i = 0; i < course.getBeltColours().size(); i++) {
			beltColors = beltColors + (course.getBeltColours().get(i) + ", ");
		}
		
		this.courseBeltsLabel.setText(beltColors.substring(0, beltColors.length() - 2));
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
