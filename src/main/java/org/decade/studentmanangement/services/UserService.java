package org.decade.studentmanangement.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.decade.studentmanangement.dao.UserDao;
import org.decade.studentmanangement.model.StaffUser;

import java.sql.SQLException;

public class UserService implements UserDao {

        private final EntityManagerFactory emf;

        public UserService(EntityManagerFactory emf) {
                this.emf = emf;
        }

        private SQLException wrap(Exception e) {
                return (e instanceof SQLException) ? (SQLException) e : new SQLException(e);
        }

        public StaffUser getUser(String username) throws SQLException {
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        return em.find(StaffUser.class, username);
                } catch (Exception e) {
                        return null;
                } finally {
                        if (em != null) em.close();
                }
        }

        public void addUser(StaffUser user) throws SQLException {
                EntityManager em = null;
                EntityTransaction tx = null;
                try {
                        em = emf.createEntityManager();
                        tx = em.getTransaction();
                        tx.begin();
                        em.persist(user);
                        tx.commit();
                } catch (Exception e) {
                        if (tx != null && tx.isActive()) tx.rollback();
                        throw wrap(e);
                } finally {
                        if (em != null) em.close();
                }
        }
}
