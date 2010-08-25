package de.sportschulApp.server.databanker;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.sportschulApp.shared.Member;

public class DataBankerMember implements DataBankerMemberInterface {
	/**
	 * Legt einen neues Mitglied an.
	 * 
	 * @param ein
	 *            Objekt des Typs Member
	 * 
	 * @return "member created" wenn ein neues Mitglied angelegt wurde, "error"
	 *         wenn das anlegen nicht funktioniert hat und
	 *         "barcode_id already used", wenn Barcodenummer schon vorhanden
	 *         ist.
	 */
	public String createMember(Member member) {
		DataBankerConnection dbc = new DataBankerConnection();
		try {
			ResultSet rs = null;
			Statement stmt2 = dbc.getStatement();
			String query = "SELECT Member_id FROM Member WHERE barcode_id='"
					+ member.getBarcodeID() + "'";

			rs = stmt2.executeQuery(query);
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					return "barcode_id already used";
				}
			}
			rs.close();
			stmt2.close();

			PreparedStatement stmt = dbc

					.getConnection()
					.prepareStatement(
							"INSERT INTO Member(barcode_id, forename, surname, zipcode, city, street, phone, mobilephone, fax, email, homepage, birth, picture, diseases, beltsize, note, trainingunits, course_01, course_02, course_03, course_04, course_05, course_06, course_07, course_08, course_09, course_10, graduation_01, graduation_02, graduation_03, graduation_04, graduation_05, graduation_06, graduation_07, graduation_08, graduation_09, graduation_10) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, member.getBarcodeID());
			stmt.setString(2, member.getForename());
			stmt.setString(3, member.getSurname());
			stmt.setInt(4, member.getZipcode());
			stmt.setString(5, member.getCity());
			stmt.setString(6, member.getStreet());
			stmt.setString(7, member.getPhone());
			stmt.setString(8, member.getMobilephone());
			stmt.setString(9, member.getFax());
			stmt.setString(10, member.getEmail());
			stmt.setString(11, member.getHomepage());
			stmt.setDate(12, member.getBirth());
			stmt.setString(13, member.getPicture());
			stmt.setString(14, member.getDiseases());
			stmt.setString(15, member.getBeltsize());
			stmt.setString(16, member.getNote());
			stmt.setInt(17, member.getTrainingunits());

			// Hinzufügen der ArrayList Courses
			int size = member.getCourses().size();
			try {
				for (int i = 0; i < size; i++) {
					stmt.setInt(i + 18, member.getCourses().get(i));
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println(e);
				System.out.println("courses out of bounds");
			}
			// Auffüllen mit Nullen, wenn Courses<10
			if (size < 10) {
				for (int i = 18 + size; i < 28; i++) {
					stmt.setInt(i, 0);
				}
			}

			// Hinzufügen der ArrayList Graduations
			size = member.getGraduations().size();
			try {
				for (int i = 0; i < size; i++) {
					stmt.setInt(i + 28, member.getGraduations().get(i));
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println(e);
				System.out.println("graduations out of bounds");
			}
			// Auffüllen mit Nullen, wenn Graduations<10
			if (size < 10) {
				for (int i = 28 + size; i < 38; i++) {
					stmt.setInt(i, 0);
				}
			}
			stmt.executeUpdate();

			dbc.close();
			stmt.close();

			return "member created";

		} catch (SQLException e) {
			System.out.println(e);
			return "error";
		}
	}

	/**
	 * Löscht einen Mitgliedereintrag
	 * 
	 * @param memberID
	 *            eines Mitgliedes
	 * 
	 * @return true bei erfolg, false bei scheitern
	 */
	public boolean deleteMember(int memberID) {

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "DELETE FROM Member WHERE Member_id='" + memberID + "'";

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
	 * Ändert einen Mitgliedereintrag
	 * 
	 * @param ein
	 *            Objekt des Typs Member
	 * 
	 * @return "Mitglied gespeichert" wenn Mitglied geändert wurde,
	 *         "Fehler beim Speichern" wenn das ändern nicht funktioniert hat
	 *         und "Barcodenummer schon vorhanden", wenn Barcodenummer schon
	 *         vorhanden ist.
	 */
	public String updateMember(Member member) {
		// TODO Update ändern, damit primery key zurück gesetzt wird

		DataBankerConnection dbc = new DataBankerConnection();
		try {
			ResultSet rs = null;
			Statement stmt2 = dbc.getStatement();
			String query = "SELECT Member_id FROM Member WHERE barcode_id='"
					+ member.getBarcodeID() + "'";

			rs = stmt2.executeQuery(query);
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					return "Barcodenummer schon vorhanden";
				}
			}
			rs.close();
			stmt2.close();
			int id = member.getMemberID();
			deleteMember(id);

			PreparedStatement stmt = dbc

					.getConnection()
					.prepareStatement(
							"INSERT INTO Member(Member_id, barcode_id, forename, surname, zipcode, city, street, phone, mobilephone, fax, email, homepage, birth, picture, diseases, beltsize, note, trainingunits, course_01, course_02, course_03, course_04, course_05, course_06, course_07, course_08, course_09, course_10, graduation_01, graduation_02, graduation_03, graduation_04, graduation_05, graduation_06, graduation_07, graduation_08, graduation_09, graduation_10) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, id);
			stmt.setInt(2, member.getBarcodeID());
			stmt.setString(3, member.getForename());
			stmt.setString(4, member.getSurname());
			stmt.setInt(5, member.getZipcode());
			stmt.setString(6, member.getCity());
			stmt.setString(7, member.getStreet());
			stmt.setString(8, member.getPhone());
			stmt.setString(9, member.getMobilephone());
			stmt.setString(10, member.getFax());
			stmt.setString(11, member.getEmail());
			stmt.setString(12, member.getHomepage());
			stmt.setDate(13, member.getBirth());
			stmt.setString(14, member.getPicture());
			stmt.setString(15, member.getDiseases());
			stmt.setString(16, member.getBeltsize());
			stmt.setString(17, member.getNote());
			stmt.setInt(18, member.getTrainingunits());

			// Hinzufügen der ArrayList Courses
			int size = member.getCourses().size();
			try {
				for (int i = 0; i < size; i++) {
					stmt.setInt(i + 19, member.getCourses().get(i));
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println(e);
				System.out.println("courses out of bounds");
			}
			// Auffüllen mit Nullen, wenn Courses<10
			if (size < 10) {
				for (int i = 19 + size; i < 29; i++) {
					stmt.setInt(i, 0);
				}
			}

			// Hinzufügen der ArrayList Graduations
			size = member.getGraduations().size();
			try {
				for (int i = 0; i < size; i++) {
					stmt.setInt(i + 29, member.getGraduations().get(i));
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println(e);
				System.out.println("graduations out of bounds");
			}
			// Auffüllen mit Nullen, wenn Graduations<10
			if (size < 10) {
				for (int i = 29 + size; i < 39; i++) {
					stmt.setInt(i, 0);
				}
			}
			stmt.executeUpdate();

			dbc.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e);
			return "Fehler beim Speichern";
		}
		return "Mitglied gespeichert";

	}

	/**
	 * Liefert einen Mitgliedereintrag
	 * 
	 * @param barcodeID
	 *            eines Mitglieds
	 * 
	 * @return Objekt des typs Member
	 */

	public Member getMember(int barcodeID) {

		Member member = new Member();
		ResultSet rs = null;

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();
		String query = "SELECT * FROM Member WHERE barcode_id='" + barcodeID
				+ "'";
		try {
			rs = stmt.executeQuery(query);
			if (rs.wasNull()) {
			}
			rs.next();
			member.setMemberID(rs.getInt("Member_id"));
			member.setBarcodeID(rs.getInt("barcode_id"));
			member.setForename(rs.getString("forename"));
			member.setSurname(rs.getString("surname"));
			member.setZipcode(rs.getInt("zipcode"));
			member.setCity(rs.getString("city"));
			member.setStreet(rs.getString("street"));
			member.setPhone(rs.getString("phone"));
			member.setMobilephone(rs.getString("mobilephone"));
			member.setFax(rs.getString("fax"));
			member.setEmail(rs.getString("email"));
			member.setHomepage(rs.getString("homepage"));
			member.setBirth(rs.getDate("birth"));
			member.setPicture(rs.getString("picture"));
			member.setDiseases(rs.getString("diseases"));
			member.setBeltsize(rs.getString("beltsize"));
			member.setNote(rs.getString("note"));

			ArrayList<Integer> courses = new ArrayList<Integer>();
			courses.add(rs.getInt("course_01"));
			courses.add(rs.getInt("course_02"));
			courses.add(rs.getInt("course_03"));
			courses.add(rs.getInt("course_04"));
			courses.add(rs.getInt("course_05"));
			courses.add(rs.getInt("course_06"));
			courses.add(rs.getInt("course_07"));
			courses.add(rs.getInt("course_08"));
			courses.add(rs.getInt("course_09"));
			courses.add(rs.getInt("course_10"));

			ArrayList<Integer> graduation = new ArrayList<Integer>();
			graduation.add(rs.getInt("graduation_01"));
			graduation.add(rs.getInt("graduation_02"));
			graduation.add(rs.getInt("graduation_03"));
			graduation.add(rs.getInt("graduation_04"));
			graduation.add(rs.getInt("graduation_05"));
			graduation.add(rs.getInt("graduation_06"));
			graduation.add(rs.getInt("graduation_07"));
			graduation.add(rs.getInt("graduation_08"));
			graduation.add(rs.getInt("graduation_09"));
			graduation.add(rs.getInt("graduation_10"));

			rs.close();
			dbc.close();
			stmt.close();
			dbc.closeStatement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return member;
	}

	/**
	 * Liefert einen Mitgliedereintrag
	 * 
	 * @param barcodeID
	 *            eines Mitglieds
	 * 
	 * @return Objekt des typs Member
	 */

	public ArrayList<Member> getMemberList() {

		ArrayList<Member> memberList = new ArrayList<Member>();

		ResultSet rs = null;

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();
		String query = "SELECT * FROM Member";
		try {
			rs = stmt.executeQuery(query);
			if (rs.wasNull()) {
			}
			while (rs.next()) {
				Member member = new Member();
				member.setMemberID(rs.getInt("Member_id"));
				member.setBarcodeID(rs.getInt("barcode_id"));
				member.setForename(rs.getString("forename"));
				member.setSurname(rs.getString("surname"));
				member.setZipcode(rs.getInt("zipcode"));
				member.setCity(rs.getString("city"));
				member.setStreet(rs.getString("street"));
				member.setPhone(rs.getString("phone"));
				member.setMobilephone(rs.getString("mobilephone"));
				member.setFax(rs.getString("fax"));
				member.setEmail(rs.getString("email"));
				member.setHomepage(rs.getString("homepage"));
				member.setBirth(rs.getDate("birth"));
				member.setPicture(rs.getString("picture"));
				member.setDiseases(rs.getString("diseases"));
				member.setBeltsize(rs.getString("beltsize"));
				member.setNote(rs.getString("note"));

				ArrayList<Integer> courses = new ArrayList<Integer>();
				courses.add(rs.getInt("course_01"));
				courses.add(rs.getInt("course_02"));
				courses.add(rs.getInt("course_03"));
				courses.add(rs.getInt("course_04"));
				courses.add(rs.getInt("course_05"));
				courses.add(rs.getInt("course_06"));
				courses.add(rs.getInt("course_07"));
				courses.add(rs.getInt("course_08"));
				courses.add(rs.getInt("course_09"));
				courses.add(rs.getInt("course_10"));

				ArrayList<Integer> graduation = new ArrayList<Integer>();
				graduation.add(rs.getInt("graduation_01"));
				graduation.add(rs.getInt("graduation_02"));
				graduation.add(rs.getInt("graduation_03"));
				graduation.add(rs.getInt("graduation_04"));
				graduation.add(rs.getInt("graduation_05"));
				graduation.add(rs.getInt("graduation_06"));
				graduation.add(rs.getInt("graduation_07"));
				graduation.add(rs.getInt("graduation_08"));
				graduation.add(rs.getInt("graduation_09"));
				graduation.add(rs.getInt("graduation_10"));

				memberList.add(member);
			}
			rs.close();
			dbc.close();
			stmt.close();
			dbc.closeStatement();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return memberList;
	}

	/**
	 * Liefert einen Mitgliedereintrag
	 * 
	 * @param memberID
	 *            eines Mitglieds
	 * 
	 * @return Objekt des typs Member
	 */
	public Member getMemberWithMemberID(int memberID) {

		Member member = null;
		ResultSet rs = null;
		DataBankerConnection dbc = new DataBankerConnection();

		Statement stmt = dbc.getStatement();
		String query = "SELECT barcode_id FROM Member WHERE Member_id='"
				+ memberID + "'";

		try {
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				member = getMember(rs.getInt(1));
			}
			rs.close();
			dbc.close();
			stmt.close();
			dbc.closeStatement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return member;
	}

	/**
	 * speichert die Trainingsanwesenheit eines Mitglieds
	 * 
	 * @param memberId
	 *            eines Mitglieds, int day, int month, int year
	 * 
	 * @return true bei erfolg, false bei fehler
	 */
	public boolean setTrainingsPresence(int memberID, int day, int month,
			int year) {

		DataBankerConnection dbc = new DataBankerConnection();
		try {

			ResultSet rs = null;
			Statement stmt = dbc.getStatement();

			String query = "SELECT COUNT(*), member_id, day, month, year FROM TrainingPresence WHERE day='"
					+ day
					+ "' AND month ='"
					+ month
					+ "' AND year = '"
					+ year
					+ "' AND member_id = '" + memberID + "'";

			rs = stmt.executeQuery(query);
			rs.next();

			if (rs.getInt(1) == 0) {
				// Datum für dieses Mitglied noch nicht eingetragen
				Statement stmt2 = dbc.getStatement();

				String query2 = "INSERT INTO TrainingPresence(member_id, day,month,year) VALUES ('"
						+ memberID
						+ "', '"
						+ day
						+ "', '"
						+ month
						+ "','"
						+ year + "')";
				stmt2.executeUpdate(query2);

				stmt.close();
				stmt2.close();
				rs.close();
				dbc.closeStatement();
				dbc.close();

				return true;

			} else {

				// Datum für dieses Mitglied schon eingetragen
				stmt.close();
				rs.close();
				dbc.closeStatement();
				dbc.close();
				return false;

			}

		} catch (SQLException e) {
			System.out.println(e);
		}
		return false;

	}

	/**
	 * liefert die Trainingsteilnahme eines Mitglieds für einen speziellen Monat
	 * und Jahr in einem 2 Dimensionalen int Array
	 * 
	 * @param memberId
	 *            eines Mitglieds, gewünschter Monat und Jahr
	 * 
	 * @return ArrayList<String> presence
	 */
	public ArrayList<int[]> getTrainingsPresence(int memberID, int month,
			int year) {

		ArrayList<int[]> presence = new ArrayList<int[]>();

		DataBankerConnection dbc = new DataBankerConnection();
		try {
			ResultSet rs = null;
			Statement stmt = dbc.getStatement();
			// String query =
			// "SELECT COUNT(*), day, month, year FROM test WHERE Member_id='"
			// + memberID + "'";

			String query = "SELECT member_id, day, month, year FROM TrainingPresence WHERE month ='"
					+ month
					+ "' AND member_id = '"
					+ memberID
					+ "' AND year = '" + year + "'";

			rs = stmt.executeQuery(query);
			while (rs.next()) {

				int[] test = new int[3];
				test[0] = rs.getInt(2);
				test[1] = rs.getInt(3);
				test[2] = rs.getInt(4);
				presence.add(test);

			}

		} catch (SQLException e) {
			System.out.println(e);
		}
		if (presence.isEmpty()) {
			System.out.println("Presence ist leer");
			return null;
		}
		return presence;

	}

	/**
	 * liefert die Anzahl der Trainingsteilnahmen eines Mitglieds für einen
	 * speziellen Monat
	 * 
	 * @param memberId
	 *            eines Mitglieds, gewünschter Monat und Jahr
	 * 
	 * @return int
	 */
	public int getTrainingsPresenceInt(int memberID, int month, int year) {
		DataBankerConnection dbc = new DataBankerConnection();
		try {
			ResultSet rs = null;
			Statement stmt = dbc.getStatement();

			String query = "SELECT count(*) FROM TrainingPresence WHERE month ='"
					+ month
					+ "' AND member_id = '"
					+ memberID
					+ "' AND year = '" + year + "'";

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				return rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println(e);
			return 0;
		}
		return 0;
	}

	/**
	 * löscht die Trainingsanwesenheit an einen bestimmten Datum
	 * 
	 * @param memberId
	 *            eines Mitglieds, gewünschter Monat
	 * 
	 * @return true bei Erfolg, false bei Scheitern oder leerem Monat
	 */
	public boolean deleteTrainingsPresence(int memberID, int day, int month,
			int year) {
		DataBankerConnection dbc = new DataBankerConnection();

		String delete = "DELETE FROM TrainingPresence WHERE Member_id='"
				+ memberID + "' AND day = '" + day + "' AND month = '" + month
				+ "' AND year = '" + year + "'";

		Statement stmt = dbc.getStatement();
		try {
			stmt.executeUpdate(delete);
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return true;
	}
}
