package de.sportschulApp.server;

import java.util.ArrayList;

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

}
