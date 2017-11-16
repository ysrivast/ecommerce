package com.test.model;

public class UserAccount {

	public static final String GENDER_MALE = "M";
	public static final String GENDER_FEMALE = "F";
	private String UserName;
	private String gender;
	private String password;

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
