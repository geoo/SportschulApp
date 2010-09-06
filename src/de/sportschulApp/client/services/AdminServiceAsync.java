package de.sportschulApp.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.sportschulApp.shared.Member;

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
}
