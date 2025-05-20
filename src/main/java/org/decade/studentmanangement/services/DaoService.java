package org.decade.studentmanangement.services;

import org.decade.studentmanangement.model.Course;
import org.decade.studentmanangement.model.StaffUser;
import org.decade.studentmanangement.model.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @author MinhTri
 */
public class DaoService {

	private static DaoService connector;
	private Connection connection;
	private ExecutorService executor;

	private DaoService() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/QuanLySinhVien", "root", "admin");
		} catch (Exception ex) {
			System.err.println(ex);
		}
		executor = Executors.newFixedThreadPool(1);
	}

	public String validateUserProcdedure(final StaffUser user) throws InterruptedException, ExecutionException {
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {

				PreparedStatement stmt = connection.prepareStatement("select * from StaffUser where username = ?");
				stmt.setString(1, user.getUserName());

				ResultSet result = stmt.executeQuery();
				if (!result.next()) {
					return "username does not exist";
				}
				if (!result.getString("pw").equals(user.getPassword())) {
					return "wrong password";
				}

				return "success";
			}
		};
		Future<String> future = executor.submit(c);
		return future.get();
	}

	public String signUpUserProcdedure(final StaffUser user) throws InterruptedException, ExecutionException {
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {
				PreparedStatement stmt = connection.prepareStatement("select * from StaffUser where username = ?");
				stmt.setString(1, user.getUserName());
				ResultSet result = stmt.executeQuery();
				if (result.next()) {
					return "username exist";
				}

				stmt = connection.prepareStatement("insert into StaffUser values(?,?,?)");
				stmt.setString(1, user.getUserName());
				stmt.setString(2, user.getPassword());
				stmt.setString(3, user.getName());
				stmt.execute();
				return "success";
			}
		};
		Future<String> future = executor.submit(c);
		return future.get();
	}

	public Student getStudent(String id) throws InterruptedException, ExecutionException {
		Callable<Student> c = new Callable<Student>() {
			@Override
			public Student call() throws Exception {
				PreparedStatement stmt = connection.prepareStatement("select * from Student where id = ?");
				stmt.setString(1, id);
				ResultSet result = stmt.executeQuery();
				if (!result.next()) {
					return null;
				}
				return new Student(result.getString("id"), result.getString("fullname"), result.getDate("birthday"),
						result.getInt("grade"), result.getString("address"), result.getString("notes"));
			}
		};
		Future<Student> future = executor.submit(c);
		return future.get();
	}

	public Course getCourse(String id, int year) throws InterruptedException, ExecutionException {
		Callable<Course> c = new Callable<Course>() {
			@Override
			public Course call() throws Exception {
				PreparedStatement stmt = connection
						.prepareStatement("select * from Course where id = ? and courseYear = ?");
				stmt.setString(1, id);
				stmt.setInt(2, year);

				ResultSet result = stmt.executeQuery();
				if (!result.next()) {
					return null;
				}
				return new Course(result.getString("id"), result.getString("courseName"), result.getString("lecture"),
						result.getInt("courseYear"), result.getString("notes"));
			}
		};
		Future<Course> future = executor.submit(c);
		return future.get();
	}

	public String addStudentProcdedure(final Student student) throws InterruptedException, ExecutionException {
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {
				PreparedStatement stmt = connection.prepareStatement("select * from Student where id = ?");
				stmt.setString(1, student.getId());
				ResultSet result = stmt.executeQuery();
				if (result.next()) {
					return "student Id exist";
				}
				stmt = connection.prepareStatement("insert into Student values(?,?,?,?,?,?)");
				stmt.setString(1, student.getId());
				stmt.setNString(2, student.getName());
				stmt.setDate(3, student.getBirdthDay());
				stmt.setInt(4, student.getGrade());
				stmt.setNString(5, student.getAddress());
				stmt.setNString(6, student.getNotes());
				stmt.execute();
				return "success";
			}
		};
		Future<String> future = executor.submit(c);
		return future.get();
	}

	public String editStudentProcdedure(final Student student) throws InterruptedException, ExecutionException {
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {
				PreparedStatement stmt;
				stmt = connection.prepareStatement(
						"update Student set fullname = ?,birthday = ?,grade = ?,address = ?,notes = ? where id = ?");
				stmt.setNString(1, student.getName());
				stmt.setDate(2, student.getBirdthDay());
				stmt.setInt(3, student.getGrade());
				stmt.setNString(4, student.getAddress());
				stmt.setNString(5, student.getNotes());
				stmt.setString(6, student.getId());
				if (stmt.executeUpdate() == 1) {
					return "success";
				}
				return "failed";
			}
		};
		Future<String> future = executor.submit(c);
		return future.get();
	}

	public ArrayList<Student> getListStudentsByNameProcdedure(final String name)
			throws InterruptedException, ExecutionException {
		Callable<ArrayList<Student>> c = new Callable<ArrayList<Student>>() {
			@Override
			public ArrayList<Student> call() throws Exception {
				PreparedStatement stmt = connection.prepareStatement("select * from Student where fullname = ?");
				stmt.setNString(1, name);
				ResultSet result = stmt.executeQuery();
				ArrayList<Student> l = new ArrayList<>();
				while (result.next()) {
					Student s = new Student(result.getString("id"), result.getNString("fullname"),
							result.getDate("birthday"), result.getInt("grade"), result.getString("address"),
							result.getString("notes"));
					l.add(s);
				}
				return l;
			}
		};
		Future<ArrayList<Student>> future = executor.submit(c);
		return future.get();
	}

	public ArrayList<Student> getListStudentsProcdedure() throws InterruptedException, ExecutionException {
		Callable<ArrayList<Student>> c = new Callable<ArrayList<Student>>() {
			@Override
			public ArrayList<Student> call() throws Exception {
				PreparedStatement stmt = connection.prepareStatement("select * from Student");
				ResultSet result = stmt.executeQuery();
				ArrayList<Student> l = new ArrayList<>();
				while (result.next()) {
					Student s = new Student(result.getString("id"), result.getNString("fullname"),
							result.getDate("birthday"), result.getInt("grade"), result.getString("address"),
							result.getString("notes"));
					l.add(s);
				}
				return l;
			}
		};
		Future<ArrayList<Student>> future = executor.submit(c);
		return future.get();
	}

	public String addCourseProcdedure(final Course course) throws InterruptedException, ExecutionException {
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {
				PreparedStatement stmt = connection
						.prepareStatement("select * from Course where id = ? and courseYear = ?");
				stmt.setString(1, course.getId());
				stmt.setInt(2, course.getYear());
				ResultSet result = stmt.executeQuery();
				if (result.next()) {
					return "course exists";
				}
				stmt = connection.prepareStatement("insert into Course values(?,?,?,?,?)");
				stmt.setString(1, course.getId());
				stmt.setNString(2, course.getName());
				stmt.setNString(3, course.getLecture());
				stmt.setInt(4, course.getYear());
				stmt.setNString(5, course.getNote());
				stmt.execute();
				return "success";
			}
		};
		Future<String> future = executor.submit(c);
		return future.get();
	}

	public String deleteStudentProcdedure(final String id) throws InterruptedException, ExecutionException {
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {
				PreparedStatement stmt;
				stmt = connection.prepareStatement("delete  from Student where id = ?");
				stmt.setString(1, id);

				if (stmt.executeUpdate() != 0) {
					stmt = connection.prepareStatement("delete from Student_Course where idStudent = ?");
					stmt.setString(1, id);
					stmt.executeUpdate();
					return "success";
				}
				return "failed";
			}
		};
		Future<String> future = executor.submit(c);
		return future.get();
	}

	public String deleteCourseProcdedure(final String id, final int year)
			throws InterruptedException, ExecutionException {
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {
				PreparedStatement stmt;
				stmt = connection.prepareStatement("delete from Course where id = ? and courseYear = ?");
				stmt.setString(1, id);
				stmt.setInt(2, year);
				if (stmt.executeUpdate() != 0) {
					stmt = connection
							.prepareStatement("delete from Student_Course where idCourse = ? and courseYear = ?");
					stmt.setString(1, id);
					stmt.setInt(2, year);
					stmt.executeUpdate();
					return "success";
				}
				return "failed";
			}
		};
		Future<String> future = executor.submit(c);
		return future.get();
	}

	public ArrayList<Course> getListCourseProcdedure() throws InterruptedException, ExecutionException {
		Callable<ArrayList<Course>> c = new Callable<ArrayList<Course>>() {
			@Override
			public ArrayList<Course> call() throws Exception {
				PreparedStatement stmt = connection.prepareStatement("select * from Course");
				ResultSet result = stmt.executeQuery();
				ArrayList<Course> l = new ArrayList<>();
				while (result.next()) {
					Course s = new Course(result.getString("id"), result.getNString("courseName"),
							result.getNString("lecture"), result.getInt("courseYear"), result.getString("notes"));
					l.add(s);
				}
				return l;
			}
		};
		Future<ArrayList<Course>> future = executor.submit(c);
		return future.get();
	}

	public String editCourseProcdedure(final Course course) throws InterruptedException, ExecutionException {
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {
				PreparedStatement stmt;
				stmt = connection.prepareStatement(
						"update Course set courseName = ?,lecture = ?,courseYear = ?,notes = ? where id = ? and courseYear = ?");
				stmt.setNString(1, course.getName());
				stmt.setNString(2, course.getLecture());
				stmt.setInt(3, course.getYear());
				stmt.setNString(4, course.getNote());
				stmt.setString(5, course.getId());
				stmt.setInt(6, course.getYear());
				if (stmt.executeUpdate() == 1) {
					return "success";
				}
				return "failed";
			}
		};
		Future<String> future = executor.submit(c);
		return future.get();
	}

	public ArrayList<Course> getListCourseByNameProcdedure(final String name)
			throws InterruptedException, ExecutionException {
		Callable<ArrayList<Course>> c = new Callable<ArrayList<Course>>() {
			@Override
			public ArrayList<Course> call() throws Exception {
				PreparedStatement stmt = connection.prepareStatement("select * from Course where courseName = ?");
				stmt.setNString(1, name);
				ResultSet result = stmt.executeQuery();
				ArrayList<Course> l = new ArrayList<>();
				while (result.next()) {
					Course s = new Course(result.getString("id"), result.getNString("courseName"),
							result.getNString("lecture"), result.getInt("courseYear"), result.getString("notes"));
					l.add(s);
				}
				return l;
			}
		};
		Future<ArrayList<Course>> future = executor.submit(c);
		return future.get();
	}

	public String addStudentToCourse(final String student, final String course, final int year)
			throws InterruptedException, ExecutionException {
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {
				PreparedStatement stmt = connection.prepareStatement("select * from Student where id = ?");
				stmt.setString(1, student);
				ResultSet result = stmt.executeQuery();
				if (!result.next()) {
					return "student does not exist";
				}
				stmt = connection.prepareStatement(
						"select * from Student_Course where idStudent = ? and idCourse = ? and courseYear = ?");
				stmt.setString(1, student);
				stmt.setString(2, course);
				stmt.setInt(3, year);
				result = stmt.executeQuery();
				if (result.next()) {
					return "student was already in the course.";
				}
				stmt = connection.prepareStatement("insert into Student_Course values(?,?,?,?)");
				stmt.setString(1, student);
				stmt.setString(2, course);
				stmt.setInt(3, year);
				stmt.setInt(4, -1);
				if (stmt.executeUpdate() == 0) {
					return "failed";
				}
				return "success";
			}
		};
		Future<String> future = executor.submit(c);
		return future.get();
	}

	public String updateStudentScore(final String student, final String course, final int year, final int score)
			throws InterruptedException, ExecutionException {
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {
				PreparedStatement stmt;
				stmt = connection.prepareStatement(
						"update Student_Course set score = ? where idStudent = ? and idCourse = ? and courseYear = ?");
				stmt.setInt(1, score);
				stmt.setString(2, student);
				stmt.setString(3, course);
				stmt.setInt(4, year);
				if (stmt.executeUpdate() == 0) {
					return "course contains this student";
				}
				return "success";
			}
		};
		Future<String> future = executor.submit(c);
		return future.get();
	}

	public String deleteStudentFromCourse(final String student, final String course, final int year)
			throws InterruptedException, ExecutionException {
		Callable<String> c = new Callable<String>() {
			@Override
			public String call() throws Exception {
				PreparedStatement stmt;
				stmt = connection.prepareStatement(
						"delete from Student_Course where idStudent = ? and idCourse = ? and courseYear = ?");
				stmt.setString(1, student);
				stmt.setString(2, course);
				stmt.setInt(3, year);

				if (stmt.executeUpdate() == 0) {
					return "failed";
				}
				return "success";
			}
		};
		Future<String> future = executor.submit(c);
		return future.get();
	}

	public ArrayList<TreeMap<String, String>> getListStudentsByCourseProcdedure(final String course, final int year)
			throws InterruptedException, ExecutionException {
		Callable<ArrayList<TreeMap<String, String>>> c = new Callable<ArrayList<TreeMap<String, String>>>() {
			@Override
			public ArrayList<TreeMap<String, String>> call() throws Exception {
				PreparedStatement stmt = connection.prepareStatement(
						"" + "select Student.*,Student_Course.score from Student,Student_Course where "
								+ "Student.id = Student_Course.idStudent and Student_Course.idCourse = ?"
								+ "and Student_Course.courseYear = ?");
				stmt.setString(1, course);
				stmt.setInt(2, year);
				ResultSet result = stmt.executeQuery();
				ArrayList<TreeMap<String, String>> l = new ArrayList<>();
				while (result.next()) {
					TreeMap<String, String> m = new TreeMap<>();
					m.put("id", result.getString("id"));
					m.put("fullname", result.getNString("fullname"));
					m.put("birthday", result.getDate("birthday").toString());
					m.put("grade", Integer.toString(result.getInt("grade")));
					m.put("address", result.getString("address"));
					m.put("notes", result.getString("notes"));
					int score = result.getInt("score");
					m.put("score", (score == -1) ? "no data" : Integer.toString(score));
					l.add(m);
				}
				return l;
			}
		};
		Future<ArrayList<TreeMap<String, String>>> future = executor.submit(c);
		return future.get();
	}

	public ArrayList<TreeMap<String, String>> getListCourseByStudentInTheYearProcdedure(final String student,
			final int year) throws InterruptedException, ExecutionException {
		Callable<ArrayList<TreeMap<String, String>>> c = new Callable<ArrayList<TreeMap<String, String>>>() {
			@Override
			public ArrayList<TreeMap<String, String>> call() throws Exception {
				PreparedStatement stmt = connection
						.prepareStatement("select Course.*,Student_Course.score from Course,Student_Course " + "where "
								+ "Course.id = Student_Course.idCourse "
								+ "and Student_Course.courseYear = Course.courseYear "
								+ "and Student_Course.idStudent = ? " + "and Student_Course.courseYear = ?");
				stmt.setString(1, student);
				stmt.setInt(2, year);
				ResultSet result = stmt.executeQuery();
				ArrayList<TreeMap<String, String>> l = new ArrayList<>();
				while (result.next()) {
					TreeMap<String, String> s = new TreeMap<>();
					s.put("id", result.getString("id"));
					s.put("name", result.getNString("courseName"));
					s.put("lecture", result.getNString("lecture"));
					s.put("year", Integer.toString(result.getInt("courseYear")));
					s.put("notes", result.getString("notes"));
					int score = result.getInt("score");
					s.put("score", (score == -1) ? "no data" : Integer.toString(score));
					l.add(s);
				}
				return l;
			}
		};
		Future<ArrayList<TreeMap<String, String>>> future = executor.submit(c);
		return future.get();
	}

	static {
		connector = new DaoService();
	}

	public static DaoService getInstance() {
		return connector;
	}
}
