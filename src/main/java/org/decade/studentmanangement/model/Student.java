package org.decade.studentmanangement.model;

import java.sql.Date;

public class Student {

	public Student(String id, String name, Date birthDay, int grade, String address, String notes) {
		this.id = id;
		this.name = name;
		this.grade = grade;
		this.address = address;
		this.birdthDay = birthDay;
		this.notes = notes;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getGrade() {
		return grade;
	}

	public Date getBirdthDay() {
		return birdthDay;
	}

	public String getAddress() {
		return address;
	}

	public String getNotes() {
		return notes;
	}

	private String id;
	private String name;
	private int grade;
	private Date birdthDay;
	private String address;
	private String notes;
}
