<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<link href="${pageContext.request.contextPath}/css/navigation.css" rel="stylesheet">
<div class="col-md-4 col-lg-3 sidebar shadow-sm">
    <div class="d-flex flex-column h-100">
        <h4 class="mb-4 fw-bold text-dark">Navigation</h4>
        <nav class="d-flex flex-column gap-2">
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