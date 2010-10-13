package de.sportschulApp.server;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.sportschulApp.client.services.AdminService;
import de.sportschulApp.server.databanker.DataBankerCourse;
import de.sportschulApp.server.databanker.DataBankerEvent;
import de.sportschulApp.server.databanker.DataBankerMember;
import de.sportschulApp.server.databanker.DataBankerUser;
import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.Event;
import de.sportschulApp.shared.Member;
import de.sportschulApp.shared.User;

@SuppressWarnings("serial")
public class AdminServiceImpl extends RemoteServiceServlet implements
		AdminService {

	DataBankerMember dbm = new DataBankerMember();
	DataBankerCourse dbc = new DataBankerCourse();
	DataBankerUser dbu = new DataBankerUser();
	DataBankerEvent dbe = new DataBankerEvent();

	public ArrayList<Member> getMemberList() {
		ArrayList<Member> memberList = dbm.getMemberList();
		return memberList;
	}

	public ArrayList<String> getCourseList() {
		return dbc.getCourseNames();
	}
	
	public ArrayList<Course> getCompleteCourseList() {
		return dbc.getCourses();
	}
	
	public Course getCourseByID(int courseID) {
		return dbc.getCourseByID(courseID);
	}
	
	public void deleteCourseByID(int courseID) {
		dbc.deleteCourse(courseID);
	}

	public ArrayList<String> getBeltList(String courseName) {
		int courseID = dbc.getCourseID(courseName);
		return dbc.getBelts(courseID);
	}

	public Member getMemberByMemberID(int memberID) {
		return dbm.getMemberWithMemberID(memberID);
	}

	public Member getMemberByBarcodeID(int barcodeID) {
		return dbm.getMember(barcodeID);
	}
	
	public void deleteMemberByMemberID(int memberID) {
		dbm.deleteMember(memberID);
	}

	public int getCourseID(String courseName) {
		return dbc.getCourseID(courseName);
	}

	public ArrayList<Integer> getCourseIDs(ArrayList<String> courseNames) {
		ArrayList<Integer> numbers = new ArrayList<Integer>();

		Iterator itr = courseNames.iterator();
		while (itr.hasNext()) {
			numbers.add(dbc.getCourseID((String) itr.next()));

		}
		return numbers;

	}

	public String saveMember(Member member) {
		System.out.println("Member Courses: "+member.getCourses());
		System.out.println("Member Grades: "+member.getGraduations());

		return dbm.createMember(member);
	
	}

	public String createCourse(Course course) {
		return dbc.createCourse(course);
	}

	public ArrayList<User> getInstructorList() {

		return dbu.getUserList();
	}

	public String getCourseName(int id) {
		return dbc.getCourseName(id);
	}
	
	public String getCourseBeltPair(int courseID, int beltID) {
		return dbc.getCourseBeltPair(courseID, beltID);
	}

	public ArrayList<Member> searchMember(String searchQuery){
		return dbm.search(searchQuery);}

	public ArrayList<User> getUserList() {
		return dbu.getUserList();
	}
	
	public User getUserByUserID(int userID) {
		return dbu.getUser(userID);
	}

	public void deleteUserByUserID(int userID) {
		dbu.deleteUser(userID);
	}
	
	public ArrayList<Event> getEventList() {
		return dbe.getEventList();
	}
	
	public Event getEventByEventID(int eventID) {
		return dbe.getEvent(eventID);
	}
	
	public void deleteEventByEventID(int eventID) {
		dbe.deleteEvent(eventID);
	}
	
	public void createEvent(Event event) {
		dbe.createEvent(event);
	}
	
	public void updateCourse(Course course) {
		dbc.updateCourse(course);
	}

	public String saveUser(User user) {
		return dbu.createUser(user);
	}

	public String changeUser(User user) {
		return dbu.updateUser(user);
	}
}
