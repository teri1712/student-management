package org.decade.studentmanangement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.*;

@Entity
@Table(name = "StaffUser")
public class StaffUser {

        public StaffUser() {
        }

        public StaffUser(String name, String userName, String password) {
                this(name, userName, password, "admin");
        }

        public StaffUser(String name, String userName, String password, String role) {
                this.name = name;
                this.userName = userName;
                this.password = password;
                this.role = role;
        }

        @Column(name = "fullname", columnDefinition = "nchar(100)")
        @NotBlank
        @Size(max = 100)
        private String name;

        @Id
        @Column(name = "username", length = 100)
        @NotBlank
        @Size(max = 100)
        private String userName;

        @Column(name = "pw", length = 100)
        @NotBlank
        @Size(min = 4, max = 100)
        private String password;

        @Column(name = "role", length = 20)
        @NotBlank
        @Size(max = 20)
        private String role;

        public String getName() {
                return name;
        }

        public String getUserName() {
                return userName;
        }

        public String getPassword() {
                return password;
        }

        public String getRole() {
                return role;
        }

        public void setName(String name) {
                this.name = name;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public void setRole(String role) {
                this.role = role;
        }
}
