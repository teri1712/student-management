package org.decade.studentmanangement.dao;

import org.decade.studentmanangement.model.Notification;

import java.util.List;

public interface NotificationDao {
        void addNotification(String courseId, int courseYear, String teacherUsername, String content) throws Exception;

        List<Notification> listByCourse(String courseId, int courseYear, Long sinceId, int limit) throws Exception;
}
