package de.sportschulApp.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CourseTariff implements Serializable {
	
	private String name;
	private String costs;
	
	public CourseTariff(String name, String costs) {
		this.setName(name);
		this.setCosts(costs);
	}
	
	public CourseTariff() {
		
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
	 * @param costs the costs to set
	 */
	public void setCosts(String costs) {
		this.costs = costs;
	}

	/**
	 * @return the costs
	 */
	public String getCosts() {
		return costs;
	}

}
