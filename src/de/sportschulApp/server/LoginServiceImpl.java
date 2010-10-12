package de.sportschulApp.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.sportschulApp.client.services.LoginService;
import de.sportschulApp.server.databanker.DataBankerCourse;
import de.sportschulApp.server.databanker.DataBankerMember;
import de.sportschulApp.server.databanker.DataBankerUser;

import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.Member;
import de.sportschulApp.shared.User;

@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	// private SessionHandler sh = new SessionHandler();

	public User login(User user) {
		DataBankerUser dbu = new DataBankerUser();
		user.setPermission(dbu.userLogin(user.getUsername(), user.getPassword()));
		user.setPassword("");
		return user;

	}

}
