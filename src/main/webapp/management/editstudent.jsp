<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<%@page import="org.decade.studentmanangement.model.Student" %>
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/scripts/editstudent.js"></script>
    <style>


        .btn-primary {
            background-color: var(--bs-primary);
            border-color: grey;
            color: #000;
        }
        
    </style>
</head>
<body>
<%
    Student student = (Student) request.getAttribute("student");
    List<StudentCourse> courses = (List<StudentCourse>) request.getAttribute("courses");
%>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../WEB-INF/includes/navigation.jsp">
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
                                   value="<%=student.getName()%>" placeholder="Enter full name">
                        </div>

                        <div class="form-group mb-4">
                            <label for="birthday" class="form-label">Birthday</label>
                            <input type="date" class="form-control" id="birthday" name="birthday"
                                   value="<%=student.getBirthDay().toString()%>" placeholder="YYYY-MM-DD">
                        </div>

                        <div class="form-group mb-4">
                            <label for="grade" class="form-label">Grade</label>
                            <input type="text" class="form-control" id="grade" name="grade"
                                   value="<%=student.getGrade()%>"
                                   placeholder="Enter grade">
                        </div>

                        <div class="form-group mb-4">
                            <label for="address" class="form-label">Address</label>
                            <input type="text" class="form-control" id="address" name="address"
                                   value="<%=student.getAddress()%>" placeholder="Enter address">
                        </div>

                        <div class="form-group mb-4">
                            <label for="notes" class="form-label">Notes</label>
                            <textarea class="form-control" id="notes" name="notes" rows="3"
                                      placeholder="Enter any additional notes"><%=student.getNotes()%></textarea>
                        </div>

                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary">Update Student</button>
                            <button type="button" class="btn btn-danger"
                                    onclick="deleteStudent('${pageContext.request.contextPath}', '<%=student.getId()%>')">
                                Delete Student
                            </button>
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
                                <th>Score</th>
                            </tr>
                            </thead>
                            <tbody id="course list">
                            <% if (courses != null)
                                for (StudentCourse i : courses) { %>
                            <tr>
                                <td><%= i.getCourse().getId() %>
                                </td>
                                <td><%= i.getCourse().getName() %>
                                </td>
                                <td><%= i.getCourse().getYear() %>
                                </td>
                                <td><%= i.getCourse().getLecture() %>
                                </td>
                                <td><%= i.getCourse().getNote() %>
                                </td>
                                <td><%= i.getScore() %>
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

</body>
</html>
