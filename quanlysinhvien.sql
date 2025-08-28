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
    role     varchar(20),
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
create table QuanLySinhVien.FileAttachment
(
    id            bigint auto_increment,
    ownerUsername char(100),
    type          varchar(50) default 'certificate',
    path          varchar(255),
    createdAt     timestamp   default current_timestamp,
    constraint PK_FileAttachment primary key (id)
);

alter table QuanLySinhVien.FileAttachment
    add foreign key (ownerUsername) references StaffUser (username);

-- Insert sample data
INSERT INTO QuanLySinhVien.Student (id, fullname, birthday, grade, address, notes)
VALUES ('SV001', 'Nguyen Van An', '2000-05-15', 3, 'Ha Noi', 'Good student'),
       ('SV002', 'Tran Thi Binh', '2001-03-20', 2, 'Ho Chi Minh', 'Active in clubs'),
       ('SV003', 'Le Minh Cuong', '2000-11-10', 4, 'Da Nang', 'Sport team captain'),
       ('SV004', 'Pham Thu Dung', '2001-07-25', 1, 'Can Tho', 'New student'),
       ('SV005', 'Hoang Van Em', '2000-12-30', 3, 'Hai Phong', 'Class representative');

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
       ('SV005', 'PHY101', 2025);

INSERT INTO QuanLySinhVien.FileAttachment (ownerUsername, path)
VALUES ('teacher1', 'certificate-sample.png')