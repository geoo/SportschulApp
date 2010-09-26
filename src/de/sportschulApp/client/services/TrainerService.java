package de.sportschulApp.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.sportschulApp.shared.Member;
import de.sportschulApp.shared.User;


@RemoteServiceRelativePath("TrainerService")
public interface TrainerService extends RemoteService {
	Member getMemberByBarcodeID(int barcodeID);


	int getTrainingspresence(int memberID, int month, int year);


	String getNote(int barcodeID);


	String setNote(int barcodeID, String note);
	
	String setTrainingsPresence(int barcodeID, int day, int month, int year);


}
