package de.sportschulApp.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.sportschulApp.shared.User;


@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService {
	User login(User user);
}
