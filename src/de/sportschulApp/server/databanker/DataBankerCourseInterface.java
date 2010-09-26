package de.sportschulApp.server.databanker;

import java.util.ArrayList;

import de.sportschulApp.shared.Course;

public interface DataBankerCourseInterface {

	public String createCourse(Course course);
	
	public boolean updateCourse(Course course);
	
	public boolean deleteCourse(int courseID);
	
	public ArrayList<Course> getCourses();
	
	public ArrayList<String> getCourseNames();
	
	public String getCourseName(int courseID);
	
	public int getCourseID(String courseName);
	
	public ArrayList<String> getBelts(int courseID);
	
	public boolean setBelts(int courseID, ArrayList<String> belts);

	public boolean updateBelts(int courseID, ArrayList<String> belts);
	
	public boolean deleteBelts(int courseID);
	
	public String nextBelt(int courseID, int lastBelt);

}
