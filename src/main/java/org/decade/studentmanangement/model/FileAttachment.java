package org.decade.studentmanangement.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.Instant;

@Entity
@Table(name = "FileAttachment")
public class FileAttachment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ownerUsername", referencedColumnName = "username")
        @NotNull
        @Valid
        private StaffUser owner;

        @Size(max = 50)
        private String type; // e.g., "certificate"

        @Column(name = "path", length = 255)
        @NotBlank
        @Size(max = 255)
        private String path; // web path or storage path

        @Column(name = "createdAt", nullable = false, updatable = false)
        @PastOrPresent
        private Instant createdAt = Instant.now();

        public FileAttachment() {
        }

        public FileAttachment(StaffUser owner, String type, String path) {
                this.owner = owner;
                this.type = type;
                this.path = path;
        }

        public Long getId() {
                return id;
        }

        public StaffUser getOwner() {
                return owner;
        }

        public String getType() {
                return type;
        }

        public String getPath() {
                return path;
        }

        public Instant getCreatedAt() {
                return createdAt;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public void setOwner(StaffUser owner) {
                this.owner = owner;
        }

        public void setType(String type) {
                this.type = type;
        }

        public void setPath(String path) {
                this.path = path;
        }
}