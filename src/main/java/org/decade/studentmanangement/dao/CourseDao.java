package org.decade.studentmanangement.dao;

import org.decade.studentmanangement.model.Course;

import java.sql.SQLException;
import java.util.List;

public interface CourseDao {
      Course getCourse(String id, int year) throws SQLException;

      void addCourse(Course course) throws SQLException;

      int deleteCourse(String id, int year) throws SQLException;

      List<Course> findCourses(String sortBy, int page, int limit) throws SQLException;

      int updateCourse(Course course) throws SQLException;

      List<Course> findCoursesByName(String name, String sortBy, int page, int limit) throws SQLException;

      int countCourseByName(String name) throws SQLException;

      int countCourses() throws SQLException;
}
