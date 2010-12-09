package de.sportschulApp.server.databanker;

import java.util.ArrayList;

import de.sportschulApp.shared.Course;

public interface DataBankerCourseInterface {

	public boolean deleteBelts(int courseID);

	public boolean deleteCourse(int courseID);

	public ArrayList<String> getBelts(int courseID);

	public Course getCourseByID(int courseID);

	public int getCourseID(String courseName);

	public String getCourseName(int courseID);

	public ArrayList<String> getCourseNames();

	public ArrayList<Course> getCourses();

	public String nextBelt(int courseID, int lastBelt);

	public boolean setBelts(int courseID, ArrayList<String> belts);

	public boolean updateBelts(int courseID, ArrayList<String> belts);

	public boolean updateCourse(Course course);

}
