package org.decade.studentmanangement.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.decade.studentmanangement.dao.UserDao;
import org.decade.studentmanangement.model.StaffUser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@WebServlet("/management/teacher/*")
@MultipartConfig
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
                if (submit == null)
                        submit = "";

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

                                Part certPart = null;
                                certPart = req.getPart("certificate");
                                if (certPart != null && certPart.getSize() > 0) {
                                        String subPath = "certificate-" + UUID.randomUUID() + ".png";
                                        Path target = FilesServlet.baseDir.resolve(subPath);
                                        Files.createDirectories(target.getParent());
                                        InputStream in = certPart.getInputStream();
                                        Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
                                        userDao.addCertificate(id, subPath);
                                }

                                req.setAttribute("success", "Created teacher account '" + id + "' (default password equals ID)");
                        }
                } catch (Exception e) {
                        req.setAttribute("error", "Failed to create teacher account. Please try again.");
                        e.printStackTrace();
                }
                req.getRequestDispatcher("/WEB-INF/management/addteacher.jsp").forward(req, resp);
        }
}
