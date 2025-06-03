<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Add Course - Student Management</title>
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
            <jsp:param name="activePage" value="course"/>
        </jsp:include>

        <!-- Main Content -->
        <div class="col-md-8 col-lg-9 content-area">
            <div class="card shadow-lg">
                <div class="card-header">
                    <h4 class="mb-0 fw-bold text-dark">Add New Course</h4>
                </div>
                <div class="card-body p-4">
                    <p id="status" class="alert alert-info d-none"></p>

                    <form class="form-container" action="${pageContext.request.contextPath}/management/course"
                          method="post">
                        <div class="form-group mb-4">
                            <label for="id" class="form-label">Course ID</label>
                            <input type="text"
                                   class="form-control" id="id" name="id" placeholder="Enter course ID">
                        </div>

                        <div class="form-group mb-4">
                            <label for="name" class="form-label">Course Name</label>
                            <input type="text" class="form-control" id="name" name="name"
                                   placeholder="Enter course name">
                        </div>

                        <div class="form-group mb-4">
                            <label for="lecture" class="form-label">Lecturer</label>
                            <input type="text" class="form-control" id="lecture" name="lecture"
                                   placeholder="Enter lecturer name">
                        </div>

                        <div class="form-group mb-4">
                            <label for="year" class="form-label">Year</label>
                            <input type="text" class="form-control" id="year" name="year"
                                   placeholder="Enter academic year">
                        </div>

                        <div class="form-group mb-4">
                            <label for="notes" class="form-label">Notes</label>
                            <textarea class="form-control" id="notes" name="notes" rows="3"
                                      placeholder="Enter any additional notes"></textarea>
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary">Add Course</button>
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
