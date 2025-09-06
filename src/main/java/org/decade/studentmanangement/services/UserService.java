package org.decade.studentmanangement.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.decade.studentmanangement.dao.UserDao;
import org.decade.studentmanangement.model.FileAttachment;
import org.decade.studentmanangement.model.StaffUser;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class UserService implements UserDao {

        @Inject
        private EntityManager em;

        @Override
        @Transactional
        public StaffUser getUser(String username) throws Exception {
                try {
                        return em.find(StaffUser.class, username);
                } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                }
        }

        @Override
        @Transactional
        public void addUser(StaffUser user) throws Exception {
                try {
                        em.persist(user);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public void addCertificate(String username, String relativePath) throws Exception {
                try {
                        StaffUser owner = em.find(StaffUser.class, username);
                        if (owner == null) throw new SQLException("User not found: " + username);
                        FileAttachment att = new FileAttachment(owner, "certificate", relativePath.replace('\\', '/'));
                        em.persist(att);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public String getLatestCertificatePath(String username) throws Exception {
                try {
                        List<FileAttachment> list = em.createQuery(
                                        "select f from FileAttachment f where f.owner.userName = :u and f.type = :t order by f.createdAt desc",
                                        FileAttachment.class)
                                .setParameter("u", username)
                                .setParameter("t", "certificate")
                                .setMaxResults(1)
                                .getResultList();
                        if (list == null || list.isEmpty()) return null;
                        String p = list.get(0).getPath();
                        return p;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public List<FileAttachment> listCertificates(String username) throws Exception {
                try {
                        return em.createQuery(
                                        "select f from FileAttachment f where f.owner.userName = :u and f.type = :t order by f.createdAt desc",
                                        FileAttachment.class)
                                .setParameter("u", username)
                                .setParameter("t", "certificate")
                                .getResultList();
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }
}
