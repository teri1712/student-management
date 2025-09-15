package org.decade.studentmanangement.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
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
                StaffUser user = (StaffUser) session.getAttribute("user");


                String op = req.getParameter("op");
                String courseId = req.getParameter("courseId");
                int year = Integer.parseInt(req.getParameter("year"));

                try {
                        Course course = courseDao.getCourse(courseId, year);
                        if (course == null || course.getLecture() == null || !course.getLecture().equals(user.getUserName())) {
                                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: not your course");
                                return;
                        }

                        if ("add".equals(op)) {
                                String studentId = req.getParameter("studentId");
                                Integer semester = Integer.parseInt(req.getParameter("semester"));
                                Integer assessYear = Integer.parseInt(req.getParameter("assessYear"));
                                int score = Integer.parseInt(req.getParameter("score"));
                                assessmentDao.addAssessment(studentId, courseId, year, semester, assessYear, score);
                        } else if ("import".equals(op)) {
                                Part filePart = req.getPart("file");
                                if (filePart != null && filePart.getSize() > 0) {
                                        assessmentDao.importCsv(courseId, year, filePart.getInputStream());
                                }
                        }

                        resp.sendRedirect(req.getContextPath() + "/teacher/course?courseId=" + courseId + "&year=" + year);
                } catch (Exception e) {
                        resp.sendError(400, "Bad request");
                }
        }

}
