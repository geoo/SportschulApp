package de.sportschulApp.client.presenter.admin;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.AdminServiceAsync;
import de.sportschulApp.client.view.admin.ListMemberView;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class CreateEventPresenter implements Presenter {
	public interface Display {

		Widget asWidget();

		HasClickHandlers getAddMemberButtonHandler();

		VerticalPanel getWrapper();

		VerticalPanel getCreateEventPanel();

		VerticalPanel getAddMemberPanel();

		HasClickHandlers getCreateEventButtonHandler();
		
		void addMemberlist(ListMemberView presenter);
		
		LocalizationConstants getConstants();
	}

	private final Display display;
	private final AdminServiceAsync rpcService;
	private final HandlerManager eventBus;

	public CreateEventPresenter(AdminServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		bind();
		addMemberView();
		
	}

	private void addMemberView() {
		ListMemberPresenter memberListPresenter = new ListMemberPresenter(rpcService, eventBus, new ListMemberView());
		display.addMemberlist((ListMemberView) memberListPresenter.getDisplay());		
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
