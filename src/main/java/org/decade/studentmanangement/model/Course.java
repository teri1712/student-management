package org.decade.studentmanangement.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Course")
@IdClass(Course.CoursePk.class)
public class Course {

      public Course() {
      }

      public Course(String id, String name, String lecture, int year, String note) {
            this.id = id;
            this.name = name;
            this.lecture = lecture;
            this.year = year;
            this.note = note;
      }

      @Id
      @Column(name = "id", length = 10)
      private String id;

      @Id
      @Column(name = "courseYear")
      private int year;

      @Column(name = "courseName", columnDefinition = "nchar(100)")
      private String name;

      @Column(name = "lecture", columnDefinition = "nchar(100)")
      private String lecture;

      @Column(name = "notes", columnDefinition = "nchar(100)")
      private String note;

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

      public static class CoursePk implements Serializable {
            private String id;
            private int year;

            public CoursePk() {}

            public CoursePk(String id, int year) {
                  this.id = id;
                  this.year = year;
            }

            public String getId() { return id; }
            public void setId(String id) { this.id = id; }
            public int getYear() { return year; }
            public void setYear(int year) { this.year = year; }

            @Override
            public boolean equals(Object o) {
                  if (this == o) return true;
                  if (o == null || getClass() != o.getClass()) return false;
                  CoursePk pk = (CoursePk) o;
                  return year == pk.year && (id != null ? id.equals(pk.id) : pk.id == null);
            }

            @Override
            public int hashCode() {
                  int result = id != null ? id.hashCode() : 0;
                  result = 31 * result + year;
                  return result;
            }
      }
}
