package org.decade.studentmanangement.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "Notification")
public class Notification {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "idCourse", length = 10, columnDefinition = "char(10)")
        private String courseId;

        @Column(name = "courseYear")
        private int courseYear;

        @Column(name = "teacherUsername", length = 100, columnDefinition = "char(100)")
        private String teacherUsername;

        @Column(name = "content", length = 500)
        private String content;

        @Column(name = "createdAt")
        private Timestamp createdAt;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumns({
                @JoinColumn(name = "idCourse", referencedColumnName = "id", insertable = false, updatable = false),
                @JoinColumn(name = "courseYear", referencedColumnName = "courseYear", insertable = false, updatable = false)
        })
        private Course course;

        @PrePersist
        public void prePersist() {
                if (createdAt == null) {
                        createdAt = new Timestamp(System.currentTimeMillis());
                }
        }

        public Notification() {
        }

        public Notification(String courseId, int courseYear, String teacherUsername, String content) {
                this.courseId = courseId;
                this.courseYear = courseYear;
                this.teacherUsername = teacherUsername;
                this.content = content;
        }

        public Long getId() {
                return id;
        }

        public String getCourseId() {
                return courseId;
        }

        public int getCourseYear() {
                return courseYear;
        }

        public String getTeacherUsername() {
                return teacherUsername;
        }

        public String getContent() {
                return content;
        }

        public Timestamp getCreatedAt() {
                return createdAt;
        }

        public Course getCourse() {
                return course;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public void setCourseId(String courseId) {
                this.courseId = courseId;
        }

        public void setCourseYear(int courseYear) {
                this.courseYear = courseYear;
        }

        public void setTeacherUsername(String teacherUsername) {
                this.teacherUsername = teacherUsername;
        }

        public void setContent(String content) {
                this.content = content;
        }

        public void setCreatedAt(Timestamp createdAt) {
                this.createdAt = createdAt;
        }

        public void setCourse(Course course) {
                this.course = course;
        }
}
