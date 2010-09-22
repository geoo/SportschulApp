package de.sportschulApp.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.sportschulApp.shared.Member;
import de.sportschulApp.shared.User;

public interface TrainerServiceAsync {

	void getMemberByBarcodeID(int barcodeID, AsyncCallback<Member> callback);
	
	void getTrainingspresence(int memberID, int month, int year, AsyncCallback<Integer> callback); 
}
