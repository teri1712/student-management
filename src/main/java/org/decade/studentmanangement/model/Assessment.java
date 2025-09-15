package org.decade.studentmanangement.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.Instant;

@Entity
@Table(name = "Assessment")
public class Assessment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumns({
                @JoinColumn(name = "idStudent", referencedColumnName = "idStudent"),
                @JoinColumn(name = "idCourse", referencedColumnName = "idCourse"),
                @JoinColumn(name = "courseYear", referencedColumnName = "courseYear")
        })
        @NotNull
        @Valid
        private StudentCourse studentCourse;

        @Column(name = "semester")
        @NotNull
        @Min(1)
        @Max(2)
        private Integer semester;

        @Column(name = "assessYear")
        @NotNull
        @Min(1900)
        @Max(2100)
        private Integer assessYear;

        @Column(name = "score")
        @Min(0)
        @Max(100)
        private Integer score;

        @Column(name = "assessedAt", nullable = false, updatable = false)
        @PastOrPresent
        private Instant assessedAt = Instant.now();

        public Assessment() {
        }

        public Assessment(StudentCourse studentCourse, Integer semester, Integer assessYear, Integer score) {
                this.studentCourse = studentCourse;
                this.semester = semester;
                this.assessYear = assessYear;
                this.score = score;
        }

        public Long getId() {
                return id;
        }

        public StudentCourse getStudentCourse() {
                return studentCourse;
        }

        public Integer getSemester() {
                return semester;
        }

        public Integer getAssessYear() {
                return assessYear;
        }

        public Integer getScore() {
                return score;
        }

        public Instant getAssessedAt() {
                return assessedAt;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public void setStudentCourse(StudentCourse studentCourse) {
                this.studentCourse = studentCourse;
        }

        public void setSemester(Integer semester) {
                this.semester = semester;
        }

        public void setAssessYear(Integer assessYear) {
                this.assessYear = assessYear;
        }

        public void setScore(Integer score) {
                this.score = score;
        }
}