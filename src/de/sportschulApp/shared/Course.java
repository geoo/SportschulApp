package de.sportschulApp.shared;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")

public class Course implements Serializable{

	private ArrayList<String> beltColours;
	private ArrayList<CourseDate> courseDates;
	private int courseID;
	private ArrayList<CourseTariff> courseTariffs;
	private String instructor;
	private String location;
	private String name;

	public Course() {
	}

	public Course(int courseID, String name, String instructor,
			String location, ArrayList<CourseDate> courseDates, ArrayList<CourseTariff> courseTariffs, ArrayList<String> beltColours) {
		this.courseID = courseID;
		this.name = name;
		this.instructor = instructor;
		this.location = location;
		setCourseDates(courseDates);
		setCourseTariffs(courseTariffs);
		this.beltColours = beltColours;

	}

	/**
	 * @return the beltColours
	 */
	public ArrayList<String> getBeltColours() {
		return beltColours;
	}

	/**
	 * @return the courseDates
	 */
	public ArrayList<CourseDate> getCourseDates() {
		return courseDates;
	}

	/**
	 * @return the courseID
	 */
	public int getCourseID() {
		return courseID;
	}

	/**
	 * @return the courseTariffs
	 */
	public ArrayList<CourseTariff> getCourseTariffs() {
		return courseTariffs;
	}

	/**
	 * @return the instructor
	 */
	public String getInstructor() {
		return instructor;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param beltColours the beltColours to set
	 */
	public void setBeltColours(ArrayList<String> beltColours) {
		this.beltColours = beltColours;
	}

	/**
	 * @param courseDates the courseDates to set
	 */
	public void setCourseDates(ArrayList<CourseDate> courseDates) {
		this.courseDates = courseDates;
	}

	/**
	 * @param courseID
	 *            the courseID to set
	 */
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	/**
	 * @param courseTariffs the courseTariffs to set
	 */
	public void setCourseTariffs(ArrayList<CourseTariff> courseTariffs) {
		this.courseTariffs = courseTariffs;
	}

	/**
	 * @param instructor
	 *            the instructor to set
	 */
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
