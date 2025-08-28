<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<%@page import="org.decade.studentmanangement.model.Course" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Courses - Teacher</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/theme.css" rel="stylesheet">
</head>
<body>
<%
    List<Course> courses = (List<Course>) request.getAttribute("courses");
    int currentPage = (int) request.getAttribute("page");
    int totalPage = (int) request.getAttribute("total");
    String currentSortBy = (String) request.getAttribute("sortBy");
    Integer filterYear = (Integer) request.getAttribute("year");
    java.util.Map<String,Integer> counts = (java.util.Map<String,Integer>) request.getAttribute("counts");
%>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../includes/navigation.jsp">
            <jsp:param name="activePage" value="teacherCourses"/>
        </jsp:include>

        <div class="col-md-8 col-lg-9 content-area">
            <div class="card shadow-lg">
                <div class="card-header">
                    <div class="d-flex justify-content-between align-items-center">
                        <h4 class="mb-0 fw-bold text-dark">My Courses</h4>
                    </div>
                </div>
                <div class="card-body p-4">
                    <% String certPath = (String) request.getAttribute("certificatePath"); %>
                    <% if (certPath != null && !certPath.isBlank()) { %>
                    <div class="mb-4">
                        <div class="card">
                            <div class="card-header">My Certificate</div>
                            <div class="card-body text-center">
                                <img src="${pageContext.request.contextPath}<%= certPath %>" alt="certificate" class="img-fluid" style="max-height:300px; object-fit:contain;">
                            </div>
                        </div>
                    </div>
                    <% } %>
                    <div class="row mb-4">
                        <form class="col-md-6" action="${pageContext.request.contextPath}/teacher/courses" method="get">
                            <div class="input-group">
                                <select name="sortBy" class="form-select">
                                    <option value="year" ${"year".equals(currentSortBy) ? "selected" : ""}>Sort by Year</option>
                                    <option value="name" ${"name".equals(currentSortBy) ? "selected" : ""}>Sort by Name</option>
                                </select>
                                <button class="btn btn-primary" type="submit">Sort</button>
                            </div>
                        </form>
                        <form class="col-md-6" action="${pageContext.request.contextPath}/teacher/courses" method="get">
                            <div class="input-group">
                                <input type="number" name="year" class="form-control" placeholder="Filter by year" value="${year != null ? year : ''}">
                                <button class="btn btn-primary" type="submit">Apply</button>
                            </div>
                        </form>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Year</th>
                                <th>Students</th>
                                <th>Notes</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <% if (courses != null) for (Course c : courses) { %>
                            <tr>
                                <td><%= c.getId() %></td>
                                <td><%= c.getName() %></td>
                                <td><%= c.getYear() %></td>
                                <td><%= counts != null ? counts.getOrDefault(c.getId() + "-" + c.getYear(), 0) : 0 %></td>
                                <td><%= c.getNote() %></td>
                                <td>
                                    <a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/teacher/course?courseId=<%= c.getId() %>&year=<%= c.getYear() %>">View students</a>
                                </td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>

                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <% for (int i = 0; i < totalPage; i++) { %>
                            <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/teacher/courses?page=<%= i %>&sortBy=<%= currentSortBy %>"><%= i + 1 %></a>
                            </li>
                            <% } %>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
