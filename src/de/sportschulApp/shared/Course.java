package de.sportschulApp.shared;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")

public class Course implements Serializable{

	private int courseID;
	private String name;
	private String instructor;
	private String location;
	private ArrayList<CourseDate> courseDates;
	private ArrayList<CourseTariff> courseTariffs;
	private ArrayList<String> beltColours;

	public Course() {
	}

	public Course(int courseID, String name, String instructor,
			String location, ArrayList<CourseDate> courseDates, ArrayList<CourseTariff> courseTariffs, ArrayList<String> beltColours) {
		this.courseID = courseID;
		this.name = name;
		this.instructor = instructor;
		this.location = location;
		this.setCourseDates(courseDates);
		this.setCourseTariffs(courseTariffs);
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
	 * @param courseDates the courseDates to set
	 */
	public void setCourseDates(ArrayList<CourseDate> courseDates) {
		this.courseDates = courseDates;
	}

	/**
	 * @return the courseDates
	 */
	public ArrayList<CourseDate> getCourseDates() {
		return courseDates;
	}

	/**
	 * @param courseTariffs the courseTariffs to set
	 */
	public void setCourseTariffs(ArrayList<CourseTariff> courseTariffs) {
		this.courseTariffs = courseTariffs;
	}

	/**
	 * @return the courseTariffs
	 */
	public ArrayList<CourseTariff> getCourseTariffs() {
		return courseTariffs;
	}
}
