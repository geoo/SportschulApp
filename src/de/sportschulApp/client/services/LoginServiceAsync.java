package de.sportschulApp.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.sportschulApp.shared.User;

public interface LoginServiceAsync {
	public void login(User user, AsyncCallback<User> callback);
}
