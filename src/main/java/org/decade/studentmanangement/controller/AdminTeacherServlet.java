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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet("/management/teacher/*")
@jakarta.servlet.annotation.MultipartConfig
public class AdminTeacherServlet extends HttpServlet {

        @Inject
        private UserDao userDao;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                String path = req.getPathInfo();
                if ("/add".equals(path)) {
                        req.getRequestDispatcher("/WEB-INF/management/addteacher.jsp").forward(req, resp);
                } else {
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
                }
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                String id = req.getParameter("id");
                String fullname = req.getParameter("fullname");
                String submit = req.getParameter("submit");
                if (submit == null) submit = ""; // to avoid NPE in some containers

                if (id == null || id.isBlank() || fullname == null || fullname.isBlank()) {
                        req.setAttribute("error", "ID and Full Name are required");
                        req.getRequestDispatcher("/WEB-INF/management/addteacher.jsp").forward(req, resp);
                        return;
                }

                try {
                        StaffUser existing = userDao.getUser(id);
                        if (existing != null) {
                                req.setAttribute("error", "Username already exists: " + id);
                        } else {
                                StaffUser teacher = new StaffUser(fullname, id, id, "teacher");
                                userDao.addUser(teacher);

                                // Handle uploaded certificate (optional)
                                try {
                                        jakarta.servlet.http.Part certPart = null;
                                        try {
                                                certPart = req.getPart("certificate");
                                        } catch (Exception ignored) {
                                        }
                                        if (certPart != null && certPart.getSize() > 0) {
                                                String subPath = "certificate-" + UUID.randomUUID().toString() + ".png";
                                                Path target = FilesServlet.baseDir.resolve(subPath);
                                                Files.createDirectories(target.getParent());
                                                try (InputStream in = certPart.getInputStream()) {
                                                        Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
                                                }
                                                try {
                                                        userDao.addCertificate(id, subPath);
                                                } catch (SQLException ignored) {
                                                }
                                        }
                                } catch (Exception ignored) {
                                }

                                req.setAttribute("success", "Created teacher account '" + id + "' (default password equals ID)");
                        }
                } catch (SQLException e) {
                        req.setAttribute("error", "Failed to create teacher account. Please try again.");
                }
                req.getRequestDispatcher("/WEB-INF/management/addteacher.jsp").forward(req, resp);
        }
}
