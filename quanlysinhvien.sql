drop database if exists QuanLySinhVien;
create database QuanLySinhVien;

create table QuanLySinhVien.Student
(
    id       char(10),
    fullname nvarchar(100),
    birthday date,
    grade    int,
    address  nvarchar(100),
    notes    nvarchar(100),
    constraint PK_Student primary key (id)
);
create table QuanLySinhVien.StaffUser
(
    username char(100) unique,
    pw       char(100),
    fullname nvarchar(100),
    role     varchar(20),
    constraint PK_Staff primary key (username)
);

create table QuanLySinhVien.Course
(
    id         char(10),
    courseName nvarchar(100),
    lecture    nvarchar(100),
    courseYear int,
    notes      nvarchar(100),
    constraint PK_Course primary key (id, courseYear)
);


create table QuanLySinhVien.Student_Course
(
    idStudent  char(10),
    idCourse   char(10),
    courseYear int,
    constraint PK_Student_Course primary key (idStudent, idCourse, courseYear)
);


create table QuanLySinhVien.Assessment
(
    id         bigint auto_increment,
    idStudent  char(10),
    idCourse   char(10),
    courseYear int,
    semester   int,
    assessYear int,
    score      int,
    assessedAt timestamp default current_timestamp,
    constraint PK_Assessment primary key (id)
);


create table QuanLySinhVien.FileAttachment
(
    id            bigint auto_increment,
    ownerUsername char(100),
    type          varchar(50) default 'certificate',
    path          varchar(255),
    createdAt     timestamp   default current_timestamp,
    constraint PK_FileAttachment primary key (id)
);

create table QuanLySinhVien.Club
(
    id          char(10),
    clubName    nvarchar(100),
    description nvarchar(255),
    constraint PK_Club primary key (id)
);

create table QuanLySinhVien.Student_Club
(
    idStudent char(10),
    idClub    char(10),
    joinDate  date,
    constraint PK_Student_Club primary key (idStudent, idClub),
    foreign key (idStudent) references QuanLySinhVien.Student (id),
    foreign key (idClub) references QuanLySinhVien.Club (id)
);

create table QuanLySinhVien.Event
(
    id        bigint auto_increment,
    eventName nvarchar(100),
    idClub    char(10),
    eventDate datetime,
    location  nvarchar(100),
    constraint PK_Event primary key (id),
    foreign key (idClub) references QuanLySinhVien.Club (id)
);



alter table QuanLySinhVien.Assessment
    add foreign key (idStudent) references Student (id);
alter table QuanLySinhVien.Assessment
    add foreign key (idCourse, courseYear) references Course (id, courseYear);

alter table QuanLySinhVien.Student_Course
    add foreign key (idStudent) references Student (id);
alter table QuanLySinhVien.Student_Course
    add foreign key (idCourse, courseYear) references Course (id, courseYear);
create index idx_student_fullname on QuanLySinhVien.Student (fullname);

-- Attachments table
alter table QuanLySinhVien.FileAttachment
    add foreign key (ownerUsername) references StaffUser (username);

-- Notifications for courses
create table QuanLySinhVien.Notification
(
    id              bigint auto_increment,
    idCourse        char(10),
    courseYear      int,
    teacherUsername char(100),
    content         nvarchar(255),
    createdAt       timestamp default current_timestamp,
    constraint PK_Notification primary key (id),
    foreign key (idCourse, courseYear) references QuanLySinhVien.Course (id, courseYear),
    foreign key (teacherUsername) references QuanLySinhVien.StaffUser (username)
);

create index idx_notif_course on QuanLySinhVien.Notification (idCourse, courseYear, id);

-- Insert sample data
INSERT INTO QuanLySinhVien.Student (id, fullname, birthday, grade, address, notes)
VALUES ('SV001', 'Nguyen Van An', '2000-05-15', 3, 'Ha Noi', 'Good student'),
       ('SV002', 'Tran Thi Binh', '2001-03-20', 2, 'Ho Chi Minh', 'Active in clubs'),
       ('SV003', 'Le Minh Cuong', '2000-11-10', 4, 'Da Nang', 'Sport team captain'),
       ('SV004', 'Pham Thu Dung', '2001-07-25', 1, 'Can Tho', 'New student'),
       ('SV005', 'Hoang Van Em', '2000-12-30', 3, 'Hai Phong', 'Class representative'),
       ('SV006', 'Vu Thi Huong', '2002-12-17', 5, 'Can Tho', 'student'),
       ('SV007', 'Tran Van Khanh', '2002-12-17', 5, 'Can Tho', 'student'),
       ('SV008', 'Le Thi Mai', '2002-12-17', 5, 'Can Tho', 'student'),
       ('SV009', 'Pham Van Nam', '2002-12-17', 5, 'Can Tho', 'student'),
       ('SV010', 'Nguyen Thi Oanh', '2002-12-17', 5, 'Can Tho', 'student');

INSERT INTO QuanLySinhVien.StaffUser (username, pw, fullname, role)
VALUES ('admin', 'admin123', 'Administrator', 'admin'),
       ('SV001', 'SV001', 'Nguyen Van A', 'student'),
       ('SV002', 'SV002', 'Tran Thi Binh', 'student'),
       ('SV003', 'SV003', 'Le Minh Cuong', 'student'),
       ('SV004', 'SV004', 'Pham Thu Dung', 'student'),
       ('teacher1', 'teacher1', 'Pham Thu Dung', 'teacher');

INSERT INTO QuanLySinhVien.Course (id, courseName, lecture, courseYear, notes)
VALUES ('COMP101', 'Introduction to Computing', 'teacher1', 2025, 'Basic course'),
       ('MATH201', 'Advanced Mathematics', 'teacher1', 2025, 'Required course'),
       ('PHY101', 'Physics Fundamentals', 'teacher1', 2025, 'With lab sessions'),
       ('ENG201', 'English Communication', 'teacher1', 2025, 'Interactive course'),
       ('PROG301', 'Programming Java', 'teacher1', 2025, 'Advanced level');

INSERT INTO QuanLySinhVien.Student_Course (idStudent, idCourse, courseYear)
VALUES ('SV001', 'COMP101', 2025),
       ('SV001', 'MATH201', 2025),
       ('SV002', 'PHY101', 2025),
       ('SV003', 'ENG201', 2025),
       ('SV004', 'PROG301', 2025),
       ('SV005', 'COMP101', 2025),
       ('SV002', 'MATH201', 2025),
       ('SV003', 'COMP101', 2025),
       ('SV004', 'ENG201', 2025),
       ('SV005', 'PHY101', 2025),
       ('SV006', 'COMP101', 2025),
       ('SV007', 'COMP101', 2025),
       ('SV008', 'COMP101', 2025),
       ('SV009', 'COMP101', 2025),
       ('SV010', 'COMP101', 2025);


INSERT INTO QuanLySinhVien.Assessment (idStudent, idCourse, courseYear, semester, assessYear, score)
VALUES
-- First assessment
('SV001', 'COMP101', 2025, 1, 2025, 8),
('SV002', 'COMP101', 2025, 1, 2025, 7),
('SV003', 'COMP101', 2025, 1, 2025, 9),
('SV004', 'COMP101', 2025, 1, 2025, 6),
('SV005', 'COMP101', 2025, 1, 2025, 8),
('SV006', 'COMP101', 2025, 1, 2025, 7),
('SV007', 'COMP101', 2025, 1, 2025, 8),
('SV008', 'COMP101', 2025, 1, 2025, 9),
('SV009', 'COMP101', 2025, 1, 2025, 6),
('SV010', 'COMP101', 2025, 1, 2025, 7),

-- Second assessment
('SV001', 'COMP101', 2025, 2, 2025, 9),
('SV002', 'COMP101', 2025, 2, 2025, 8),
('SV003', 'COMP101', 2025, 2, 2025, 10),
('SV004', 'COMP101', 2025, 2, 2025, 7),
('SV005', 'COMP101', 2025, 2, 2025, 9),
('SV006', 'COMP101', 2025, 2, 2025, 8),
('SV007', 'COMP101', 2025, 2, 2025, 7),
('SV008', 'COMP101', 2025, 2, 2025, 8),
('SV009', 'COMP101', 2025, 2, 2025, 7),
('SV010', 'COMP101', 2025, 2, 2025, 8),

-- Third assessment
('SV001', 'COMP101', 2025, 3, 2025, 8),
('SV002', 'COMP101', 2025, 3, 2025, 9),
('SV003', 'COMP101', 2025, 3, 2025, 9),
('SV004', 'COMP101', 2025, 3, 2025, 8),
('SV005', 'COMP101', 2025, 3, 2025, 10),
('SV006', 'COMP101', 2025, 3, 2025, 9),
('SV007', 'COMP101', 2025, 3, 2025, 8),
('SV008', 'COMP101', 2025, 3, 2025, 9),
('SV009', 'COMP101', 2025, 3, 2025, 8),
('SV010', 'COMP101', 2025, 3, 2025, 9);

INSERT INTO QuanLySinhVien.FileAttachment (ownerUsername, path)
VALUES ('teacher1', 'certificate-sample.png');

-- Sample course notifications
INSERT INTO QuanLySinhVien.Notification (idCourse, courseYear, teacherUsername, content)
VALUES ('COMP101', 2025, 'teacher1', 'Welcome to the course!'),
       ('COMP101', 2025, 'teacher1', 'Assignment 1 released.'),
       ('MATH201', 2025, 'teacher1', 'Midterm scheduled next week.');

INSERT INTO QuanLySinhVien.Club (id, clubName, description)
VALUES ('CLUB01', 'IT Club', 'Club for students interested in Information Technology'),
       ('CLUB02', 'English Club', 'Club for improving English skills'),
       ('CLUB03', 'Sports Club', 'Club for various sports activities');

INSERT INTO QuanLySinhVien.Student_Club (idStudent, idClub, joinDate)
VALUES ('SV001', 'CLUB01', '2023-01-15'),
       ('SV002', 'CLUB01', '2023-01-15'),
       ('SV002', 'CLUB02', '2023-02-20'),
       ('SV003', 'CLUB03', '2023-03-10'),
       ('SV004', 'CLUB02', '2023-02-20');

INSERT INTO QuanLySinhVien.Event (eventName, idClub, eventDate, location)
VALUES ('Hackathon 2024', 'CLUB01', '2024-10-25 08:00:00', 'Main Hall'),
       ('English Speaking Contest', 'CLUB02', '2024-11-15 14:00:00', 'Room 201'),
       ('Football Tournament', 'CLUB03', '2024-12-05 09:00:00', 'Stadium');
