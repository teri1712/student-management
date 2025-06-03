package org.decade.studentmanangement.dao;

import org.decade.studentmanangement.model.StaffUser;

import java.sql.SQLException;

public interface UserDao {
    StaffUser getUser(String username) throws SQLException;
    void addUser(StaffUser user) throws SQLException;
}