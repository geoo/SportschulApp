package de.sportschulApp.client.presenter;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import de.sportschulApp.client.CookieManager;
import de.sportschulApp.client.event.LanguageChangeEvent;
import de.sportschulApp.client.event.LoginEvent;
import de.sportschulApp.client.services.LoginServiceAsync;
import de.sportschulApp.shared.User;

public class LoginPresenter implements Presenter {
	public interface Display {
		Widget asWidget();
		HasChangeHandlers getLanguagePickerOnChange();
		String getLanguagePickerValue();
		HasClickHandlers getLoginButton();
		HasValue<String> getPassword();
		HasKeyUpHandlers getPasswordOnKeyUp();
		HasValue<String> getUserName();
		HasKeyUpHandlers getUserNameOnKeyUp();
		void setErrorLabel();
	}

	private final Display display;
	private final HandlerManager eventBus;
	private User loginUser;
	private final LoginServiceAsync rpcService;

	public LoginPresenter(LoginServiceAsync rpcService,
			HandlerManager eventBus, Display display) {
		this.rpcService = rpcService;
		this.eventBus = eventBus;
		this.display = display;
		bind();
	}

	public void bind() {
		display.getLoginButton().addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				doLogin();
			}
		});


		display.getUserNameOnKeyUp().addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == 13) {
					doLogin();
				}
			}
		});

		display.getPasswordOnKeyUp().addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == 13) {
					doLogin();
				}
			}
		});

		display.getLanguagePickerOnChange().addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				changeLanguage();
			}
		});
	}

	public void changeLanguage(){
		eventBus.fireEvent(new LanguageChangeEvent(display.getLanguagePickerValue()));
	}

	public void doLogin() {
		loginUser = new User();
		loginUser.setUsername(display.getUserName().getValue());
		loginUser.setPassword(display.getPassword().getValue());

		rpcService.login(loginUser, new AsyncCallback<User>() {
			public void onFailure(Throwable caught) {
				Window.alert("Anmelden fehlgeschlagen");
			}

			public void onSuccess(User user) {
				if (!user.getPermission().equals("wrongPW")) {
					CookieManager.setUserCookie(user.getUsername(),
							user.getPermission());
					eventBus.fireEvent(new LoginEvent());

					System.out.println("Angemeldet als: " + user.getUsername());
					System.out.println("Permission: "+user.getPermission());
				}
				else{
					showErrorLabel();
				}
			}
		});
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(display.asWidget());
	}

	public void showErrorLabel() {
		display.setErrorLabel();
	}
}
