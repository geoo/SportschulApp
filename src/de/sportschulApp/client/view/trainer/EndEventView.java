package de.sportschulApp.client.view.trainer;

import java.util.ArrayList;

import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import de.sportschulApp.client.presenter.trainer.EndEventPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Event;
import de.sportschulApp.shared.EventParticipant;

public class EndEventView extends Composite implements
EndEventPresenter.Display {

	private VerticalPanel newEventWrapper;
	private ListDataProvider<EventParticipant> ldp;
	private ArrayList<EventParticipant> listData = new ArrayList<EventParticipant>();
	private CellTable<EventParticipant> cellTable = new CellTable<EventParticipant>();
	private Column<EventParticipant, Boolean> passedColumn;
	private Label allPassedLabel;
	private Label submitLabel;

	public EndEventView(LocalizationConstants constants) {

		newEventWrapper = new VerticalPanel();
		newEventWrapper.addStyleName("newEventWrapper");
		initWidget(newEventWrapper);
		
		VerticalPanel header = new VerticalPanel();
		header.addStyleName("formWrapper");
		header.setWidth("100%");

		HorizontalPanel formHeader = new HorizontalPanel();
		formHeader.addStyleName("formHeader");
		formHeader.add(new Label("Event abschließen"));
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setSpacing(10);
		
		allPassedLabel = new Label("Alle als Bestanden markieren");
		allPassedLabel.addStyleName("clickableLabel");
		submitLabel = new Label("Auswahl abschließen");
		submitLabel.addStyleName("clickableLabel");
		
		buttonPanel.add(allPassedLabel);
		buttonPanel.add(submitLabel);

		header.add(formHeader);
		header.add(buttonPanel);
		
		newEventWrapper.add(header);
		newEventWrapper.add(buildParticipantsList());
	}
	
	public VerticalPanel buildParticipantsList() {
		VerticalPanel tableWrapper = new VerticalPanel();
		

		cellTable = new CellTable<EventParticipant>();
		cellTable.setPageSize(1000);
		cellTable.setWidth("700px");		

		ldp = new ListDataProvider<EventParticipant>();
		ldp.setList(listData);
		ldp.refresh();
		ldp.addDataDisplay(cellTable);
		
		Column<EventParticipant, String> pictureColumn = new Column<EventParticipant, String>(new ImageCell()) {
			public String getValue(EventParticipant object) {
				return object.getPicUrl();
			}
		};

		Column<EventParticipant, String> barcodeColumn = new Column<EventParticipant, String>(new TextCell()) {
			public String getValue(EventParticipant object) {
				return object.getBarcodeID();
			}
		};

		Column<EventParticipant, String> forenameColumn = new Column<EventParticipant, String>(new TextCell()) {
			public String getValue(EventParticipant object) {
				return object.getForename();
			}
		};

		Column<EventParticipant, String> surnameColumn = new Column<EventParticipant, String>(new TextCell()) {
			public String getValue(EventParticipant object) {
				return object.getSurname();
			}
		};

		Column<EventParticipant, String> noteColumn = new Column<EventParticipant, String>(new EditTextCell()) {
			public String getValue(EventParticipant object) {
				return object.getNote();
			}
		};

		noteColumn.setFieldUpdater(new FieldUpdater<EventParticipant, String>() {
			public void update(int index, EventParticipant object, String value) {
				object.setNote(value);
			}
		});
		
		Column<EventParticipant, String> paidColumn = new Column<EventParticipant, String>(new TextCell()) {
			public String getValue(EventParticipant object) {
				return object.getPaid();
			}
		};
		
		passedColumn = new Column<EventParticipant, Boolean>(new CheckboxCell(true)) {
			public Boolean getValue(EventParticipant object) {
				if (object.getPassed().equals("Ja")) {
					return true;
				} else {
					object.setPassed("Nein");
					return false;
				}
			}
		};

		passedColumn.setFieldUpdater(new FieldUpdater<EventParticipant, Boolean>() {
			public void update(int index, EventParticipant object, Boolean value) {
				if (value == true) {
					object.setPassed("Ja");
				} else {
					object.setPassed("Nein");
				}
				cellTable.redraw();
			}
		});


		cellTable.addColumn(pictureColumn, "Bild");
		cellTable.addColumn(barcodeColumn, "Barcode");
		cellTable.addColumn(forenameColumn, "Vorname");
		cellTable.addColumn(surnameColumn, "Nachname");
		cellTable.addColumn(paidColumn, "Bezahlt?");
		cellTable.addColumn(noteColumn, "Notiz");
		cellTable.addColumn(passedColumn, "Bestanden?");
		
		tableWrapper.add(cellTable);
		
		return tableWrapper;
	}

	public Widget asWidget() {
		return this;
	}
		
	public void setMemberList(ArrayList<EventParticipant> memberList) {
		ldp.setList(memberList);
	}
	
	public HasClickHandlers getAllPassedLabel() {
		return allPassedLabel;
	}
	
	public HasClickHandlers getSubmitLabel() {
		return submitLabel;
	}
	
	public ListDataProvider<EventParticipant> getListProvider() {
		return ldp;
	}
	
	public CellTable<EventParticipant> getCellTable() {
		return cellTable;
	}

}
