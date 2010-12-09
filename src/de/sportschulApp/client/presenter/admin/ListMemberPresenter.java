package de.sportschulApp.client.presenter.admin;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;

import de.sportschulApp.client.event.ShowMemberEvent;
import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.shared.Member;

@SuppressWarnings("unchecked")
public class ListMemberPresenter implements Presenter{
	public interface Display{
		Widget asWidget();
		HasClickHandlers getSearchButton();
		TextBox getSearchInput();
		HasValue<String> getSearchQuery();
		HasClickHandlers getShowAllButton();
		void setMemberList(ArrayList<Member> memberList);
		void setSelectionModel(SingleSelectionModel selectionModel);
	}

	private final Display display;
	private final HandlerManager eventBus;
	private final AdminServiceAsync rpcService;

	public ListMemberPresenter(AdminServiceAsync rpcService, HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		bind();
		fetchListData();
	}

	private void bind() {
		setSelectionModel();

		display.getSearchButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				executeSearch();
			}
		});

		display.getSearchInput().addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == 13) {
					executeSearch();
				}
			}
		});

		display.getShowAllButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				fetchListData();
				display.getSearchInput().setText("");
			}
		});
	}

	public void executeSearch() {
		String searchQuery = new String(display.getSearchQuery().getValue());
		rpcService.searchMember(searchQuery, new AsyncCallback<ArrayList<Member>>() {
			public void onFailure(Throwable caught) {
			}
			public void onSuccess(ArrayList<Member> result) {
				display.setMemberList(result);
			}
		});
	}

	public void fetchListData() {
		rpcService.getMemberList(new AsyncCallback<ArrayList<Member>>() {
			public void onFailure(Throwable caught) {
				Window.alert("Abrufen der Mitgliedsdaten fehlgeschlagen.");
			}
			public void onSuccess(ArrayList<Member> result) {
				System.out.println("testtest");
				display.setMemberList(result);
			}
		});

	}

	public Display getDisplay(){
		return display;
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	public void setSelectionModel() {
		final SingleSelectionModel<Member> selectionModel = new SingleSelectionModel<Member>();
		Handler selectionHandler = new SelectionChangeEvent.Handler() {
			public void onSelectionChange(SelectionChangeEvent event) {
				Member selection = selectionModel.getSelectedObject();
				eventBus.fireEvent(new ShowMemberEvent(selection.getBarcodeID()));
				selectionModel.setSelected(selection, false);
			}
		};
		selectionModel.addSelectionChangeHandler(selectionHandler);
		display.setSelectionModel(selectionModel);
	}

}
