package de.sportschulApp.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.sportschulApp.shared.Member;

public interface AdminServiceAsync {
	void getMemberList(AsyncCallback<ArrayList<Member>> callback);
}
