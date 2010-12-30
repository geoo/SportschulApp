package de.sportschulApp.shared;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Member implements Serializable {
	private String accountForename;
	private String accountNumber;
	private String accountSurname;
	private String bankName;
	private String bankNumber;
	private int barcodeID;
	private String beltsize;
	private String birthDay;
	private String birthMonth;
	private String birthYear;
	private String city;
	private ArrayList<Integer> courses;
	private String diseases;
	private String email;
	private String fax;
	private String forename;
	private ArrayList<Integer> graduations;
	private ArrayList<Float> tariffs;
	private String homepage;
	private int memberID;
	private String mobilephone;
	private String note;
	private String phone;
	private String picture;
	private String street;
	private String surname;
	private int trainingunits;
	private int zipcode;
	private int trainingUnitsInMonth;
	
	public Member() {
	}

	public Member(int memberID, int barcodeID, String forename, String surname,
			int zipcode, String city, String street, String phone,
			String mobilephone, String fax, String email, String homepage,
			String birthDay, String birthMonth, String birthYear,
			String picture, String diseases, String beltsize, String note,
			int trainingunits, String accountForename, String accountSurname,
			String accountNumber, String bankName, String bankNumber,
			ArrayList<Integer> courses, ArrayList<Integer> graduations,
			ArrayList<Float> tariffs) {

		setMemberID(memberID);
		setBarcodeID(barcodeID);
		setForename(forename);
		setSurname(surname);
		setZipcode(zipcode);
		setCity(city);
		setStreet(street);
		setPhone(phone);
		setMobilephone(mobilephone);
		setFax(fax);
		setEmail(email);
		setHomepage(homepage);
		setBirthDay(birthDay);
		setBirthMonth(birthMonth);
		setBirthYear(birthYear);
		setPicture(picture);
		setDiseases(diseases);
		setBeltsize(beltsize);
		setNote(note);
		setTrainingunits(trainingunits);
		setCourses(courses);
		setGraduations(graduations);
		setTariffs(tariffs);
		setAccountForename(accountForename);
		setAccountSurname(accountSurname);
		setAccountNumber(accountNumber);
		setBankName(bankName);
		setBankNumber(bankNumber);

	}

	public void setTariffs(ArrayList<Float> tariffs) {
		this.tariffs = tariffs;
	}


	/**
	 * @return the accountForename
	 */
	public String getAccountForename() {
		return accountForename;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @return the accountSurname
	 */
	public String getAccountSurname() {
		return accountSurname;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @return the bankNumber
	 */
	public String getBankNumber() {
		return bankNumber;
	}

	/**
	 * @return the barcodeID
	 */
	public int getBarcodeID() {
		return barcodeID;
	}

	/**
	 * @return the beltsize
	 */
	public String getBeltsize() {
		return beltsize;
	}

	/**
	 * @return the birthDay
	 */
	public String getBirthDay() {
		return birthDay;
	}

	/**
	 * @return the birthMonth
	 */
	public String getBirthMonth() {
		return birthMonth;
	}

	/**
	 * @return the birthYear
	 */
	public String getBirthYear() {
		return birthYear;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return the courses
	 */
	public ArrayList<Integer> getCourses() {
		return courses;
	}
	
	/**
	 * @return the tariffs
	 */
	public ArrayList<Float> getTariffs() {
		return tariffs;
	}
	
	/**
	 * @return the diseases
	 */
	public String getDiseases() {
		return diseases;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @return the forename
	 */
	public String getForename() {
		return forename;
	}

	/**
	 * @return the graduations
	 */
	public ArrayList<Integer> getGraduations() {
		return graduations;
	}

	/**
	 * @return the homepage
	 */
	public String getHomepage() {
		return homepage;
	}

	/**
	 * @return the memberID
	 */
	public int getMemberID() {
		return memberID;
	}

	/**
	 * @return the mobilephone
	 */
	public String getMobilephone() {
		return mobilephone;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return note;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @return the picture
	 */
	public String getPicture() {
		return picture;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @return the trainingunits
	 */
	public int getTrainingunits() {
		return trainingunits;
	}

	/**
	 * @return the zipcode
	 */
	public int getZipcode() {
		return zipcode;
	}

	/**
	 * @param accountForename
	 *            the accountForename to set
	 */
	public void setAccountForename(String accountForename) {
		this.accountForename = accountForename;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @param accountSurname
	 *            the accountSurname to set
	 */
	public void setAccountSurname(String accountSurname) {
		this.accountSurname = accountSurname;
	}

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @param bankNumber
	 *            the bankNumber to set
	 */
	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	/**
	 * @param barcodeID
	 *            the barcodeID to set
	 */
	public void setBarcodeID(int barcodeID) {
		this.barcodeID = barcodeID;
	}

	/**
	 * @param beltsize
	 *            the beltsize to set
	 */
	public void setBeltsize(String beltsize) {
		this.beltsize = beltsize;
	}

	/**
	 * @param birthDay
	 *            the birthDay to set
	 */
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	/**
	 * @param birthMonth
	 *            the birthMonth to set
	 */
	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}

	/**
	 * @param birthYear
	 *            the birthYear to set
	 */
	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @param courses
	 *            the courses to set
	 */
	public void setCourses(ArrayList<Integer> courses) {
		this.courses = courses;
	}

	/**
	 * @param diseases
	 *            the diseases to set
	 */
	public void setDiseases(String diseases) {
		this.diseases = diseases;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @param forename
	 *            the forename to set
	 */
	public void setForename(String forename) {
		this.forename = forename;
	}

	/**
	 * @param graduations
	 *            the graduations to set
	 */
	public void setGraduations(ArrayList<Integer> graduations) {
		this.graduations = graduations;
	}

	/**
	 * @param homepage
	 *            the homepage to set
	 */
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	/**
	 * @param memberID
	 *            the memberID to set
	 */
	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	/**
	 * @param mobilephone
	 *            the mobilephone to set
	 */
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @param picture
	 *            the picture to set
	 */
	public void setPicture(String picture) {
		this.picture = picture;
	}

	/**
	 * @param street
	 *            the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @param surname
	 *            the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @param trainingunits
	 *            the trainingunits to set
	 */
	public void setTrainingunits(int trainingunits) {
		this.trainingunits = trainingunits;
	}

	/**
	 * @param zipcode
	 *            the zipcode to set
	 */
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	/**
	 * @param trainingUnitsInMonth the trainingUnitsInMonth to set
	 */
	public void setTrainingUnitsInMonth(int trainingUnitsInMonth) {
		this.trainingUnitsInMonth = trainingUnitsInMonth;
	}

	/**
	 * @return the trainingUnitsInMonth
	 */
	public int getTrainingUnitsInMonth() {
		return trainingUnitsInMonth;
	}

}
