package de.sportschulApp.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.sportschulApp.shared.Member;
import de.sportschulApp.shared.User;

public interface TrainerServiceAsync {

	void getMemberByBarcodeID(int barcodeID, AsyncCallback<Member> callback);
	
	void getTrainingspresence(int memberID, int month, int year, AsyncCallback<Integer> callback);

	void getNote(int barcodeID, AsyncCallback<String> asyncCallback);

	void setNote(int barcodeID, String note, AsyncCallback<String> asyncCallback);

	void setTrainingsPresence(int barcodeID, int day, int month, int year,
			AsyncCallback<String> callback); 
}
