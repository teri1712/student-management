package org.decade.studentmanangement.dao;

import org.decade.studentmanangement.model.StudentCourse;

import java.util.List;

public interface CourseStudentDao {
        int addStudentToCourse(String student, String course, int year) throws Exception;

        int updateStudentScore(String student, String course, int year, int score) throws Exception;

        int deleteStudentFromCourse(String student, String course, int year) throws Exception;

        List<StudentCourse> getListStudentsByCourse(String id, int year) throws Exception;

        List<StudentCourse> getCoursesByStudentInTheYear(String studentId, int year) throws Exception;

        int countStudentsOfCourse(String courseId, int year) throws Exception;
}