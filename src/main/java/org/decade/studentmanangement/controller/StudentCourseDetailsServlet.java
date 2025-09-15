package org.decade.studentmanangement.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.decade.studentmanangement.dao.CourseDao;
import org.decade.studentmanangement.dao.CourseStudentDao;
import org.decade.studentmanangement.model.Course;
import org.decade.studentmanangement.model.StaffUser;
import org.decade.studentmanangement.model.StudentCourse;

import java.io.IOException;
import java.util.List;

@WebServlet("/student/course")
public class StudentCourseDetailsServlet extends HttpServlet {

        @Inject
        private CourseDao courseDao;

        @Inject
        private CourseStudentDao courseStudentDao;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                HttpSession session = req.getSession(false);
                StaffUser user = session == null ? null : (StaffUser) session.getAttribute("user");
                String courseId = req.getParameter("courseId");
                int year = Integer.parseInt(req.getParameter("year"));
                try {
                        Course course = courseDao.getCourse(courseId, year);
                        if (course == null) {
                                resp.sendError(404, "Course not found");
                                return;
                        }
                        List<StudentCourse> students = courseStudentDao.getListStudentsByCourse(courseId, year);
                        boolean enrolled = false;
                        if (students != null) {
                                for (StudentCourse sc : students) {
                                        if (sc.getStudent().getId().equals(user.getUserName())) {
                                                enrolled = true;
                                                break;
                                        }
                                }
                        }
                        if (!enrolled) {
                                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: not enrolled");
                                return;
                        }
                        req.setAttribute("course", course);
                        req.setAttribute("students", students);
                        req.getRequestDispatcher("/WEB-INF/student/course_details.jsp").forward(req, resp);
                } catch (Exception e) {
                        resp.sendError(400, "Bad request");
                }
        }
}
