package org.decade.studentmanangement.dao;

import org.decade.studentmanangement.model.Course;

import java.util.List;

public interface CourseDao {
        Course getCourse(String id, int year) throws Exception;

        void addCourse(Course course) throws Exception;

        int deleteCourse(String id, int year) throws Exception;

        List<Course> findCourses(String sortBy, int page, int limit) throws Exception;

        int updateCourse(Course course) throws Exception;

        List<Course> findCoursesByName(String name, String sortBy, int page, int limit) throws Exception;

        int countCourseByName(String name) throws Exception;

        int countCourses() throws Exception;

        // New: lecturer-based queries for teacher views
        List<Course> findCoursesByLecturer(String lecturerUsername, String sortBy, int page, int limit) throws Exception;

        int countCoursesByLecturer(String lecturerUsername) throws Exception;

        // Lecturer + year filters for teacher view
        List<Course> findCoursesByLecturerAndYear(String lecturerUsername, int year, String sortBy, int page, int limit) throws Exception;

        int countCoursesByLecturerAndYear(String lecturerUsername, int year) throws Exception;
}
