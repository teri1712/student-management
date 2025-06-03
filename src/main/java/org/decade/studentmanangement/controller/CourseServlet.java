package org.decade.studentmanangement.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.decade.studentmanangement.dao.CourseDao;
import org.decade.studentmanangement.dao.CourseStudentDao;
import org.decade.studentmanangement.model.Course;
import org.decade.studentmanangement.model.StudentCourse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/management/course/*")
public class CourseServlet extends HttpServlet {


      @Resource(name = "services/CourseDao")
      private CourseDao courseDao;

      @Resource(name = "services/CourseStudentDao")
      private CourseStudentDao courseStudentDao;

      public final int PAGE_LIMIT = 10;


      private void handleListRequest(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
            String query = request.getParameter("query");
            String sortBy = request.getParameter("sortBy");
            String pageString = request.getParameter("page");
            if (sortBy == null) {
                  sortBy = "year";
            }
            if (pageString == null) {
                  pageString = "0";
            }

            List<Course> list = null;
            int page = Integer.parseInt(pageString);
            int limit = PAGE_LIMIT;
            int total;
            if (query == null) {
                  list = courseDao.findCourses(sortBy, page, limit);
                  total = courseDao.countCourses();
            } else {
                  list = courseDao.findCoursesByName(query, sortBy, page, limit);
                  total = courseDao.countCourseByName(query);
            }

            request.setAttribute("courses", list);
            request.setAttribute("page", page);
            request.setAttribute("sortBy", sortBy);
            request.setAttribute("limit", limit);
            request.setAttribute("total", (total + limit - 1) / limit);
            request.setAttribute("query", query);

            request.getRequestDispatcher("/management/courseManagement.jsp").forward(request, response);
      }

      @Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String path = request.getPathInfo();
            try {
                  if ("/list".equals(path)) {
                        handleListRequest(request, response);
                  } else {
                        String id = request.getParameter("id");
                        int year = Integer.parseInt(request.getParameter("year"));
                        Course course = courseDao.getCourse(id, year);
                        if (course == null) {
                              response.sendError(404, "Course not found");
                              return;
                        }
                        List<StudentCourse> students = courseStudentDao.getListStudentsByCourse(id, year);
                        request.setAttribute("students", students);
                        request.setAttribute("course", course);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/management/editcourse.jsp");
                        requestDispatcher.forward(request, response);
                  }
            } catch (SQLException e) {
//                  response.sendError(400, "Bad request");
                  throw new RuntimeException(e);
            }
      }

      @Override
      protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try {
                  String id = req.getParameter("id");
                  int year = Integer.parseInt(req.getParameter("year"));
                  courseDao.deleteCourse(id, year);

                  resp.setContentType("text/plain;charset=UTF-8");
                  resp.getWriter().write("Delete success");
            } catch (SQLException e) {
                  resp.sendError(400, "Bad request");
            }
      }

      @Override
      protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String lecture = req.getParameter("lecture");
            int year = Integer.parseInt(req.getParameter("year"));
            String notes = req.getParameter("notes");

            try {
                  Course course = courseDao.getCourse(id, year);
                  if (course == null) {
                        course = new Course(id, name, lecture, year, notes);
                        courseDao.addCourse(course);
                  } else {
                        course.setName(name);
                        course.setLecture(lecture);
                        course.setYear(year);
                        course.setNote(notes);
                        courseDao.updateCourse(course);
                  }

                  req.setAttribute("course", course);
                  RequestDispatcher requestDispatcher = req.getRequestDispatcher("/management/editcourse.jsp");
                  requestDispatcher.forward(req, resp);


            } catch (SQLException e) {
                  resp.sendError(400, "Bad request");
            }
      }
}
