<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%
    HttpSession session = request.getSession(false);
    boolean userLoggedIn = session != null && session.getAttribute("user") != null;
    if (userLoggedIn) {
        response.sendRedirect(request.getContextPath() + "/management/student/list");
        return;
    }
%>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login - Student Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <style>
        :root {
            --bs-primary: #ffc107;
            --bs-primary-rgb: 255, 193, 7;
        }

        .btn-primary {
            background-color: var(--bs-primary);
            border-color: var(--bs-primary);
            color: #000;
        }


        .bg-primary {
            background-color: var(--bs-primary) !important;
        }

        .text-primary {
            color: var(--bs-primary) !important;
        }


        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
        }

        .card-header {
            border-radius: 15px 15px 0 0 !important;
            border-bottom: none;
        }


        .btn-link {
            color: var(--bs-primary);
            text-decoration: none;
        }

        .btn-link:hover {
            color: #e0a800;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row justify-content-center align-items-center min-vh-100">
        <div class="col-md-6 col-lg-4">
            <div class="card shadow-lg">
                <div class="card-header bg-primary">
                    <h4 class="mb-0 text-dark text-center fw-bold">Welcome Back!</h4>
                </div>
                <div class="card-body p-4">
                    <% if (request.getAttribute("error") != null) { %>
                    <p id="status" class="alert alert-danger"><%= request.getAttribute("error") %>
                    </p>
                    <% } else { %>
                    <p id="status" class="alert alert-info d-none"></p>
                    <% } %>

                    <form class="form-container" action="${pageContext.request.contextPath}/login" method="post">
                        <div class="form-group mb-4">
                            <label for="username" class="form-label fw-semibold">Username</label>
                            <input type="text" value="admin" required minlength="5" class="form-control" id="username"
                                   name="username" placeholder="Enter your username">
                        </div>

                        <div class="form-group mb-4">
                            <label for="password" class="form-label fw-semibold">Password</label>
                            <input type="password" value="admin123" required minlength="5" class="form-control"
                                   id="password" name="password" placeholder="Enter your password">
                        </div>

                        <div class="d-grid gap-2 mb-4">
                            <button type="submit" class="btn btn-primary">Sign In</button>
                        </div>
                    </form>

                    <div class="text-center">
                        <p class="mb-2 text-muted">Don't have an account?</p>
                        <a href="${pageContext.request.contextPath}/signup.jsp" class="btn btn-link fw-semibold">Create
                            Account</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
