package de.sportschulApp.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Belt implements Serializable {
	
	private int beltID;
	private String name;
	
	public Belt(int beltID, String name) {
		this.setBeltID(beltID);
		this.setName(name);
	}
	
	public Belt() {
		
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param beltID the beltID to set
	 */
	public void setBeltID(int beltID) {
		this.beltID = beltID;
	}

	/**
	 * @return the beltID
	 */
	public int getBeltID() {
		return beltID;
	}
}
