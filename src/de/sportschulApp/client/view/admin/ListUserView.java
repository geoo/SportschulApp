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

import de.sportschulApp.client.presenter.admin.ListUserPresenter;
import de.sportschulApp.shared.User;

@SuppressWarnings("unchecked")
public class ListUserView extends Composite implements ListUserPresenter.Display {


	private CellTable<User> cellTable = new CellTable<User>();
	private ListDataProvider<User> ldp;
	private ArrayList<User> listData = new ArrayList<User>();
	private SelectionModel selectionModel;
	private VerticalPanel wrapper = new VerticalPanel();

	public ListUserView() {
		wrapper.addStyleName("listWrapper");
		initWidget(wrapper);
		
		wrapper.add(createHeadPanel());
	}
	
	public VerticalPanel createHeadPanel() {
		VerticalPanel listHeader = new VerticalPanel();
		listHeader.addStyleName("formWrapper");
		listHeader.setWidth("100%");

		HorizontalPanel formHeader = new HorizontalPanel();
		formHeader.addStyleName("formHeader");
		formHeader.add(new Label("Nutzer anzeigen"));

		listHeader.add(formHeader);

		return listHeader;
	}


	@Override
	public Widget asWidget() {
		return this;
	}


	public VerticalPanel createMemberListTable() {
		cellTable = new CellTable<User>();

		ldp = new ListDataProvider<User>();

		VerticalPanel tableWrapper = new VerticalPanel();
		ldp.setList(listData);
		ldp.refresh();

		cellTable.setPageSize(1000);
		cellTable.setWidth("700px");
		cellTable.setSelectionModel(selectionModel);

		ldp.addDataDisplay(cellTable);

		cellTable.addColumn(new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return user.getPermission();
			}
		}, "Typ");

		cellTable.addColumn(new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return user.getUsername();
			}
		}, "Benutzername");

		cellTable.addColumn(new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return user.getForename();
			}
		}, "Vorname");

		cellTable.addColumn(new TextColumn<User>() {
			@Override
			public String getValue(User user) {
				return user.getSurname();
			}
		}, "Nachname");

		tableWrapper.add(cellTable);

		return tableWrapper;
	}

	public void setListData(ArrayList<User> listData) {
		this.listData = listData;
		cellTable.removeFromParent();
		wrapper.add(createMemberListTable());
	}

	public void setSelectionModel(SingleSelectionModel selectionModel) {
		this.selectionModel = selectionModel;
	}
}
