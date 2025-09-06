package org.decade.studentmanangement.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.decade.studentmanangement.dao.UserDao;
import org.decade.studentmanangement.model.StaffUser;

import java.io.IOException;

@WebServlet("/signup")
public class SignUp extends HttpServlet {

        @Inject
        private UserDao userDao;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
                // Redirect legacy signup page to the new admin add page
                response.sendRedirect(request.getContextPath() + "/management/admin/add");
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
                // Ensure only admins can reach; then redirect to new admin add page
                HttpSession session = request.getSession(false);
                Object u = session == null ? null : session.getAttribute("user");
                if (!(u instanceof StaffUser) || !"admin".equalsIgnoreCase(((StaffUser) u).getRole())) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: admin role required");
                        return;
                }
                response.sendRedirect(request.getContextPath() + "/management/admin/add");
        }
}