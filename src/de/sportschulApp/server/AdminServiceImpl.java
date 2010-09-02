package de.sportschulApp.server;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.sportschulApp.client.services.AdminService;
import de.sportschulApp.server.databanker.DataBankerCourse;
import de.sportschulApp.server.databanker.DataBankerMember;
import de.sportschulApp.shared.Member;

@SuppressWarnings("serial")
public class AdminServiceImpl extends RemoteServiceServlet implements
		AdminService {

	DataBankerMember dbm = new DataBankerMember();
	DataBankerCourse dbc = new DataBankerCourse();

	public ArrayList<Member> getMemberList() {
		ArrayList<Member> memberList = dbm.getMemberList();
		return memberList;
	}

	public ArrayList<String> getCourseList() {
		return dbc.getCourseNames();
	}

	public ArrayList<String> getBeltList(String courseName) {
		int courseID = dbc.getCourseID(courseName);
		return dbc.getBelts(courseID);
	}

	public Member getMemberByMemberID(int memberID) {
		return dbm.getMemberWithMemberID(memberID);
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

}
