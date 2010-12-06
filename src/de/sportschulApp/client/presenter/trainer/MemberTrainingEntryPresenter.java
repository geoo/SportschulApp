package de.sportschulApp.client.presenter.trainer;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.services.TrainerServiceAsync;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Member;

public class MemberTrainingEntryPresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		void closePopup();

		void fillEntry(Member member, Integer trainingspresence);

		LocalizationConstants getConstants();

		Image getDeleteButton();

		Image getNoteButton();

		HasClickHandlers getPopupCancelButton();

		HasClickHandlers getPopupSendButton();

		String getPopupTextArea();

		void showPopup(int left, int top, String note);

	}

	private LocalizationConstants constants;
	private final Display display;
	private Member member;
	private final TrainerServiceAsync rpcService;

	public MemberTrainingEntryPresenter(TrainerServiceAsync rpcService,
			Member member, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		this.member = member;
		constants = display.getConstants();
		bind();
		getTrainingsPresence(member.getBarcodeID());
	}

	public Display asWidget() {
		return display;
	}

	private void bind() {
		display.getDeleteButton().addMouseDownHandler(new MouseDownHandler() {

			public void onMouseDown(MouseDownEvent event) {
				display.getDeleteButton().setUrl("imgs/closeButtonGrey.png");

			}
		});
		display.getDeleteButton().addMouseUpHandler(new MouseUpHandler() {

			public void onMouseUp(MouseUpEvent event) {
				display.getDeleteButton().setUrl("imgs/closeButton.png");

			}
		});

		display.getNoteButton().addMouseDownHandler(new MouseDownHandler() {

			private int left;
			private String note;
			private int top;

			public void onMouseDown(MouseDownEvent event) {
				Widget source = (Widget) event.getSource();
				left = source.getAbsoluteLeft() - 180;
				top = source.getAbsoluteTop() + 10;
				display.getNoteButton().setUrl("imgs/noteGrey.png");

				rpcService.getNote(member.getBarcodeID(),
						new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					public void onSuccess(String result) {

						note = result;
					}
				});

				Timer timer = new Timer() {
					@Override
					public void run() {
						display.showPopup(left, top, note);
					}
				};
				timer.schedule(500);

			}
		});
		display.getNoteButton().addMouseUpHandler(new MouseUpHandler() {

			public void onMouseUp(MouseUpEvent event) {
				display.getNoteButton().setUrl("imgs/note.png");

			}
		});

		display.getPopupCancelButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				display.closePopup();
			}
		});
		display.getPopupSendButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				rpcService.setNote(member.getBarcodeID(),
						display.getPopupTextArea(),
						new AsyncCallback<String>() {

					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					public void onSuccess(String result) {
						System.out.println("Note gespeichert!");
						display.closePopup();
					}
				});
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
				display.fillEntry(member, result);
			}
		});
	}

	public void go(HasWidgets container) {
		// TODO Auto-generated method stub

	}

}
