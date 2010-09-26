package de.sportschulApp.client.view.admin;

import java.util.ArrayList;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListViewAdapter;
import com.google.gwt.view.client.SingleSelectionModel;

import de.sportschulApp.client.presenter.admin.MemberListPresenter;
import de.sportschulApp.shared.Member;

public class MemberListView extends Composite implements MemberListPresenter.Display {

	ArrayList<Member> memberList = new ArrayList<Member>();
    CellTable<Member> cellTable = new CellTable<Member>();
    ListViewAdapter<Member> lva = new ListViewAdapter<Member>();
    VerticalPanel wrapper = new VerticalPanel();
	
	public MemberListView() {
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

        cellTable.setPageSize(500);
        cellTable.setSize("700px", "500px");
		cellTable.setSelectionEnabled(true);
		
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

        tableWrapper.add(cellTable);
        
        return tableWrapper;
	}
	

	public void setMemberList(ArrayList<Member> memberList) {
		this.memberList = memberList;
		cellTable.removeFromParent();
		wrapper.add(createMemberListTable()); 
	}
	
	public void setSelectionModel(SingleSelectionModel selectionModel) {
		cellTable.setSelectionModel(selectionModel);
	}

	public Widget asWidget() {
		return this;
	}
	
	

}
