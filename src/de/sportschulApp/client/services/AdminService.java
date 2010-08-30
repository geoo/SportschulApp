package de.sportschulApp.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.sportschulApp.shared.Member;


@RemoteServiceRelativePath("AdminService")
public interface AdminService extends RemoteService {
	ArrayList<Member> getMemberList();

	ArrayList<String> getCourseList();

	ArrayList<String> getBeltList(String courseName);
}
