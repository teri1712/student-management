package org.decade.studentmanangement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudentCourseId implements Serializable {
        @Column(name = "idStudent", length = 10, columnDefinition = "char(10)")
        @NotBlank
        @Size(max = 10)
        private String studentId;

        @Column(name = "idCourse", length = 10, columnDefinition = "char(10)")
        @NotBlank
        @Size(max = 10)
        private String courseId;

        @Column(name = "courseYear")
        @Min(1900)
        @Max(2100)
        private int courseYear;

        public StudentCourseId() {
        }

        public StudentCourseId(String studentId, String courseId, int courseYear) {
                this.studentId = studentId;
                this.courseId = courseId;
                this.courseYear = courseYear;
        }

        public String getStudentId() {
                return studentId;
        }

        public void setStudentId(String studentId) {
                this.studentId = studentId;
        }

        public String getCourseId() {
                return courseId;
        }

        public void setCourseId(String courseId) {
                this.courseId = courseId;
        }

        public int getCourseYear() {
                return courseYear;
        }

        public void setCourseYear(int courseYear) {
                this.courseYear = courseYear;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                StudentCourseId that = (StudentCourseId) o;
                return courseYear == that.courseYear && Objects.equals(studentId, that.studentId) && Objects.equals(courseId, that.courseId);
        }

        @Override
        public int hashCode() {
                return Objects.hash(studentId, courseId, courseYear);
        }
}
