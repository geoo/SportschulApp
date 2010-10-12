package de.sportschulApp.server.databanker;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;

import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.Event;

public class DataBankerEvent implements DataBankerEventInterface {

	/**
	 * Legt einen neuen Event an.
	 * 
	 * @param ein
	 *            Objekt des Typs Event
	 * @return true wenn anlegen funktioniert hat, false wenn ein Fehler
	 *         aufgetreten ist
	 */
	public boolean createEvent(Event event) {

		DataBankerConnection dbc = new DataBankerConnection();
		try {

			PreparedStatement stmt = dbc

					.getConnection()
					.prepareStatement(
							"INSERT INTO Event(Course_id, name, costs, location, date, time) VALUES(?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, event.getCourseID());
			stmt.setString(2, event.getName());
			stmt.setString(3, event.getCosts());
			stmt.setString(4, event.getLocation());
			stmt.setDate(5, event.getDate());
			stmt.setTime(6, event.getTime());
			
			
			stmt.executeUpdate();

			ResultSet generatedKeys = stmt.getGeneratedKeys();
			
			while(generatedKeys.next()) {
				setExaminersForEvent(generatedKeys.getInt(1), event.getExaminers());
			}
			
			dbc.close();
			stmt.close();

			return true;

		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

	}

	/**
	 * L�scht einen Event
	 * 
	 * @param eventID
	 *            eines Events
	 * 
	 * @return true bei erfolg, false bei scheitern
	 */
	public boolean deleteEvent(int eventID) {

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "DELETE FROM Event WHERE Event_id='" + eventID + "'";

		try {
			stmt.executeUpdate(query);
			deleteExaminersFromEvent(eventID);
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
	 * �ndert einen Event
	 * 
	 * @param ein
	 *            Objekt des Typs Event
	 * 
	 * @return true bei erfolg, false bei scheitern
	 */
	public boolean updateEvent(Event event) {

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "UPDATE Event SET Course_id ='" + event.getCourseID()
				+ "', name ='" + event.getName() + "', costs ='"
				+ event.getCosts() + "', date ='" + event.getDate()
				+ "', time ='" + event.getTime() + "', location ='"
				+ event.getLocation() + "' where Event_id = '"
				+ event.getEventID() + "'";

		try {
			stmt.executeUpdate(query);
			updateExaminersForEvent(event.getEventID(), event.getExaminers());
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
	 * F�gt ein Mitglied zu einem Event hinzu
	 * 
	 * @param memberID
	 *            des Mitglieds und eventID des Events
	 * 
	 * @return true bei erfolg, false bei scheitern
	 */
	public boolean addMemberToEvent(int memberID, int eventID) {

		DataBankerConnection dbc = new DataBankerConnection();
		try {

			PreparedStatement stmt = dbc

					.getConnection()
					.prepareStatement(
							"INSERT INTO EventParticipants(Member_id, Event_id) VALUES(?,?)");
			stmt.setInt(1, memberID);
			stmt.setInt(2, eventID);

			stmt.executeUpdate();

			dbc.close();
			stmt.close();

			return true;

		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

	}

	/**
	 * Entfernt ein Mitglied von einem Event
	 * 
	 * @param memberID
	 *            des Mitglieds und eventID des Events
	 * 
	 * @return true bei erfolg, false bei scheitern
	 */
	public boolean removeMemberFromEvent(int memberID, int eventID) {

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "DELETE FROM EventParticipants WHERE Member_id='"
				+ memberID + "' AND Event_id='" + eventID + "'";

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

	public Event getEvent(int eventID) {

		Event event = new Event();
		DataBankerConnection dbc = new DataBankerConnection();
		try {

			ResultSet rs = null;
			Statement stmt = dbc.getStatement();
			String query = "SELECT * FROM Event WHERE Event_id='" + eventID
					+ "'";

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				event.setEventID(rs.getInt("Event_id"));
				event.setCourseID(rs.getInt("Course_id"));
				event.setName(rs.getString("name"));
				event.setCosts(rs.getString("costs"));
				event.setDate(rs.getDate("date"));
				event.setTime(rs.getTime("time"));
				event.setLocation(rs.getString("location"));
				event.setExaminers(getExaminersForEvent(event.getEventID()));
				
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}

		return event;

	}

	public ArrayList<Integer> getEventParticipants(int eventID) {

		ArrayList<Integer>participants = new ArrayList<Integer>();
		DataBankerConnection dbc = new DataBankerConnection();
		try {

			ResultSet rs = null;
			Statement stmt = dbc.getStatement();
			String query = "SELECT Member_id FROM EventParticipants WHERE Event_id='"
					+ eventID + "'";

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				participants.add(rs.getInt(1));
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
		
		return participants;

	}

	public ArrayList<Event> getEventList() {
		ArrayList<Event> events = new ArrayList<Event>();

		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs = null;
		Statement stmt = dbc.getStatement();
		String query = "SELECT Event_id, Course_id, name, costs, date, time, location FROM Event";

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Event newEvent = new Event();
				newEvent.setEventID(rs.getInt(1));
				newEvent.setCourseID(rs.getInt(2));
				newEvent.setName(rs.getString(3));
				newEvent.setCosts(rs.getString(4));
				System.out.println(rs.getDate(5));
				newEvent.setDate(rs.getDate(5));
				newEvent.setTime(rs.getTime(6));
				newEvent.setLocation(rs.getString(7));
				newEvent.setExaminers(getExaminersForEvent(newEvent.getEventID()));
				events.add(newEvent);
			}
			rs.close();
			dbc.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return events;
	}
	
	public ArrayList<String> getExaminersForEvent(int eventID) {
		ArrayList<String> examiners = new ArrayList<String>();
		
		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs = null;
		Statement stmt = dbc.getStatement();
		String query = "SELECT examiner FROM Event_has_examiners WHERE Event_ID='" + eventID + "'";
		
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				examiners.add(rs.getString(1));
			}
			rs.close();
			dbc.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return examiners;
	}
	
	public void setExaminersForEvent(int eventID, ArrayList<String> examiners) {
		DataBankerConnection dbc = new DataBankerConnection();
		for (int i = 0; i < examiners.size(); i++) {
			try {
				PreparedStatement stmt = dbc.getConnection().prepareStatement(
								"INSERT INTO Event_has_examiners(Event_id, examiner) VALUES(?,?)");
				stmt.setInt(1, eventID);
				stmt.setString(2, examiners.get(i));
	
				stmt.executeUpdate();
	
				dbc.close();
				stmt.close();
	
	
			} catch (SQLException e) {
				System.out.println(e);
			}
		}
	}
	
	public void deleteExaminersFromEvent(int eventID) {
		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "DELETE FROM Event_has_examiners WHERE Event_id='" + eventID + "'";

		try {
			stmt.executeUpdate(query);
			dbc.close();
			stmt.close();
			dbc.closeStatement();

		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public void updateExaminersForEvent(int eventID, ArrayList<String> examiners) {
		deleteExaminersFromEvent(eventID);
		setExaminersForEvent(eventID, examiners);
	}
}
