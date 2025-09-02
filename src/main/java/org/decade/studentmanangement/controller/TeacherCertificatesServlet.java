package org.decade.studentmanangement.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.decade.studentmanangement.dao.UserDao;
import org.decade.studentmanangement.model.FileAttachment;
import org.decade.studentmanangement.model.StaffUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/teacher/certificates")
public class TeacherCertificatesServlet extends HttpServlet {

    @Inject
    private UserDao userDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Object u = session == null ? null : session.getAttribute("user");
        if (!(u instanceof StaffUser)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        StaffUser user = (StaffUser) u;
        String username = user.getUserName();

        try {
            List<FileAttachment> list = userDao.listCertificates(username);
            req.setAttribute("files", list);
            req.getRequestDispatcher("/WEB-INF/teacher/certificates.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
