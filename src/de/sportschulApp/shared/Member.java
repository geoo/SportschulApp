package de.sportschulApp.shared;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class Member implements Serializable{
	private int memberID;
	private int barcodeID;
	private String forename;
	private String surname;
	private int zipcode;
	private String city;
	private String street;
	private String phone;
	private String mobilephone;
	private String fax;
	private String email;
	private String homepage;
	private Date birth;
	private String picture;
	private String diseases;
	private String beltsize;
	private String note;
	private int trainingunits;
	private ArrayList<Integer> courses;
	private ArrayList<Integer> graduations;


	public Member(int memberID, int barcodeID, String forename, String surname,
			int zipcode, String city, String street, String phone,
			String mobilephone, String fax, String email, String homepage,
			Date birth, String picture, String diseases, String beltsize,
			String note, int trainingunits, ArrayList<Integer> courses, ArrayList<Integer> graduations) {

		this.setMemberID(memberID);
		this.setBarcodeID(barcodeID);
		this.setForename(forename);
		this.setSurname(surname);
		this.setZipcode(zipcode);
		this.setCity(city);
		this.setStreet(street);
		this.setPhone(phone);
		this.setMobilephone(mobilephone);
		this.setFax(fax);
		this.setEmail(email);
		this.setHomepage(homepage);
		this.setBirth(birth);
		this.setPicture(picture);
		this.setDiseases(diseases);
		this.setBeltsize(beltsize);
		this.setNote(note);
		this.setTrainingunits(trainingunits);
		this.setCourses(courses);
		this.setGraduations(graduations);

	}

	public Member() {
	}

	/**
	 * @param memberID the memberID to set
	 */
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	/**
	 * @return the memberID
	 */
	public int getMemberID() {
		return memberID;
	}

	/**
	 * @param barcodeID the barcodeID to set
	 */
	public void setBarcodeID(int barcodeID) {
		this.barcodeID = barcodeID;
	}

	/**
	 * @return the barcodeID
	 */
	public int getBarcodeID() {
		return barcodeID;
	}

	/**
	 * @param forename the forename to set
	 */
	public void setForename(String forename) {
		this.forename = forename;
	}

	/**
	 * @return the forename
	 */
	public String getForename() {
		return forename;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param zipcode the zipcode to set
	 */
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * @return the zipcode
	 */
	public int getZipcode() {
		return zipcode;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param mobilephone the mobilephone to set
	 */
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	/**
	 * @return the mobilephone
	 */
	public String getMobilephone() {
		return mobilephone;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param homepage the homepage to set
	 */
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	/**
	 * @return the homepage
	 */
	public String getHomepage() {
		return homepage;
	}

	/**
	 * @param date the birth to set
	 */
	public void setBirth(Date date) {
		this.birth = date;
	}

	/**
	 * @return the birth
	 */
	public Date getBirth() {
		return birth;
	}

	/**
	 * @param picture the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}

	/**
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}

	/**
	 * @param diseases the diseases to set
	 */
	public void setDiseases(String diseases) {
		this.diseases = diseases;
	}

	/**
	 * @return the diseases
	 */
	public String getDiseases() {
		return diseases;
	}

	/**
	 * @param beltsize the beltsize to set
	 */
	public void setBeltsize(String beltsize) {
		this.beltsize = beltsize;
	}

	/**
	 * @return the beltsize
	 */
	public String getBeltsize() {
		return beltsize;
	}

	/**
	 * @param note the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @param trainingunits the trainingunits to set
	 */
	public void setTrainingunits(int trainingunits) {
		this.trainingunits = trainingunits;
	}

	/**
	 * @return the trainingunits
	 */
	public int getTrainingunits() {
		return trainingunits;
	}

	/**
	 * @param courses the courses to set
	 */
	public void setCourses(ArrayList<Integer> courses) {
		this.courses = courses;
	}

	/**
	 * @return the courses
	 */
	public ArrayList<Integer> getCourses() {
		return courses;
	}

	/**
	 * @param graduations the graduations to set
	 */
	public void setGraduations(ArrayList<Integer> graduations) {
		this.graduations = graduations;
	}

	/**
	 * @return the graduations
	 */
	public ArrayList<Integer> getGraduations() {
		return graduations;
	}
}
