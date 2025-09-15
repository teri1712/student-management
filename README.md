# Student Management – Deployment Guide

## Requirements

* **Apache Tomcat 8+**
* **MySQL 10+**
* **`quanlysinhvien.sql`** (included in this repo)

## Demo

A short live version of this project is available at:  
[https://drive.google.com/file/d/1aA4ecMHrku7M9cRj9M-DvkRN7aKuGswh/view?usp=sharing](https://drive.google.com/file/d/1aA4ecMHrku7M9cRj9M-DvkRN7aKuGswh/view?usp=sharing)

## 1 – Build and Deploy

1. Execute:

   ```bash
   mvn clean package
   ```
   This produces `.war` in the *target/* directory.
2. Copy the WAR into **`${CATALINA_BASE}/webapps/`**.
3. Start (or restart) Tomcat – it unpacks and serves the app automatically.

## 2 – Initialise the Database

Import the schema **once** before first run:

```bash
mysql -u <user> -p < quanlysinhvien.sql
```

## 3 – Configure Database (Tomcat JNDI Resource)

This app uses a Tomcat-managed DataSource defined in `src/main/webapp/META-INF/context.xml`.
Adjust credentials and (optionally) the host there:

```xml

<Resource name="jdbc/StudentDS"
          auth="Container"
          type="javax.sql.DataSource"
          factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
          driverClassName="com.mysql.cj.jdbc.Driver"
          url="jdbc:mysql://localhost:3306/QuanLySinhVien"
          username="root"
          password="root"/>
```

NOTES: When running with Docker Compose, set `localhost` to the `mysql`.

## 4 – First Run Checklist (in case of running without Docker Compose)

1. MySQL is running and the *quanlysinhvien* schemas exists.
2. Tomcat has been started/restarted.
3. Navigate to `http://localhost:8080//StudentManangement` – the login page should appear.

## 5 – Build and Run with Docker Compose

Prerequisites:

- Docker and Docker Compose installed
- Set `localhost` to the `mysql` in `context.xml`
  Run:

```bash
docker-compose up --build
```

What happens:

- The compose file starts two services: `mysql` and `server` (Tomcat).
- MYSQL's port 3306 is forwarded on localhost. The server's port 8080 is forwarded on localhost.
- Access http://localhost:8080/ to see the app.

# Student Management – Features and Flows

This document summarizes the application features, role-based flows, and key technical decisions.

## Roles and Access

- Admin
    - Manage courses (add, edit, delete, list, search/sort, paginate)
    - Manage students (add, edit, delete, list, search/sort, paginate)
    - Create teacher accounts (username/password default to teacher ID)
    - Create admin accounts (admin-only page)
    - Add students to courses
    - View latest scores on the course edit page (read-only)
- Teacher
    - View “My Courses” with sorting and optional filter by year
    - See student count per course
    - View students of a selected course and their latest scores
    - Add assessments for students (inline form) and import CSV on the teacher course page
    - Post course notifications (students see them in real time via simple polling)
    - See their certificate on the main teacher page
- Student
    - View “My Grades” with optional year filter
    - Enter a course from the grades table to see details
    - See classmates enrolled in the course
    - See course notifications in real time (simple polling)
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
- Teacher can add one assessment inline for a student on the teacher course page
- CSV import of assessments supported on the teacher course page (/teacher/assessment)
    - CSV format: studentId,semester,assessYear,score
    - Sample file: samples/assessments-sample.csv
- Admin course edit page shows latest score (read-only)
- Student GPA calculation ignores enrollments with no assessments

## Teacher Views

- My Courses (sortable, filter by year)
    - Displays student count per course
    - Link to Course details page to see enrolled students and latest scores
- Teacher Course page
    - Add assessments inline and import CSV for scores
    - Post course notifications (Bootstrap-styled list, left accent)

## Course Notifications

- Endpoint: GET /notifications?courseId={id}&year={year}&sinceId={lastId}
    - Returns JSON array of latest notifications (ordered by id desc)
- Endpoint: POST /notifications (teacher only; must own the course)
    - Body params: courseId, year, content
- UI/Behavior
    - Implemented with a lightweight polling script (no long-polling)
    - Shared JS: scripts/course-notifs.js (imported by teacher and student course pages)
    - Renders with Bootstrap list-group items; no inline script in JSP

## Student Views

- My Grades
    - Optional filter by year
    - Each row has an Enter button to open the selected course
    - Displays latest score per course, and aggregates average score and GPA (score/25)
- Course Details (for student)
    - Shows classmates (student ID and name only)
    - Shows course notifications with the same Bootstrap styling and polling behavior

## JPA & DI

- Persistence provider: Hibernate JPA, MySQL backend
- JTA DataSource: `java:comp/env/jdbc/StudentDS` configured in `src/main/webapp/META-INF/context.xml` (Tomcat JNDI)
- EntityManagerFactory created from persistence unit `StudentManangementPU` (JTA); EntityManager is produced per-request
  via CDI (see Resources.java)
- Services (CourseService, StudentService, UserService, CourseStudentService, AssessmentService) use JPA (EntityManager
  per operation)
- JPA hbm2ddl is set to validate; schema provided by SQL script

## SQL Seed

- Schema: quanlysinhvien.sql
    - Creates all tables, constraints, and indexes
    - Seeds exactly one initial admin: admin / admin123
    - Provides sample students, courses, and enrollments
    - Adds Notification table (for course announcements), index on (idCourse, courseYear, id), and sample notifications