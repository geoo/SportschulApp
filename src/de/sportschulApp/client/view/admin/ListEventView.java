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

import de.sportschulApp.client.presenter.admin.ListEventPresenter;
import de.sportschulApp.shared.Event;

@SuppressWarnings("unchecked")
public class ListEventView extends Composite implements ListEventPresenter.Display {


	private CellTable<Event> cellTable = new CellTable<Event>();
	private ListDataProvider<Event> ldp;
	private ArrayList<Event> listData = new ArrayList<Event>();
	private SelectionModel selectionModel;
	private VerticalPanel wrapper = new VerticalPanel();

	public ListEventView() {
		wrapper.addStyleName("listWrapper");
		initWidget(wrapper);
		
		wrapper.add(createHeadPanel());
	}
	
	public VerticalPanel createHeadPanel() {
		VerticalPanel listEventHeader = new VerticalPanel();
		listEventHeader.addStyleName("formWrapper");
		listEventHeader.setWidth("100%");

		HorizontalPanel formHeader = new HorizontalPanel();
		formHeader.addStyleName("formHeader");
		formHeader.add(new Label("Events anzeigen"));

		listEventHeader.add(formHeader);

		return listEventHeader;
	}


	@Override
	public Widget asWidget() {
		return this;
	}


	public VerticalPanel createMemberListTable() {
		cellTable = new CellTable<Event>();

		ldp = new ListDataProvider<Event>();

		VerticalPanel tableWrapper = new VerticalPanel();
		ldp.setList(listData);
		ldp.refresh();

		cellTable.setPageSize(1000);
		cellTable.setWidth("100%");
		cellTable.setSelectionModel(selectionModel);

		ldp.addDataDisplay(cellTable);

		cellTable.addColumn(new TextColumn<Event>() {
			@Override
			public String getValue(Event event) {
				return event.getName();
			}
		}, "Name");
		
		cellTable.addColumn(new TextColumn<Event>() {
			public String getValue(Event event) {
				return event.getType();
			}
		}, "Typ");

		cellTable.addColumn(new TextColumn<Event>() {
			@Override
			public String getValue(Event event) {
				return event.getCosts();
			}
		}, "Kosten");

		cellTable.addColumn(new TextColumn<Event>() {
			@Override
			public String getValue(Event event) {
				return event.getDate().toString();
			}
		}, "Datum");

		cellTable.addColumn(new TextColumn<Event>() {
			@Override
			public String getValue(Event event) {
				return event.getTime().toString() + " Uhr";
			}
		}, "Uhrzeit");

		cellTable.addColumn(new TextColumn<Event>() {
			@Override
			public String getValue(Event event) {
				return event.getLocation();
			}
		}, "Ort");

		tableWrapper.add(cellTable);

		return tableWrapper;
	}

	public void setListData(ArrayList<Event> listData) {
		this.listData = listData;
		cellTable.removeFromParent();
		wrapper.add(createMemberListTable());
	}

	public void setSelectionModel(SingleSelectionModel selectionModel) {
		this.selectionModel = selectionModel;
	}
}
