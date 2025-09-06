package org.decade.studentmanangement.dao;

import org.decade.studentmanangement.model.Student;

import java.util.List;

public interface StudentDao {
        Student getStudent(String id) throws Exception;

        void addStudent(Student student) throws Exception;

        void updateStudent(Student student) throws Exception;

        List<Student> findStudentsByName(String name, String sortBy, int page, int limit) throws Exception;

        int countStudentsByName(String name) throws Exception;

        int countStudents() throws Exception;

        List<Student> findStudents(int page, String sortBy, int limit) throws Exception;

        void deleteStudent(String id) throws Exception;
}
