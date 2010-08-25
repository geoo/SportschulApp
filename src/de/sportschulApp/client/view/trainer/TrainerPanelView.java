package de.sportschulApp.client.view.trainer;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.trainer.TrainerPanelPresenter;

public class TrainerPanelView extends Composite implements TrainerPanelPresenter.Display {

	public TrainerPanelView() {
		VerticalPanel trainerTestPanel = new VerticalPanel();
		initWidget(trainerTestPanel);
		
		Label testLabel2 = new Label("trainer");
		
		trainerTestPanel.add(testLabel2);
		
	}
	
	public Widget asWidget() {
		return this;
	}

}
