package org.decade.studentmanangement.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.decade.studentmanangement.dao.CourseStudentDao;
import org.decade.studentmanangement.dao.StudentDao;
import org.decade.studentmanangement.model.Student;
import org.decade.studentmanangement.model.StudentCourse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@WebServlet("/management/student/*")
public class StudentServlet extends HttpServlet {

      @Resource(name = "services/StudentDao")
      private StudentDao studentDao;

      @Resource(name = "services/CourseStudentDao")
      private CourseStudentDao courseStudentDao;

      public final int PAGE_LIMIT = 10;


      private void handleListRequest(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
            String query = request.getParameter("query");
            String sortBy = request.getParameter("sortBy");
            String pageString = request.getParameter("page");
            if (sortBy == null) {
                  sortBy = "fullname";
            }
            if (pageString == null) {
                  pageString = "0";
            }

            List<Student> list = null;
            int page = Integer.parseInt(pageString);
            int limit = PAGE_LIMIT;
            int total;
            if (query == null) {
                  list = studentDao.findStudents(page, sortBy, limit);
                  total = studentDao.countStudents();
            } else {
                  list = studentDao.findStudentsByName(query, sortBy, page, limit);
                  total = studentDao.countStudentsByName(query);
            }

            request.setAttribute("students", list);
            request.setAttribute("page", page);
            request.setAttribute("sortBy", sortBy);
            request.setAttribute("limit", limit);
            request.setAttribute("total", (total + limit - 1) / limit);
            request.setAttribute("query", query);

            request.getRequestDispatcher("/management/studentManagement.jsp").forward(request, response);
      }

      @Override
      protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String path = req.getPathInfo();
            try {
                  if ("/list".equals(path)) {
                        handleListRequest(req, resp);
                  } else {
                        String id = path == null ? "" : path.substring(1);
                        String courseYear = req.getParameter("courseYear");
                        Student s = studentDao.getStudent(id);
                        if (s == null) {
                              resp.sendError(404, "Student not found");
                              return;
                        }
                        List<StudentCourse> courses = courseStudentDao.getCoursesByStudentInTheYear(id, courseYear == null ? -1 : Integer.parseInt(courseYear));
                        req.setAttribute("student", s);
                        req.setAttribute("courses", courses);
                        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/management/editstudent.jsp");
                        requestDispatcher.forward(req, resp);
                  }
            } catch (SQLException e) {
                  e.printStackTrace();
                  resp.sendError(400, "Bad request");
            }
      }

      @Override
      protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String id = req.getParameter("id");
            String fullname = req.getParameter("fullname");
            LocalDate birthday = LocalDate.parse(req.getParameter("birthday"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String address = req.getParameter("address");
            int grade = Integer.parseInt(req.getParameter("grade"));
            String notes = req.getParameter("notes");
            try {
                  Student s = studentDao.getStudent(id);
                  if (s == null) {
                        s = new Student(id, fullname, Date.valueOf(birthday), grade, address, notes);
                        studentDao.addStudent(s);
                  } else {
                        s.setName(fullname);
                        s.setBirthDay(Date.valueOf(birthday));
                        s.setNotes(notes);
                        s.setAddress(address);
                        studentDao.updateStudent(s);
                  }

                  req.setAttribute("student", s);
                  RequestDispatcher requestDispatcher = req.getRequestDispatcher("/management/editstudent.jsp");
                  requestDispatcher.forward(req, resp);


            } catch (SQLException e) {
                  resp.sendError(400, "Bad request");
            }
      }

      @Override
      protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String id = req.getPathInfo().substring(1);
            try {
                  studentDao.deleteStudent(id);

                  resp.setContentType("text/plain;charset=UTF-8");
                  resp.getWriter().write("Delete success");
            } catch (SQLException e) {
                  resp.sendError(400, "Bad request");
            }
      }
}
