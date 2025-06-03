<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add Student - Student Management</title>
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


        .content-area {
            padding: 2rem;
        }


    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../WEB-INF/includes/navigation.jsp">
            <jsp:param name="activePage" value="student"/>
        </jsp:include>

        <!-- Main Content -->
        <div class="col-md-8 col-lg-9 content-area">
            <div class="card shadow-lg">
                <div class="card-header">
                    <h4 class="mb-0 fw-bold text-dark">Add New Student</h4>
                </div>
                <div class="card-body p-4">
                    <p id="status" class="alert alert-info d-none"></p>

                    <form class="form-container" action="${pageContext.request.contextPath}/management/student"
                          method="post">
                        <div class="form-group mb-4">
                            <label for="id" class="form-label">Student ID</label>
                            <input type="text" class="form-control" id="id" name="id" placeholder="Enter student ID">
                        </div>

                        <div class="form-group mb-4">
                            <label for="fullname" class="form-label">Full Name</label>
                            <input type="text" class="form-control" id="fullname" name="fullname"
                                   placeholder="Enter full name">
                        </div>

                        <div class="form-group mb-4">
                            <label for="birthday" class="form-label">Birthday</label>
                            <input type="date" class="form-control" id="birthday" name="birthday"
                                   placeholder="YYYY-MM-DD">
                        </div>

                        <div class="form-group mb-4">
                            <label for="grade" class="form-label">Grade</label>
                            <input type="text" class="form-control" id="grade" name="grade" placeholder="Enter grade">
                        </div>

                        <div class="form-group mb-4">
                            <label for="address" class="form-label">Address</label>
                            <input type="text" class="form-control" id="address" name="address"
                                   placeholder="Enter address">
                        </div>

                        <div class="form-group mb-4">
                            <label for="notes" class="form-label">Notes</label>
                            <textarea class="form-control" id="notes" name="notes" rows="3"
                                      placeholder="Enter any additional notes"></textarea>
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary">Add Student</button>
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
