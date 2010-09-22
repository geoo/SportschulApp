package de.sportschulApp.client.presenter.trainer;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.presenter.trainer.NewTrainingPresenter.Display;
import de.sportschulApp.client.services.TrainerServiceAsync;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.client.view.trainer.MemberTrainingEntryView;
import de.sportschulApp.shared.Member;

public class MemberTrainingEntryPresenter implements Presenter {

	public interface Display {
		Widget asWidget();

		LocalizationConstants getConstants();

		void fillEntry(Member member, Integer trainingspresence);

		Image getDeleteButton();

		Image getNoteButton();

		void showPopup(MouseDownEvent event);

	}

	private final Display display;
	private final TrainerServiceAsync rpcService;
	private LocalizationConstants constants;
	private Member member;

	public MemberTrainingEntryPresenter(TrainerServiceAsync rpcService,
			Member member, Display display) {
		this.display = display;
		this.rpcService = rpcService;
		this.member = member;
		this.constants = display.getConstants();
		bind();
		getTrainingsPresence(member.getBarcodeID());
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

			public void onMouseDown(MouseDownEvent event) {
				display.getNoteButton().setUrl("imgs/noteGrey.png");
				display.showPopup(event);

			}
		});
		display.getNoteButton().addMouseUpHandler(new MouseUpHandler() {

			public void onMouseUp(MouseUpEvent event) {
				display.getNoteButton().setUrl("imgs/note.png");

			}
		});

	}

	public void go(HasWidgets container) {
		// TODO Auto-generated method stub

	}

	public Display asWidget() {
		return display;
	}

	private void getTrainingsPresence(int barcodeID) {
		Date today = new Date();
		rpcService.getTrainingspresence(barcodeID, today.getMonth(),
				today.getYear(), new AsyncCallback<Integer>() {

					public void onSuccess(Integer result) {
						display.fillEntry(member, result);
					}

					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});
	}

}
