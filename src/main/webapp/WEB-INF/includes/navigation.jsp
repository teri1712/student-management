<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<link href="${pageContext.request.contextPath}/css/navigation.css" rel="stylesheet">
<div class="col-md-4 col-lg-3 sidebar shadow-sm">
    <div class="d-flex flex-column h-100">
        <h4 class="mb-4 fw-bold text-dark">Navigation</h4>
        <nav class="d-flex flex-column gap-2">
            <%
                String role = null;
                jakarta.servlet.http.HttpSession navSession = request.getSession(false);
                Object u = navSession == null ? null : navSession.getAttribute("user");
                if (u instanceof org.decade.studentmanangement.model.StaffUser) {
                    role = ((org.decade.studentmanangement.model.StaffUser) u).getRole();
                }
                boolean isAdmin = "admin".equalsIgnoreCase(role);
                boolean isTeacher = "teacher".equalsIgnoreCase(role);
                boolean isStudent = "student".equalsIgnoreCase(role);
            %>
            <% if (isAdmin) { %>
            <a href="${pageContext.request.contextPath}/management/course/list"
               class="nav-item ${param.activePage == 'course' ? 'active' : ''}">
                <i class="bi bi-book me-2"></i>
                Course Management
            </a>
            <a href="${pageContext.request.contextPath}/management/student/list"
               class="nav-item ${param.activePage == 'student' ? 'active' : ''}">
                <i class="bi bi-people me-2"></i>
                Student Management
            </a>
            <a href="${pageContext.request.contextPath}/management/teacher/add"
               class="nav-item ${param.activePage == 'addTeacher' ? 'active' : ''}">
                <i class="bi bi-person-badge me-2"></i>
                Add Teacher
            </a>
            <a href="${pageContext.request.contextPath}/management/admin/add"
               class="nav-item ${param.activePage == 'addAdmin' ? 'active' : ''}">
                <i class="bi bi-shield-lock me-2"></i>
                Add Admin
            </a>
            <% } %>

            <% if (isTeacher) { %>
            <a href="${pageContext.request.contextPath}/teacher/courses"
               class="nav-item ${param.activePage == 'teacherCourses' ? 'active' : ''}">
                <i class="bi bi-briefcase me-2"></i>
                My Courses
            </a>
            <% } %>

            <% if (isStudent) { %>
            <a href="${pageContext.request.contextPath}/student/grades"
               class="nav-item ${param.activePage == 'studentGrades' ? 'active' : ''}">
                <i class="bi bi-mortarboard me-2"></i>
                My Grades
            </a>
            <% } %>
        </nav>
        <div class="mt-auto">
            <form action="${pageContext.request.contextPath}/logout" method="post">
                <button type="submit"
                        class="btn logout-btn w-100 d-flex align-items-center justify-content-center gap-2">
                    <i class="bi bi-box-arrow-right"></i>
                    Logout
                </button>
            </form>
        </div>
    </div>
</div>