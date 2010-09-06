package de.sportschulApp.client;

import java.util.Locale;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

import de.sportschulApp.client.event.LanguageChangeEvent;
import de.sportschulApp.client.event.LanguageChangeHandler;
import de.sportschulApp.client.event.LoginEvent;
import de.sportschulApp.client.event.LoginEventHandler;
import de.sportschulApp.client.event.LogoutEvent;
import de.sportschulApp.client.event.LogoutEventHandler;
import de.sportschulApp.client.presenter.AdminPanelPresenter;
import de.sportschulApp.client.presenter.LoginPresenter;
import de.sportschulApp.client.presenter.Presenter;
import de.sportschulApp.client.presenter.TrainerPanelPresenter;
import de.sportschulApp.client.services.LoginServiceAsync;
import de.sportschulApp.client.view.AdminPanelView;
import de.sportschulApp.client.view.LoginView;
import de.sportschulApp.client.view.TrainerPanelView;
import de.sportschulApp.client.view.localization.LocalizationConstants;

public class AppController implements Presenter, ValueChangeHandler<String> {
	private final HandlerManager eventBus;
	private final LoginServiceAsync rpcService;
	private HasWidgets container;
	private LocalizationConstants constants;

	public AppController(LoginServiceAsync rpcService, HandlerManager eventBus, LocalizationConstants constants) {
		this.eventBus = eventBus;
		this.rpcService = rpcService;
		this.constants = constants;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(LoginEvent.TYPE, new LoginEventHandler() {
			public void onLogin(LoginEvent event) {
				doLogin();
			}
		});

		eventBus.addHandler(LogoutEvent.TYPE, new LogoutEventHandler() {
			public void onLogout(LogoutEvent event) {
				doLogout();
			}
		});
		
		eventBus.addHandler(LanguageChangeEvent.TYPE, new LanguageChangeHandler() {
			public void onLanguageChange(LanguageChangeEvent event) {
				if (event.getValue().equals("Deutsch")) {
					Window.open("SportschulApp.html?gwt.codesvr=127.0.0.1:9997&locale=de#" + History.getToken(), "_self", null);						
				}
				if (event.getValue().equals("English")) {
					Window.open("SportschulApp.html?gwt.codesvr=127.0.0.1:9997&locale=en#" + History.getToken(), "_self", null);					
				}
				CookieManager.setLanguageCookie(event.getValue());
			}
		});
	}

	/*
	 * Nach der Loginprozedur entscheidet der AppController welcher Presenter
	 * geladen wird.
	 */

	private void doLogin() {
		if (Cookies.getCookie("SportschuleUserRight").equals("admin")) {
			History.newItem("adminMembersShowMembers");
			History.fireCurrentHistoryState();
		}
		if (Cookies.getCookie("SportschuleUserRight").equals("trainer")) {
			History.newItem("trainerPanel");
			History.fireCurrentHistoryState();
		}

	}

	private void doLogout() {
		CookieManager.deleteAllCookies();
		History.newItem("login");
		History.fireCurrentHistoryState();
	}

	public void go(final HasWidgets container) {
		this.container = container;

		if ("".equals(History.getToken())) {
			History.newItem("login");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	/*
	 * Bei onValueChange (URL/History Token �nderung) wird �berpr�ft ob es f�r
	 * den jeweiligen Token einen Presenter gibt. Zus�tzlich wird im try/catch
	 * Block �berpr�ft ob ein Cookie im Browser abgelegt wurde. Falls nicht,
	 * wird der LoginPresenter geladen. Falls doch, wird �berpr�ft ob ein Admin
	 * oder Trainier angemeldet ist.
	 */

	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {
			Presenter presenter = null;

			if (token.equals("login")) {
				presenter = new LoginPresenter(rpcService, eventBus,
						new LoginView(constants));
			} else {
				try {
					if (!Cookies.getCookie("SportschuleUserName").isEmpty()) {
						if (Cookies.getCookie("SportschuleUserRight").equals(
								"admin")) {
							if (token.substring(0, 5).equals("admin")) {
								presenter = new AdminPanelPresenter(eventBus,
										new AdminPanelView(), constants, token);
							}
						}
						if (Cookies.getCookie("SportschuleUserRight").equals(
								"trainer")) {
							if (token.substring(0, 7).equals("trainer")) {
								presenter = new TrainerPanelPresenter(eventBus,
										new TrainerPanelView());
							}
						}
					} else {
						presenter = new LoginPresenter(rpcService, eventBus,
								new LoginView(constants));
					}
				} catch (NullPointerException e) {
					presenter = new LoginPresenter(rpcService, eventBus,
							new LoginView(constants));
				}
			}

			if (presenter != null) {
				presenter.go(container);
			}
		}
	}

}
