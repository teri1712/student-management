package org.decade.studentmanangement.model;

import java.sql.Date;

public class Student {

      private String id;
      private String name;
      private int grade;
      private Date birthDay;
      private String address;
      private String notes;

      public Student(String id, String name, Date birthDay, int grade, String address, String notes) {
            this.id = id;
            this.name = name;
            this.grade = grade;
            this.address = address;
            this.birthDay = birthDay;
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

      public Date getBirthDay() {
            return birthDay;
      }

      public void setId(String id) {
            this.id = id;
      }

      public void setName(String name) {
            this.name = name;
      }

      public void setGrade(int grade) {
            this.grade = grade;
      }

      public void setBirthDay(Date birthDay) {
            this.birthDay = birthDay;
      }

      public void setAddress(String address) {
            this.address = address;
      }

      public void setNotes(String notes) {
            this.notes = notes;
      }

      public String getAddress() {
            return address;
      }

      public String getNotes() {
            return notes;
      }

}