<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<%@page import="org.decade.studentmanangement.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="org.decade.studentmanangement.model.StudentCourse" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Edit Course - Student Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/scripts/editcourse.js"></script>
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


        .btn-outline-success {
            color: #28a745;
            border-color: #28a745;
        }

        .btn-outline-success:hover {
            background-color: #28a745;
            border-color: #28a745;
            color: white;
        }

        .btn-outline-danger {
            color: #dc3545;
            border-color: #dc3545;
        }

        .btn-outline-danger:hover {
            background-color: #dc3545;
            border-color: #dc3545;
            color: white;
        }

        .btn-sm {
            padding: 0.4rem 0.8rem;
            font-size: 0.875rem;
        }

        .form-control-sm {
            padding: 0.4rem 0.8rem;
            font-size: 0.875rem;
            border-radius: 8px;
        }
    </style>
</head>
<body>
<%
    Course course = (Course) request.getAttribute("course");
    List<StudentCourse> students = (List<StudentCourse>) request.getAttribute("students");
%>
<div class="container-fluid">
    <div class="row">
        <jsp:include page="../WEB-INF/includes/navigation.jsp">
            <jsp:param name="activePage" value="course"/>
        </jsp:include>

        <!-- Main Content -->
        <div class="col-md-8 col-lg-9 content-area">
            <div class="card shadow-lg mb-4">
                <div class="card-header">
                    <h4 class="mb-0 fw-bold text-dark">Edit Course</h4>
                </div>
                <div class="card-body p-4">
                    <p id="status" class="alert alert-info d-none"></p>

                    <form class="form-container" action="${pageContext.request.contextPath}/management/course"
                          method="post">
                        <div class="form-group mb-4">
                            <label for="id" class="form-label">Course ID</label>
                            <input type="text" class="form-control" id="id" name="id" value="<%=course.getId()%>"
                                   readonly>
                        </div>

                        <div class="form-group mb-4">
                            <label for="name" class="form-label">Course Name</label>
                            <input type="text" class="form-control" id="name" name="name" value="<%=course.getName()%>"
                                   placeholder="Enter course name">
                        </div>

                        <div class="form-group mb-4">
                            <label for="lecture" class="form-label">Lecturer</label>
                            <input type="text" class="form-control" id="lecture" name="lecture"
                                   value="<%=course.getLecture()%>" placeholder="Enter lecturer name">
                        </div>

                        <div class="form-group mb-4">
                            <label for="year" class="form-label">Year</label>
                            <input type="text" class="form-control" id="year" name="year" value="<%=course.getYear()%>"
                                   readonly>
                        </div>

                        <div class="form-group mb-4">
                            <label for="notes" class="form-label">Notes</label>
                            <textarea class="form-control" id="notes" name="notes" rows="3"
                                      placeholder="Enter any additional notes"><%=course.getNote()%></textarea>
                        </div>

                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-primary">Update Course</button>
                            <button type="button" class="btn btn-danger"
                                    onclick="deleteCourse('${pageContext.request.contextPath}', '<%=course.getId()%>', <%=course.getYear()%>)">
                                Delete Course
                            </button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="card shadow-lg mb-4">
                <div class="card-header">
                    <h4 class="mb-0 fw-bold text-dark">Add Student to Course</h4>
                </div>
                <div class="card-body p-4">
                    <form class="form-group" action="${pageContext.request.contextPath}/management/course-student"
                          method="post">
                        <label for="studentId" class="form-label">Enter Student ID</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="studentId" name="studentId"
                                   placeholder="Enter student ID">
                            <input type="hidden" name="op" value="add">
                            <input type="hidden" name="courseId" value="<%=course.getId()%>">
                            <input type="hidden" name="year" value="<%=course.getYear()%>">
                            <button class="btn btn-primary" type="submit">Add Student</button>
                        </div>
                    </form>
                </div>
            </div>

            <div class="card shadow-lg">
                <div class="card-header">
                    <h4 class="mb-0 fw-bold text-dark">Students in Course</h4>
                </div>
                <div class="card-body p-4">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Grade</th>
                                <th>Birthday</th>
                                <th>Address</th>
                                <th>Notes</th>
                                <th>Score</th>
                                <th class="text-center">Actions</th>
                            </tr>
                            </thead>

                            <tbody id="student-list">
                            <% if (students != null)
                                for (StudentCourse student : students) { %>
                            <tr>
                                <td><%= student.getStudent().getId() %>
                                </td>
                                <td><%= student.getStudent().getName() %>
                                </td>
                                <td><%= student.getStudent().getGrade() %>
                                </td>
                                <td><%= student.getStudent().getBirthDay() %>
                                </td>
                                <td><%= student.getStudent().getAddress() %>
                                </td>
                                <td><%= student.getStudent().getNotes() %>
                                </td>
                                <td>
                                    <input type="number"
                                           name="score"
                                           value="<%= student.getScore() %>"
                                           class="form-control form-control-sm"
                                           form="updateForm_<%= student.getStudent().getId() %>">
                                </td>
                                <td class="text-nowrap text-center">
                                    <form id="updateForm_<%= student.getStudent().getId() %>"
                                          action="<%= request.getContextPath() %>/management/course-student/<%= student.getStudent().getId() %>"
                                          method="post"
                                          class="d-inline">
                                        <input type="hidden" name="courseId" value="<%= course.getId() %>">
                                        <input type="hidden" name="year" value="<%= course.getYear() %>">
                                        <input type="hidden" name="op" value="update">
                                        <input type="hidden" name="studentId"
                                               value="<%= student.getStudent().getId() %>">
                                        <button type="submit" class="btn btn-outline-success btn-sm me-1">
                                            <i class="bi bi-check-lg"></i>
                                        </button>
                                    </form>

                                    <div class="d-inline">
                                        <button type="button"
                                                class="btn btn-outline-danger btn-sm"
                                                onclick="deleteStudent('${pageContext.request.contextPath}','<%= course.getId() %>', <%= course.getYear() %>, '<%= student.getStudent().getId() %>')"
                                        >
                                            <i class="bi bi-trash"></i>
                                        </button>
                                    </div>
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
