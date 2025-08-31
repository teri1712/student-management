package org.decade.studentmanangement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.*;
import java.sql.Date;

@Entity
@Table(name = "Student")
public class Student {

        public Student() {
        }

        public Student(String id, String fullname, Date birthDay, int grade, String address, String notes) {
                this.id = id;
                this.fullname = fullname;
                this.grade = grade;
                this.address = address;
                this.birthDay = birthDay;
                this.notes = notes;
        }

        @Id
        @Column(name = "id", length = 10)
        @NotBlank
        @Size(max = 10)
        private String id;

        @Column(name = "fullname", columnDefinition = "nchar(100)")
        @NotBlank
        @Size(max = 100)
        private String fullname;

        @Column(name = "grade")
        @Min(1)
        @Max(4)
        private int grade;

        @Column(name = "birthday")
        @Past
        private Date birthDay;

        @Column(name = "address", columnDefinition = "nchar(100)")
        @Size(max = 100)
        private String address;

        @Column(name = "notes", columnDefinition = "nchar(100)")
        @Size(max = 100)
        private String notes;

        public String getId() {
                return id;
        }

        public String getFullname() {
                return fullname;
        }

        public int getGrade() {
                return grade;
        }

        public Date getBirthDay() {
                return birthDay;
        }

        public void setId(String id) {
                this.id = id;
        }

        public void setFullname(String name) {
                this.fullname = name;
        }

        public void setGrade(int grade) {
                this.grade = grade;
        }

        public void setBirthDay(Date birthDay) {
                this.birthDay = birthDay;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public void setNotes(String notes) {
                this.notes = notes;
        }

        public String getAddress() {
                return address;
        }

        public String getNotes() {
                return notes;
        }

}