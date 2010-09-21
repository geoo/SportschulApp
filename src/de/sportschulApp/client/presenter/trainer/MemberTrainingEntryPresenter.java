package de.sportschulApp.client.presenter.trainer;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.HasWidgets;
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
		// TODO Auto-generated method stub
		
	}

	public void go(HasWidgets container) {
		// TODO Auto-generated method stub
		
	}
	
	public Display asWidget(){
		return display;
	}

}
