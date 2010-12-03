package de.sportschulApp.client.view.admin;

import java.util.ArrayList;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
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

import de.sportschulApp.client.event.ShowMemberEvent;
import de.sportschulApp.client.presenter.admin.ListEventParticipantsPresenter;
import de.sportschulApp.shared.EventParticipant;
import de.sportschulApp.shared.Member;

@SuppressWarnings("unchecked")
public class ListEventParticipantsView extends Composite implements ListEventParticipantsPresenter.Display {
	

	private ArrayList<EventParticipant> listData = new ArrayList<EventParticipant>();
	private CellTable<EventParticipant> cellTable = new CellTable<EventParticipant>();
	private ListDataProvider<EventParticipant> ldp;
	private SelectionModel selectionModel;
	private VerticalPanel wrapper = new VerticalPanel();
	private TextBox searchInputField;
	private Label searchButton;
	private Label showAllButton;
	private HandlerManager eventBus;
	private Button submitButton;
	
	private static interface GetValue<C> {
	    C getValue(EventParticipant member);
	  }

	
	public ListEventParticipantsView(HandlerManager eventBus) {
		this.eventBus = eventBus;
		
		wrapper.addStyleName("listWrapper");
		initWidget(wrapper);
	
		wrapper.add(createHeadPanel());
	}
	
	public VerticalPanel createHeadPanel() {
		VerticalPanel listMemberMenuWrapper = new VerticalPanel();
		listMemberMenuWrapper.addStyleName("formWrapper");
		listMemberMenuWrapper.setWidth("700px");
		
		HorizontalPanel formHeader = new HorizontalPanel();
		formHeader.addStyleName("formHeader");
		formHeader.add(new Label("Teilnehmer bearbeiten"));

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
		
		listMemberMenuWrapper.add(formHeader);
		listMemberMenuWrapper.add(searchPanel);
		
		return listMemberMenuWrapper;
	}
	
	
	public VerticalPanel createMemberListTable() {
		cellTable = new CellTable<EventParticipant>();

		ldp = new ListDataProvider<EventParticipant>();
		
		VerticalPanel tableWrapper = new VerticalPanel();
        ldp.setList(listData);
        ldp.refresh();

        cellTable.setPageSize(1000);
        cellTable.setWidth("700px");
        cellTable.setSelectionModel(selectionModel);
		
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
        
        Column<EventParticipant, Boolean> participationColumn = new Column<EventParticipant, Boolean>(
                new CheckboxCell(true)) {
              @Override
              public Boolean getValue(EventParticipant object) {
            	  if (object.getParticipant().equals("Ja")) {
            		  return true;
            	  } else {
            	  return false;
            	  }
              }
            };
            
        participationColumn.setFieldUpdater(new FieldUpdater<EventParticipant, Boolean>() {
              public void update(int index, EventParticipant object, Boolean value) {
            	  if (value == true) {
            		  object.setParticipant("Ja");
            	  } else {
            		  object.setParticipant("Nein");
            	  }
              }
         });
        
        Column<EventParticipant, Boolean> paidColumn = new Column<EventParticipant, Boolean>(
                new CheckboxCell(true)) {
              @Override
              public Boolean getValue(EventParticipant object) {
            	  if (object.getPaid().equals("Ja")) {
            		  return true;
            	  } else {
            	  return false;
            	  }
              }
            };
            
         paidColumn.setFieldUpdater(new FieldUpdater<EventParticipant, Boolean>() {
              public void update(int index, EventParticipant object, Boolean value) {
            	  if (value == true) {
            		  object.setPaid("Ja");
            	  } else {
            		  object.setPaid("Nein");
            	  }
              }
         });
            
         Column<EventParticipant, String> showMemberColumn = new Column<EventParticipant, String>(
                    new ButtonCell()) {
        	 			public String getValue(EventParticipant object) {
							return "Klick";
						}
         };
         
         showMemberColumn.setFieldUpdater(new FieldUpdater<EventParticipant, String>() {
        	 public void update(int index, EventParticipant object, String value) {
				eventBus.fireEvent(new ShowMemberEvent(Integer.valueOf(object.getBarcodeID())));				
			}
		});

        cellTable.addColumn(pictureColumn, "Bild");
        cellTable.addColumn(barcodeColumn, "Barcode");
        cellTable.addColumn(forenameColumn, "Vorname");
        cellTable.addColumn(surnameColumn, "Nachname");
        cellTable.addColumn(participationColumn, "Teilnahme");
        cellTable.addColumn(paidColumn, "Bezahlt?");
        cellTable.addColumn(noteColumn, "Notiz");
        cellTable.addColumn(showMemberColumn, "Mitglied anzeigen");
        
        submitButton = new Button("Teilnehmerliste Speichern");
        
        tableWrapper.add(cellTable);
        tableWrapper.add(submitButton);
       
        return tableWrapper;
	}
	

	public void setMemberList(ArrayList<EventParticipant> memberList) {
		this.listData = memberList;
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
	
	public TextBox getSearchInput() {
		return this.searchInputField;
	}
	
	public HasValue<String> getSearchQuery() {
		return this.searchInputField;
	}

	public Widget asWidget() {
		return this;
	}
		
	public CellTable getCellTable() {
		return this.cellTable;
	}
	
	public HasClickHandlers getSubmitButton() {
		return this.submitButton;
	}

}
