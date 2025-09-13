<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<%@ page import="org.decade.studentmanangement.model.Course" %>
<%@ page import="org.decade.studentmanangement.model.StudentCourse" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Course Details - Student</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/theme.css" rel="stylesheet">
</head>
<body>
<%
    Course course = (Course) request.getAttribute("course");
    List<StudentCourse> students = (List<StudentCourse>) request.getAttribute("students");
%>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../includes/navigation.jsp">
            <jsp:param name="activePage" value="studentGrades"/>
        </jsp:include>

        <div class="col-md-8 col-lg-9 content-area">
            <div class="card shadow-lg mb-4">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h4 class="mb-0 fw-bold text-dark">Course <%= course.getId() %> - <%= course.getYear() %>
                    </h4>
                    <a class="btn btn-outline-secondary btn-sm"
                       href="${pageContext.request.contextPath}/student/grades">Back</a>
                </div>
                <div class="card-body p-4">
                    <div class="mb-3">Course name: <strong><%= course.getName() %>
                    </strong></div>
                    <div class="row">
                        <div class="col-lg-6">
                            <h5>Classmates</h5>
                            <div class="table-responsive">
                                <table class="table table-striped table-hover">
                                    <thead>
                                    <tr>
                                        <th>Student ID</th>
                                        <th>Name</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <% if (students != null) for (StudentCourse sc : students) { %>
                                    <tr>
                                        <td><%= sc.getStudent().getId() %>
                                        </td>
                                        <td><%= sc.getStudent().getFullname() %>
                                        </td>
                                    </tr>
                                    <% } %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <h5>Course Notifications</h5>
                            <ul id="notifList" class="list-group list-group-flush" data-ctx="${pageContext.request.contextPath}" data-course-id="<%= course.getId() %>" data-year="<%= course.getYear() %>"></ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/scripts/course-notifs.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
