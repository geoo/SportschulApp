package de.sportschulApp.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.sportschulApp.client.services.AdminService;
import de.sportschulApp.server.databanker.DataBankerMember;
import de.sportschulApp.shared.Member;

@SuppressWarnings("serial")
public class AdminServiceImpl extends RemoteServiceServlet implements
		AdminService {
	
	DataBankerMember dbm = new DataBankerMember();

	public ArrayList<Member> getMemberList() {
		ArrayList<Member> memberList = dbm.getMemberList();
		return memberList;
	}

	
}
