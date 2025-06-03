package org.decade.studentmanangement.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.decade.studentmanangement.dao.UserDao;
import org.decade.studentmanangement.model.StaffUser;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@WebServlet("/signup")
public class SignUp extends HttpServlet {


      @Resource(name = "services/UserDao")
      private UserDao userDao;

      @Override
      protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String fullname = request.getParameter("fullname");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            try {
                  StaffUser user = new StaffUser(fullname, username, password);
                  userDao.addUser(user);

                  HttpSession session = request.getSession();
                  session.setAttribute("user", user);
                  response.sendRedirect(request.getContextPath() + "/management/studentManagement.jsp");
            } catch (SQLIntegrityConstraintViolationException ex) {
                  request.setAttribute("error", "Username exists");
                  request.getRequestDispatcher("signup.jsp").forward(request, response);
                  ex.printStackTrace();
            } catch (SQLException e) {
                  request.setAttribute("error", "Account creation failed. Please try again later.");
                  request.getRequestDispatcher("signup.jsp").forward(request, response);
                  e.printStackTrace();
            }
      }


}