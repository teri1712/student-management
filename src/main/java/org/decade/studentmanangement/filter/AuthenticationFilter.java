package org.decade.studentmanangement.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Redirects unauthenticated users to /login.jsp.
 * A user is considered authenticated when a non-null "user" attribute
 * exists in the HttpSession.
 */
public class AuthenticationFilter implements Filter {

      @Override
      public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;

            HttpSession session = req.getSession(false);
            boolean loggedIn = session != null && session.getAttribute("user") != null;
            if (!loggedIn) {
                  resp.sendRedirect(req.getContextPath() + "/login.jsp");
            } else {
                  chain.doFilter(request, response);
            }
      }

}