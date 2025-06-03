package org.decade.studentmanangement.services;

import org.decade.studentmanangement.dao.CourseDao;
import org.decade.studentmanangement.model.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseService implements CourseDao {
      private final ConnectionService connectionService;

      public CourseService(ConnectionService connectionService) {
            this.connectionService = connectionService;
      }

      // Helper method to validate sortBy parameter to prevent SQL injection
      private String validateSortBy(String sortBy) {
            // Whitelist of allowed column names
            switch (sortBy.toLowerCase()) {
                  case "name":
                        return sortBy.toLowerCase();
                  default:
                        return "id";
            }
      }

      public Course getCourse(String id, int year) throws SQLException {
            return connectionService.execute(new ConnectionCallback<Course>() {
                  @Override
                  public Course run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("select * from Course where id = ? and courseYear = ?");
                        stmt.setString(1, id);
                        stmt.setInt(2, year);

                        ResultSet result = stmt.executeQuery();
                        if (!result.next()) {
                              return null;
                        }
                        return new Course(
                              result.getString("id"),
                              result.getString("courseName"),
                              result.getString("lecture"),
                              result.getInt("courseYear"),
                              result.getString("notes")
                        );
                  }
            });
      }


      public void addCourse(final Course course) throws SQLException {
            connectionService.execute(new ConnectionCallback<Void>() {
                  @Override
                  public Void run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("insert into Course values(?,?,?,?,?)");
                        stmt.setString(1, course.getId());
                        stmt.setNString(2, course.getName());
                        stmt.setNString(3, course.getLecture());
                        stmt.setInt(4, course.getYear());
                        stmt.setNString(5, course.getNote());
                        stmt.execute();
                        return null;
                  }
            });
      }

      public int deleteCourse(final String id, final int year)
            throws SQLException {
            return connectionService.execute(new ConnectionCallback<Integer>() {
                  @Override
                  public Integer run(Connection connection) throws SQLException {
                        PreparedStatement stmt;
                        stmt = connection
                              .prepareStatement("delete from Student_Course where idCourse = ? and courseYear = ?");
                        stmt.setString(1, id);
                        stmt.setInt(2, year);
                        stmt.executeUpdate();

                        stmt = connection.prepareStatement("delete from Course where id = ? and courseYear = ?");
                        stmt.setString(1, id);
                        stmt.setInt(2, year);
                        return stmt.executeUpdate();
                  }
            });
      }

      public List<Course> findCourses(String sortBy, int page, int limit) throws SQLException {
            String validatedSortBy = validateSortBy(sortBy);
            return connectionService.execute(new ConnectionCallback<List<Course>>() {
                  @Override
                  public List<Course> run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("select * from Course order by " + validatedSortBy + " LIMIT ? OFFSET ?");
                        stmt.setInt(1, limit);
                        stmt.setInt(2, page * limit);
                        ResultSet result = stmt.executeQuery();
                        List<Course> l = new ArrayList<>();
                        while (result.next()) {
                              Course s = new Course(result.getString("id"), result.getNString("courseName"),
                                    result.getNString("lecture"), result.getInt("courseYear"), result.getString("notes"));
                              l.add(s);
                        }
                        return l;
                  }
            });
      }

      public int updateCourse(final Course course) throws SQLException {
            return connectionService.execute(new ConnectionCallback<Integer>() {

                  @Override
                  public Integer run(Connection connection) throws SQLException {
                        PreparedStatement stmt;
                        stmt = connection.prepareStatement(
                              "update Course set courseName = ?,lecture = ?,courseYear = ?,notes = ? where id = ? and courseYear = ?");
                        stmt.setNString(1, course.getName());
                        stmt.setNString(2, course.getLecture());
                        stmt.setInt(3, course.getYear());
                        stmt.setNString(4, course.getNote());
                        stmt.setString(5, course.getId());
                        stmt.setInt(6, course.getYear());
                        return stmt.executeUpdate();
                  }
            });
      }

      @Override
      public int countCourseByName(String name) throws SQLException {
            return connectionService.execute(new ConnectionCallback<Integer>() {
                  @Override
                  public Integer run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("select COUNT(*) from Course where courseName like ?");
                        stmt.setNString(1, "%" + name + "%");
                        ResultSet rs = stmt.executeQuery();
                        rs.next();
                        return rs.getInt(1);
                  }
            });
      }

      @Override
      public int countCourses() throws SQLException {
            return connectionService.execute(new ConnectionCallback<Integer>() {
                  @Override
                  public Integer run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("select COUNT(*) from Course");
                        ResultSet rs = stmt.executeQuery();
                        rs.next();
                        return rs.getInt(1);
                  }
            });
      }

      public List<Course> findCoursesByName(final String name, String sortBy, int page, int limit)
            throws SQLException {
            String validatedSortBy = validateSortBy(sortBy);
            return connectionService.execute(new ConnectionCallback<List<Course>>() {
                  @Override
                  public List<Course> run(Connection connection) throws SQLException {
                        PreparedStatement stmt = connection.prepareStatement("select * from Course where courseName like ? order by " + validatedSortBy + " LIMIT ? OFFSET ?");
                        stmt.setNString(1, "%" + name + "%");
                        stmt.setInt(2, limit);
                        stmt.setInt(3, page * limit);
                        ResultSet result = stmt.executeQuery();
                        List<Course> l = new ArrayList<>();
                        while (result.next()) {
                              Course s = new Course(
                                    result.getString("id"),
                                    result.getNString("courseName"),
                                    result.getNString("lecture"),
                                    result.getInt("courseYear"),
                                    result.getString("notes"));
                              l.add(s);
                        }
                        return l;
                  }
            });
      }
}
