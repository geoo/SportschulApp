package de.sportschulApp.client.presenter.trainer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
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
		HashMap<Integer, String> getBarcodeIDs();

		Widget asWidget();

		TextBox getBarcodeTextBox();

		LocalizationConstants getConstants();

		HasClickHandlers getScanButton();

		Image getScanImage();

		VerticalPanel getMemberEntryPanel();
		
		void setMemberList(ArrayList<Member> memberList);

		void addMemberToList(Member result);

		void setRpcService(TrainerServiceAsync rpcService);

	}

	private LocalizationConstants constants;
	private final Display display;
	private final TrainerServiceAsync rpcService;
	Date today = new Date();

	public NewTrainingPresenter(TrainerServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		constants = display.getConstants();
		bind();
		display.setRpcService(rpcService);
		
		

	}

	private void addTrainingspresence(int barcodeID) {
		rpcService.setTrainingsPresence(barcodeID, today.getDate(),
				today.getMonth(), today.getYear(), new AsyncCallback<String>() {

			public void onFailure(Throwable caught) {

			}

			public void onSuccess(String result) {
				System.out.println("Trainingspresence added!");
			}
		});
	}

	private void bind() {

		display.getBarcodeTextBox().addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == 13) {
					rpcService.getMemberByBarcodeID2(Integer.parseInt(display
							.getBarcodeTextBox().getValue()), today.getMonth(), today.getYear(),
							new AsyncCallback<Member>() {

						private MemberTrainingEntryPresenter presenter;

						public void onFailure(Throwable caught) {
							

						}

						public void onSuccess(Member result) {
							try {
								if (result.getForename() == null) {
									System.out
									.println("Barcode nicht in DB!");
								} else {
									if (display.getBarcodeIDs().containsKey(result
											.getBarcodeID())) {
										//Member schon gescannt
										System.out
										.println("Member schon gescannt!");
									} else {

										display.getBarcodeIDs().put(
												result.getBarcodeID(),
												null);
										if (display.getMemberEntryPanel()
												.getWidgetCount() == 0) {
										//	display.getMemberEntryPanel()
										//	.setStyleName(
										//			"memberEntryPanel");

											display.addMemberToList(result);
											
											//TODO
											addTrainingspresence(result
													.getBarcodeID());
											/*
											presenter = new MemberTrainingEntryPresenter(
													rpcService,
													result,
													new MemberTrainingEntryView(
															constants));
											display.getMemberEntryPanel()
											.insert((MemberTrainingEntryView) presenter
													.asWidget(),
													0);*/
										} else {
											display.addMemberToList(result);

											addTrainingspresence(result
													.getBarcodeID());
											/*
											presenter = new MemberTrainingEntryPresenter(
													rpcService,
													result,
													new MemberTrainingEntryView(
															constants));

											display.getMemberEntryPanel()
											.insert((MemberTrainingEntryView) presenter
													.asWidget(),
													0);
													*/
										}
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

		display.getBarcodeTextBox().addFocusHandler(new FocusHandler() {

			public void onFocus(FocusEvent event) {
				display.getScanImage().setUrl("imgs/greenlight.png");
			}
		});
		display.getBarcodeTextBox().addBlurHandler(new BlurHandler() {

			public void onBlur(BlurEvent event) {
				display.getScanImage().setUrl("imgs/redlight.png");

			}
		});
		display.getBarcodeTextBox().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				display.getBarcodeTextBox().setSelectionRange(0,
						display.getBarcodeTextBox().getText().length());
			}
		});
	}

	public void go(HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

}
