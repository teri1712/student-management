<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<%@page import="org.decade.studentmanangement.model.Course" %>
<%@page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Course Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/theme.css" rel="stylesheet">
</head>
<body>
<%
    List<Course> l = (List<Course>) request.getAttribute("courses");
    String currentQuery = (String) request.getAttribute("query");
    String currentSortBy = (String) request.getAttribute("sortBy");
    int currentPage = (int) request.getAttribute("page");
    int pageLimit = (int) request.getAttribute("limit");
    int totalPage = (int) request.getAttribute("total");
%>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../includes/navigation.jsp">
            <jsp:param name="activePage" value="course"/>
        </jsp:include>

        <!-- Main Content -->
        <div class="col-md-8 col-lg-9 content-area">
            <div class="card shadow-lg">
                <div class="card-header">
                    <div class="d-flex justify-content-between align-items-center">
                        <h4 class="mb-0 fw-bold text-dark">Course Management</h4>
                        <a href="${pageContext.request.contextPath}/management/course/add" class="btn btn-success">
                            <i class="fas fa-plus"></i> Add New Course
                        </a>
                    </div>
                </div>
                <div class="card-body p-4">
                    <p id="status" class="alert alert-info d-none"></p>

                    <div class="row mb-4">
                        <form class="col-md-6" action="${pageContext.request.contextPath}/management/course/list"
                              method="get">
                            <div class="input-group">
                                <input type="text" id="name" name="query" class="form-control"
                                       placeholder="Search courses by name...">
                                <button class="btn btn-primary" type="submit">Search</button>
                            </div>
                        </form>
                        <form class="col-md-6" action="${pageContext.request.contextPath}/management/course/list" method="get">
                            <div class="input-group">
                                <select name="sortBy" class="form-select">
                                    <option value="year" ${"year".equals(currentSortBy) ? "selected" : ""}>Sort by Year</option>
                                    <option value="name" ${"name".equals(currentSortBy) ? "selected" : ""}>Sort by Name</option>
                                </select>
                                <button class="btn btn-primary" type="submit">Sort</button>
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
                                <th>Lecturer</th>
                                <th>Notes</th>
                                <th>Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <% if (l != null) for (Course i : l) { %>
                            <tr>
                                <td><%= i.getId() %></td>
                                <td><%= i.getName() %></td>
                                <td><%= i.getYear() %></td>
                                <td><%= i.getLecture() %></td>
                                <td><%= i.getNote() %></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/management/course?id=<%= i.getId() %>&year=<%= i.getYear() %>"
                                       class="btn btn-primary btn-sm">Edit</a>
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
                                   href="${pageContext.request.contextPath}/management/course/list?page=<%= i %>&sortBy=<%= currentSortBy %><%= currentQuery == null ? "" : ("&query=" + currentQuery) %>"><%= i + 1 %></a>
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
