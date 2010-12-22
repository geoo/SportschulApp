package de.sportschulApp.client.view.trainer;


import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.CookieManager;
import de.sportschulApp.client.presenter.trainer.TrainerNavigationPresenter;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class TrainerNavigationView extends Composite implements TrainerNavigationPresenter.Display {

	private ListBox languagePicker = new ListBox();
	private Label logOutLabel;
	private Label menuTrainingNewTraining;
	private Label menuTrainingNewEvent;
	private Label menuTrainingContinueEvent;

	public TrainerNavigationView(int tabIndex, LocalizationConstants constants) {
		VerticalPanel adminHeadPanel = new VerticalPanel();
		adminHeadPanel.setWidth("100%");
		initWidget(adminHeadPanel);

		HorizontalPanel metaHeadPanel = new HorizontalPanel();
		metaHeadPanel.setStyleName("metaHeadPanel");
		metaHeadPanel.setSize("100%", "28px");
		HorizontalPanel metaHeadLoginDetailsPanel = new HorizontalPanel();
		metaHeadLoginDetailsPanel.setStyleName("metaHeadLoginDetailsPanel");

		Label loggedAs = new Label("Angemeldet als: ");
		Label loginName = new Label(CookieManager.getUsername());
		logOutLabel = new Label("Abmelden");
		logOutLabel.addStyleName("logOutLabel");

		metaHeadLoginDetailsPanel.add(loggedAs);
		metaHeadLoginDetailsPanel.add(loginName);
		metaHeadLoginDetailsPanel.add(logOutLabel);

		HorizontalPanel languagePanel = new HorizontalPanel();
		languagePanel.addStyleName("languagePanel");
		Label languageLabel = new Label(constants.language() + ": ");
		languagePicker.addItem("Deutsch");
		languagePicker.addItem("English");

		try {
			if (!Cookies.getCookie("SportschuleLanguage").isEmpty()) {
				if (Cookies.getCookie("SportschuleLanguage").equals("Deutsch")) {
					languagePicker.setSelectedIndex(0);
				}
				if (Cookies.getCookie("SportschuleLanguage").equals("English")) {
					languagePicker.setSelectedIndex(1);
				}
			}
		} catch (NullPointerException e) {
			languagePicker.setSelectedIndex(0);
		}

		languagePanel.add(languageLabel);
		languagePanel.add(languagePicker);

		metaHeadPanel.add(metaHeadLoginDetailsPanel);
//		metaHeadPanel.add(languagePanel);


		Image naviLogo = new Image("imgs/mm-logo-navi.png");
		naviLogo.addStyleName("naviLogo");

		TabLayoutPanel navigationPanel = new TabLayoutPanel(1.5, Unit.EM);
		navigationPanel.addStyleName("navigationPanel");
		navigationPanel.add(getSubNavigationElements(0), "Training");

		navigationPanel.selectTab(tabIndex);

		HorizontalPanel mainHeadPanel = new HorizontalPanel();
		mainHeadPanel.setStyleName("mainHeadPanel");
		mainHeadPanel.setHeight("54px");
		mainHeadPanel.setWidth("100%");

		mainHeadPanel.add(naviLogo);
		mainHeadPanel.add(navigationPanel);


		adminHeadPanel.add(metaHeadPanel);
		adminHeadPanel.add(mainHeadPanel);

	}

	@Override
	public Widget asWidget() {
		return this;
	}

	public HasChangeHandlers getLanguagePickerOnChange() {
		return languagePicker;
	}

	public String getLanguagePickerValue() {
		return languagePicker.getValue(languagePicker.getSelectedIndex());
	}

	public HasClickHandlers getLogoutButton() {
		return logOutLabel;
	}

	public HasClickHandlers getMenuTrainingNewTraining() {
		return menuTrainingNewTraining;
	}
	
	public HasClickHandlers getMenuTrainingNewEvent() {
		return menuTrainingNewEvent;
	}
	
	public HasClickHandlers getMenuTrainingContinueEventLabel() {
		return menuTrainingContinueEvent;
	}

	public HorizontalPanel getSubNavigationElements(int navigationID) {
		HorizontalPanel subNavPanel = new HorizontalPanel();
		subNavPanel.addStyleName("subNavigationPanel");


		if (navigationID == 0){
			menuTrainingNewTraining = new Label("Neues Training");
			subNavPanel.add(menuTrainingNewTraining);
			menuTrainingNewEvent = new Label("Event starten");
			subNavPanel.add(menuTrainingNewEvent);
			menuTrainingContinueEvent = new Label("Event fortsetzen");
			subNavPanel.add(menuTrainingContinueEvent);
		}


		return subNavPanel;
	}
}
