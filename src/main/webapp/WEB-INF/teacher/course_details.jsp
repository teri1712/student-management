<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<%@ page import="org.decade.studentmanangement.model.Course" %>
<%@ page import="org.decade.studentmanangement.model.StudentCourse" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Course Students - Teacher</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/theme.css" rel="stylesheet">
</head>
<body>
<%
    Course course = (Course) request.getAttribute("course");
    List<StudentCourse> students = (List<StudentCourse>) request.getAttribute("students");
    Integer count = (Integer) request.getAttribute("count");
%>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../includes/navigation.jsp">
            <jsp:param name="activePage" value="teacherCourses"/>
        </jsp:include>

        <div class="col-md-8 col-lg-9 content-area">
            <div class="card shadow-lg">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h4 class="mb-0 fw-bold text-dark">Course <%= course.getId() %> - <%= course.getYear() %>
                    </h4>
                    <a class="btn btn-outline-secondary btn-sm"
                       href="${pageContext.request.contextPath}/teacher/courses">Back</a>
                </div>
                <div class="card-body p-4">
                    <div class="mb-3">Course name: <strong><%= course.getName() %>
                    </strong></div>
                    <div class="mb-4">Total students: <strong><%= count %>
                    </strong></div>

                    <div class="mb-4">
                        <form class="row g-2 align-items-center"
                              action="${pageContext.request.contextPath}/teacher/assessment" method="post"
                              enctype="multipart/form-data">
                            <input type="hidden" name="op" value="import">
                            <input type="hidden" name="courseId" value="<%=course.getId()%>">
                            <input type="hidden" name="year" value="<%=course.getYear()%>">
                            <div class="col-auto">
                                <label class="col-form-label">Import CSV</label>
                            </div>
                            <div class="col-auto">
                                <input type="file" name="file" accept=".csv" class="form-control">
                            </div>
                            <div class="col-auto">
                                <button class="btn btn-primary" type="submit">Upload</button>
                            </div>
                        </form>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th>Student ID</th>
                                <th>Name</th>
                                <th>Latest Score</th>
                                <th>Add Assessment (Sem, Year, Score)</th>
                            </tr>
                            </thead>
                            <tbody>
                            <% if (students != null) for (StudentCourse sc : students) { %>
                            <tr>
                                <td><%= sc.getStudent().getId() %></td>
                                <td><%= sc.getStudent().getFullname() %></td>
                                <td><%= sc.getScore() == null ? "-" : sc.getScore() %></td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/teacher/assessment" method="post" class="d-flex gap-1">
                                        <input type="hidden" name="op" value="add">
                                        <input type="hidden" name="courseId" value="<%= course.getId() %>">
                                        <input type="hidden" name="year" value="<%= course.getYear() %>">
                                        <input type="hidden" name="studentId" value="<%= sc.getStudent().getId() %>">
                                        <input type="number" name="semester" min="1" max="3" class="form-control form-control-sm" placeholder="Sem">
                                        <input type="number" name="assessYear" class="form-control form-control-sm" placeholder="Year" value="<%= course.getYear() %>">
                                        <input type="number" name="score" class="form-control form-control-sm" placeholder="Score" required>
                                        <button type="submit" class="btn btn-outline-success btn-sm">Add</button>
                                    </form>
                                </td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>

                    <hr/>
                    <div class="row">
                        <div class="col-lg-6">
                            <h5>Course Notifications</h5>
                            <form id="notifForm" class="mb-3" onsubmit="return false;">
                                <div class="input-group">
                                    <input type="text" id="notifContent" class="form-control" placeholder="Enter notification message">
                                    <button id="notifSend" class="btn btn-primary" type="button">Post</button>
                                </div>
                            </form>
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
