<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.List" %>
<%@ page import="org.decade.studentmanangement.model.FileAttachment" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Certificates - Teacher</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/theme.css" rel="stylesheet">
</head>
<body>
<%
    List<FileAttachment> files = (List<FileAttachment>) request.getAttribute("files");
%>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../includes/navigation.jsp">
            <jsp:param name="activePage" value="teacherCertificates"/>
        </jsp:include>

        <div class="col-md-8 col-lg-9 content-area">
            <div class="card shadow-lg">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h4 class="mb-0 fw-bold text-dark">My Certificates</h4>
                </div>
                <div class="card-body p-4">
                    <% if (files == null || files.isEmpty()) { %>
                    <div class="alert alert-info">No certificates uploaded yet. Newly created teachers get a sample certificate attached by admin action.</div>
                    <% } else { %>
                    <div class="row g-3">
                        <% for (FileAttachment f : files) { %>
                        <div class="col-md-4">
                            <div class="card">
                                <img class="card-img-top" src="${pageContext.request.contextPath}<%= f.getPath() %>" alt="certificate">
                                <div class="card-body">
                                    <div class="small text-muted"><%= f.getCreatedAt() %></div>
                                    <div class="fw-semibold">Type: <%= f.getType() %></div>
                                    <div class="text-truncate">Path: <%= f.getPath() %></div>
                                </div>
                            </div>
                        </div>
                        <% } %>
                    </div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
