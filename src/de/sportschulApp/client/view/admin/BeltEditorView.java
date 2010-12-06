package de.sportschulApp.client.view.admin;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

import de.sportschulApp.client.presenter.admin.BeltEditorPresenter;
import de.sportschulApp.shared.Belt;

@SuppressWarnings("unchecked")
public class BeltEditorView extends Composite implements BeltEditorPresenter.Display {


	private Label addBeltLabel = new Label("hinzufügen");
	private TextBox addBeltTextBox = new TextBox();
	private CellTable<Belt> cellTable = new CellTable<Belt>();
	private ListDataProvider<Belt> ldp;
	private ArrayList<Belt> listData = new ArrayList<Belt>();
	private SelectionModel selectionModel;
	private VerticalPanel wrapper = new VerticalPanel();

	public BeltEditorView() {
		wrapper.addStyleName("listWrapper");
		initWidget(wrapper);

		VerticalPanel beltMenuWrapper = new VerticalPanel();
		beltMenuWrapper.addStyleName("formWrapper");
		beltMenuWrapper.setWidth("550px");

		HorizontalPanel formHeader = new HorizontalPanel();
		formHeader.addStyleName("formHeader");
		formHeader.add(new Label("Gürtel Editor (anlegen, bearbeiten, löschen)"));

		HorizontalPanel addBeltPanel = new HorizontalPanel();
		addBeltPanel.setStyleName("addBeltPanel");
		addBeltPanel.add(new Label("Gürtel anlegen: "));

		addBeltTextBox.setStyleName("formInput");
		addBeltTextBox.setWidth("200px");
		addBeltPanel.add(addBeltTextBox);

		addBeltLabel.setStyleName("clickableLabel");
		addBeltPanel.add(addBeltLabel);

		beltMenuWrapper.add(formHeader);
		beltMenuWrapper.add(addBeltPanel);

		wrapper.add(beltMenuWrapper);
	}


	@Override
	public Widget asWidget() {
		return this;
	}

	public VerticalPanel createMemberListTable() {
		cellTable = new CellTable<Belt>();

		ldp = new ListDataProvider<Belt>();

		VerticalPanel tableWrapper = new VerticalPanel();
		ldp.setList(listData);
		ldp.refresh();

		cellTable.setPageSize(1000);
		cellTable.setWidth("550px");
		cellTable.setSelectionModel(selectionModel);

		ldp.addDataDisplay(cellTable);


		//
		//        cellTable.addColumn(new TextColumn<Course>() {
		//            public String getValue(Course course) {
		//            	String dates = new String();
		//            	for (int i = 0; i < course.getCourseDates().size(); i++) {
		//            		dates = dates + course.getCourseDates().get(i).getWeekDay() + " " + course.getCourseDates().get(i).getTime() + ", ";
		//            	}
		//                return dates.substring(0, dates.length() - 2);
		//            }
		//        }, "Termine");
		//
		//
		cellTable.addColumn(new TextColumn<Belt>() {
			@Override
			public String getValue(Belt belt) {
				return belt.getName();
			}
		}, "Gürtel");
		//
		//        cellTable.addColumn(new TextColumn<Course>() {
		//            public String getValue(Course course) {
		//                return course.getLocation();
		//            }
		//        }, "Ort");

		tableWrapper.add(cellTable);

		return tableWrapper;
	}

	public HasClickHandlers getAddBeltLabel() {
		return addBeltLabel;
	}

	public HasValue<String> getAddBeltTextBox() {
		return addBeltTextBox;
	}

	public HasKeyUpHandlers getBeltTextBoxOnKeyUp() {
		return addBeltTextBox;
	}

	public void setListData(ArrayList<Belt> listData) {
		this.listData = listData;
		cellTable.removeFromParent();
		wrapper.add(createMemberListTable());
	}

	public void setSelectionModel(SingleSelectionModel selectionModel) {
		this.selectionModel = selectionModel;
	}



}
