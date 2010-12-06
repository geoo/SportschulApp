package de.sportschulApp.server.databanker;

import java.util.ArrayList;

import de.sportschulApp.shared.User;

public interface DataBankerUserInterface {

	public String createUser(User user);

	public boolean deleteUser(int userID);

	public User getUser(int userID);

	public User getUser(String username);

	public ArrayList<User> getUserList();

	public String updateUser(User user);

	public String userLogin(String username, String password);

}
