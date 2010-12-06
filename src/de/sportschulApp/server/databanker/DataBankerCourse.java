package de.sportschulApp.server.databanker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.sportschulApp.shared.Belt;
import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.CourseDate;
import de.sportschulApp.shared.CourseTariff;

public class DataBankerCourse implements DataBankerCourseInterface {

	public void createBelt(String beltName) {
		DataBankerConnection dbc = new DataBankerConnection();
		try {
			PreparedStatement stmt = dbc.getConnection().prepareStatement(
					"INSERT INTO AvailableBelts(name) VALUES(?)");
			stmt.setString(1, beltName);

			stmt.executeUpdate();
			dbc.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/**
	 * Erstellt einen Kurseintrag
	 * 
	 * @param Object
	 *            des typs Course
	 * 
	 * 
	 * @return "name already used" wenn der Kursname schon vergeben ist, "error"
	 *         wenn ein Fehler auftritt und "course created" wenn der Kurs
	 *         angelegt wurde
	 */
	public String createCourse(Course course) {
		DataBankerConnection dbc = new DataBankerConnection();
		int courseID = 0;

		try {

			ResultSet rs = null;
			Statement stmt2 = dbc.getStatement();
			String query = "SELECT count(*) FROM Courses WHERE name='"
				+ course.getName() + "'";

			rs = stmt2.executeQuery(query);
			while (rs.next()) {
				if (rs.getInt(1) > 0) {
					return "name alrady used";
				}
			}
			stmt2.close();

			PreparedStatement stmt = null;
			stmt = dbc

			.getConnection()
			.prepareStatement(
					"INSERT INTO Courses(name, instructor, location) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, course.getName());
			stmt.setString(2, course.getInstructor());
			stmt.setString(3, course.getLocation());

			stmt.executeUpdate();

			ResultSet generatedKeys = stmt.getGeneratedKeys();

			while(generatedKeys.next()) {
				setDatesForCourse(generatedKeys.getInt(1), course.getCourseDates());
				setTariffsForCourse(generatedKeys.getInt(1), course.getCourseTariffs());
			}

			ResultSet rs2 = null;
			Statement stmt3 = dbc.getStatement();
			String query2 = "SELECT Courses_id FROM Courses WHERE name='"
				+ course.getName() + "'";

			rs2 = stmt3.executeQuery(query2);

			while (rs2.next()) {
				courseID = rs2.getInt(1);
			}

			rs.close();
			rs2.close();
			stmt3.close();
			dbc.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e);
			return "error";
		}

		if (setBelts(courseID, course.getBeltColours())) {
			return "course created";
		} else {
			return "error";
		}
	}

	public void deleteBeltByID(int beltID) {
		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "DELETE FROM AvailableBelts WHERE Belt_id='" + beltID + "'";

		try {
			stmt.executeUpdate(query);
			dbc.close();
			stmt.close();
			dbc.closeStatement();

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/**
	 * L�scht einen G�rteleintrag
	 * 
	 * @param courseID
	 *            der G�rtel
	 * 
	 * 
	 * @return true bei erfolg, false bei scheitern
	 */
	public boolean deleteBelts(int courseID) {

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "DELETE FROM Belts WHERE Course_id='" + courseID + "'";

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

	public boolean deleteCourse(int courseID) {

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "DELETE FROM Courses WHERE Courses_id='" + courseID
		+ "'";

		try {
			stmt.executeUpdate(query);
			deleteDatesFromCourse(courseID);
			deleteTariffsFromCourse(courseID);
			dbc.close();
			stmt.close();
			dbc.closeStatement();

		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

		if (deleteBelts(courseID)) {
			return true;
		} else {
			return false;
		}

	}

	public void deleteDatesFromCourse(int courseID) {
		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "DELETE FROM Course_has_date WHERE Course_id='" + courseID + "'";

		try {
			stmt.executeUpdate(query);
			dbc.close();
			stmt.close();
			dbc.closeStatement();

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void deleteTariffsFromCourse(int courseID) {
		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();

		String query = "DELETE FROM Course_has_tariff WHERE Course_id='" + courseID + "'";

		try {
			stmt.executeUpdate(query);
			dbc.close();
			stmt.close();
			dbc.closeStatement();

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public ArrayList<Belt> getAvailableBelts() {

		ArrayList<Belt> belts = new ArrayList<Belt>();

		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs = null;
		Statement stmt = dbc.getStatement();

		String query = "SELECT * FROM AvailableBelts ORDER BY name";

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				belts.add(new Belt(rs.getInt(1), rs.getString(2)));
			}
			rs.close();
			dbc.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return belts;
	}

	public Belt getBeltByID(int beltID){
		Belt belt = new Belt();
		ResultSet rs = null;

		DataBankerConnection dbc = new DataBankerConnection();
		Statement stmt = dbc.getStatement();
		String query = "SELECT * FROM AvailableBelts WHERE Belt_id='" + beltID + "'";

		try {
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				belt.setBeltID(rs.getInt(1));
				belt.setName(rs.getString(2));
			}
			rs.close();
			dbc.close();
			stmt.close();
			dbc.closeStatement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return belt;
	}

	public ArrayList<String> getBelts(int courseID) {

		ArrayList<String> belts = new ArrayList<String>();
		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs = null;
		Statement stmt = dbc.getStatement();

		String query = "SELECT * FROM Belts WHERE Course_id='" + courseID + "'";

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				for (int i = 2; i < 22 && rs.getString(i).length() > 0; i++) {
					belts.add(rs.getString(i));
				}
			}
			rs.close();
			dbc.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return belts;
	}

	public String getCourseBeltPair(int courseID, int beltID) {
		String beltName = null;
		String courseName = null;
		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Statement stmt1 = dbc.getStatement();
		Statement stmt2 = dbc.getStatement();

		String query1 = "SELECT name FROM Courses where Courses_id = '"
			+ courseID + "'";

		String query2 = "SELECT grade" + beltID
		+ " FROM Belts where Course_id = '" + courseID + "'";

		try {
			rs1 = stmt1.executeQuery(query1);
			while (rs1.next()) {
				courseName = rs1.getString(1);
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

		try {
			rs2 = stmt2.executeQuery(query2);
			while (rs2.next()) {
				beltName = rs2.getString(1);
			}
			rs1.close();
			rs2.close();
			dbc.close();
			stmt1.close();
			stmt2.close();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return courseName + "(" + beltName + ") ";
	}

	public Course getCourseByID(int courseID) {
		Course course = new Course();
		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs = null;
		Statement stmt = dbc.getStatement();

		String query = "SELECT * FROM Courses where  Courses_id = '" + courseID
		+ "'";

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				course.setCourseID(rs.getInt(1));
				course.setName(rs.getString(2));
				course.setInstructor(rs.getString(3));
				course.setLocation(rs.getString(4));
				course.setCourseDates(getCourseDatesForCourse(rs.getInt(1)));
				course.setCourseTariffs(getCourseTariffsForCourse(rs.getInt(1)));
				course.setBeltColours(getBelts(rs.getInt(1)));
			}
			rs.close();
			dbc.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return course;
	}

	public ArrayList<CourseDate> getCourseDatesForCourse(int courseId) {
		ArrayList<CourseDate> courseDates = new ArrayList<CourseDate>();


		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs = null;
		Statement stmt = dbc.getStatement();
		String query = "SELECT weekDay,time FROM Course_has_date WHERE Course_ID='" + courseId + "'";

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				courseDates.add(new CourseDate(rs.getString(1), rs.getString(2)));
			}
			rs.close();
			dbc.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return courseDates;
	}

	public int getCourseID(String courseName) {
		int courseID = 0;
		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs = null;
		Statement stmt = dbc.getStatement();

		String query = "SELECT Courses_id FROM Courses WHERE name = '"
			+ courseName + "'";

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				courseID = rs.getInt(1);
			}
			rs.close();
			dbc.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			return 0;
		}
		return courseID;
	}

	public String getCourseName(int courseID) {
		String courseName = null;
		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs = null;
		Statement stmt = dbc.getStatement();

		String query = "SELECT name FROM Courses where Courses_id = '"
			+ courseID + "'";

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				courseName = rs.getString(1);
			}
			rs.close();
			dbc.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return courseName;
	}

	public ArrayList<String> getCourseNames() {

		ArrayList<String> names = new ArrayList<String>();

		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs = null;
		Statement stmt = dbc.getStatement();

		String query = "SELECT name FROM Courses";

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				names.add(rs.getString(1));
			}
			rs.close();
			dbc.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return names;
	}

	public ArrayList<Course> getCourses() {

		ArrayList<Course> courses = new ArrayList<Course>();

		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs = null;
		Statement stmt = dbc.getStatement();

		String query = "SELECT * FROM Courses ORDER BY name";

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				Course newCourse = new Course();
				newCourse.setCourseID(rs.getInt(1));
				newCourse.setName(rs.getString(2));
				newCourse.setInstructor(rs.getString(3));
				newCourse.setLocation(rs.getString(4));
				newCourse.setCourseDates(getCourseDatesForCourse(rs.getInt(1)));
				newCourse.setCourseTariffs(getCourseTariffsForCourse(rs.getInt(1)));
				newCourse.setBeltColours(getBelts(rs.getInt(1)));
				courses.add(newCourse);
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

	public ArrayList<CourseTariff> getCourseTariffsForCourse(int courseId) {
		ArrayList<CourseTariff> courseTariffs = new ArrayList<CourseTariff>();


		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs = null;
		Statement stmt = dbc.getStatement();
		String query = "SELECT name,costs FROM Course_has_tariff WHERE Course_ID='" + courseId + "'";

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				courseTariffs.add(new CourseTariff(rs.getString(1), rs.getString(2)));
			}
			rs.close();
			dbc.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return courseTariffs;
	}

	public String nextBelt(int courseID, int lastBelt) {

		int newBelt = lastBelt + 1;
		String graduation = "grade" + newBelt;

		DataBankerConnection dbc = new DataBankerConnection();
		ResultSet rs = null;
		Statement stmt = dbc.getStatement();

		String query = "SELECT " + graduation + " FROM Belts WHERE Course_id='"
		+ courseID + "'";

		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				return rs.getString(1);
			}
			rs.close();
			dbc.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

		return null;
	}

	public void renameBeltByID(Belt belt) {
		DataBankerConnection dbc = new DataBankerConnection();
		try {
			PreparedStatement stmt = dbc.getConnection().prepareStatement(
					"UPDATE AvailableBelts SET name='" + belt.getName() + "' WHERE Belt_id='" + belt.getBeltID() + "'");
			stmt.executeUpdate();

			dbc.close();
			stmt.close();


		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	/**
	 * Erstellt einen G�rteleintrag
	 * 
	 * @param courseID
	 *            der G�rtel, ArraList<String> mit G�rtelfarben
	 * 
	 * 
	 * @return true bei erfolg, false bei scheitern
	 */
	public boolean setBelts(int courseID, ArrayList<String> belts) {

		DataBankerConnection dbc = new DataBankerConnection();
		try {
			PreparedStatement stmt = dbc

			.getConnection()
			.prepareStatement(
					"INSERT INTO Belts(Course_id, grade1, grade2, grade3, grade4, grade5, grade6, grade7, grade8, grade9, grade10, grade11, grade12, grade13, grade14, grade15, grade16, grade17, grade18, grade19 ,grade20) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, courseID);

			if (belts.isEmpty()) {
				return false;
			} else {
				for (int i = 0; i < belts.size(); i++) {
					stmt.setString(i + 2, belts.get(i));
				}
				for (int j = belts.size() + 2; j < 22; j++) {
					stmt.setString(j, "");
				}
			}
			stmt.executeUpdate();

			dbc.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		return true;
	}

	public void setDatesForCourse(int courseID, ArrayList<CourseDate> courseDates) {
		DataBankerConnection dbc = new DataBankerConnection();
		for (int i = 0; i < courseDates.size(); i++) {
			try {
				PreparedStatement stmt = dbc.getConnection().prepareStatement(
				"INSERT INTO Course_has_date(Course_id, weekDay, time) VALUES(?,?,?)");
				stmt.setInt(1, courseID);
				stmt.setString(2, courseDates.get(i).getWeekDay());
				stmt.setString(3, courseDates.get(i).getTime());

				stmt.executeUpdate();

				dbc.close();
				stmt.close();


			} catch (SQLException e) {
				System.out.println(e);
			}
		}
	}

	public void setTariffsForCourse(int courseID, ArrayList<CourseTariff> courseTariffs) {
		DataBankerConnection dbc = new DataBankerConnection();
		for (int i = 0; i < courseTariffs.size(); i++) {
			try {
				PreparedStatement stmt = dbc.getConnection().prepareStatement(
				"INSERT INTO Course_has_tariff(Course_id, name, costs) VALUES(?,?,REPLACE(?,',','.'))");
				stmt.setInt(1, courseID);
				stmt.setString(2, courseTariffs.get(i).getName());
				stmt.setString(3, courseTariffs.get(i).getCosts());

				stmt.executeUpdate();

				dbc.close();
				stmt.close();


			} catch (SQLException e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Updated einen G�rteleintrag
	 * 
	 * @param courseID
	 *            der G�rtel, ArrayList<String> mit den farben
	 * 
	 * 
	 * @return true bei erfolg, false bei scheitern
	 */
	public boolean updateBelts(int courseID, ArrayList<String> belts) {
		if (deleteBelts(courseID)) {
			setBelts(courseID, belts);
			return true;
		} else {
			return false;
		}
	}

	public boolean updateCourse(Course course) {
		if (deleteCourse(course.getCourseID())) {
			createCourse(course);
		}
		return false;
	}

}
