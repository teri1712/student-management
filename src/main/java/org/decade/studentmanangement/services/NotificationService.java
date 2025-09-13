package org.decade.studentmanangement.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.decade.studentmanangement.dao.NotificationDao;
import org.decade.studentmanangement.model.Notification;

import java.util.List;

@ApplicationScoped
public class NotificationService implements NotificationDao {

    @Inject
    private EntityManager em;

    @Override
    @Transactional
    public void addNotification(String courseId, int courseYear, String teacherUsername, String content) throws Exception {
        Notification n = new Notification(courseId, courseYear, teacherUsername, content);
        em.persist(n);
    }

    @Override
    @Transactional
    public List<Notification> listByCourse(String courseId, int courseYear, Long sinceId, int limit) throws Exception {
        String jpql = "select n from Notification n where n.courseId = :cid and n.courseYear = :yr " +
                (sinceId != null ? "and n.id > :sid " : "") +
                "order by n.id desc";
        var q = em.createQuery(jpql, Notification.class)
                .setParameter("cid", courseId)
                .setParameter("yr", courseYear);
        if (sinceId != null) q.setParameter("sid", sinceId);
        if (limit > 0) q.setMaxResults(limit);
        return q.getResultList();
    }
}
