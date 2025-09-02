package org.decade.studentmanangement.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.decade.studentmanangement.dao.AssessmentDao;
import org.decade.studentmanangement.dao.CourseDao;
import org.decade.studentmanangement.dao.CourseStudentDao;
import org.decade.studentmanangement.model.Course;
import org.decade.studentmanangement.model.StudentCourse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/management/assessment")
@MultipartConfig
public class AssessmentServlet extends HttpServlet {

    @Inject
    private AssessmentDao assessmentDao;

    @Inject
    private CourseDao courseDao;

    @Inject
    private CourseStudentDao courseStudentDao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String op = req.getParameter("op");
        String courseId = req.getParameter("courseId");
        int year = Integer.parseInt(req.getParameter("year"));

        try {
            if ("add".equalsIgnoreCase(op)) {
                String studentId = req.getParameter("studentId");
                Integer semester = parseIntOrNull(req.getParameter("semester"));
                Integer assessYear = parseIntOrNull(req.getParameter("assessYear"));
                int score = Integer.parseInt(req.getParameter("score"));
                assessmentDao.addAssessment(studentId, courseId, year, semester, assessYear, score);
            } else if ("import".equalsIgnoreCase(op)) {
                Part filePart = req.getPart("file");
                if (filePart != null && filePart.getSize() > 0) {
                    assessmentDao.importCsv(courseId, year, filePart.getInputStream());
                }
            }

            Course course = courseDao.getCourse(courseId, year);
            List<StudentCourse> students = courseStudentDao.getListStudentsByCourse(courseId, year);
            req.setAttribute("course", course);
            req.setAttribute("students", students);
            req.getRequestDispatcher("/WEB-INF/management/editcourse.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private Integer parseIntOrNull(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return null; }
    }
}
