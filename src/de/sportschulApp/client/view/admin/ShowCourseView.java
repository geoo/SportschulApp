package de.sportschulApp.client.view.admin;

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.ShowCoursePresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.Member;

public class ShowCourseView extends Composite implements ShowCoursePresenter.Display {
	
	private Label courseIDLabel = new Label();
	private Label courseNameLabel = new Label();
	private Label courseDateLabel = new Label();
	private Label courseInsructorLabel = new Label();
	private Label courseLocationLabel = new Label();
	private Label courseBeltsLabel = new Label();
	private Label editCourseLabel;

	public ShowCourseView(LocalizationConstants constants) {
		VerticalPanel memberPanel = new VerticalPanel();
		memberPanel.setWidth("450px");
		initWidget(memberPanel);
		
		editCourseLabel = new Label("Kurs bearbeiten");
		editCourseLabel.addStyleName("editButton");
		memberPanel.add(editCourseLabel);

		FlexTable courseDetailsPanel = new FlexTable();
		courseDetailsPanel.setStyleName("courseDetailsPanel");
		courseDetailsPanel.setWidget(0, 0, new Label("ID: "));
		courseDetailsPanel.setWidget(0, 1, courseIDLabel);
		courseDetailsPanel.setWidget(1, 0, new Label(constants.courseName() + ":"));
		courseDetailsPanel.setWidget(1, 1, courseNameLabel);
		courseDetailsPanel.setWidget(2, 0, new Label(constants.time() + ":"));
		courseDetailsPanel.setWidget(2, 1, courseDateLabel);
		courseDetailsPanel.setWidget(3, 0, new Label(constants.instructor() + ":"));
		courseDetailsPanel.setWidget(3, 1, courseInsructorLabel);
		courseDetailsPanel.setWidget(4, 0, new Label(constants.location() + ":"));
		courseDetailsPanel.setWidget(4, 1, courseLocationLabel);
		courseDetailsPanel.setWidget(5, 0, new Label(constants.belts() + ":"));
		courseDetailsPanel.setWidget(5, 1, courseBeltsLabel);
		
		memberPanel.add(courseDetailsPanel);
	}
	
	public Widget asWidget() {
		return this;
	}

	public void setCourseData(Course course) {
		this.courseIDLabel.setText(course.getCourseID() + "");
		this.courseNameLabel.setText(course.getName());
		this.courseDateLabel.setText(course.getTime());
		this.courseInsructorLabel.setText(course.getInstructor());
		this.courseLocationLabel.setText(course.getLocation());
		
		String beltColors = new String();
		for (int i = 0; i < course.getBeltColours().size(); i++) {
			beltColors = beltColors + (course.getBeltColours().get(i) + "   ");
		}
		System.out.println(beltColors);
		
		this.courseBeltsLabel.setText(beltColors);
	}
	
	public HasClickHandlers getEditCourseLabel() {
		return this.editCourseLabel;
	}
	
	public String getCourseID() {
		return this.courseIDLabel.getText().trim();
	}
	
}
