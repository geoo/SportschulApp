package de.sportschulApp.client.view.trainer;

import java.util.ArrayList;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
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
	private TextBox barcodeTextBox;
	private Button scanButton;
	private Image scanImage;
	private Column<EventParticipant, Boolean> attendedColumn;
	private Label saveLabel;
	private Label abortLabel;
	private Label endLabel;

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
		
		HorizontalPanel barcodeInputPanel = new HorizontalPanel();
		barcodeInputPanel.setStyleName("participantsBarcodePanel");
		Label barcodeLabel = new Label("Teilnehmer scannen: ");
		barcodeTextBox = new TextBox();
		barcodeTextBox.addStyleName("formInput");
		scanButton = new Button(constants.scan());
		scanImage = new Image("imgs/redlight.png");
		barcodeInputPanel.add(barcodeLabel);
		barcodeInputPanel.add(barcodeTextBox);
		barcodeInputPanel.add(scanButton);
		barcodeInputPanel.add(scanImage);
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.addStyleName("buttonPanel");
		buttonPanel.setSpacing(10);
		
		saveLabel = new Label("Zwischenspeichern");
		saveLabel.addStyleName("clickableLabel");
		abortLabel = new Label("Event abbrechen");
		abortLabel.addStyleName("clickableLabel");
		endLabel = new Label("Event beenden");
		endLabel.addStyleName("clickableLabel");
		
		buttonPanel.add(endLabel);
		buttonPanel.add(saveLabel);
		buttonPanel.add(abortLabel);

		header.add(formHeader);
		header.add(barcodeInputPanel);
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
		
		attendedColumn = new Column<EventParticipant, Boolean>(new CheckboxCell(true)) {
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
					if (!object.getPaid().equals("Ja")) {
						participantPaymentEditor(index, object);
					} else {
						object.setAttended("Ja");							
					}
				} else {
					object.setAttended("Nein");
				}
				cellTable.redraw();
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
		
		Column<EventParticipant, String> diseasesColumn = new Column<EventParticipant, String>(new TextCell()) {
			public String getValue(EventParticipant object) {
				return object.getDiseases();
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
		cellTable.addColumn(diseasesColumn, "Krankheiten");
		
		tableWrapper.add(cellTable);
		
		return tableWrapper;
	}

	public Widget asWidget() {
		return this;
	}
	
	public void participantPaymentEditor(final int index, final EventParticipant participant) {
		final DialogBox participantEditor = new DialogBox(true);
		participantEditor.setAnimationEnabled(true);
		participantEditor.setText("Teilnehmer bearbeiten");
		participantEditor.setGlassEnabled(true);
		participantEditor.center();
		participantEditor.setPopupPosition(participantEditor.getAbsoluteLeft() - (participantEditor.getOffsetWidth() / 2), participantEditor.getAbsoluteTop() - (participantEditor.getOffsetHeight() / 2));
		participantEditor.setWidth("auto");
		
		VerticalPanel editor = new VerticalPanel();
		editor.add(new Label("Achtung: " + participant.getForename() + " " + participant.getSurname() + " hat noch nicht bezahlt!"));
		
		participantEditor.add(editor);
		
		final CheckBox paidCheckbox = new CheckBox();
		paidCheckbox.setText("Als 'bezahlt' markieren");
		
		HorizontalPanel labelPanel = new HorizontalPanel();
		Label continueLabel = new Label("Anwesend markieren");
		continueLabel.setStyleName("clickableLabel");
		
		Label abortLabel = new Label("Abbrechen");
		abortLabel.setStyleName("clickableLabel");
		
		labelPanel.add(continueLabel);
		labelPanel.add(abortLabel);
		labelPanel.setSpacing(5);
		
		editor.add(paidCheckbox);
		editor.add(labelPanel);
		editor.setSpacing(5);
		
		continueLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (paidCheckbox.getValue()) {
					participant.setPaid("Ja");
				}
				cellTable.getDisplayedItem(index).setAttended("Ja");
				cellTable.redraw();
				participantEditor.hide();
				barcodeTextBox.setText("");
				barcodeTextBox.setFocus(true);
			}
		});
		
		abortLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				cellTable.getDisplayedItem(index).setAttended("Nein");
				cellTable.redraw();
				participantEditor.hide();
				barcodeTextBox.setText("");
				barcodeTextBox.setFocus(true);
			}
		});
	}
	
	public void setMemberList(ArrayList<EventParticipant> memberList) {
		ldp.setList(memberList);
	}
	
	public TextBox getBarcodeTextBox() {
		return barcodeTextBox;
	}
	
	public ListDataProvider<EventParticipant> getListProvider() {
		return ldp;
	}
	
	public HasClickHandlers getScanButton() {
		return scanButton;
	}
	
	public HasClickHandlers getSaveLabel() {
		return saveLabel;
	}
	
	public HasClickHandlers getEndLabel() {
		return endLabel;
	}
	
	public HasClickHandlers getAbortLabel() {
		return abortLabel;
	}
	
	public Image getScanImage() {
		return scanImage;
	}
	
	public CellTable<EventParticipant> getCellTable() {
		return cellTable;
	}
	
	public Column<EventParticipant, Boolean> getAttendedColumn() {
		return attendedColumn;
	}

}
