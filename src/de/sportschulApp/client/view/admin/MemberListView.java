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

import de.sportschulApp.client.presenter.admin.MemberListPresenter;
import de.sportschulApp.shared.Member;

@SuppressWarnings("unchecked")
public class MemberListView extends Composite implements MemberListPresenter.Display {
	

	private ArrayList<Member> memberList = new ArrayList<Member>();
	private CellTable<Member> cellTable = new CellTable<Member>();
	private ListDataProvider<Member> lva;
	private SelectionModel selectionModel;
	private VerticalPanel wrapper = new VerticalPanel();
	private TextBox searchInputField;
	private Label searchButton;
	private Label showAllButton;
	
	public MemberListView() {
		wrapper.addStyleName("memberListWrapper");
		initWidget(wrapper);
	
		wrapper.add(createSearchPanel());
	}
	
	public HorizontalPanel createSearchPanel() {
		HorizontalPanel searchPanel = new HorizontalPanel();
		searchPanel.setStyleName("searchContainer");
		
		showAllButton = new Label("Alle anzeigen");
		showAllButton.addStyleName("searchLabel");
		
		Label searchPanelLabel = new Label("Suche: ");
		
		searchInputField = new TextBox();
		searchInputField.setStyleName("searchInput");
		
		searchButton = new Label("Suchen");
		searchButton.addStyleName("searchLabel");
		
		searchPanel.add(showAllButton);
		searchPanel.add(searchPanelLabel);
		searchPanel.add(searchInputField);
		searchPanel.add(searchButton);
		
		return searchPanel;
	}
	
	
	public VerticalPanel createMemberListTable() {
		cellTable = new CellTable<Member>();

		lva = null;
		lva = new ListDataProvider<Member>();
		
		VerticalPanel tableWrapper = new VerticalPanel();
        lva.setList(memberList);
        lva.refresh();

        cellTable.setPageSize(10);
        cellTable.setSize("700px", "500px");
        cellTable.setSelectionModel(selectionModel);
		
        lva.addDataDisplay(cellTable);
                
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
		this.selectionModel = selectionModel;
	}
	
	public HasClickHandlers getSearchButton() {
		return this.searchButton;
	}
	
	public HasClickHandlers getShowAllButton() {
		return this.showAllButton;
	}
	
	public HasKeyUpHandlers getSearchInput() {
		return this.searchInputField;
	}
	
	public HasValue<String> getSearchQuery() {
		return this.searchInputField;
	}

	public Widget asWidget() {
		return this;
	}
	
	

}
