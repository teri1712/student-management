package org.decade.studentmanangement.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.decade.studentmanangement.model.StaffUser;

import java.io.IOException;

public class AuthorizationFilter implements Filter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                HttpServletRequest req = (HttpServletRequest) request;
                HttpServletResponse resp = (HttpServletResponse) response;

                String uri = req.getRequestURI();
                String ctx = req.getContextPath();
                if (ctx != null && !ctx.isEmpty() && uri.startsWith(ctx)) {
                        uri = uri.substring(ctx.length());
                }

                StaffUser user = req.getSession(false) == null ? null : (StaffUser) req.getSession(false).getAttribute("user");
                String role = user == null ? null : user.getRole();

                boolean requiresAdmin = uri.startsWith("/management/");
                boolean requiresTeacher = uri.startsWith("/teacher/");
                boolean requiresStudent = uri.startsWith("/student/");

                if ((requiresAdmin && !"admin".equals(role))) {
                        resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: admin role required");
                        return;
                }
                if (requiresTeacher && !"teacher".equals(role)) {
                        resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: teacher role required");
                        return;
                }
                if (requiresStudent && !"student".equals(role)) {
                        resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: student role required");
                        return;
                }

                chain.doFilter(request, response);
        }
}
