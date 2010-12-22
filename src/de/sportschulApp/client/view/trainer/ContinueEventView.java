package de.sportschulApp.client.view.trainer;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.trainer.ContinueEventPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;
import de.sportschulApp.shared.Event;

public class ContinueEventView extends Composite implements
ContinueEventPresenter.Display {

	private VerticalPanel newEventWrapper;
	private ListBox selectEventListBox;
	private Label continueLabel;
	private ArrayList<Event> events = new ArrayList<Event>();

	public ContinueEventView(LocalizationConstants constants) {

		newEventWrapper = new VerticalPanel();
		newEventWrapper.addStyleName("newEventWrapper");
		initWidget(newEventWrapper);

		
		VerticalPanel formWrapper = new VerticalPanel();
		formWrapper.addStyleName("newEventFormWrapper");
		formWrapper.setSize("450px", "130px");
		
		selectEventListBox = new ListBox();
		
		continueLabel = new Label("Weiter");
		continueLabel.addStyleName("clickableLabel");

		formWrapper.add(new Label("Wählen sie die Prüfung oder das Event aus, dass sie fortsetzen möchten."));
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
			if (events.get(i).getHappened().equals("Ja")) {
				this.events.add(events.get(i));
				selectEventListBox.addItem(events.get(i).getName() + " (" + events.get(i).getType() + " - " + events.get(i).getDate() + ")");
			}
		}
	}
	
	public Event getSelectedItem() {
		return events.get(selectEventListBox.getSelectedIndex());
	}
	
	public HasClickHandlers getContinueButton() {
		return continueLabel;
	}

}
