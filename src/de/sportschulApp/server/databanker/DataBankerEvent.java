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
import de.sportschulApp.shared.EventParticipant;
import de.sportschulApp.shared.Member;

public class DataBankerEvent implements DataBankerEventInterface {

	/**
	 * Legt einen neuen Event an.
	 * 
	 * @param ein
	 *            Objekt des Typs Event
	 * @return true wenn anlegen funktioniert hat, false wenn ein Fehler
	 *         aufgetreten ist
	 */
	public Integer createEvent(Event event) {
		DataBankerConnection dbc = new DataBankerConnection();
		try {

			PreparedStatement stmt = dbc

					.getConnection()
					.prepareStatement(
							"INSERT INTO Event(name, type, costs, location, date, time) VALUES(?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1, event.getName());
			stmt.setString(2, event.getType());
			stmt.setString(3, event.getCosts());
			stmt.setString(4, event.getLocation());
			stmt.setString(5, event.getDate());
			stmt.setString(6, event.getTime());
			
			
			stmt.executeUpdate();
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			int eventID = 0;
			while(generatedKeys.next()) {
				eventID = generatedKeys.getInt(1);
				setExaminersForEvent(eventID, event.getExaminers());
				if (event.getEventID() > 0) {
					updateParticipantsID(event.getEventID(), eventID);
				}
			}
			
			dbc.close();
			stmt.close();

			return eventID;

		} catch (SQLException e) {
			System.out.println(e);
			return 0;
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
	public Integer updateEvent(Event event) {
		if (deleteEvent(event.getEventID())) {
			return createEvent(event);
		}
		return 0;
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
				event.setType(rs.getString("type"));
				event.setName(rs.getString("name"));
				event.setCosts(rs.getString("costs"));
				event.setDate(rs.getString("date"));
				event.setTime(rs.getString("time"));
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

	public ArrayList<EventParticipant> getEventParticipants(int eventID, ArrayList<Member> members) {
		ArrayList<EventParticipant> participants = new ArrayList<EventParticipant>();
		for (int i = 0; i < members.size(); i++) {
			EventParticipant participant = new EventParticipant();
			participant.setEventID(eventID);
			participant.setBarcodeID(members.get(i).getBarcodeID() + "");
			participant.setForename(members.get(i).getForename());
			participant.setSurname(members.get(i).getSurname());
			participant.setPassed("Nein");
			participant.setPaid("Nein");
			participant.setNote("");
			participant.setParticipant("Nein");
			participant.setPicUrl(members.get(i).getPicture());
			participants.add(participant);
		}
		DataBankerConnection dbc = new DataBankerConnection();
		
		try {

			ResultSet rs = null;
			Statement stmt = dbc.getStatement();
			String query = "SELECT Barcode_id, passed, paid, note FROM Event_has_participants WHERE Event_id='"
					+ eventID + "'";

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				for (int i = 0; i < participants.size(); i++) {
					if (rs.getString("Barcode_id").equals(participants.get(i).getBarcodeID())) {
						participants.get(i).setPassed(rs.getString("passed"));
						participants.get(i).setPaid(rs.getString("paid"));
						participants.get(i).setNote(rs.getString("note"));
						participants.get(i).setParticipant("Ja");
					}
				}
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
		
		return participants;

	}
	
	public void updateParticipantsID(int oldEventID, int newEventID) {
		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();
		String query = "UPDATE Event_has_participants SET Event_id=" + newEventID + " WHERE Event_id=" + oldEventID;
		
		try {
			stmt.executeUpdate(query);
			dbc.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}

	public ArrayList<Event> getEventList() {
		ArrayList<Event> events = new ArrayList<Event>();

		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs = null;
		Statement stmt = dbc.getStatement();
		String query = "SELECT * FROM Event ORDER BY name";

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Event newEvent = new Event();
				newEvent.setEventID(rs.getInt(1));
				newEvent.setName(rs.getString(2));
				newEvent.setType(rs.getString(3));
				newEvent.setCosts(rs.getString(4));
				newEvent.setLocation(rs.getString(5));
				newEvent.setDate(rs.getString(6));
				newEvent.setTime(rs.getString(7));
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
	
	public Boolean deleteExaminersFromEvent(int eventID) {
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
			return false;
		}
		return true;
	}
	
	public void updateExaminersForEvent(int eventID, ArrayList<String> examiners) {
		if (deleteExaminersFromEvent(eventID)) {
			setExaminersForEvent(eventID, examiners);
		}
	}
	
	public Boolean deleteParticipantsForEvent(int eventID) {
		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "DELETE FROM Event_has_participants WHERE Event_id='" + eventID + "'";

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

	public void setParticipantsForEvent(int eventID,
			ArrayList<EventParticipant> participants) {
		if (deleteParticipantsForEvent(eventID)) {
			DataBankerConnection dbc = new DataBankerConnection();
			for (int i = 0; i < participants.size(); i++) {
				try {
					PreparedStatement stmt = dbc.getConnection().prepareStatement(
									"INSERT INTO Event_has_participants(Event_id, Barcode_id, passed, paid, note) VALUES(?,?,?,?,?)");
					stmt.setInt(1, eventID);
					stmt.setString(2, participants.get(i).getBarcodeID());
					stmt.setString(3, participants.get(i).getPassed());
					stmt.setString(4, participants.get(i).getPaid());
					stmt.setString(5, participants.get(i).getNote());
		
					stmt.executeUpdate();
		
					dbc.close();
					stmt.close();
		
		
				} catch (SQLException e) {
					System.out.println(e);
				}
			}
			
		}
	}
}



