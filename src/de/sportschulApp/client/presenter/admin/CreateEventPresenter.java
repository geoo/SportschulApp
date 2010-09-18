package de.sportschulApp.client.presenter.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.shared.User;

public class CreateEventPresenter implements Presenter {
	public interface Display {

		Widget asWidget();

		HasClickHandlers getAddMemberButtonHandler();

		VerticalPanel getWrapper();

		VerticalPanel getCreateEventPanel();

		VerticalPanel getAddMemberPanel();

		HasClickHandlers getCreateEventButtonHandler();
	}

	private final Display display;
	private final AdminServiceAsync rpcService;

	public CreateEventPresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		bind();
	}

	private void bind() {
		display.getAddMemberButtonHandler().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				display.getWrapper().remove(display.getCreateEventPanel());
				display.getWrapper().add(display.getAddMemberPanel());

			}
		});

		display.getCreateEventButtonHandler().addClickHandler(
				new ClickHandler() {

					public void onClick(ClickEvent event) {

						display.getWrapper()
								.remove(display.getAddMemberPanel());
						display.getWrapper().add(display.getCreateEventPanel());

					}
				});
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}


}
