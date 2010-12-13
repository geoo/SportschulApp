package de.sportschulApp.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.sportschulApp.shared.Belt;
import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.CourseTariff;
import de.sportschulApp.shared.Event;
import de.sportschulApp.shared.EventParticipant;
import de.sportschulApp.shared.Member;
import de.sportschulApp.shared.User;


@RemoteServiceRelativePath("AdminService")
public interface AdminService extends RemoteService {
	String changeUser(User user);

	void createBelt(String beltName);

	String createCourse(Course course);

	Integer createEvent(Event event);

	void deleteBeltByID(int beltID);

	void deleteCourseByID(int courseID);

	void deleteEventByEventID(int eventID);

	void deleteMemberByMemberID(int memberID);

	void deleteUserByUserID(int userID);

	ArrayList<Belt> getAvailableBelts();

	Belt getBeltByID(int beltID);

	ArrayList<String> getBeltList(String courseName);

	ArrayList<Course> getCompleteCourseList();

	String getCourseBeltPair(int courseID, int beltID);

	Course getCourseByID(int courseID);

	int getCourseID(String courseName);

	ArrayList<Integer> getCourseIDs(ArrayList<String> courseNames);

	ArrayList<String> getCourseList();

	String getCourseName(int id);

	Event getEventByEventID(int eventID);

	ArrayList<Event> getEventList();

	ArrayList<EventParticipant> getEventParticipants(int eventID);

	ArrayList<User> getInstructorList();

	Member getMemberByBarcodeID(int barcodeID);

	Member getMemberByMemberID(int memberID);

	ArrayList<Member> getMemberList();

	User getUserByUserID(int userID);

	ArrayList<User> getUserList();

	void renameBeltByID(Belt belt);

	String saveMember(Member member);

	String saveUser(User user);

	ArrayList<Member> searchMember(String searchQuery);

	void setEventParticipants(int eventID, ArrayList<EventParticipant> participants);

	void updateCourse(Course course);

	Integer updateEvent(Event event);

	ArrayList<CourseTariff> getTariff(String selectedCourseName);

	String updateMember(Member member);

	ArrayList<String> getMemberCourses(int course, float tariff);

}
