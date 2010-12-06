package de.sportschulApp.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.sportschulApp.client.services.TrainerService;
import de.sportschulApp.server.databanker.DataBankerMember;
import de.sportschulApp.shared.Member;

@SuppressWarnings("serial")
public class TrainerServiceImpl extends RemoteServiceServlet implements
TrainerService {

	DataBankerMember dbm = new DataBankerMember();

	public Member getMemberByBarcodeID(int barcodeID) {
		return dbm.getMember(barcodeID);
	}

	public String getNote(int barcodeID) {
		return dbm.getNote(barcodeID);
	}

	public int getTrainingspresence(int memberID, int month, int year) {
		return dbm.getTrainingsPresenceInt(memberID, month, year);
	}

	public String setNote(int barcodeID, String note) {
		dbm.setNote(barcodeID, note);
		return null;
	}

	public String setTrainingsPresence(int barcodeID, int day, int month,
			int year) {
		dbm.setTrainingsPresence(barcodeID, day, month, year);
		return null;
	}

}
