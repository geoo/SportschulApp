package de.sportschulApp.client.view.admin;

import java.util.ArrayList;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.sportschulApp.client.presenter.admin.ListCoursePresenter;
import de.sportschulApp.shared.Course;

@SuppressWarnings("unchecked")
public class ListCourseView extends Composite implements ListCoursePresenter.Display {


	private CellTable<Course> cellTable = new CellTable<Course>();
	private ListDataProvider<Course> ldp;
	private ArrayList<Course> listData = new ArrayList<Course>();
	private SelectionModel selectionModel;
	private VerticalPanel wrapper = new VerticalPanel();

	public ListCourseView() {
		wrapper.addStyleName("listWrapper");
		initWidget(wrapper);

		VerticalPanel courseMenuWrapper = new VerticalPanel();
		courseMenuWrapper.addStyleName("formWrapper");
		courseMenuWrapper.setWidth("700px");

		HorizontalPanel formHeader = new HorizontalPanel();
		formHeader.addStyleName("formHeader");
		formHeader.add(new Label("Kurse anzeigen"));

		courseMenuWrapper.add(formHeader);

		wrapper.add(courseMenuWrapper);
	}


	@Override
	public Widget asWidget() {
		return this;
	}

	public VerticalPanel createMemberListTable() {
		cellTable = new CellTable<Course>();

		ldp = new ListDataProvider<Course>();

		VerticalPanel tableWrapper = new VerticalPanel();
		ldp.setList(listData);
		ldp.refresh();

		cellTable.setPageSize(1000);
		cellTable.setWidth("700px");
		cellTable.setSelectionModel(selectionModel);

		ldp.addDataDisplay(cellTable);

		cellTable.addColumn(new TextColumn<Course>() {
			@Override
			public String getValue(Course course) {
				return course.getName();
			}
		}, "Kursname");

		cellTable.addColumn(new TextColumn<Course>() {
			@Override
			public String getValue(Course course) {
				String dates = new String();
				for (int i = 0; i < course.getCourseDates().size(); i++) {
					dates = dates + course.getCourseDates().get(i).getWeekDay() + " " + course.getCourseDates().get(i).getTime() + ", ";
				}
				return dates.substring(0, dates.length() - 2);
			}
		}, "Termine");


		cellTable.addColumn(new TextColumn<Course>() {
			@Override
			public String getValue(Course course) {
				return course.getInstructor();
			}
		}, "Trainer");

		cellTable.addColumn(new TextColumn<Course>() {
			@Override
			public String getValue(Course course) {
				return course.getLocation();
			}
		}, "Ort");

		tableWrapper.add(cellTable);

		return tableWrapper;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		listData = courseList;
		cellTable.removeFromParent();
		wrapper.add(createMemberListTable());
	}

	public void setSelectionModel(SingleSelectionModel selectionModel) {
		this.selectionModel = selectionModel;
	}



}
