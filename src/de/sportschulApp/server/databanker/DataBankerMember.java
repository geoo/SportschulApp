package de.sportschulApp.server.databanker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import sun.security.action.GetBooleanAction;

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
							"INSERT INTO Member(barcode_id, forename, surname, zipcode, city, street, phone, mobilephone, fax, email, homepage, birthDay, birthMonth, birthYear, picture, diseases, beltsize, note, trainingunits, accountForename, accountSurname, accountNumber, bankName, bankNumber) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
			stmt.setString(12, member.getBirthDay());
			stmt.setString(13, member.getBirthMonth());
			stmt.setString(14, member.getBirthYear());
			stmt.setString(15, member.getPicture());
			stmt.setString(16, member.getDiseases());
			stmt.setString(17, member.getBeltsize());
			stmt.setString(18, member.getNote());
			stmt.setInt(19, member.getTrainingunits());
			stmt.setString(20, member.getAccountForename());
			stmt.setString(21, member.getAccountSurname());
			stmt.setString(22, member.getAccountNumber());
			stmt.setString(23, member.getBankName());
			stmt.setString(24, member.getBankNumber());

			stmt.executeUpdate();
			stmt.close();

			PreparedStatement stmt3 = null;
			try {
				for (int i = 0; i < member.getCourses().size(); i++) {
					stmt3 = dbc
							.getConnection()
							.prepareStatement(
									"INSERT INTO Member_has_courses(barcode_id, course_id, graduation, tariff) VALUES(?,?,?,?)");
					stmt3.setInt(1, member.getBarcodeID());
					stmt3.setInt(2, member.getCourses().get(i));
					stmt3.setInt(3, member.getGraduations().get(i));
					stmt3.setFloat(4, member.getTariffs().get(i));
					stmt3.executeUpdate();
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println(e);
				System.out.println("courses out of bounds");
			}
			stmt3.close();

			return "member created";

		} catch (SQLException e) {
			System.out.println(e);
			return "error";
		}
	}

	/**
	 * L�scht einen Mitgliedereintrag
	 * 
	 * @param memberID
	 *            eines Mitgliedes
	 * 
	 * @return true bei erfolg, false bei scheitern
	 */
	public boolean deleteMember(int memberID) {
		int barcodeId = 0;
		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "DELETE FROM Member WHERE Member_id='" + memberID + "'";

		ResultSet rs = null;
		Statement stmt2 = dbc.getStatement();
		String query2 = "SELECT barcode_id FROM Member WHERE Member_id='"
				+ memberID + "'";

		try {
			rs = stmt2.executeQuery(query2);

			while (rs.next()) {
				barcodeId = rs.getInt(1);
			}
			rs.close();
			stmt2.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Statement stmt3 = dbc.getStatement();
		String query3 = "DELETE FROM Member_has_courses WHERE barcode_id='"
				+ barcodeId + "'";

		try {
			stmt.executeUpdate(query);
			stmt3.executeUpdate(query3);
			dbc.close();
			stmt.close();
			stmt3.close();
			dbc.closeStatement();

		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return true;
	}

	/**
	 * l�scht die Trainingsanwesenheit an einen bestimmten Datum
	 * 
	 * @param barcodeId
	 *            eines Mitglieds, gew�nschter Monat
	 * 
	 * @return true bei Erfolg, false bei Scheitern oder leerem Monat
	 */
	public boolean deleteTrainingsPresence(int barcodeID, int day, int month,
			int year) {
		DataBankerConnection dbc = new DataBankerConnection();

		String delete = "DELETE FROM TrainingPresence WHERE barcode_id='"
				+ barcodeID + "' AND day = '" + day + "' AND month = '" + month
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
		ResultSet rs2 = null;

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();
		String query = "SELECT * FROM Member WHERE barcode_id='" + barcodeID
				+ "'";

		Statement stmt2 = dbc.getStatement();
		String query2 = "SELECT * FROM Member_has_courses WHERE barcode_id='"
				+ barcodeID + "'";

		try {
			rs = stmt.executeQuery(query);
			if (rs.wasNull()) {
			}
			while (rs.next()) {
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
				member.setBirthDay(rs.getString("birthDay"));
				member.setBirthMonth(rs.getString("birthMonth"));
				member.setBirthYear(rs.getString("birthYear"));
				member.setPicture(rs.getString("picture"));
				member.setDiseases(rs.getString("diseases"));
				member.setBeltsize(rs.getString("beltsize"));
				member.setNote(rs.getString("note"));
				member.setTrainingunits(rs.getInt("trainingunits"));
				member.setAccountForename(rs.getString("accountForename"));
				member.setAccountSurname(rs.getString("accountSurname"));
				member.setAccountNumber(rs.getString("accountNumber"));
				member.setBankName(rs.getString("bankName"));
				member.setBankNumber(rs.getString("bankNumber"));

			}

			ArrayList<Integer> courses = new ArrayList<Integer>();
			ArrayList<Integer> graduation = new ArrayList<Integer>();
			ArrayList<Float> tariffs = new ArrayList<Float>();

			rs2 = stmt2.executeQuery(query2);
			if (rs2.wasNull()) {
			}
			while (rs2.next()) {
				courses.add(rs2.getInt("course_id"));
				graduation.add(rs2.getInt("graduation"));
				tariffs.add(rs2.getFloat("tariff"));
			}
			member.setCourses(courses);
			member.setGraduations(graduation);
			member.setTariffs(tariffs);

			rs.close();
			rs2.close();
			dbc.close();
			stmt.close();
			stmt2.close();
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
				member.setBirthDay(rs.getString("birthDay"));
				member.setBirthMonth(rs.getString("birthMonth"));
				member.setBirthYear(rs.getString("birthYear"));
				member.setPicture(rs.getString("picture"));
				member.setDiseases(rs.getString("diseases"));
				member.setBeltsize(rs.getString("beltsize"));
				member.setNote(rs.getString("note"));
				member.setAccountForename(rs.getString("accountForename"));
				member.setAccountSurname(rs.getString("accountSurname"));
				member.setAccountNumber(rs.getString("accountNumber"));
				member.setBankName(rs.getString("bankName"));
				member.setBankNumber(rs.getString("bankNumber"));

				ArrayList<Integer> courses = new ArrayList<Integer>();
				ArrayList<Integer> graduation = new ArrayList<Integer>();
				ArrayList<Float> tariffs = new ArrayList<Float>();

				ResultSet rs2 = null;
				Statement stmt2 = dbc.getStatement();
				String query2 = "SELECT * FROM Member_has_courses WHERE barcode_id='"
						+ member.getBarcodeID() + "'";

				rs2 = stmt2.executeQuery(query2);
				if (rs2.wasNull()) {
				}
				while (rs2.next()) {
					courses.add(rs2.getInt("course_id"));
					graduation.add(rs2.getInt("graduation"));
					tariffs.add(rs2.getFloat("tariff"));
				}
				member.setCourses(courses);
				member.setGraduations(graduation);
				member.setTariffs(tariffs);

				rs2.close();
				stmt2.close();

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

	public String getNote(int barcodeID) {
		DataBankerConnection dbc = new DataBankerConnection();
		try {
			ResultSet rs = null;
			Statement stmt = dbc.getStatement();

			String query = "SELECT note FROM Member WHERE barcode_id ='"
					+ barcodeID + "'";

			rs = stmt.executeQuery(query);
			while (rs.next()) {
				return rs.getString(1);
			}

		} catch (SQLException e) {
			System.out.println(e);
			return null;
		}
		return null;
	}

	/**
	 * liefert die Trainingsteilnahme eines Mitglieds f�r einen speziellen Monat
	 * und Jahr in einem 2 Dimensionalen int Array
	 * 
	 * @param barcodeId
	 *            eines Mitglieds, gew�nschter Monat und Jahr
	 * 
	 * @return ArrayList<String> presence
	 */
	public ArrayList<int[]> getTrainingsPresence(int barcodeID, int month,
			int year) {

		ArrayList<int[]> presence = new ArrayList<int[]>();

		DataBankerConnection dbc = new DataBankerConnection();
		try {
			ResultSet rs = null;
			Statement stmt = dbc.getStatement();
			// String query =
			// "SELECT COUNT(*), day, month, year FROM test WHERE Member_id='"
			// + memberID + "'";

			String query = "SELECT barcode_id, day, month, year FROM TrainingPresence WHERE month ='"
					+ month
					+ "' AND barcode_id = '"
					+ barcodeID
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
	 * liefert die Anzahl der Trainingsteilnahmen eines Mitglieds f�r einen
	 * speziellen Monat
	 * 
	 * @param barcodeId
	 *            eines Mitglieds, gew�nschter Monat und Jahr
	 * 
	 * @return int
	 */
	public int getTrainingsPresenceInt(int barcodeID, int month, int year) {
		DataBankerConnection dbc = new DataBankerConnection();
		try {
			ResultSet rs = null;
			Statement stmt = dbc.getStatement();

			String query = "SELECT count(*) FROM TrainingPresence WHERE month ='"
					+ month
					+ "' AND barcode_id = '"
					+ barcodeID
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

	public ArrayList<Member> search(String searchQuery) {

		ArrayList<Member> memberList = new ArrayList<Member>();

		ResultSet rs = null;

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();
		String query = "SELECT * FROM Member m  where m.forename LIKE '"
				+ searchQuery + "%' or m.surname LIKE '" + searchQuery
				+ "%' or m.zipcode LIKE '" + searchQuery
				+ "%' or m.city LIKE '" + searchQuery + "%' or m.street LIKE '"
				+ searchQuery + "%' or m.phone LIKE '" + searchQuery
				+ "%' or m.mobilephone LIKE '" + searchQuery
				+ "%' or m.fax LIKE '" + searchQuery + "%' or m.email LIKE '"
				+ searchQuery + "%' or m.homepage LIKE '" + searchQuery
				+ "%' or m.birthDay LIKE '" + searchQuery
				+ "%' or m.birthMonth LIKE '" + searchQuery
				+ "%' or m.birthYear LIKE '" + searchQuery
				+ "%' or m.diseases LIKE '" + searchQuery
				+ "%' or m.beltsize LIKE '" + searchQuery
				+ "%' or m.note LIKE '" + searchQuery
				+ "%' or m.trainingunits LIKE '" + searchQuery + "%'";

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
				member.setBirthDay(rs.getString("birthDay"));
				member.setBirthMonth(rs.getString("birthMonth"));
				member.setBirthYear(rs.getString("birthYear"));
				member.setPicture(rs.getString("picture"));
				member.setDiseases(rs.getString("diseases"));
				member.setBeltsize(rs.getString("beltsize"));
				member.setNote(rs.getString("note"));
				member.setAccountForename(rs.getString("accountForename"));
				member.setAccountSurname(rs.getString("accountSurname"));
				member.setAccountNumber(rs.getString("accountNumber"));
				member.setBankName(rs.getString("bankName"));
				member.setBankNumber(rs.getString("bankNumber"));
				
				ArrayList<Integer> courses = new ArrayList<Integer>();
				ArrayList<Integer> graduation = new ArrayList<Integer>();
				ArrayList<Float> tariffs = new ArrayList<Float>();

				ResultSet rs2 = null;
				Statement stmt2 = dbc.getStatement();
				String query2 = "SELECT * FROM Member_has_courses WHERE barcode_id='"
						+ member.getBarcodeID() + "'";

				rs2 = stmt2.executeQuery(query2);
				if (rs2.wasNull()) {
				}
				while (rs2.next()) {
					courses.add(rs2.getInt("course_id"));
					graduation.add(rs2.getInt("graduation"));
					tariffs.add(rs2.getFloat("tariff"));
				}
				member.setCourses(courses);
				member.setGraduations(graduation);
				member.setTariffs(tariffs);

				rs2.close();
				stmt2.close();

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

	public ArrayList<Member> search2(String searchQuery) {

		ArrayList<Member> memberList = new ArrayList<Member>();

		ResultSet rs = null;

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();
		String query = "SELECT * FROM Member m INNER JOIN Courses c on m.course_01= c.Courses_id or m.course_02=c.Courses_id or m.course_03=c.Courses_id or m.course_04=c.Courses_id or m.course_05=c.Courses_id or m.course_06=c.Courses_id or m.course_07=c.Courses_id or m.course_08=c.Courses_id or m.course_09=c.Courses_id or m.course_10=c.Courses_id where m.forename LIKE '"
				+ searchQuery
				+ "%' or m.surname LIKE '"
				+ searchQuery
				+ "%' or m.zipcode LIKE '"
				+ searchQuery
				+ "%' or m.city LIKE '"
				+ searchQuery
				+ "%' or m.street LIKE '"
				+ searchQuery
				+ "%' or m.phone LIKE '"
				+ searchQuery
				+ "%' or m.mobilephone LIKE '"
				+ searchQuery
				+ "%' or m.fax LIKE '"
				+ searchQuery
				+ "%' or m.email LIKE '"
				+ searchQuery
				+ "%' or m.homepage LIKE '"
				+ searchQuery
				+ "%' or m.birthDay LIKE '"
				+ searchQuery
				+ "%' or m.birthMonth LIKE '"
				+ searchQuery
				+ "%' or m.birthYear LIKE '"
				+ searchQuery
				+ "%' or m.diseases LIKE '"
				+ searchQuery
				+ "%' or m.beltsize LIKE '"
				+ searchQuery
				+ "%' or m.note LIKE '"
				+ searchQuery
				+ "%' or m.trainingunits LIKE '"
				+ searchQuery
				+ "%' or c.name LIKE '" + searchQuery + "%'";

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
				member.setBirthDay(rs.getString("birthDay"));
				member.setBirthMonth(rs.getString("birthMonth"));
				member.setBirthYear(rs.getString("birthYear"));
				member.setPicture(rs.getString("picture"));
				member.setDiseases(rs.getString("diseases"));
				member.setBeltsize(rs.getString("beltsize"));
				member.setNote(rs.getString("note"));
				member.setAccountForename(rs.getString("accountForename"));
				member.setAccountSurname(rs.getString("accountSurname"));
				member.setAccountNumber(rs.getString("accountNumber"));
				member.setBankName(rs.getString("bankName"));
				member.setBankNumber(rs.getString("bankNumber"));

				ArrayList<Integer> courses = new ArrayList<Integer>();
				ArrayList<Integer> graduation = new ArrayList<Integer>();
				ArrayList<Float> tariffs = new ArrayList<Float>();

				ResultSet rs2 = null;
				Statement stmt2 = dbc.getStatement();
				String query2 = "SELECT * FROM Member_has_courses WHERE barcode_id='"
						+ member.getBarcodeID() + "'";

				rs2 = stmt.executeQuery(query2);
				if (rs2.wasNull()) {
				}
				while (rs2.next()) {
					courses.add(rs2.getInt("course_id"));
					graduation.add(rs2.getInt("graduation"));
					tariffs.add(rs2.getFloat("tariff"));
				}
				member.setCourses(courses);
				member.setGraduations(graduation);
				member.setTariffs(tariffs);

				rs2.close();
				stmt2.close();

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

	public boolean setNote(int barcodeID, String note) {

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "UPDATE Member SET note ='" + note
				+ "' where barcode_id = '" + barcodeID + "'";

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
	 * speichert die Trainingsanwesenheit eines Mitglieds
	 * 
	 * @param barcodeId
	 *            eines Mitglieds, int day, int month, int year
	 * 
	 * @return true bei erfolg, false bei fehler
	 */
	public boolean setTrainingsPresence(int barcodeID, int day, int month,
			int year) {

		DataBankerConnection dbc = new DataBankerConnection();
		try {

			ResultSet rs = null;
			Statement stmt = dbc.getStatement();

			String query = "SELECT COUNT(*), barcode_id, day, month, year FROM TrainingPresence WHERE day='"
					+ day
					+ "' AND month ='"
					+ month
					+ "' AND year = '"
					+ year
					+ "' AND barcode_id = '" + barcodeID + "'";

			rs = stmt.executeQuery(query);
			rs.next();

			if (rs.getInt(1) == 0) {
				// Datum f�r dieses Mitglied noch nicht eingetragen
				Statement stmt2 = dbc.getStatement();

				String query2 = "INSERT INTO TrainingPresence(barcode_id, day,month,year) VALUES ('"
						+ barcodeID
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

				// Datum f�r dieses Mitglied schon eingetragen
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
	 * �ndert einen Mitgliedereintrag
	 * 
	 * @param ein
	 *            Objekt des Typs Member
	 * 
	 * @return "Mitglied gespeichert" wenn Mitglied ge�ndert wurde,
	 *         "Fehler beim Speichern" wenn das �ndern nicht funktioniert hat
	 *         und "Barcodenummer schon vorhanden", wenn Barcodenummer schon
	 *         vorhanden ist.
	 */
	public String updateMember(Member member) {
		// TODO Update �ndern, damit primery key zur�ck gesetzt wird
		DataBankerConnection dbc = new DataBankerConnection();
		try {
			ResultSet rs = null;
			Statement stmt2 = dbc.getStatement();
			String query = "SELECT Member_id FROM Member WHERE barcode_id='"
					+ member.getBarcodeID() + "'";

			rs = stmt2.executeQuery(query);
			while (rs.next()) {
				member.setMemberID(rs.getInt(1));
			}
			deleteMember(member.getMemberID());
			rs.close();
			stmt2.close();

			PreparedStatement stmt = dbc

					.getConnection()
					.prepareStatement(
							"INSERT INTO Member(Member_id, barcode_id, forename, surname, zipcode, city, street, phone, mobilephone, fax, email, homepage, birthDay, birthMonth, birthYear, picture, diseases, beltsize, note, trainingunits, accountForename, accountSurname, accountNumber, bankName, bankNumber) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, member.getMemberID());
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
			stmt.setString(13, member.getBirthDay());
			stmt.setString(14, member.getBirthMonth());
			stmt.setString(15, member.getBirthYear());
			stmt.setString(16, member.getPicture());
			stmt.setString(17, member.getDiseases());
			stmt.setString(18, member.getBeltsize());
			stmt.setString(19, member.getNote());
			stmt.setInt(20, member.getTrainingunits());
			stmt.setString(21, member.getAccountForename());
			stmt.setString(22, member.getAccountSurname());
			stmt.setString(23, member.getAccountNumber());
			stmt.setString(24, member.getBankName());
			stmt.setString(25, member.getBankNumber());

			stmt.executeUpdate();

			PreparedStatement stmt3 = null;
			try {
				for (int i = 0; i < member.getCourses().size(); i++) {
					stmt3 = dbc
							.getConnection()
							.prepareStatement(
									"INSERT INTO Member_has_courses(barcode_id, course_id, graduation, tariff) VALUES(?,?,?,?)");
					stmt3.setInt(1, member.getBarcodeID());
					stmt3.setInt(2, member.getCourses().get(i));
					stmt3.setInt(3, member.getGraduations().get(i));
					stmt3.setFloat(4, member.getTariffs().get(i));
					stmt3.executeUpdate();
				}
			} catch (IndexOutOfBoundsException e) {
				System.out.println(e);
				System.out.println("courses out of bounds");
			}
			try {
				stmt3.close();
			} catch (NullPointerException e) {
			}
			dbc.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e);
			return "Fehler beim Speichern";
		}
		return "Mitglied gespeichert";

	}

	
}
