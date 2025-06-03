package org.decade.studentmanangement.dao;

import org.decade.studentmanangement.model.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDao {
      Student getStudent(String id) throws SQLException;

      void addStudent(Student student) throws SQLException;

      void updateStudent(Student student) throws SQLException;

      List<Student> findStudentsByName(String name, String sortBy, int page, int limit) throws SQLException;

      int countStudentsByName(String name) throws SQLException;

      int countStudents() throws SQLException;

      List<Student> findStudents(int page, String sortBy, int limit) throws SQLException;

      void deleteStudent(String id) throws SQLException;
}
