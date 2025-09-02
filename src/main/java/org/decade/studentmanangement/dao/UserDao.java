package org.decade.studentmanangement.dao;

import org.decade.studentmanangement.model.FileAttachment;
import org.decade.studentmanangement.model.StaffUser;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    StaffUser getUser(String username) throws SQLException;
    void addUser(StaffUser user) throws SQLException;

    // CDI/JPA-based helpers for teacher certificate feature
    void addCertificate(String username, String relativePath) throws SQLException;
    String getLatestCertificatePath(String username) throws SQLException;
    List<FileAttachment> listCertificates(String username) throws SQLException;
}