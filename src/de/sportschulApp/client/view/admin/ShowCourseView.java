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

	private Label courseBeltsLabel = new Label();
	private Label courseDateLabel = new Label();
	private Label courseInsructorLabel = new Label();
	private Label courseLocationLabel = new Label();
	private Label courseNameLabel = new Label();
	private Label courseTariffsLabel = new Label();
	private Label deleteLabel;
	private Label editLabel;
	private Label closeLabel;

	public ShowCourseView(LocalizationConstants constants) {
		VerticalPanel detailsPanel = new VerticalPanel();
		detailsPanel.setWidth("450px");
		initWidget(detailsPanel);

		HorizontalPanel metaPanel = new HorizontalPanel();
		metaPanel.addStyleName("metaPanel");

		editLabel = new Label("Kurs bearbeiten");
		deleteLabel = new Label("Kurs löschen");
		closeLabel = new Label("Schließen");

		metaPanel.add(editLabel);
		metaPanel.add(deleteLabel);
		metaPanel.add(closeLabel);

		detailsPanel.add(metaPanel);

		FlexTable courseDetailsPanel = new FlexTable();
		courseDetailsPanel.setStyleName("detailsDataPanel");
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
		courseDetailsPanel.setWidget(5, 0, new Label(constants.tariffs() + ":"));
		courseDetailsPanel.setWidget(5, 1, courseTariffsLabel);

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
	
	public HasClickHandlers getCloseLabel() {
		return closeLabel;
	}

	public void setData(Course course) {
		courseNameLabel.setText(course.getName());

		String dates = new String();
		for (int i = 0; i < course.getCourseDates().size(); i++) {
			dates = dates + course.getCourseDates().get(i).getWeekDay() + " " + course.getCourseDates().get(i).getTime() + ", ";
		}
		courseDateLabel.setText(dates.substring(0, dates.length() - 2));

		String tariffs = new String();
		for (int i = 0; i < course.getCourseTariffs().size(); i++) {
			tariffs = tariffs + course.getCourseTariffs().get(i).getName() + ": " + course.getCourseTariffs().get(i).getCosts() + " €, ";
		}
		courseTariffsLabel.setText(tariffs.substring(0, tariffs.length() -2));

		courseInsructorLabel.setText(course.getInstructor());
		courseLocationLabel.setText(course.getLocation());

		String beltColors = new String();
		for (int i = 0; i < course.getBeltColours().size(); i++) {
			beltColors = beltColors + (course.getBeltColours().get(i) + ", ");
		}

		courseBeltsLabel.setText(beltColors.substring(0, beltColors.length() - 2));
	}
}
