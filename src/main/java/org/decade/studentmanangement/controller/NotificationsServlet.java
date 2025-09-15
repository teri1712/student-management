package org.decade.studentmanangement.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.decade.studentmanangement.dao.CourseDao;
import org.decade.studentmanangement.dao.NotificationDao;
import org.decade.studentmanangement.model.Course;
import org.decade.studentmanangement.model.Notification;
import org.decade.studentmanangement.model.StaffUser;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/notifications")
public class NotificationsServlet extends HttpServlet {

        @Inject
        private NotificationDao notificationDao;

        @Inject
        private CourseDao courseDao;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                String courseId = req.getParameter("courseId");
                int year = Integer.parseInt(req.getParameter("year"));
                Long sinceId = req.getParameter("sinceId") == null ? null : Long.parseLong(req.getParameter("sinceId"));
                try {
                        List<Notification> list = notificationDao.listByCourse(courseId, year, sinceId, 50);
                        resp.setContentType("application/json;charset=UTF-8");
                        StringBuilder sb = new StringBuilder();
                        sb.append("[");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        boolean first = true;
                        for (Notification n : list) {
                                if (!first)
                                        sb.append(",");
                                first = false;
                                sb.append("{")
                                        .append("\"id\":").append(n.getId()).append(",")
                                        .append("\"teacher\":\"").append(n.getTeacherUsername()).append("\",")
                                        .append("\"createdAt\":\"").append(sdf.format(n.getCreatedAt())).append("\",")
                                        .append("\"content\":\"").append(n.getContent()).append("\"")
                                        .append("}");
                        }
                        sb.append("]");
                        resp.getWriter().write(sb.toString());
                } catch (Exception e) {
                        resp.sendError(400, "Bad request");
                        e.printStackTrace();
                }
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                HttpSession session = req.getSession(false);
                StaffUser user = session == null ? null : (StaffUser) session.getAttribute("user");
                if (!"teacher".equals(user.getRole())) {
                        resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
                        return;
                }
                String courseId = req.getParameter("courseId");
                int year = Integer.parseInt(req.getParameter("year"));
                String content = req.getParameter("content");
                try {
                        Course course = courseDao.getCourse(courseId, year);
                        if (!course.getLecture().equals(user.getUserName())) {
                                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: not your course");
                                return;
                        }
                        notificationDao.addNotification(courseId, year, user.getUserName(), content.trim());
                        resp.setContentType("text/plain;charset=UTF-8");
                        resp.getWriter().write("OK");
                } catch (Exception e) {
                        resp.sendError(400, "Bad request");
                }
        }
}
