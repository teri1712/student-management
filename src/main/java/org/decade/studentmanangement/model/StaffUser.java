package org.decade.studentmanangement.model;

public class StaffUser {

	public StaffUser(String name, String userName, String password) {
		this.name = name;
		this.userName = userName;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	private String name;
	private String userName;
	private String password;
}
