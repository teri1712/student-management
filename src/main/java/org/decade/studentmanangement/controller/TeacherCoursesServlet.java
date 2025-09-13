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
import org.decade.studentmanangement.dao.UserDao;
import org.decade.studentmanangement.model.Course;
import org.decade.studentmanangement.model.StaffUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/teacher/courses")
public class TeacherCoursesServlet extends HttpServlet {

        @Inject
        private CourseDao courseDao;

        @Inject
        private CourseStudentDao courseStudentDao;

        @Inject
        private UserDao userDao;

        private static final int PAGE_LIMIT = 10;

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

                String sortBy = req.getParameter("sortBy");
                if (sortBy == null) sortBy = "year";
                Integer filterYear = null;
                try {
                        String y = req.getParameter("year");
                        if (y != null && !y.isBlank()) filterYear = Integer.parseInt(y);
                } catch (Exception ignored) {
                }
                int page = 0;
                try {
                        page = Integer.parseInt(req.getParameter("page"));
                } catch (Exception ignored) {
                }
                int limit = PAGE_LIMIT;

                try {
                        List<Course> courses;
                        int total;
                        if (filterYear != null) {
                                courses = courseDao.findCoursesByLecturerAndYear(lecturer, filterYear, sortBy, page, limit);
                                total = courseDao.countCoursesByLecturerAndYear(lecturer, filterYear);
                        } else {
                                courses = courseDao.findCoursesByLecturer(lecturer, sortBy, page, limit);
                                total = courseDao.countCoursesByLecturer(lecturer);
                        }

                        java.util.Map<String, Integer> counts = new java.util.HashMap<>();
                        for (Course c : courses) {
                                try {
                                        int cnt = courseStudentDao.countStudentsOfCourse(c.getId(), c.getYear());
                                        counts.put(c.getId() + "-" + c.getYear(), cnt);
                                } catch (SQLException ignored) {
                                        counts.put(c.getId() + "-" + c.getYear(), 0);
                                }
                        }

                        // Load teacher certificate (latest) via UserDao
                        try {
                                String p = userDao.getLatestCertificatePath(lecturer);
                                if (p != null && !p.isBlank()) {
                                        String shown = (p.startsWith("/")) ? p : ("/files/" + p);
                                        req.setAttribute("certificatePath", shown);
                                }
                        } catch (SQLException ignored) {
                        }

                        req.setAttribute("courses", courses);
                        req.setAttribute("counts", counts);
                        req.setAttribute("page", page);
                        req.setAttribute("sortBy", sortBy);
                        req.setAttribute("year", filterYear);
                        req.setAttribute("limit", limit);
                        req.setAttribute("total", (total + limit - 1) / limit);

                        req.getRequestDispatcher("/WEB-INF/teacher/courses.jsp").forward(req, resp);
                } catch (Exception e) {
                        e.printStackTrace();
                        resp.sendError(400, "Bad request");
                }
        }
}
