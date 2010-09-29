package de.sportschulApp.client.view.admin;

import java.util.ArrayList;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.sportschulApp.client.presenter.admin.CourseListPresenter;
import de.sportschulApp.shared.Course;

@SuppressWarnings("unchecked")
public class CourseListView extends Composite implements CourseListPresenter.Display {
	

	private ArrayList<Course> courseList = new ArrayList<Course>();
	private CellTable<Course> cellTable = new CellTable<Course>();
	private ListDataProvider<Course> lva;
	private SelectionModel selectionModel;
	private VerticalPanel wrapper = new VerticalPanel();
	
	public CourseListView() {
		wrapper.addStyleName("listWrapper");
		initWidget(wrapper);
	}
	
	
	public VerticalPanel createMemberListTable() {
		cellTable = new CellTable<Course>();

		lva = null;
		lva = new ListDataProvider<Course>();
		
		VerticalPanel tableWrapper = new VerticalPanel();
        lva.setList(courseList);
        lva.refresh();

        cellTable.setPageSize(10);
        cellTable.setSize("700px", "500px");
        cellTable.setSelectionModel(selectionModel);
		
        lva.addDataDisplay(cellTable);

        cellTable.addColumn(new TextColumn<Course>() {
            public String getValue(Course course) {
                return course.getName();
            }
        }, "Kursname");
    
        cellTable.addColumn(new TextColumn<Course>() {
            public String getValue(Course course) {
                return course.getTime();
            }
        }, "Zeitpunkt");
        
        cellTable.addColumn(new TextColumn<Course>() {
            public String getValue(Course course) {
                return course.getInstructor();
            }
        }, "Trainer");
    
        cellTable.addColumn(new TextColumn<Course>() {
            public String getValue(Course course) {
                return course.getLocation();
            }
        }, "Ort");

        tableWrapper.add(cellTable);
        
        return tableWrapper;
	}
	

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
		cellTable.removeFromParent();
		wrapper.add(createMemberListTable());
	}
	
	public void setSelectionModel(SingleSelectionModel selectionModel) {
		this.selectionModel = selectionModel;
	}

	public Widget asWidget() {
		return this;
	}
	
	

}
