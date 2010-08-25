package de.sportschulApp.shared;

import java.util.ArrayList;

public class Course {

	private int courseID;
	private String name;
	private String time;
	private String instructor;
	private String location;
	private ArrayList<String> beltColours;

	public Course() {
	}

	public Course(int courseID, String name, String time, String instructor,
			String location, ArrayList<String> beltColours) {
		this.courseID = courseID;
		this.name = name;
		this.time = time;
		this.instructor = instructor;
		this.location = location;
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
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
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
}
