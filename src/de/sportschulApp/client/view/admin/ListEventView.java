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

import de.sportschulApp.client.presenter.admin.ListEventPresenter;
import de.sportschulApp.shared.Event;

@SuppressWarnings("unchecked")
public class ListEventView extends Composite implements ListEventPresenter.Display {
	

	private ArrayList<Event> listData = new ArrayList<Event>();
	private CellTable<Event> cellTable = new CellTable<Event>();
	private ListDataProvider<Event> ldp;
	private SelectionModel selectionModel;
	private VerticalPanel wrapper = new VerticalPanel();
	
	public ListEventView() {
		wrapper.addStyleName("listWrapper");
		initWidget(wrapper);
	}
	
	
	public VerticalPanel createMemberListTable() {
		cellTable = new CellTable<Event>();

		ldp = new ListDataProvider<Event>();
		
		VerticalPanel tableWrapper = new VerticalPanel();
        ldp.setList(listData);
        ldp.refresh();

        cellTable.setPageSize(1000);
        cellTable.setWidth("700px");
        cellTable.setSelectionModel(selectionModel);
		
        ldp.addDataDisplay(cellTable);

        cellTable.addColumn(new TextColumn<Event>() {
            public String getValue(Event event) {
                return event.getName();
            }
        }, "Name");
    
        cellTable.addColumn(new TextColumn<Event>() {
            public String getValue(Event event) {
                return event.getCosts();
            }
        }, "Kosten");
        
        cellTable.addColumn(new TextColumn<Event>() {
            public String getValue(Event event) {
                return event.getDate().toString();
            }
        }, "Datum");
    
        cellTable.addColumn(new TextColumn<Event>() {
            public String getValue(Event event) {
                return event.getTime().toString();
            }
        }, "Uhrzeit");
        
        cellTable.addColumn(new TextColumn<Event>() {
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

	public Widget asWidget() {
		return this;
	}
}
