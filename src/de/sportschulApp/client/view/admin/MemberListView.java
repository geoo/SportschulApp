package de.sportschulApp.client.view.admin;

import java.util.ArrayList;

import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListViewAdapter;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionModel.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel.SelectionChangeHandler;

import de.sportschulApp.client.event.ShowMemberEvent;
import de.sportschulApp.client.presenter.admin.MemberListPresenter;
import de.sportschulApp.shared.Member;

public class MemberListView extends Composite implements MemberListPresenter.Display {

	ArrayList<Member> memberList = new ArrayList<Member>();
    CellTable<Member> cellTable = new CellTable<Member>();
    ListViewAdapter<Member> lva = new ListViewAdapter<Member>();
    VerticalPanel wrapper = new VerticalPanel();
    HandlerManager eventBus;
	
	public MemberListView(HandlerManager eventBus) {
		this.eventBus = eventBus;
		wrapper.addStyleName("memberListWrapper");
		initWidget(wrapper);
		HorizontalPanel searchPanel = new HorizontalPanel();
		searchPanel.addStyleName("memberSearchPanel");
		searchPanel.setSize("700px", "50px");
		Label searchPanelLabel = new Label("SearchPanel");
		searchPanel.add(searchPanelLabel);
		wrapper.add(searchPanel);
	}
	
	
	public VerticalPanel createMemberListTable() {
		VerticalPanel tableWrapper = new VerticalPanel();
		
        lva.setList(memberList);

        setSelectionModel(cellTable);
        cellTable.setPageSize(20);
        cellTable.setSize("700px", "500px");
        
        lva.addView(cellTable);

        
        cellTable.addColumn(new TextColumn<Member>() {
            public String getValue(Member member) {
                return member.getForename();
            }
        }, "Vorname");

        cellTable.addColumn(new TextColumn<Member>() {
            public String getValue(Member member) {
                return member.getSurname();
            }
        }, "Nachname");
    
        cellTable.addColumn(new TextColumn<Member>() {
            public String getValue(Member member) {
                return member.getCity();
            }
        }, "Stadt");

        SimplePager<Member> pager = new SimplePager<Member>(cellTable, SimplePager.TextLocation.CENTER);
        pager.setRangeLimited(true);
		
        tableWrapper.add(pager);
        tableWrapper.add(cellTable);
        
        return tableWrapper;
	}
	
	private void setSelectionModel(CellTable<Member> cellTable) {
		final SingleSelectionModel<Member> selectionModel = new SingleSelectionModel<Member>();
		SelectionChangeHandler selectionHandler = new SelectionChangeHandler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Member member = selectionModel.getSelectedObject();
				eventBus.fireEvent(new ShowMemberEvent(member.getBarcodeID()));
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);
		cellTable.setSelectionEnabled(true);
		cellTable.setSelectionModel(selectionModel);
	}

	public void setMemberList(ArrayList<Member> memberList) {
		this.memberList = memberList;
		cellTable.removeFromParent();
		wrapper.add(createMemberListTable()); 
	}

	public Widget asWidget() {
		return this;
	}
	
//	public HasSelectionHandlers<Member> getMemberListSelection() {
//		return 
//	}

}
