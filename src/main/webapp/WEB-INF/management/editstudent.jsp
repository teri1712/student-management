<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<%@ page import="org.decade.studentmanangement.model.Student" %>
<%@ page import="java.util.List" %>
<%@ page import="org.decade.studentmanangement.model.StudentCourse" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit Student - Student Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/theme.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body>
<%
    Student student = (Student) request.getAttribute("student");
    List<StudentCourse> courses = (List<StudentCourse>) request.getAttribute("courses");
%>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../includes/navigation.jsp">
            <jsp:param name="activePage" value="student"/>
        </jsp:include>

        <!-- Main Content -->
        <div class="col-md-8 col-lg-9 content-area">
            <div class="card shadow-lg mb-4">
                <div class="card-header">
                    <h4 class="mb-0 fw-bold text-dark">Edit Student</h4>
                </div>
                <div class="card-body p-4">
                    <p id="status" class="alert alert-info d-none"></p>

                    <form class="form-container" action="${pageContext.request.contextPath}/management/student"
                          method="post">
                        <div class="form-group mb-4">
                            <label for="id" class="form-label">Student ID</label>
                            <input type="text" class="form-control" id="id" name="id" value="<%=student.getId()%>"
                                   readonly>
                        </div>

                        <div class="form-group mb-4">
                            <label for="fullname" class="form-label">Full Name</label>
                            <input type="text" class="form-control" id="fullname" name="fullname"
                                   value="<%=student.getFullname()%>" placeholder="Enter full name">
                        </div>

                        <div class="form-group mb-4">
                            <label for="birthday" class="form-label">Birthday</label>
                            <input type="date" class="form-control" id="birthday" name="birthday"
                                   value="<%=student.getBirthDay().toString()%>">
                        </div>

                        <div class="form-group mb-4">
                            <label for="grade" class="form-label">Grade</label>
                            <input type="text" class="form-control" id="grade" name="grade"
                                   value="<%=student.getGrade()%>">
                        </div>

                        <div class="form-group mb-4">
                            <label for="address" class="form-label">Address</label>
                            <input type="text" class="form-control" id="address" name="address"
                                   value="<%=student.getAddress()%>">
                        </div>

                        <div class="form-group mb-4">
                            <label for="notes" class="form-label">Notes</label>
                            <textarea class="form-control" id="notes" name="notes"
                                      rows="3"><%=student.getNotes()%></textarea>
                        </div>

                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary">Update Student</button>
                            <form action="${pageContext.request.contextPath}/management/student" method="post"
                                  class="d-inline">
                                <input type="hidden" name="op" value="delete">
                                <input type="hidden" name="id" value="<%=student.getId()%>">
                                <button type="submit" class="btn btn-danger">Delete Student</button>
                            </form>
                        </div>
                    </form>
                </div>
            </div>

            <div class="card shadow-lg">
                <div class="card-header">
                    <div class="d-flex justify-content-between align-items-center">
                        <h4 class="mb-0 fw-bold text-dark">Courses by Year</h4>
                        <form class="input-group w-auto"
                              action="${pageContext.request.contextPath}/management/student/<%= student.getId() %>"
                              method="get">
                            <input type="number" id="name" name="courseYear" class="form-control"
                                   placeholder="Enter year" min="0">
                            <button class="btn btn-primary" type="submit">Search</button>
                        </form>
                    </div>
                </div>
                <div class="card-body p-4">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Year</th>
                                <th>Lecturer</th>
                                <th>Notes</th>
                            </tr>
                            </thead>
                            <tbody id="course-list">
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
                                <td><%= sc.getCourse().getNote() %>
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
