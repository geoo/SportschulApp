package de.sportschulApp.client.view.trainer;


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

	public NewTrainingView(LocalizationConstants constants) {
		this.constants = constants;
		
		newTrainingWrapper = new VerticalPanel();
		newTrainingWrapper.setStyleName("newTrainingWrapper");
		initWidget(newTrainingWrapper);
		
		HorizontalPanel barcodeInputPanel = new HorizontalPanel();
		barcodeInputPanel.setStyleName("barcodePanel");
		barcodeLabel = new Label(constants.barcode() + ": ");
		barcodeTextBox = new TextBox();
		barcodeInputPanel.add(barcodeLabel);
		barcodeInputPanel.add(barcodeTextBox);
		
		newTrainingWrapper.add(barcodeInputPanel);
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
		return newTrainingWrapper;
	}

}
