package de.sportschulApp.client.view.admin;

import java.util.ArrayList;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.PageSizePager;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListViewAdapter;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionModel.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel.SelectionChangeHandler;

import de.sportschulApp.client.presenter.admin.MemberListPresenter;
import de.sportschulApp.shared.Member;

public class MemberListView extends Composite implements MemberListPresenter.Display {

	public MemberListView() {
		HorizontalPanel wrapper = new HorizontalPanel();
		initWidget(wrapper);

		ArrayList<Member> values = new ArrayList<Member>();
		Member testmember = new Member();
		testmember.setForename("Michi");
		testmember.setSurname("Luki");
        values.add(testmember);
        values.add(testmember);
        values.add(testmember);

        ListViewAdapter<Member> lva = new ListViewAdapter<Member>();
        lva.setList(values);

        {
            // CellTable
            CellTable<Member> cellTable = new CellTable<Member>();
            setSelectionModel(cellTable);
            
            cellTable.setPageSize(5);
//            ct.setTitle("mitglieder");
            lva.addView(cellTable);

            // add a column with a simple string header
        cellTable.addColumn(new TextColumn<Member>() {

            @Override
            public String getValue(Member member) {
            	System.out.println(member.getForename());
                return member.getForename();
            }
        }, "Vorname");

        //add a column with a TextCell header
        cellTable.addColumn(new TextColumn<Member>() {

            @Override
            public String getValue(Member object) {
                return object.getSurname();
            }
        }, "Nachname");
        

//             create a pager, giving it a handle to the CellTable
            SimplePager<Member> pager = new SimplePager<Member>(cellTable, SimplePager.TextLocation.CENTER);

            wrapper.add(cellTable);
            wrapper.add(pager);
	}
        
	}
	
	private void setSelectionModel(CellTable<Member> cellTable) {
		final SingleSelectionModel<Member> selectionModel = new SingleSelectionModel<Member>();
		SelectionChangeHandler selectionHandler = new SelectionChangeHandler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Member user = selectionModel.getSelectedObject();
                Window.alert(user.getForename() + ": " + user.getSurname());
				
			}
        };
        selectionModel.addSelectionChangeHandler(selectionHandler);
        cellTable.setSelectionEnabled(true);
        cellTable.setSelectionModel(selectionModel);
		
	}

	public Widget asWidget() {
		return this;
	}

}
