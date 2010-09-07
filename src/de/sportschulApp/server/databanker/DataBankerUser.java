package de.sportschulApp.server.databanker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.sportschulApp.server.BCrypt;
import de.sportschulApp.shared.User;

public class DataBankerUser implements DataBankerUserInterface {

	/**
	 * Legt einen neuen Benutzer an.
	 * 
	 * @param ein
	 *            Objekt des Typs User
	 * @return "Benutzer angelegt" wenn ein neuer User angelegt wurde,
	 *         "Fehler beim anlegen" wenn das anlegen nicht funktioniert hat und
	 *         "Benutzername schon vorhanden", wenn Benutzername schon vorhanden
	 *         ist.
	 */
	public String createUser(User user) {

		DataBankerConnection dbc = new DataBankerConnection();
		try {

			ResultSet rs = null;
			Statement stmt = dbc.getStatement();
			String query = "SELECT User_id FROM User WHERE username='"
					+ user.getUsername() + "'";

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					return "Benutzername schon vorhanden";
				}
			}
			rs.close();
			stmt.close();

			PreparedStatement stmt2 = dbc

					.getConnection()
					.prepareStatement(
							"INSERT INTO user(permission, username, password, forename, surname) VALUES(?,?,?,?,?)");
			stmt2.setString(1, user.getPermission());
			stmt2.setString(2, user.getUsername());
			stmt2.setString(3,
					BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			stmt2.setString(4, user.getForename());
			stmt2.setString(5, user.getSurname());

			stmt2.executeUpdate();

			dbc.close();
			stmt2.close();

			return "Benutzer angelegt";

		} catch (SQLException e) {
			System.out.println(e);
			return "Fehler beim anlegen";
		}

	}

	/**
	 * L�scht einen Benutzer
	 * 
	 * @param userID
	 *            eines Benutzers
	 * 
	 * @return true bei erfolg, false bei scheitern
	 */
	public boolean deleteUser(int userID) {

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "DELETE FROM User WHERE User_id='" + userID + "'";

		try {
			stmt.executeUpdate(query);
			dbc.close();
			stmt.close();
			dbc.closeStatement();

		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return true;

	}

	/**
	 * �ndert einen Benutzereintrag
	 * 
	 * @param ein
	 *            Objekt des Typs User
	 * 
	 * @return true bei erfolg, false bei scheitern
	 */
	public boolean updateUser(User user) {

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String password = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

		String query = "UPDATE User SET permission ='" + user.getPermission()
				+ "', username ='" + user.getUsername() + "', password ='"
				+ password + "', forename ='" + user.getForename()
				+ "', surname ='" + user.getSurname() + "' where User_id = '"
				+ user.getUserID() + "'";

		try {
			stmt.executeUpdate(query);
			dbc.close();
			stmt.close();
			dbc.closeStatement();

		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return true;

	}

	/**
	 * Ist f�r den Login eines Benutzers verantwortlich
	 * 
	 * @param Benutzername
	 *            und Passwort des Benutzers
	 * 
	 * @return bei richtigem Passwort: Permission des Benutzers, bei falschem
	 *         Passwort oder Benutzernamen "wrongPW"
	 * 
	 */

	public String userLogin(String username, String password) {

		String permission = null;
		String hashFromDB = null;

		ResultSet rs = null;
		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "SELECT permission, password FROM User WHERE username='"
				+ username + "'";
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				hashFromDB = rs.getString(2);
				permission = rs.getString(1);
			}
			rs.close();
			dbc.close();
			stmt.close();
			dbc.closeStatement();

			if (hashFromDB == null) {
				return "wrongPW";
			}
		} catch (SQLException e) {
			System.out.println(e);
			return "wrongPW";
		}

		// Passwort check
		if (BCrypt.checkpw(password, hashFromDB)) {
			return permission;
		}

		// Bei falschem Passwort
		return "wrongPW";
	}

	/**
	 * Liefert ein User Objekt zur�ck
	 * 
	 * @param userID
	 * 
	 * @return Objekt des Typs User
	 * 
	 */
	public User getUser(int userID) {

		User user = new User();
		ResultSet rs = null;

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();
		String query = "SELECT * FROM User WHERE User_id='" + userID + "'";
		try {
			rs = stmt.executeQuery(query);
			if (rs.wasNull()) {
			}
			rs.next();
			user.setUserID(rs.getInt("User_id"));
			user.setPermission(rs.getString("permission"));
			user.setUsername(rs.getString("username"));
			user.setForename(rs.getString("forename"));
			user.setSurname(rs.getString("surname"));
			user.setLastLogin(rs.getDate("lastlogin"));
			rs.close();
			dbc.close();
			stmt.close();
			dbc.closeStatement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}

	/**
	 * Liefert ein User Objekt zur�ck
	 * 
	 * @param userID
	 * 
	 * @return Objekt des Typs User
	 * 
	 */
	public ArrayList<User> getUserList() {
		ArrayList<User> userList = new ArrayList<User>();
		ResultSet rs = null;

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();
		String query = "SELECT * FROM User";
		try {
			rs = stmt.executeQuery(query);
			if (rs.wasNull()) {
			}
			while (rs.next()) {
				User user = new User();
				user.setUserID(rs.getInt("User_id"));
				user.setPermission(rs.getString("permission"));
				user.setUsername(rs.getString("username"));
				user.setForename(rs.getString("forename"));
				user.setSurname(rs.getString("surname"));
				user.setLastLogin(rs.getDate("lastlogin"));
				userList.add(user);
			}
			rs.close();
			dbc.close();
			stmt.close();
			dbc.closeStatement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return userList;
	}

	/**
	 * Liefert ein User Objekt zur�ck
	 * 
	 * @param username
	 * 
	 * @return Objekt des Typs User
	 * 
	 */
	public User getUser(String username) {

		User user = new User();
		ResultSet rs = null;

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();
		String query = "SELECT * FROM User WHERE username='" + username + "'";
		try {
			rs = stmt.executeQuery(query);
			if (rs.wasNull()) {
			}
			rs.next();
			user.setUserID(rs.getInt("User_id"));
			user.setPermission(rs.getString("permission"));
			user.setUsername(rs.getString("username"));
			user.setForename(rs.getString("forename"));
			user.setSurname(rs.getString("surname"));
			user.setLastLogin(rs.getDate("lastlogin"));
			rs.close();
			dbc.close();
			stmt.close();
			dbc.closeStatement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return user;
	}
}