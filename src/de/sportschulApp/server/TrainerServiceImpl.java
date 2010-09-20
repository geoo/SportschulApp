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

}
