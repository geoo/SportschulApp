package de.sportschulApp.server.databanker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
							"INSERT INTO Event(Course_id, name, costs, date, time, location, examiner_1, examiner_2, examiner_4, examiner_5) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, event.getCourseID());
			stmt.setString(2, event.getName());
			stmt.setString(3, event.getCosts());
			stmt.setDate(4, event.getDate());
			stmt.setTime(5, event.getTime());
			stmt.setString(6, event.getLocation());
			stmt.setString(7, event.getExaminer1());
			stmt.setString(8, event.getExaminer2());
			stmt.setString(9, event.getExaminer3());
			stmt.setString(10, event.getExaminer4());
			stmt.setString(11, event.getExaminer5());

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
	 * Lšscht einen Event
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
	 * €ndert einen Event
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
				+ event.getLocation() + "', examiner_1 ='"
				+ event.getExaminer1() + "', examiner_2 ='"
				+ event.getExaminer2() + "', examiner_3 ='"
				+ event.getExaminer3() + "', examiner_04 ='"
				+ event.getExaminer4() + "', examiner_5 ='"
				+ event.getExaminer5() + "' where Event_id = '"
				+ event.getEventID() + "'";

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
	 * FŸgt ein Mitglied zu einem Event hinzu
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
				event.setExaminer1(rs.getString("examiner_1"));
				event.setExaminer2(rs.getString("examiner_2"));
				event.setExaminer3(rs.getString("examiner_3"));
				event.setExaminer4(rs.getString("examiner_4"));
				event.setExaminer5(rs.getString("examiner_5"));

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

}
