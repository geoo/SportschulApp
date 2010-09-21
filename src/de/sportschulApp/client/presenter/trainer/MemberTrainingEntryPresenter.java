package de.sportschulApp.client.presenter.trainer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
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

public class MemberTrainingEntryPresenter implements Presenter{

	public interface Display {
		Widget asWidget();

		LocalizationConstants getConstants();
		
		void fillEntry(Member member);
		
		Image getDeleteButton();

	}

	private final Display display;
	private LocalizationConstants constants;

	public MemberTrainingEntryPresenter(Member member, Display display) {
		this.display = display;
		this.constants = display.getConstants();
		bind();
		display.fillEntry(member);
	}

	

	private void bind() {
		display.getDeleteButton().addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent event) {
				display.getDeleteButton().setUrl("imgs/closeButtonGrey.png");
			}
		});
	}

	public void go(HasWidgets container) {
		// TODO Auto-generated method stub
		
	}
	
	public Display asWidget(){
		return display;
	}

}
