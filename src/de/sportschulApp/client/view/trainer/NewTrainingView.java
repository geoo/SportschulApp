package de.sportschulApp.client.view.trainer;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.presenter.trainer.NewTrainingPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class NewTrainingView extends Composite implements
NewTrainingPresenter.Display {

	private Label barcodeLabel;
	private TextBox barcodeTextBox;
	private LocalizationConstants constants;
	private VerticalPanel memberEntryPanel;
	private VerticalPanel newTrainingWrapper;
	private Button scanButton;
	private Image scanImage;

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
		scanImage = new Image("imgs/redlight.png");
		barcodeInputPanel.add(barcodeLabel);
		barcodeInputPanel.add(barcodeTextBox);
		barcodeInputPanel.add(scanButton);
		barcodeInputPanel.add(scanImage);

		memberEntryPanel = new VerticalPanel();

		newTrainingWrapper.add(barcodeInputPanel);
		newTrainingWrapper.add(memberEntryPanel);

	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public TextBox getBarcodeTextBox() {
		return barcodeTextBox;
	}

	public LocalizationConstants getConstants() {
		return constants;
	}

	public HasClickHandlers getScanButton() {
		return scanButton;
	}

	public Image getScanImage() {
		return scanImage;
	}

	public VerticalPanel getWrapper() {
		return memberEntryPanel;
	}

}
