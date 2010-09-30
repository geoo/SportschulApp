package de.sportschulApp.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.Member;
import de.sportschulApp.shared.User;


@RemoteServiceRelativePath("AdminService")
public interface AdminService extends RemoteService {
	ArrayList<Member> getMemberList();

	ArrayList<String> getCourseList();
	
	ArrayList<Course> getCompleteCourseList();
	
	String getCourseName(int id);
	
	String getCourseBeltPair(int courseID, int beltID);

	ArrayList<String> getBeltList(String courseName);
	
	Member getMemberByMemberID(int memberID);
	
	void deleteMemberByMemberID(int memberID);
	
	Member getMemberByBarcodeID(int barcodeID);

	int getCourseID(String courseName);

	ArrayList<Integer> getCourseIDs(ArrayList<String> courseNames);

	String saveMember(Member member);

	String createCourse(Course course);
	
	Course getCourseByID(int courseID);
	
	void deleteCourseByID(int courseID);

	ArrayList<User> getInstructorList();
	
	ArrayList<Member> searchMember(String searchQuery);
	
	ArrayList<User> getUserList();
	
	User getUserByUserID(int userID);
	
	void deleteUserByUserID(int userID);
}
