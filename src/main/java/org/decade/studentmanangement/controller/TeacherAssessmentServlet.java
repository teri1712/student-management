package org.decade.studentmanangement.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.decade.studentmanangement.dao.AssessmentDao;
import org.decade.studentmanangement.dao.CourseDao;
import org.decade.studentmanangement.model.Course;
import org.decade.studentmanangement.model.StaffUser;

import java.io.IOException;

@WebServlet("/teacher/assessment")
@MultipartConfig
public class TeacherAssessmentServlet extends HttpServlet {

    @Inject
    private AssessmentDao assessmentDao;

    @Inject
    private CourseDao courseDao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Object u = session == null ? null : session.getAttribute("user");
        if (!(u instanceof StaffUser) || !"teacher".equalsIgnoreCase(((StaffUser) u).getRole())) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: teacher role required");
            return;
        }
        StaffUser user = (StaffUser) u;

        String op = req.getParameter("op");
        String courseId = req.getParameter("courseId");
        int year = Integer.parseInt(req.getParameter("year"));

        try {
            Course course = courseDao.getCourse(courseId, year);
            if (course == null || course.getLecture() == null || !course.getLecture().equals(user.getUserName())) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: not your course");
                return;
            }

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

            // Redirect back to teacher course page
            resp.sendRedirect(req.getContextPath() + "/teacher/course?courseId=" + courseId + "&year=" + year);
        } catch (Exception e) {
            resp.sendError(400, "Bad request");
        }
    }

    private Integer parseIntOrNull(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return null; }
    }
}
