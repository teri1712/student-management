package org.decade.studentmanangement.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Student_Course")
public class StudentCourse {

        @EmbeddedId
        @NotNull
        @Valid
        private StudentCourseId id;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "idStudent", referencedColumnName = "id", insertable = false, updatable = false)
        @NotNull
        private Student student;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumns({
                @JoinColumn(name = "idCourse", referencedColumnName = "id", insertable = false, updatable = false),
                @JoinColumn(name = "courseYear", referencedColumnName = "courseYear", insertable = false, updatable = false)
        })
        @NotNull
        private Course course;

        @OneToMany(mappedBy = "studentCourse", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
        @OrderBy("semester DESC, id DESC")
        @Valid
        private List<Assessment> assessments = new ArrayList<>();

        public StudentCourse() {
        }

        public StudentCourse(Course course, Student student) {
                this.course = course;
                this.student = student;
        }

        public StudentCourseId getId() {
                return id;
        }

        public void setId(StudentCourseId id) {
                this.id = id;
        }

        public Course getCourse() {
                return course;
        }

        public void setCourse(Course course) {
                this.course = course;
        }

        public Student getStudent() {
                return student;
        }

        public void setStudent(Student student) {
                this.student = student;
        }

        @Transient
        public Integer getScore() {
                if (assessments == null || assessments.isEmpty()) return null;
                Integer val = assessments.get(0).getScore();
                return val;
        }

        public List<Assessment> getAssessments() {
                return assessments;
        }

        public void setAssessments(List<Assessment> assessments) {
                this.assessments = assessments;
        }
}
