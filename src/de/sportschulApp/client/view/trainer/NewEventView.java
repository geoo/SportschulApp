package de.sportschulApp.client.view.trainer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.trainer.NewEventPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Event;

public class NewEventView extends Composite implements
NewEventPresenter.Display {

	private VerticalPanel newEventWrapper;
	private LocalizationConstants constants;
	private ListBox selectEventListBox;
	private Label continueLabel;

	public NewEventView(LocalizationConstants constants) {
		this.constants = constants;

		newEventWrapper = new VerticalPanel();
		newEventWrapper.addStyleName("newEventWrapper");
		initWidget(newEventWrapper);

		
		VerticalPanel formWrapper = new VerticalPanel();
		formWrapper.addStyleName("newEventFormWrapper");
		formWrapper.setSize("450px", "130px");
		
		selectEventListBox = new ListBox();
		
		continueLabel = new Label("Weiter");
		continueLabel.addStyleName("clickableLabel");

		formWrapper.add(new Label("Wählen sie die Prüfung oder das Event aus, dass sie durchführen möchten. Ein Event/Prüfung kann nur einmal gestartet werden. Danach steht es nicht mehr zur Auswahl und bekommt den Status 'stattgefunden'."));
		formWrapper.add(selectEventListBox);
		formWrapper.add(continueLabel);

		newEventWrapper.add(createHeadPanel());
		newEventWrapper.add(formWrapper);
	}
	
	public VerticalPanel createHeadPanel() {
		VerticalPanel header = new VerticalPanel();
		header.addStyleName("formWrapper");
		header.setWidth("100%");

		HorizontalPanel formHeader = new HorizontalPanel();
		formHeader.addStyleName("formHeader");
		formHeader.add(new Label("Events auswählen"));

		header.add(formHeader);

		return header;
	}

	@Override
	public Widget asWidget() {
		return this;
	}
	
	public void setEventListBox(ArrayList<Event> events) {
		for (int i = 0; i < events.size(); i++) {
			selectEventListBox.addItem(events.get(i).getName() + " (" + events.get(i).getType() + " - " + events.get(i).getDate() + ")");
		}
	}
	
	public HasClickHandlers getContinueButton() {
		return continueLabel;
	}

}
