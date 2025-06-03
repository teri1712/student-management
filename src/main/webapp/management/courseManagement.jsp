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


        .btn-success {
            background-color: #28a745;
            border-color: #28a745;
            color: white;
        }

        .btn-success:hover {
            background-color: #218838;
            border-color: #1e7e34;
        }


        .pagination .page-link {
            color: #000;
            background-color: transparent;
            border-color: var(--bs-primary);
        }

        .pagination .page-item.active .page-link {
            background-color: var(--bs-primary);
            border-color: var(--bs-primary);
            color: #000;
        }

        .pagination .page-link:hover {
            background-color: #e0a800;
            border-color: #d39e00;
            color: #000;
        }

        .btn-sm {
            padding: 0.4rem 0.8rem;
        }

        .content-area {
            padding: 2rem;
        }
    </style>
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
        <jsp:include page="../WEB-INF/includes/navigation.jsp">
            <jsp:param name="activePage" value="course"/>
        </jsp:include>

        <!-- Main Content -->
        <div class="col-md-8 col-lg-9 content-area">
            <div class="card shadow-lg">
                <div class="card-header">
                    <div class="d-flex justify-content-between align-items-center">
                        <h4 class="mb-0 fw-bold text-dark">Course Management</h4>
                        <a href="${pageContext.request.contextPath}/management/addcourse.jsp" class="btn btn-success">
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
                        <div class="col-md-6 text-md-end mt-3 mt-md-0">
                            <div class="dropdown">
                                <button class="btn btn-primary dropdown-toggle" type="button" id="sortDropdown"
                                        data-bs-toggle="dropdown" aria-expanded="false">
                                    Sort By
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="sortDropdown">
                                    <li><a class="dropdown-item"
                                           href="${pageContext.request.contextPath}/management/course/list?sortBy=courseName<%= currentQuery != null ? "&query=" + currentQuery : "" %>">Sort
                                        by Name</a></li>
                                </ul>
                            </div>
                        </div>
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
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody id="course list">
                            <% if (l != null)
                                for (Course i : l) { %>
                            <tr>
                                <td><%= i.getId() %>
                                </td>
                                <td><%= i.getName() %>
                                </td>
                                <td><%= i.getYear() %>
                                </td>
                                <td><%= i.getLecture() %>
                                </td>
                                <td><%= i.getNote() %>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/management/course?id=<%= i.getId() %>&year=<%= i.getYear() %>"
                                       class="btn btn-sm btn-primary">Edit</a>
                                </td>
                            </tr>
                            <% } %>
                            </tbody>
                        </table>
                    </div>

                    <!-- Pagination -->
                    <div class="d-flex justify-content-center mt-4">
                        <nav aria-label="Page navigation">
                            <ul class="pagination">
                                <li class="page-item <%= currentPage <= 0 ? "disabled" : "" %>">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/management/course/list?<%= currentQuery != null ? "&query=" + currentQuery : "" %><%= currentSortBy != null ? "&sortBy=" + currentSortBy : "" %>&page=<%= currentPage - 1 %>"
                                       aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                    </a>
                                </li>
                                <% for (int i = 0; i < totalPage; i++) { %>
                                <li class="page-item <%= currentPage == i ? "active" : "" %>">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/management/course/list?<%= currentQuery != null ? "&query=" + currentQuery : "" %><%= currentSortBy != null ? "&sortBy=" + currentSortBy : "" %>&page=<%= i %>"><%= i + 1 %>
                                    </a>
                                </li>
                                <% } %>
                                <li class="page-item <%= currentPage >= (totalPage - 1) ? "disabled" : "" %>">
                                    <a class="page-link"
                                       href="${pageContext.request.contextPath}/management/course/list?<%= currentQuery != null ? "&query=" + currentQuery : "" %><%= currentSortBy != null ? "&sortBy=" + currentSortBy : "" %>&page=<%= currentPage + 1 %>"
                                       aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/scripts/editcourse.js"></script>
</body>
</html>
