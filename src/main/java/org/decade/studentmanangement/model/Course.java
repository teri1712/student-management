package org.decade.studentmanangement.model;

public class Course {

      public Course(String id, String name, String lecture, int year, String note) {
            this.id = id;
            this.name = name;
            this.lecture = lecture;
            this.year = year;
            this.note = note;
      }

      public String getId() {
            return id;
      }

      public String getName() {
            return name;
      }

      public String getLecture() {
            return lecture;
      }

      public int getYear() {
            return year;
      }

      public String getNote() {
            return note;
      }

      public void setId(String id) {
            this.id = id;
      }

      public void setName(String name) {
            this.name = name;
      }

      public void setLecture(String lecture) {
            this.lecture = lecture;
      }

      public void setYear(int year) {
            this.year = year;
      }

      public void setNote(String note) {
            this.note = note;
      }

      private String id;
      private String name;
      private String lecture;
      private int year;
      private String note;
}
