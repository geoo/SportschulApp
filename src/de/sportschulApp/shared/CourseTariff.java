package de.sportschulApp.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CourseTariff implements Serializable {

	private String costs;
	private String name;

	public CourseTariff() {

	}

	public CourseTariff(String name, String costs) {
		setName(name);
		setCosts(costs);
	}

	/**
	 * @return the costs
	 */
	public String getCosts() {
		return costs;
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
