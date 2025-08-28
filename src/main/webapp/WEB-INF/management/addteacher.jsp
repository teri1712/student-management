<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add Teacher - Student Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/theme.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../includes/navigation.jsp">
            <jsp:param name="activePage" value="addTeacher"/>
        </jsp:include>

        <!-- Main Content -->
        <div class="col-md-8 col-lg-9 content-area">
            <div class="card shadow-lg">
                <div class="card-header">
                    <h4 class="mb-0 fw-bold text-dark">Add New Teacher</h4>
                </div>
                <div class="card-body p-4">
                    <% if (request.getAttribute("error") != null) { %>
                    <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
                    <% } %>
                    <% if (request.getAttribute("success") != null) { %>
                    <div class="alert alert-success"><%= request.getAttribute("success") %></div>
                    <% } %>

                    <form class="form-container" action="${pageContext.request.contextPath}/management/teacher" method="post" enctype="multipart/form-data">
                        <div class="form-group mb-4">
                            <label for="id" class="form-label">Teacher ID (will be username)</label>
                            <input type="text" class="form-control" id="id" name="id" placeholder="Enter teacher ID">
                        </div>

                        <div class="form-group mb-4">
                            <label for="fullname" class="form-label">Full Name</label>
                            <input type="text" class="form-control" id="fullname" name="fullname" placeholder="Enter full name">
                        </div>

                        <div class="form-group mb-4">
                            <label for="certificate" class="form-label">Certificate (image)</label>
                            <input type="file" class="form-control" id="certificate" name="certificate" accept="image/*">
                            <div class="form-text">Optional: upload the teacher's certificate image.</div>
                        </div>

                        <div class="form-text mb-3">
                            Default credentials: username = ID, password = ID
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" name="submit" value="1" class="btn btn-primary">Create Teacher</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
