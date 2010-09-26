package de.sportschulApp.server.databanker;

import java.sql.Date;
import java.util.ArrayList;

import de.sportschulApp.shared.Member;

public interface DataBankerMemberInterface {

	public String createMember(Member member);

	// TODO Memberliste

	public boolean deleteMember(int memberID);

	public String updateMember(Member member);

	public Member getMember(int barcodeID);

	public Member getMemberWithMemberID(int memberID);

	public boolean setTrainingsPresence(int memberID, int day, int month,
			int year);

	public ArrayList<int[]> getTrainingsPresence(int memberID, int month,
			int year);

	public int getTrainingsPresenceInt(int memberID, int month, int year);

	public boolean deleteTrainingsPresence(int memberID, int day, int month,
			int year);

	public ArrayList<Member> getMemberList();

	public boolean setNote(int barcodeID, String note);

	public String getNote(int barcodeID);

	public ArrayList<Member> search(String searchQuery);

}
