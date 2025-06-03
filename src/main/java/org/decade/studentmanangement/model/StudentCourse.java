package org.decade.studentmanangement.model;

public class StudentCourse {
      private Course course;
      private Student student;
      private int score;

      public StudentCourse(Course course, Student student, int score) {
            this.course = course;
            this.student = student;
            this.score = score;
      }

      public Course getCourse() {
            return course;
      }

      public void setCourse(Course course) {
            this.course = course;
      }

      public Student getStudent() {
            return student;
      }

      public void setStudent(Student student) {
            this.student = student;
      }

      public int getScore() {
            return score;
      }

      public void setScore(int score) {
            this.score = score;
      }
}
