package org.decade.studentmanangement.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.decade.studentmanangement.dao.CourseStudentDao;
import org.decade.studentmanangement.model.StaffUser;
import org.decade.studentmanangement.model.StudentCourse;

import java.io.IOException;
import java.util.List;

@WebServlet("/student/grades")
public class StudentGradesServlet extends HttpServlet {

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
                // For student role, we assume username equals Student.id
                String studentId = user.getUserName();

                int year = -1;
                try {
                        year = Integer.parseInt(req.getParameter("year"));
                } catch (Exception ignored) {
                }

                try {
                        List<StudentCourse> list = courseStudentDao.getCoursesByStudentInTheYear(studentId, year);
                        req.setAttribute("courses", list);
                        req.setAttribute("year", year);

                        // compute GPA as avg score mapped to 4.0 scale (score/25)
                        if (list != null && !list.isEmpty()) {
                                double sum = 0.0;
                                int counted = 0;
                                for (StudentCourse sc : list) {
                                        if (sc.getScore() != null) {
                                                sum += sc.getScore();
                                                counted++;
                                        }
                                }
                                if (counted > 0) {
                                        double avg = sum / counted;
                                        double gpa = Math.round((avg / 25.0) * 100.0) / 10.0;
                                        req.setAttribute("avgScore", Math.round(avg * 100.0) / 100.0);
                                        req.setAttribute("gpa", gpa);
                                }
                        }

                        req.getRequestDispatcher("/WEB-INF/student/grades.jsp").forward(req, resp);
                } catch (Exception e) {
                        resp.sendError(400, "Bad request");
                }
        }
}
