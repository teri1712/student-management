package org.decade.studentmanangement.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.decade.studentmanangement.dao.UserDao;
import org.decade.studentmanangement.model.StaffUser;

import java.io.IOException;

@WebServlet("/management/admin/*")
public class AdminAddAdminServlet extends HttpServlet {

        @Inject
        private UserDao userDao;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                String path = req.getPathInfo();
                if (path == null || "/add".equals(path)) {
                        req.getRequestDispatcher("/WEB-INF/management/addadmin.jsp").forward(req, resp);
                } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                String username = req.getParameter("username");
                String fullname = req.getParameter("fullname");
                String password = req.getParameter("password");
                if (password == null || password.isBlank()) password = username; // default

                if (username == null || username.isBlank() || fullname == null || fullname.isBlank()) {
                        req.setAttribute("error", "Username and Full Name are required");
                        req.getRequestDispatcher("/WEB-INF/management/addadmin.jsp").forward(req, resp);
                        return;
                }
                try {
                        StaffUser existing = userDao.getUser(username);
                        if (existing != null) {
                                req.setAttribute("error", "Username already exists: " + username);
                        } else {
                                StaffUser admin = new StaffUser(fullname, username, password, "admin");
                                userDao.addUser(admin);
                                req.setAttribute("success", "Created admin '" + username + "'.");
                        }
                } catch (Exception e) {
                        req.setAttribute("error", "Failed to create admin. Please try again.");
                }
                req.getRequestDispatcher("/WEB-INF/management/addadmin.jsp").forward(req, resp);
        }
}
