package org.decade.studentmanangement.dao;

import org.decade.studentmanangement.model.StudentCourse;

import java.sql.SQLException;
import java.util.List;

public interface CourseStudentDao {
      int addStudentToCourse(String student, String course, int year) throws SQLException;

      int updateStudentScore(String student, String course, int year, int score) throws SQLException;

      int deleteStudentFromCourse(String student, String course, int year) throws SQLException;

      List<StudentCourse> getListStudentsByCourse(String id, int year) throws SQLException;

      List<StudentCourse> getCoursesByStudentInTheYear(String studentId, int year) throws SQLException;
}