package de.sportschulApp.client.view.trainer;


import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.trainer.NewTrainingPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class NewTrainingView extends Composite implements
		NewTrainingPresenter.Display {

	private LocalizationConstants constants;
	private Label barcodeLabel;
	private TextBox barcodeTextBox;
	private VerticalPanel newTrainingWrapper;
	private VerticalPanel memberEntryPanel;
	private Button scanButton;

	public NewTrainingView(LocalizationConstants constants) {
		this.constants = constants;
		
		newTrainingWrapper = new VerticalPanel();
		newTrainingWrapper.setStyleName("newTrainingWrapper");
		initWidget(newTrainingWrapper);
		
		HorizontalPanel barcodeInputPanel = new HorizontalPanel();
		barcodeInputPanel.setStyleName("barcodePanel");
		barcodeLabel = new Label(constants.barcode() + ": ");
		barcodeTextBox = new TextBox();
		scanButton = new Button(constants.scan());
		barcodeInputPanel.add(barcodeLabel);
		barcodeInputPanel.add(barcodeTextBox);
		barcodeInputPanel.add(scanButton);
		
		memberEntryPanel = new VerticalPanel();
		
		newTrainingWrapper.add(barcodeInputPanel);
		newTrainingWrapper.add(memberEntryPanel);
	}

	public Widget asWidget() {
		return this;
	}

	public LocalizationConstants getConstants() {
		return constants;
	}

	public TextBox getBarcodeTextBox() {
		return barcodeTextBox;
	}

	public VerticalPanel getWrapper() {
		return memberEntryPanel;
	}
	public HasClickHandlers getScanButton(){
		return scanButton;
	}

}
