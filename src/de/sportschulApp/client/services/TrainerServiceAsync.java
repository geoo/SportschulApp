package de.sportschulApp.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.sportschulApp.shared.Member;

public interface TrainerServiceAsync {

	void getMemberByBarcodeID(int barcodeID, AsyncCallback<Member> callback);

	void getNote(int barcodeID, AsyncCallback<String> asyncCallback);

	void getTrainingspresence(int memberID, int month, int year, AsyncCallback<Integer> callback);

	void setNote(int barcodeID, String note, AsyncCallback<String> asyncCallback);

	void setTrainingsPresence(int barcodeID, int day, int month, int year,
			AsyncCallback<String> callback);
}
