package de.sportschulApp.shared;

import java.io.Serializable;
import java.sql.Date;

@SuppressWarnings("serial")
public class User implements Serializable {
	private String forename;
	private Date lastLogin;
	private String password;
	private String permission;
	private String surname;
	private int userID;
	private String username;

	public User() {

	}

	public User(int userID, String permission, String username,
			String password, String forename, String surname) {
		setUserID(userID);
		setPermission(permission);
		setUsername(username);
		setPassword(password);
		setForename(forename);
		setSurname(surname);
	}

	/**
	 * @return the forename
	 */
	public String getForename() {
		return forename;
	}

	/**
	 * @return the lastLogin
	 */
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param forename
	 *            the forename to set
	 */
	public void setForename(String forename) {
		this.forename = forename;
	}

	/**
	 * @param date
	 *            the lastLogin to set
	 */
	public void setLastLogin(Date date) {
		lastLogin = date;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param permission
	 *            the permission to set
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}

	/**
	 * @param surname
	 *            the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @param userID
	 *            the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
