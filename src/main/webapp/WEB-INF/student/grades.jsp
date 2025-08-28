<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.List" %>
<%@ page import="org.decade.studentmanangement.model.StudentCourse" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Grades - Student</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/theme.css" rel="stylesheet">
</head>
<body>
<%
    List<StudentCourse> courses = (List<StudentCourse>) request.getAttribute("courses");
    Object gpaObj = request.getAttribute("gpa");
    Object avgObj = request.getAttribute("avgScore");
    Integer year = (Integer) request.getAttribute("year");
%>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../includes/navigation.jsp">
            <jsp:param name="activePage" value="studentGrades"/>
        </jsp:include>

        <div class="col-md-8 col-lg-9 content-area">
            <div class="card shadow-lg mb-4">
                <div class="card-header">
                    <div class="d-flex justify-content-between align-items-center">
                        <h4 class="mb-0 fw-bold text-dark">My Grades</h4>
                        <form class="input-group w-auto" action="${pageContext.request.contextPath}/student/grades"
                              method="get">
                            <input type="number" name="year" class="form-control" placeholder="Filter by year" min="0">
                            <button class="btn btn-primary" type="submit">Filter</button>
                        </form>
                    </div>
                </div>
                <div class="card-body p-4">
                    <% if (gpaObj != null && avgObj != null) { %>
                    <div class="alert alert-info">
                        Average Score: <strong><%= avgObj %>
                    </strong> | GPA: <strong><%= gpaObj %>
                    </strong>
                    </div>
                    <% } %>

                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th>Course ID</th>
                                <th>Name</th>
                                <th>Year</th>
                                <th>Lecturer</th>
                                <th>Score</th>
                            </tr>
                            </thead>
                            <tbody>
                            <% if (courses != null) for (StudentCourse sc : courses) { %>
                            <tr>
                                <td><%= sc.getCourse().getId() %>
                                </td>
                                <td><%= sc.getCourse().getName() %>
                                </td>
                                <td><%= sc.getCourse().getYear() %>
                                </td>
                                <td><%= sc.getCourse().getLecture() %>
                                </td>
                                <td><%= sc.getScore() != null ? sc.getScore().toString() : "Haven't finished" %>
                                </td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
