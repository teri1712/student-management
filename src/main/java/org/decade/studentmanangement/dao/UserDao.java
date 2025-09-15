package org.decade.studentmanangement.dao;

import org.decade.studentmanangement.model.FileAttachment;
import org.decade.studentmanangement.model.StaffUser;

import java.util.List;

public interface UserDao {
        StaffUser getUser(String username) throws Exception;

        void addUser(StaffUser user) throws Exception;

        void addCertificate(String username, String relativePath) throws Exception;

        String getLatestCertificatePath(String username) throws Exception;

        List<FileAttachment> listCertificates(String username) throws Exception;
}