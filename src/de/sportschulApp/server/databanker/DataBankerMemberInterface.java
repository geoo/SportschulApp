package de.sportschulApp.server.databanker;

import java.util.ArrayList;

import de.sportschulApp.shared.Member;

public interface DataBankerMemberInterface {

	public String createMember(Member member);

	// TODO Memberliste

	public boolean deleteMember(int memberID);

	public boolean deleteTrainingsPresence(int memberID, int day, int month,
			int year);

	public Member getMember(int barcodeID);

	public ArrayList<Member> getMemberList();

	public Member getMemberWithMemberID(int memberID);

	public String getNote(int barcodeID);

	public ArrayList<int[]> getTrainingsPresence(int memberID, int month,
			int year);

	public int getTrainingsPresenceInt(int memberID, int month, int year);

	public ArrayList<Member> search(String searchQuery);

	public boolean setNote(int barcodeID, String note);

	public boolean setTrainingsPresence(int memberID, int day, int month,
			int year);

	public String updateMember(Member member);

}
