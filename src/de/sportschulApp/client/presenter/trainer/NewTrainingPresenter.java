package de.sportschulApp.client.presenter.trainer;

import com.google.gwt.app.client.IntegerBox;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.TrainerServiceAsync;

import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.client.view.trainer.MemberTrainingEntryView;
import de.sportschulApp.shared.Member;

public class NewTrainingPresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		LocalizationConstants getConstants();

		TextBox getBarcodeTextBox();

		VerticalPanel getWrapper();

		HasClickHandlers getScanButton();
	}

	private final Display display;
	private final TrainerServiceAsync rpcService;
	private LocalizationConstants constants;

	public NewTrainingPresenter(TrainerServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		this.constants = display.getConstants();
		bind();

	}

	private void bind() {
		
		this.display.getBarcodeTextBox().addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == 13) {
					rpcService.getMemberByBarcodeID(Integer.parseInt(display
							.getBarcodeTextBox().getValue()),
							new AsyncCallback<Member>() {

								private MemberTrainingEntryPresenter presenter;

								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}

								public void onSuccess(Member result) {
									try {
										if (result.getForename() == null) {
											System.out
													.println("Barcode nicht in DB!");
										} else {
											if (display.getWrapper()
													.getWidgetCount() == 0) {
												display.getWrapper()
														.setStyleName(
																"memberEntryPanel");

												presenter = new MemberTrainingEntryPresenter(
														rpcService,
														result,
														new MemberTrainingEntryView(
																constants));
												display.getWrapper()
														.insert((MemberTrainingEntryView) presenter
																.asWidget(), 0);
											} else {
												presenter = new MemberTrainingEntryPresenter(
														rpcService,
														result,
														new MemberTrainingEntryView(
																constants));

												display.getWrapper()
														.insert((MemberTrainingEntryView) presenter
																.asWidget(), 0);
											}
										}
										display.getBarcodeTextBox()
												.setSelectionRange(
														0,
														display.getBarcodeTextBox()
																.getText()
																.length());
									} catch (NullPointerException e) {
									}
								}
							});
				}
			}
		});
		display.getScanButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				display.getBarcodeTextBox().setFocus(true);
			}
		});

	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}
