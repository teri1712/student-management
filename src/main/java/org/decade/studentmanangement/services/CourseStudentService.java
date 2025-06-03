package org.decade.studentmanangement.services;

import org.decade.studentmanangement.dao.CourseDao;
import org.decade.studentmanangement.dao.CourseStudentDao;
import org.decade.studentmanangement.dao.StudentDao;
import org.decade.studentmanangement.model.Course;
import org.decade.studentmanangement.model.Student;
import org.decade.studentmanangement.model.StudentCourse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseStudentService implements CourseStudentDao {
      private final ConnectionService connectionService;
      private final CourseDao courseDao;
      private final StudentDao studentDao;

      public CourseStudentService(
            ConnectionService connectionService,
            CourseDao courseDao,
            StudentDao studentDao) {
            this.connectionService = connectionService;
            this.courseDao = courseDao;
            this.studentDao = studentDao;
      }


      public int addStudentToCourse(final String student, final String course, final int year)
            throws SQLException {
            return connectionService.execute(new ConnectionCallback<Integer>() {
                  @Override
                  public Integer run(java.sql.Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("insert into Student_Course values(?,?,?,?)");
                        stmt.setString(1, student);
                        stmt.setString(2, course);
                        stmt.setInt(3, year);
                        stmt.setInt(4, 0);
                        return stmt.executeUpdate();
                  }
            });
      }

      public int updateStudentScore(final String student, final String course, final int year, final int score)
            throws SQLException {
            return connectionService.execute(new ConnectionCallback<Integer>() {

                  @Override
                  public Integer run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement(
                              "update Student_Course set score = ? where idStudent = ? and idCourse = ? and courseYear = ?");
                        stmt.setInt(1, score);
                        stmt.setString(2, student);
                        stmt.setString(3, course);
                        stmt.setInt(4, year);
                        return stmt.executeUpdate();
                  }
            });
      }

      public int deleteStudentFromCourse(final String student, final String course, final int year)
            throws SQLException {
            return connectionService.execute(new ConnectionCallback<Integer>() {
                  @Override
                  public Integer run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement(
                              "delete from Student_Course where idStudent = ? and idCourse = ? and courseYear = ?");
                        stmt.setString(1, student);
                        stmt.setString(2, course);
                        stmt.setInt(3, year);

                        return stmt.executeUpdate();
                  }
            });
      }

      public List<StudentCourse> getListStudentsByCourse(final String id, final int year)
            throws SQLException {
            return connectionService.execute(new ConnectionCallback<List<StudentCourse>>() {
                  @Override
                  public List<StudentCourse> run(Connection connection) throws SQLException {
                        Course course = courseDao.getCourse(id, year);

                        PreparedStatement stmt = connection.prepareStatement(
                              "select Student.*,Student_Course.score " +
                                    "from Student_Course " +
                                    "INNER JOIN Student ON Student.id = Student_Course.idStudent " +
                                    "where Student_Course.idCourse = ?"
                                    + "and Student_Course.courseYear = ?");
                        stmt.setString(1, id);
                        stmt.setInt(2, year);
                        ResultSet result = stmt.executeQuery();
                        List<StudentCourse> l = new ArrayList<>();
                        while (result.next()) {
                              Student student = new Student(
                                    result.getString("id"),
                                    result.getString("fullname"),
                                    result.getDate("birthday"),
                                    result.getInt("grade"),
                                    result.getString("address"),
                                    result.getString("notes")
                              );
                              int score = result.getInt("score");

                              l.add(new StudentCourse(course, student, score));
                        }
                        return l;
                  }
            });
      }

      public List<StudentCourse> getCoursesByStudentInTheYear(final String studentId, final int year) throws SQLException {
            return connectionService.execute(new ConnectionCallback<List<StudentCourse>>() {
                  @Override
                  public List<StudentCourse> run(Connection connection) throws SQLException {
                        Student student = studentDao.getStudent(studentId);
                        PreparedStatement stmt;
                        if (year != -1) {
                              stmt = connection
                                    .prepareStatement(
                                          "select Course.*,Student_Course.score " +
                                                "from Student_Course " +
                                                "INNER JOIN Course " +
                                                "ON Student_Course.idCourse = Course.id " +
                                                "where Student_Course.courseYear = Course.courseYear " +
                                                "and Student_Course.idStudent = ? " + "and Student_Course.courseYear = ?");
                              stmt.setString(1, studentId);
                              stmt.setInt(2, year);
                        } else {
                              stmt = connection
                                    .prepareStatement(
                                          "select Course.*,Student_Course.score " +
                                                "from Student_Course " +
                                                "INNER JOIN Course " +
                                                "ON Student_Course.idCourse = Course.id " +
                                                "where Student_Course.courseYear = Course.courseYear " +
                                                "and Student_Course.idStudent = ? ");
                              stmt.setString(1, studentId);
                        }
                        ResultSet result = stmt.executeQuery();
                        List<StudentCourse> l = new ArrayList<>();
                        while (result.next()) {
                              Course course = new Course(
                                    result.getString("id"),
                                    result.getString("courseName"),
                                    result.getString("lecture"),
                                    result.getInt("courseYear"),
                                    result.getString("notes")
                              );
                              int score = result.getInt("score");
                              l.add(new StudentCourse(course, student, score));
                        }
                        return l;
                  }
            });
      }
}
