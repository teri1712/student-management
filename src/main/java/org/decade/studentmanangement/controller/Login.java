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

@WebServlet("/login")
public class Login extends HttpServlet {

      @Resource(name = "services/UserDao")
      private UserDao userDao;


      @Override
      protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            try {
                  StaffUser user = userDao.getUser(username);
                  String error = null;
                  if (user == null || !user.getPassword().equals(password)) {

                  }
                  if (user == null) {
                        error = "Username not found";
                  } else if (!user.getPassword().equals(password)) {
                        error = "Wrong password";
                  }
                  if (error != null) {
                        request.setAttribute("error", error);
                        request.getRequestDispatcher("login.jsp").forward(request, response);
                        return;
                  }
                  HttpSession session = request.getSession();
                  session.setAttribute("user", user);
                  response.sendRedirect(request.getContextPath() + "/management/student/list");
            } catch (Exception ex) {
                  ex.printStackTrace();
            }
      }
}
