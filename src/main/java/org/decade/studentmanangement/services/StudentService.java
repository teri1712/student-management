package org.decade.studentmanangement.services;

import org.decade.studentmanangement.dao.StudentDao;
import org.decade.studentmanangement.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentDao {
      private final ConnectionService connectionService;

      public StudentService(ConnectionService connectionService) {
            this.connectionService = connectionService;
      }

      // Helper method to validate sortBy parameter to prevent SQL injection
      private String validateSortBy(String sortBy) {
            // Whitelist of allowed column names
            switch (sortBy.toLowerCase()) {
                  case "id":
                  case "fullname":
                  case "birthday":
                  case "grade":
                  case "address":
                  case "notes":
                        return sortBy.toLowerCase();
                  default:
                        return "id";
            }
      }


      public Student getStudent(String id) throws SQLException {
            return connectionService.execute(new ConnectionCallback<Student>() {
                  @Override
                  public Student run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("select * from Student where id = ?");
                        stmt.setString(1, id);
                        ResultSet result = stmt.executeQuery();
                        if (!result.next()) {
                              return null;
                        }
                        return new Student(
                              result.getString("id"),
                              result.getString("fullname"),
                              result.getDate("birthday"),
                              result.getInt("grade"),
                              result.getString("address"),
                              result.getString("notes")
                        );
                  }
            });
      }

      public void addStudent(final Student student) throws SQLException {
            connectionService.execute(new ConnectionCallback<Void>() {
                  @Override
                  public Void run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("insert into Student values(?,?,?,?,?,?)");
                        stmt.setString(1, student.getId());
                        stmt.setNString(2, student.getName());
                        stmt.setDate(3, student.getBirthDay());
                        stmt.setInt(4, student.getGrade());
                        stmt.setNString(5, student.getAddress());
                        stmt.setNString(6, student.getNotes());
                        stmt.execute();
                        return null;
                  }
            });
      }

      public void updateStudent(final Student student) throws SQLException {
            connectionService.execute(new ConnectionCallback<Void>() {
                  @Override
                  public Void run(Connection connection) throws SQLException {
                        PreparedStatement stmt;
                        stmt = connection.prepareStatement(
                              "update Student set fullname = ?,birthday = ?,grade = ?,address = ?,notes = ? where id = ?");
                        stmt.setNString(1, student.getName());
                        stmt.setDate(2, student.getBirthDay());
                        stmt.setInt(3, student.getGrade());
                        stmt.setNString(4, student.getAddress());
                        stmt.setNString(5, student.getNotes());
                        stmt.setString(6, student.getId());
                        stmt.executeUpdate();
                        return null;

                  }
            });
      }

      public List<Student> findStudentsByName(String name, String sortBy, int page, int limit) throws SQLException {
            // Validate sortBy to prevent SQL injection
            String validatedSortBy = validateSortBy(sortBy);

            return connectionService.execute(new ConnectionCallback<List<Student>>() {
                  @Override
                  public List<Student> run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("select * from Student where fullname like ? order by " + validatedSortBy + " LIMIT ? OFFSET ?");
                        stmt.setNString(1, "%" + name + "%");
                        stmt.setInt(2, limit);
                        stmt.setInt(3, page * limit);
                        ResultSet result = stmt.executeQuery();
                        List<Student> l = new ArrayList<>();
                        while (result.next()) {
                              Student s = new Student(result.getString("id"), result.getNString("fullname"),
                                    result.getDate("birthday"), result.getInt("grade"), result.getString("address"),
                                    result.getString("notes"));
                              l.add(s);
                        }
                        return l;
                  }
            });
      }

      public int countStudentsByName(String name) throws SQLException {
            return connectionService.execute(new ConnectionCallback<Integer>() {
                  @Override
                  public Integer run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("select COUNT(*) from Student where fullname like ?");
                        stmt.setNString(1, "%" + name + "%");
                        ResultSet rs = stmt.executeQuery();
                        rs.next();
                        return rs.getInt(1);
                  }
            });
      }

      public int countStudents() throws SQLException {
            return connectionService.execute(new ConnectionCallback<Integer>() {
                  @Override
                  public Integer run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("select COUNT(*) from Student");
                        ResultSet rs = stmt.executeQuery();
                        rs.next();
                        return rs.getInt(1);
                  }
            });
      }

      public List<Student> findStudents(int page, String sortBy, int limit) throws SQLException {
            // Validate sortBy to prevent SQL injection
            String validatedSortBy = validateSortBy(sortBy);

            return connectionService.execute(new ConnectionCallback<List<Student>>() {
                  @Override
                  public List<Student> run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("select * from Student order by " + validatedSortBy + " LIMIT ? OFFSET ?");
                        stmt.setInt(1, limit);
                        stmt.setInt(2, page * limit);
                        ResultSet result = stmt.executeQuery();
                        List<Student> l = new ArrayList<>();
                        while (result.next()) {
                              Student s = new Student(result.getString("id"), result.getNString("fullname"),
                                    result.getDate("birthday"), result.getInt("grade"), result.getString("address"),
                                    result.getString("notes"));
                              l.add(s);
                        }
                        return l;
                  }
            });
      }

      public void deleteStudent(final String id) throws SQLException {
            connectionService.execute(new ConnectionCallback<Void>() {
                  @Override
                  public Void run(Connection connection) throws SQLException {
                        PreparedStatement stmt;
                        stmt = connection.prepareStatement("delete from Student_Course where idStudent = ?");
                        stmt.setString(1, id);
                        stmt.executeUpdate();

                        stmt = connection.prepareStatement("delete from Student where id = ?");
                        stmt.setString(1, id);
                        stmt.executeUpdate();
                        return null;
                  }
            });
      }
}
