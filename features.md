# Student Management – Features and Flows

This document summarizes the application features, role-based flows, and key technical decisions.

## Roles and Access
- Admin
  - Manage courses (add, edit, delete, list, search/sort, paginate)
  - Manage students (add, edit, delete, list, search/sort, paginate)
  - Create teacher accounts (username/password default to teacher ID)
  - Create admin accounts (admin-only page)
  - Add students to courses
  - Manage assessments (add per-student assessment for a course or CSV import)
- Teacher
  - View “My Courses” with sorting and optional filter by year
  - See student count per course
  - View students of a selected course and their latest scores
  - See their certificate on the main teacher page
- Student
  - View “My Grades” with optional year filter
  - See latest score per enrolled course, average score and GPA

## Authentication & Authorization
- AuthenticationFilter requires a session user for:
  - /management/*, /teacher/*, /student/*
- AuthorizationFilter enforces roles:
  - /management/* → admin only
  - /teacher/* → teacher only
  - /student/* → student only
  - Legacy /signup endpoints are protected as admin-only
- Login redirects by role:
  - admin → /management/student/list
  - teacher → /teacher/courses
  - student → /student/grades

## Data Model (JPA)
- Student (id, fullname, birthday, grade, address, notes)
- StaffUser (username, pw, fullname, role)
- Course (id, courseName, lecture, courseYear, notes) with composite PK (id, courseYear)
- Student_Course (idStudent, idCourse, courseYear)
  - Modeled as entity StudentCourse with @EmbeddedId
  - One-to-many Assessments (latest score derived from Assessments)
- Assessment (id, idStudent, idCourse, courseYear, semester, assessYear, score, assessedAt)
  - Linked to StudentCourse via composite foreign key
- FileAttachment (id, ownerUsername → StaffUser.username, type, path, createdAt)
  - Used for teacher certificate (type = "certificate")

## Certificate Feature (Teacher)
- Each teacher has one certificate, displayed on the teacher’s main page (/teacher/courses):
  - The page shows the teacher’s latest certificate (if present) above the courses list
  - The navigation link to a separate certificates page is removed
  - When an admin creates a teacher, the system attaches a sample certificate if none exists yet
  - Sample asset shipped: /sample-certificate.png

## Course and Student Management
- Admin pages under /WEB-INF/management:
  - Courses: list, search by name, sort by year/name, edit course, add course
  - Students: list, search by name, sort by name/grade, edit student, add student
  - Student/profile page shows the student’s enrollments by optional year filter
- Joining students to a course
  - Admin can add student to course from course edit page

## Assessments and GPA
- Assessments record per-student scoring for a course and semester/year
- Latest score for a StudentCourse is derived from the most recent Assessment
- Admin can add one assessment inline for a student from the course edit page
- CSV import of assessments supported at the course edit page
  - CSV format: studentId,semester,assessYear,score
  - Sample file: samples/assessments-sample.csv
- Student GPA calculation ignores enrollments with no assessments

## Teacher Views
- My Courses (sortable, filter by year)
  - Displays student count per course
  - Link to Course details page to see enrolled students and latest scores

## Student Views
- My Grades
  - Optional filter by year
  - Displays latest score per course, and aggregates average score and GPA (score/25)

## JPA & DI
- Persistence provider: Hibernate JPA, MySQL backend
- EntityManagerFactory obtained via JNDI Resource (services/EntityManagerFactory) with custom ObjectFactory
- Services (CourseService, StudentService, UserService, CourseStudentService, AssessmentService) use JPA (EntityManager per operation)
- JPA hbm2ddl is set to validate; schema provided by SQL script

## SQL Seed
- Schema: quanlysinhvien.sql
  - Creates all tables, constraints, and indexes
  - Seeds exactly one initial admin: admin / admin123
  - Provides sample students, courses, and enrollments

## Deployment & Running
- Deployable as WAR to Tomcat 10+
- Context path example: /StudentManangement
- Welcome page index.jsp redirects to login.jsp
- Ensure MySQL is running and import the SQL before first run

## File Locations (selected)
- Teacher main page: src/main/webapp/WEB-INF/teacher/courses.jsp
- Teacher courses servlet: org.decade.studentmanangement.controller.TeacherCoursesServlet
- Admin add teacher: org.decade.studentmanangement.controller.AdminTeacherServlet
- Assessment handling: org.decade.studentmanangement.controller.AssessmentServlet
- CSV sample: samples/assessments-sample.csv
- Sample certificate: sample-certificate.png
