ALTER
    USER 'root'@'localhost' IDENTIFIED BY 'admin';

drop database if exists QuanLySinhVien;
create database QuanLySinhVien;

create table QuanLySinhVien.Student
(
    id       char(10),
    fullname nchar(100),
    birthday date,
    grade    int,
    address  nchar(100),
    notes    nchar(100),
    constraint PK_Student primary key (id)
);
create table QuanLySinhVien.StaffUser
(
    username char(100) unique,
    pw       char(100),
    fullname nchar(100),
    constraint PK_Staff primary key (username)
);

create table QuanLySinhVien.Course
(
    id         char(10),
    courseName nchar(100),
    lecture    nchar(100),
    courseYear int,
    notes      nchar(100),
    constraint PK_Course primary key (id, courseYear)
);


create table QuanLySinhVien.Student_Course
(
    idStudent  char(10),
    idCourse   char(10),
    courseYear int,
    score      int,
    constraint PK_Student_Course primary key (idStudent, idCourse, courseYear)
);

alter table QuanLySinhVien.Student_Course
    add foreign key (idStudent) references Student (id);
alter table QuanLySinhVien.Student_Course
    add foreign key (idCourse, courseYear) references Course (id, courseYear);
create index idx_student_fullname on QuanLySinhVien.Student (fullname);

-- Insert sample data
INSERT INTO QuanLySinhVien.Student (id, fullname, birthday, grade, address, notes)
VALUES ('SV001', 'Nguyen Van An', '2000-05-15', 3, 'Ha Noi', 'Good student'),
       ('SV002', 'Tran Thi Binh', '2001-03-20', 2, 'Ho Chi Minh', 'Active in clubs'),
       ('SV003', 'Le Minh Cuong', '2000-11-10', 4, 'Da Nang', 'Sport team captain'),
       ('SV004', 'Pham Thu Dung', '2001-07-25', 1, 'Can Tho', 'New student'),
       ('SV005', 'Hoang Van Em', '2000-12-30', 3, 'Hai Phong', 'Class representative');

INSERT INTO QuanLySinhVien.StaffUser (username, pw, fullname)
VALUES ('admin', 'admin123', 'Administrator'),
       ('teacher1', 'teach123', 'Nguyen Thi Huong'),
       ('staff1', 'staff123', 'Tran Van Minh'),
       ('academic', 'acad123', 'Le Thi Nga'),
       ('manager', 'manage123', 'Pham Van Quan');

INSERT INTO QuanLySinhVien.Course (id, courseName, lecture, courseYear, notes)
VALUES ('COMP101', 'Introduction to Computing', 'Dr. Smith', 2025, 'Basic course'),
       ('MATH201', 'Advanced Mathematics', 'Prof. Johnson', 2025, 'Required course'),
       ('PHY101', 'Physics Fundamentals', 'Dr. Wilson', 2025, 'With lab sessions'),
       ('ENG201', 'English Communication', 'Ms. Brown', 2025, 'Interactive course'),
       ('PROG301', 'Programming Java', 'Dr. Davis', 2025, 'Advanced level');

INSERT INTO QuanLySinhVien.Student_Course (idStudent, idCourse, courseYear, score)
VALUES ('SV001', 'COMP101', 2025, 85),
       ('SV001', 'MATH201', 2025, 78),
       ('SV002', 'PHY101', 2025, 92),
       ('SV003', 'ENG201', 2025, 88),
       ('SV004', 'PROG301', 2025, 75),
       ('SV005', 'COMP101', 2025, 90),
       ('SV002', 'MATH201', 2025, 85),
       ('SV003', 'COMP101', 2025, 82),
       ('SV004', 'ENG201', 2025, 95),
       ('SV005', 'PHY101', 2025, 88);