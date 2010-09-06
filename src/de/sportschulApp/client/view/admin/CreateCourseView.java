package de.sportschulApp.client.view.admin;

import gwtupload.client.PreloadedImage;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.digester.rss.Image;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.admin.CreateCoursePresenter;
import de.sportschulApp.client.view.admin.dualListBox.DualListBox;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class CreateCourseView extends Composite implements
		CreateCoursePresenter.Display {

	private LocalizationConstants constants;
	private DualListBox dualListBox;

	public CreateCourseView(LocalizationConstants constants) {
		this.constants = constants;

		VerticalPanel dummyPanel = new VerticalPanel();
		initWidget(dummyPanel);

		Label dummyLabel = new Label("Kurs erstellen");

		dummyPanel.add(dummyLabel);

		dualListBox = new DualListBox(10, "10em");
		// dualListBox.getDragController().addDragHandler(demoDragHandler);

		// use the dual list box as our widget
		// setWidget(dualListBox);

		// add some items to the list
		dualListBox.addLeft("Apples");
		dualListBox.addLeft("Bananas");
		dualListBox.addLeft("Cucumbers");
		dualListBox.addLeft("Dates");
		dualListBox.addLeft("Enchiladas");

		dualListBox.setStyleName("dualListBox");
		dummyPanel.add(dualListBox);

		Button testButton = new Button("test");
		dummyPanel.add(testButton);

		testButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				/*
				 * String[] temp2;
				 * 
				 * String
				 * temp=dualListBox.getWidgetListLeft().get(0).toString(); temp2
				 * = temp.split(">"); temp2 = temp2[1].split("<");
				 */
				System.out.println("LEFT: " + dualListBox.getWidgetListLeft());
				System.out.println("RIGHT: " + dualListBox.getWidgetListRight());

			}
		});
		
				
	}

	public Widget asWidget() {
		return this;
	}

}
