package de.sportschulApp.shared;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")

public class Course implements Serializable{

	private int courseID;
	private String name;
	private String instructor;
	private String location;
	private ArrayList<String> weekDays;
	private ArrayList<String> times;
	private ArrayList<String> tariffNames;
	private ArrayList<String> tariffCosts;
	private ArrayList<String> beltColours;

	public Course() {
	}

	public Course(int courseID, String name, String instructor,
			String location, ArrayList<String> weekDays, ArrayList<String> times, ArrayList<String> tariffNames, ArrayList<String> tariffCosts, ArrayList<String> beltColours) {
		this.courseID = courseID;
		this.name = name;
		this.instructor = instructor;
		this.location = location;
		this.weekDays = weekDays;
		this.times = times;
		this.tariffNames = tariffNames;
		this.tariffCosts = tariffCosts;
		this.beltColours = beltColours;

	}

	/**
	 * @param courseID
	 *            the courseID to set
	 */
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	/**
	 * @return the courseID
	 */
	public int getCourseID() {
		return courseID;
	}

	/**
	 * @param name
	 *            the name to set
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
	 * @param instructor
	 *            the instructor to set
	 */
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	/**
	 * @return the instructor
	 */
	public String getInstructor() {
		return instructor;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param beltColours the beltColours to set
	 */
	public void setBeltColours(ArrayList<String> beltColours) {
		this.beltColours = beltColours;
	}

	/**
	 * @return the beltColours
	 */
	public ArrayList<String> getBeltColours() {
		return beltColours;
	}

	/**
	 * @param weekDays the weekDays to set
	 */
	public void setWeekDays(ArrayList<String> weekDays) {
		this.weekDays = weekDays;
	}

	/**
	 * @return the weekDays
	 */
	public ArrayList<String> getWeekDays() {
		return weekDays;
	}

	/**
	 * @param times the times to set
	 */
	public void setTimes(ArrayList<String> times) {
		this.times = times;
	}

	/**
	 * @return the times
	 */
	public ArrayList<String> getTimes() {
		return times;
	}

	/**
	 * @param tariffNames the tariffNames to set
	 */
	public void setTariffNames(ArrayList<String> tariffNames) {
		this.tariffNames = tariffNames;
	}

	/**
	 * @return the tariffNames
	 */
	public ArrayList<String> getTariffNames() {
		return tariffNames;
	}

	/**
	 * @param tariffCosts the tariffCosts to set
	 */
	public void setTariffCosts(ArrayList<String> tariffCosts) {
		this.tariffCosts = tariffCosts;
	}

	/**
	 * @return the tariffCosts
	 */
	public ArrayList<String> getTariffCosts() {
		return tariffCosts;
	}
}
