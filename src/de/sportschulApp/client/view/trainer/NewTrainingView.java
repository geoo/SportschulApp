package de.sportschulApp.client.view.trainer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import de.sportschulApp.client.presenter.trainer.NewTrainingPresenter;
import de.sportschulApp.client.services.TrainerServiceAsync;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Member;

public class NewTrainingView extends Composite implements
		NewTrainingPresenter.Display {

	private CellTable<Member> cellTable = new CellTable<Member>();
	private Label barcodeLabel;
	private TextBox barcodeTextBox;
	private LocalizationConstants constants;
	private VerticalPanel memberEntryPanel;
	private VerticalPanel newTrainingWrapper;
	private Button scanButton;
	private Image scanImage;
	private ArrayList<Member> listData = new ArrayList<Member>();
	private ListDataProvider<Member> ldp;
	private TrainerServiceAsync rpcService;
	private HashMap<Integer, String> barcodeIDs = new HashMap<Integer, String>();
	Date today = new Date();
	protected Integer trainingsPresence;

	public NewTrainingView(LocalizationConstants constants) {
		this.constants = constants;

		newTrainingWrapper = new VerticalPanel();
		newTrainingWrapper.setStyleName("newTrainingWrapper");
		initWidget(newTrainingWrapper);

		HorizontalPanel barcodeInputPanel = new HorizontalPanel();
		barcodeInputPanel.setStyleName("barcodePanel");
		barcodeLabel = new Label(constants.barcode() + ": ");
		barcodeTextBox = new TextBox();
		scanButton = new Button(constants.scan());
		scanImage = new Image("imgs/redlight.png");
		barcodeInputPanel.add(barcodeLabel);
		barcodeInputPanel.add(barcodeTextBox);
		barcodeInputPanel.add(scanButton);
		barcodeInputPanel.add(scanImage);

		memberEntryPanel = new VerticalPanel();

		newTrainingWrapper.add(barcodeInputPanel);
		newTrainingWrapper.add(memberEntryPanel);

	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public TextBox getBarcodeTextBox() {
		return barcodeTextBox;
	}

	public LocalizationConstants getConstants() {
		return constants;
	}

	public HasClickHandlers getScanButton() {
		return scanButton;
	}

	public Image getScanImage() {
		return scanImage;
	}

	public VerticalPanel getMemberEntryPanel() {
		return memberEntryPanel;
	}

	public void setMemberList(ArrayList<Member> memberList) {
		listData = memberList;
		cellTable.removeFromParent();
		memberEntryPanel.add(createMemberListTable());
	}

	public void setMemberList() {
		cellTable.removeFromParent();
		memberEntryPanel.add(createMemberListTable());
	}

	public void addMemberToList(Member member) {
		// TODO
		listData.add(0, member);
		setMemberList();
	}

	public void deleteMemberFromList(int index, int barcodeId) {
		listData.remove(index);
		barcodeIDs.remove(barcodeId);
		removeTrainingspresence(barcodeId);
		setMemberList();
	}

	public VerticalPanel createMemberListTable() {
		cellTable = new CellTable<Member>();
		cellTable.sinkEvents(Event.ONCLICK);

		ldp = new ListDataProvider<Member>();

		VerticalPanel tableWrapper = new VerticalPanel();
		ldp.setList(listData);
		ldp.refresh();

		cellTable.setPageSize(1000);
		cellTable.setWidth("700px");
		// cellTable.setSelectionModel(selectionModel);

		ldp.addDataDisplay(cellTable);

		Column<Member, String> pictureColumn = new Column<Member, String>(
				new ImageCell()) {
			@Override
			public String getValue(Member object) {
				return object.getPicture();
			}
		};

		Column<Member, String> barcodeColumn = new Column<Member, String>(
				new TextCell()) {
			@Override
			public String getValue(Member object) {
				return "" + object.getBarcodeID();
			}
		};

		Column<Member, String> forenameColumn = new Column<Member, String>(
				new TextCell()) {
			@Override
			public String getValue(Member object) {
				return object.getForename();
			}
		};

		Column<Member, String> surnameColumn = new Column<Member, String>(
				new TextCell()) {
			@Override
			public String getValue(Member object) {
				return object.getSurname();
			}
		};

		Column<Member, String> thumbsColumn = new Column<Member, String>(
				new ImageCell()) {
			@Override
			public String getValue(Member object) {
				if ((object.getTrainingUnitsInMonth() + 1) <= object
						.getTrainingunits()) {
					return "/imgs/thumbs-up.png";
				} else {
					return "/imgs/thumbs-down.png";

				}
			}
		};
		Column<Member, String> trainingsPresenceColumn = new Column<Member, String>(
				new TextCell()) {
			@Override
			public String getValue(Member object) {
				// TODO
				return (object.getTrainingUnitsInMonth() + 1) + " von "
						+ object.getTrainingunits();
			}
		};

		Column<Member, String> noteColumn = new Column<Member, String>(
				new EditTextCell()) {
			@Override
			public String getValue(Member object) {
				return object.getNote();
			}
		};

		noteColumn.setFieldUpdater(new FieldUpdater<Member, String>() {
			public void update(int index, Member object, String value) {
				object.setNote(value);
				saveNote(object.getBarcodeID(), value);
			}
		});

		Column<Member, String> deleteColumn = new Column<Member, String>(
				new ButtonCell()) {
			@Override
			public String getValue(Member object) {
				return "entfernen";
			}
		};

		deleteColumn.setFieldUpdater(new FieldUpdater<Member, String>() {

			public void update(int index, Member object, String value) {
				deleteMemberFromList(index, object.getBarcodeID());
			}
		});

		cellTable.addColumn(thumbsColumn, "");
		cellTable.addColumn(pictureColumn, "Bild");
		cellTable.addColumn(trainingsPresenceColumn, "Anwesehnheit");
		//cellTable.addColumn(barcodeColumn, "Barcode");
		cellTable.addColumn(forenameColumn, "Vorname");
		cellTable.addColumn(surnameColumn, "Nachname");
		cellTable.addColumn(noteColumn, "Notiz");
		cellTable.addColumn(deleteColumn, "");

		tableWrapper.add(cellTable);

		return tableWrapper;
	}

	public void saveNote(int barcodeId, String note) {
		rpcService.setNote(barcodeId, note, new AsyncCallback<String>() {

			public void onSuccess(String result) {
				System.out.println("Notiz gespeichert!");
			}

			public void onFailure(Throwable caught) {
				System.out.println("Fehler beim Speichern der Notiz!");

			}
		});
	}

	public void setRpcService(TrainerServiceAsync rpcService) {
		this.rpcService = rpcService;
	}

	public HashMap<Integer, String> getBarcodeIDs() {
		return barcodeIDs;
	}

	private void removeTrainingspresence(int barcodeID) {
		rpcService.removeTrainingsPresence(barcodeID, today.getDate(),
				today.getMonth(), today.getYear(), new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {

					}

					public void onSuccess(String result) {
						System.out.println("Trainingspresence removed!");
					}
				});
	}

	private void getTrainingsPresence(int barcodeID) {
		Date today = new Date();
		rpcService.getTrainingspresence(barcodeID, today.getMonth(),
				today.getYear(), new AsyncCallback<Integer>() {

					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
					}

					public void onSuccess(Integer result) {
						System.out.println("RESULT:: " + result);
						// listData.get(0).setTrainingsPresenceInMonth(result);
					}
				});
	}

}
