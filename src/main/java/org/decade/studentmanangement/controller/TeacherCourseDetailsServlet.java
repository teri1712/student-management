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
import java.sql.SQLException;
import java.util.List;

@WebServlet("/teacher/course")
public class TeacherCourseDetailsServlet extends HttpServlet {

    @Inject
    private CourseDao courseDao;

    @Inject
    private CourseStudentDao courseStudentDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Object u = session == null ? null : session.getAttribute("user");
        if (!(u instanceof StaffUser)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        StaffUser user = (StaffUser) u;
        String lecturer = user.getUserName();

        String courseId = req.getParameter("courseId");
        int year = Integer.parseInt(req.getParameter("year"));
        try {
            Course course = courseDao.getCourse(courseId, year);
            if (course == null || course.getLecture() == null || !course.getLecture().equals(lecturer)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: not your course");
                return;
            }
            List<StudentCourse> students = courseStudentDao.getListStudentsByCourse(courseId, year);
            req.setAttribute("course", course);
            req.setAttribute("students", students);
            req.setAttribute("count", students == null ? 0 : students.size());
            req.getRequestDispatcher("/WEB-INF/teacher/course_details.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
