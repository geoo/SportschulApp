package de.sportschulApp.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.sportschulApp.client.services.LoginService;
import de.sportschulApp.server.databanker.DataBankerCourse;
import de.sportschulApp.server.databanker.DataBankerMember;
import de.sportschulApp.server.databanker.DataBankerUser;

import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.Member;
import de.sportschulApp.shared.User;

@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	// private SessionHandler sh = new SessionHandler();

	public User login(User user) {

		DataBankerUser dbu = new DataBankerUser();
		user.setPermission(dbu.userLogin(user.getUsername(), user.getPassword()));
		user.setPassword("");
		dbsTest();
		return user;

	}

	public void dbsTest() {
		// DataBanker Test

		/*
		 * Test von createUser
		 */
		// test
		// User user = new User(0, "admin", "gee", "12345", "Ge", "Org");

		// System.out.println(dbs.createUser(user));

		/*
		 * Test von createMember
		 */

		/*
		 * Test von deleteMember
		 */
		/*
		 * if (dbs.deleteMember(5))
		 * 
		 * System.out.println("Löschen erfolgreich"); else
		 * System.out.println("Löschen fehlgeschlagen");
		 */
		/*
		 * Test von deleteUser
		 */
		/*
		 * if (dbs.deleteUser(7)) System.out.println("Löschen erfolgreich");
		 * else System.out.println("Löschen fehlgeschlagen");
		 */

		/*
		 * Test von updateUser
		 */
		/*
		 * User user = new User(1, "admin", "gee", "12345", "Ge", "Org");
		 * dbs.updateUser(user);
		 */

		/*
		 * Test von updateMember
		 */
		/*
		 * ArrayList<Integer> courses = new ArrayList<Integer>();
		 * courses.add(23); courses.add(245); courses.add(234555);
		 * courses.add(2399);
		 * 
		 * ArrayList<Integer> graduations = new ArrayList<Integer>();
		 * graduations.add(1234); graduations.add(3245); graduations.add(3456);
		 * graduations.add(346);
		 * 
		 * 
		 * Date date = new Date(1976-02-02);
		 * 
		 * System.out.println("Update Member: " + dbs.updateMember(new Member(1,
		 * 4308, "Georg", "Ortwein", 36358, "Stockhausen",
		 * "Am langen Garten 12", "1234567", "123457", "1234567",
		 * "georg.ortwein@gmail.com", "www.blabla.de", date, "pictureURL",
		 * "Krankheitenbla", "160", "Kommentar blablabla", 8, courses,
		 * graduations)));
		 */

		/*
		 * Test von getMember
		 */
		/*
		 * Member member = dbs.getMember(8008);
		 * System.out.println(member.getBarcodeID());
		 * System.out.println(member.getForename());
		 * System.out.println(member.getSurname());
		 * System.out.println(member.getMemberID());
		 */

		/*
		 * Test von getMemberWithMemberID
		 */
		/*
		 * Member member = dbs.getMemberWithMemberID(4);
		 * System.out.println(member.getBarcodeID());
		 * System.out.println(member.getForename());
		 * System.out.println(member.getSurname());
		 * System.out.println(member.getMemberID());
		 */
		/*
		 * Test von setTrainingsPresence
		 */

		DataBankerMember dbm = new DataBankerMember();
		/*
		 * Date date = new Date(2010 - 8 - 22); dbm.setTrainingsPresence(1,
		 * date);
		 * 
		 * Date date2 = new Date(2010 - 8 - 22); dbm.setTrainingsPresence(1,
		 * date2);
		 * 
		 * Date date3 = new Date(2010 - 8 - 22); dbm.setTrainingsPresence(1,
		 * date3);
		 */
		/*
		 * Test von getTrainingsPresenceMonth
		 */
		/*
		 * DataBankerMember dbm = new DataBankerMember();
		 * 
		 * System.out.println("monat 1: " + dbm.getTrainingsPresenceMonth(1,
		 * 1).get(2)); System.out.println("monat 9: " +
		 * dbm.getTrainingsPresenceMonth(1, 9).get(2));
		 * 
		 * System.out.println("Anzahl monat 9: " +
		 * dbm.getTrainingsPresenceMonthInt(1, 9));
		 * System.out.println("Anzahl monat 10: " +
		 * dbm.getTrainingsPresenceMonthInt(1, 10));
		 */
		// DataBankerMember dbm = new DataBankerMember();
		/*
		 * if (dbm.deleteTrainingsPresence(1, 1)) {
		 * System.out.println("Delete erfolgreich"); } else {
		 * System.out.println("Delete gescheitert"); }
		 */
		// Date date = new Date(2010, 01, 01);
		// dbm.setTrainingsPresence(4, 7, 30, 2010);
		// System.out.println("Trainings Anwesenheit: "+dbm.getTrainingsPresenceInt(4,
		// 30, 2010));
		// System.out.println("Training 01 Tag: "
		// + dbm.getTrainingsPresenceMonth(4, 5, 2010).get(0)[0]);
		// dbm.deleteTrainingsPresence(1, 1, 3, 1980);

		/*
		 * System.out.println("Training 02 Monat: " +
		 * dbm.getTrainingsPresenceMonth(4, 5, 2010).get(0)[1]);
		 * System.out.println("Training 03 Jahr: " +
		 * dbm.getTrainingsPresenceMonth(4, 5, 2010).get(0)[2]);
		 * 
		 * System.out.println("Training 11 Tag: " +
		 * dbm.getTrainingsPresenceMonth(4, 5, 2010).get(1)[0]);
		 * System.out.println("Training 12 Monat: " +
		 * dbm.getTrainingsPresenceMonth(4, 5, 2010).get(1)[1]);
		 * System.out.println("Training 13 Jahr: " +
		 * dbm.getTrainingsPresenceMonth(4, 5, 2010).get(1)[2]);
		 */
		DataBankerCourse dbc = new DataBankerCourse();
		ArrayList<String> belts = new ArrayList<String>();
		belts.add("rot");
		belts.add("gruen");
		belts.add("gelb");
		belts.add("blau");
		belts.add("braun");
		belts.add("orange");

		Course course = new Course(0, "lala", "Donnerstag 15.00 Uhr",
				"Georg Ortwein", "Daheim", belts);
		// dbc.createCourse(course);
		/*
		 * if(dbc.updateBelts(1, belts)){
		 * System.out.println("Update erfolgreich"); } else {
		 * System.out.println("Update fehlgeschlagen"); }
		 * System.out.println("Ergebnis: "+dbc.nextBelt(1, 2)); }
		 */
		// dbc.updateCourse(course);
		/*
		 * System.out.println("Gürtelfarben für 3: " + dbc.getBelts(3));
		 * System.out.println("Alle Kursname: " + dbc.getCourseNames());
		 * 
		 * System.out.println("getCourses: " + dbc.getCourses().get(0));
		 */
		ArrayList<Member> test = dbm.getMemberList();
		System.out.println("Member 0: " + test.get(0).getForename()
				+ test.get(0).getSurname());
		
		System.out.println("Member 1: " + test.get(1).getForename()
				+ test.get(1).getSurname());
	}
}
