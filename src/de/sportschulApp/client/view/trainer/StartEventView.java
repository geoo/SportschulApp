package de.sportschulApp.client.view.trainer;

import java.util.ArrayList;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import de.sportschulApp.client.event.ShowMemberEvent;
import de.sportschulApp.client.presenter.trainer.StartEventPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.EventParticipant;

public class StartEventView extends Composite implements
StartEventPresenter.Display {

	private VerticalPanel newEventWrapper;
	private LocalizationConstants constants;
	private ListDataProvider<EventParticipant> ldp;
	private ArrayList<EventParticipant> listData = new ArrayList<EventParticipant>();
	private CellTable<EventParticipant> cellTable = new CellTable<EventParticipant>();

	public StartEventView(LocalizationConstants constants) {
		this.constants = constants;

		newEventWrapper = new VerticalPanel();
		newEventWrapper.addStyleName("newEventWrapper");
		initWidget(newEventWrapper);
		
		VerticalPanel header = new VerticalPanel();
		header.addStyleName("formWrapper");
		header.setWidth("100%");

		HorizontalPanel formHeader = new HorizontalPanel();
		formHeader.addStyleName("formHeader");
		formHeader.add(new Label("Teilnehmer"));

		header.add(formHeader);

		newEventWrapper.add(header);

	}
	
	public VerticalPanel buildParticipantsList() {
		cellTable = new CellTable<EventParticipant>();

		ldp = new ListDataProvider<EventParticipant>();

		VerticalPanel tableWrapper = new VerticalPanel();
		ldp.setList(listData);
		ldp.refresh();

		cellTable.setPageSize(1000);
		cellTable.setWidth("700px");

		ldp.addDataDisplay(cellTable);
		
		Column<EventParticipant, Boolean> attendedColumn = new Column<EventParticipant, Boolean>(
				new CheckboxCell(true)) {
			@Override
			public Boolean getValue(EventParticipant object) {
				if (object.getAttended().equals("Ja")) {
					return true;
				} else {
					return false;
				}
			}
		};

		attendedColumn.setFieldUpdater(new FieldUpdater<EventParticipant, Boolean>() {
			public void update(int index, EventParticipant object, Boolean value) {
				if (value == true) {
					object.setAttended("Ja");
				} else {
					object.setAttended("Nein");
				}
			}
		});
		
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

		cellTable.addColumn(attendedColumn, "Anwesend");
		cellTable.addColumn(pictureColumn, "Bild");
		cellTable.addColumn(barcodeColumn, "Barcode");
		cellTable.addColumn(forenameColumn, "Vorname");
		cellTable.addColumn(surnameColumn, "Nachname");
		cellTable.addColumn(paidColumn, "Bezahlt?");
		cellTable.addColumn(noteColumn, "Notiz");
		
		tableWrapper.add(cellTable);
		
		return tableWrapper;
	}

	public Widget asWidget() {
		return this;
	}
	
	public void setMemberList(ArrayList<EventParticipant> memberList) {
		listData = memberList;
		cellTable.removeFromParent();
		newEventWrapper.add(buildParticipantsList());
	}

}
