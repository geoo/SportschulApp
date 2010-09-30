package de.sportschulApp.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.sportschulApp.shared.Course;
import de.sportschulApp.shared.Member;
import de.sportschulApp.shared.User;

public interface AdminServiceAsync {
	void getMemberList(AsyncCallback<ArrayList<Member>> callback);

	void getCourseList(AsyncCallback<ArrayList<String>> asyncCallback);

	void getBeltList(String courseName,
			AsyncCallback<ArrayList<String>> callback);

	void getMemberByMemberID(int memberID, AsyncCallback<Member> callback);

	void getCourseID(String courseName, AsyncCallback<Integer> asyncCallback);

	void getCourseIDs(ArrayList<String> courseNames,
			AsyncCallback<ArrayList<Integer>> asyncCallback);

	void saveMember(Member member, AsyncCallback<String> asyncCallback);

	void getMemberByBarcodeID(int barcodeID, AsyncCallback<Member> callback);

	void createCourse(Course course, AsyncCallback<String> asyncCallback);

	void getInstructorList(AsyncCallback<ArrayList<User>> asyncCallback);

	void getCourseName(int id, AsyncCallback<String> callback);

	void getCourseBeltPair(int courseID, int beltID,
			AsyncCallback<String> callback);

	void searchMember(String searchQuery,
			AsyncCallback<ArrayList<Member>> callback);

	void getCompleteCourseList(AsyncCallback<ArrayList<Course>> callback);

	void getCourseByID(int courseID, AsyncCallback<Course> callback);

	void deleteCourseByID(int courseID, AsyncCallback<Void> callback);

	void deleteMemberByMemberID(int memberID, AsyncCallback<Void> callback);

	void getUserList(AsyncCallback<ArrayList<User>> callback);

	void getUserByUserID(int userID, AsyncCallback<User> callback);

	void deleteUserByUserID(int userID, AsyncCallback<Void> callback);
}
