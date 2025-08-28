<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%
    HttpSession session = request.getSession(false);
    boolean userLoggedIn = session != null && session.getAttribute("user") != null;
    if (userLoggedIn) {
        // Redirect by role if already logged in
        Object u = session.getAttribute("user");
        String role = null;
        if (u instanceof org.decade.studentmanangement.model.StaffUser) {
            role = ((org.decade.studentmanangement.model.StaffUser) u).getRole();
        }
        String target;
        if ("admin".equalsIgnoreCase(role)) {
            target = "/management/student/list";
        } else if ("teacher".equalsIgnoreCase(role)) {
            target = "/teacher/courses";
        } else if ("student".equalsIgnoreCase(role)) {
            target = "/student/grades";
        } else {
            target = "/management/student/list"; // default fallback
        }
        response.sendRedirect(request.getContextPath() + target);
        return;
    }
%>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sign Up - Student Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/theme.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row justify-content-center align-items-center min-vh-100">
        <div class="col-md-6 col-lg-4">
            <div class="card shadow-lg">
                <div class="card-header bg-primary">
                    <h4 class="mb-0 text-dark text-center fw-bold">Create Account</h4>
                </div>
                <div class="card-body p-4">
                    <% if (request.getAttribute("error") != null) { %>
                    <p id="status" class="alert alert-danger"><%= request.getAttribute("error") %>
                    </p>
                    <% } else { %>
                    <p id="status" class="alert alert-info d-none"></p>
                    <% } %>

                    <form class="form-container" action="${pageContext.request.contextPath}/signup" method="post">
                        <div class="form-group mb-4">
                            <label for="username" class="form-label fw-semibold">Username</label>
                            <input type="text" required minlength="3" class="form-control" id="username"
                                   name="username" placeholder="Choose a username">
                        </div>

                        <div class="form-group mb-4">
                            <label for="password" class="form-label fw-semibold">Password</label>
                            <input type="password" required minlength="3" class="form-control" id="password"
                                   name="password" placeholder="Create a password">
                        </div>

                        <div class="form-group mb-4">
                            <label for="fullname" class="form-label fw-semibold">Full Name</label>
                            <input type="text" required minlength="3" class="form-control" id="fullname"
                                   name="fullname" placeholder="Enter your full name">
                        </div>

                        <div class="form-group mb-4">
                            <label for="role" class="form-label fw-semibold">Role</label>
                            <select id="role" name="role" class="form-select" required>
                                <option value="student" selected>Student</option>
                                <option value="teacher">Teacher</option>
                            </select>
                            <div class="form-text">Admin account creation is not available on public signup.</div>
                        </div>

                        <div class="d-grid gap-2 mb-4">
                            <button type="submit" class="btn btn-primary">Create Account</button>
                        </div>
                    </form>

                    <div class="text-center">
                        <p class="mb-2 text-muted">Already have an account?</p>
                        <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-link fw-semibold">Sign
                            In</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
