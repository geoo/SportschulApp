package de.sportschulApp.server.databanker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.sportschulApp.shared.Event;
import de.sportschulApp.shared.EventParticipant;
import de.sportschulApp.shared.Member;

public class DataBankerEvent implements DataBankerEventInterface {

	/**
	 * Legt einen neuen Event an.
	 */
	public Integer createEvent(Event event) {
		DataBankerConnection dbc = new DataBankerConnection();
		try {
			if (event.getEventID() > 0) {
				PreparedStatement stmt = dbc.getConnection().prepareStatement("INSERT INTO Event(Event_id, name, type, costs, location, date, startTime, endTime, happened, trainer, courseID) VALUES(?,?,?,?,?,?,?,?,?,?,?)");
				
				stmt.setInt(1, event.getEventID());
				stmt.setString(2, event.getName());
				stmt.setString(3, event.getType());
				stmt.setString(4, event.getCosts());
				stmt.setString(5, event.getLocation());
				stmt.setString(6, event.getDate());
				stmt.setString(7, event.getStartTime());
				stmt.setString(8, event.getEndTime());
				stmt.setString(9, "Nein");
				stmt.setString(10, "-");
				stmt.setInt(11, event.getCourseID());
				
				
				stmt.executeUpdate();
				setExaminersForEvent(event.getEventID(), event.getExaminers());
				stmt.close();
			} else {
				PreparedStatement stmt = dbc.getConnection().prepareStatement("INSERT INTO Event(name, type, costs, location, date, startTime, endTime, happened, trainer, courseID) VALUES(?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				
				stmt.setString(1, event.getName());
				stmt.setString(2, event.getType());
				stmt.setString(3, event.getCosts());
				stmt.setString(4, event.getLocation());
				stmt.setString(5, event.getDate());
				stmt.setString(6, event.getStartTime());
				stmt.setString(7, event.getEndTime());
				stmt.setString(8, "Nein");
				stmt.setString(9, "-");
				stmt.setInt(10, event.getCourseID());
				
				stmt.executeUpdate();
				ResultSet generatedKeys = stmt.getGeneratedKeys();
				
				while(generatedKeys.next()) {
					setExaminersForEvent(generatedKeys.getInt(1), event.getExaminers());
					event.setEventID(generatedKeys.getInt(1));
				}
				stmt.close();
			}

			dbc.close();

			return event.getEventID();

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
			deleteCoursesFromEvent(eventID);
			dbc.close();
			stmt.close();
			dbc.closeStatement();

		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return true;

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
	
	public Boolean deleteCoursesFromEvent(int eventID) {
		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "DELETE FROM Event_has_courses WHERE Event_id='" + eventID + "'";

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
				event.setStartTime(rs.getString("startTime"));
				event.setEndTime(rs.getString("endTime"));
				event.setLocation(rs.getString("location"));
				event.setHappened(rs.getString("happened"));
				event.setUser(rs.getString("trainer"));
				event.setExaminers(getExaminersForEvent(event.getEventID()));
				event.setCourseID(rs.getInt("courseID"));

			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}

		return event;

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
				newEvent.setStartTime(rs.getString(7));
				newEvent.setEndTime(rs.getString(8));
				newEvent.setHappened(rs.getString(9));
				newEvent.setUser(rs.getString(10));
				newEvent.setExaminers(getExaminersForEvent(newEvent.getEventID()));
				newEvent.setCourseID(rs.getInt(11));
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

	public ArrayList<EventParticipant> getEventParticipants(int eventID, ArrayList<Member> members) {
		ArrayList<EventParticipant> participants = new ArrayList<EventParticipant>();
		for (int i = 0; i < members.size(); i++) {
			EventParticipant participant = new EventParticipant();
			participant.setEventID(eventID);
			participant.setBarcodeID(members.get(i).getBarcodeID() + "");
			participant.setForename(members.get(i).getForename());
			participant.setSurname(members.get(i).getSurname());
			participant.setParticipant("Ja");
			participant.setPassed("-");
			participant.setPaid("Nein");
			participant.setNote("");
			participant.setParticipant("Nein");
			participant.setAttended("Nein");
			participant.setPicUrl(members.get(i).getPicture());
			participant.setDiseases(members.get(i).getDiseases());
			participants.add(participant);
		}
		DataBankerConnection dbc = new DataBankerConnection();

		try {

			ResultSet rs = null;
			Statement stmt = dbc.getStatement();
			String query = "SELECT Barcode_id, participant, passed, paid, note, attended FROM Event_has_participants WHERE Event_id='"
				+ eventID + "'";

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				for (int i = 0; i < participants.size(); i++) {
					if (rs.getString("Barcode_id").equals(participants.get(i).getBarcodeID())) {
						participants.get(i).setPassed(rs.getString("passed"));
						participants.get(i).setPaid(rs.getString("paid"));
						participants.get(i).setNote(rs.getString("note"));
						participants.get(i).setParticipant(rs.getString("participant"));
						participants.get(i).setAttended(rs.getString("attended"));
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
	
	public ArrayList<Integer> getEventCourses(int eventID) {
		ArrayList<Integer> courses = new ArrayList<Integer>();

		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs = null;
		Statement stmt = dbc.getStatement();
		String query = "SELECT course_id FROM Event_has_courses WHERE Event_ID='" + eventID + "'";

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				courses.add(rs.getInt(1));
			}
			rs.close();
			dbc.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return courses;
	}
	
	public void setEventCourses(int eventID, ArrayList<Integer> courses) {
		DataBankerConnection dbc = new DataBankerConnection();
		for (int i = 0; i < courses.size(); i++) {
			try {
				PreparedStatement stmt = dbc.getConnection().prepareStatement(
				"INSERT INTO Event_has_courses(Event_id, course_id) VALUES(?,?)");
				stmt.setInt(1, eventID);
				stmt.setInt(2, courses.get(i));

				stmt.executeUpdate();

				dbc.close();
				stmt.close();


			} catch (SQLException e) {
				System.out.println(e);
			}
		}
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

	public void setParticipantsForEvent(int eventID, ArrayList<EventParticipant> participants) {
		if (deleteParticipantsForEvent(eventID)) {
			DataBankerConnection dbc = new DataBankerConnection();
			for (int i = 0; i < participants.size(); i++) {
				try {
					PreparedStatement stmt = dbc.getConnection().prepareStatement(
					"INSERT INTO Event_has_participants(Event_id, Barcode_id, participant, passed, paid, note, attended) VALUES(?,?,?,?,?,?,?)");
					stmt.setInt(1, eventID);
					stmt.setString(2, participants.get(i).getBarcodeID());
					stmt.setString(3, participants.get(i).getParticipant());
					stmt.setString(4, participants.get(i).getPassed());
					stmt.setString(5, participants.get(i).getPaid());
					stmt.setString(6, participants.get(i).getNote());
					stmt.setString(7, participants.get(i).getAttended());
					
					stmt.executeUpdate();

					dbc.close();
					stmt.close();


				} catch (SQLException e) {
					System.out.println(e);
				}
			}

		}
	}

	public void startEvent(int eventID, String user) {
		DataBankerConnection dbc = new DataBankerConnection();
		
		try {
			PreparedStatement stmt = dbc.getConnection().prepareStatement("UPDATE Event SET happened='Ja', trainer='"+ user + "' WHERE Event_id=" + eventID);

			stmt.executeUpdate();

			dbc.close();
			stmt.close();


		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public void abortEvent(int eventID) {
		DataBankerConnection dbc = new DataBankerConnection();
		
		try {
			PreparedStatement stmt = dbc.getConnection().prepareStatement("UPDATE Event SET happened='Pause' WHERE Event_id=" + eventID);

			stmt.executeUpdate();

			dbc.close();
			stmt.close();


		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public void endEvent(int eventID) {
		DataBankerConnection dbc = new DataBankerConnection();
		
		try {
			PreparedStatement stmt = dbc.getConnection().prepareStatement("UPDATE Event SET happened='Beendet' WHERE Event_id=" + eventID);

			stmt.executeUpdate();

			dbc.close();
			stmt.close();


		} catch (SQLException e) {
			System.out.println(e);
		}
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

	public void updateExaminersForEvent(int eventID, ArrayList<String> examiners) {
		if (deleteExaminersFromEvent(eventID)) {
			setExaminersForEvent(eventID, examiners);
		}
	}

	public void setPassedValues(Event event, ArrayList<EventParticipant> participants) {
		DataBankerConnection dbc = new DataBankerConnection();
			
			try {
				for (int i = 0; i < participants.size(); i++) {
					PreparedStatement stmt = dbc.getConnection().prepareStatement("UPDATE Event_has_participants SET passed='" + participants.get(i).getPassed() + "' WHERE Event_id=" + event.getEventID() + " AND Barcode_id=" + participants.get(i).getBarcodeID());
					stmt.executeUpdate();	
					stmt.close();
				}
				dbc.close();
			} catch (SQLException e) {
				System.out.println(e);
			}
		updateGrades(event, participants);
	}
	
	public void updateGrades(Event event, ArrayList<EventParticipant> participants) {
		DataBankerConnection dbc = new DataBankerConnection();
		
		try {
			for (int i = 0; i < participants.size(); i++) {
				if(participants.get(i).getPassed().equals("Ja")) {
					PreparedStatement stmt = dbc.getConnection().prepareStatement("UPDATE Member_has_courses SET graduation=(graduation+1) WHERE barcode_id=" + participants.get(i).getBarcodeID() + " AND course_id=" + event.getCourseID());
					stmt.executeUpdate();	
					stmt.close();
				}
			}
			dbc.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}



