package org.decade.studentmanangement.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.decade.studentmanangement.dao.StudentDao;
import org.decade.studentmanangement.model.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentService implements StudentDao {
        private final EntityManagerFactory emf;

        public StudentService(EntityManagerFactory emf) {
                this.emf = emf;
        }


        public Student getStudent(String id) throws SQLException {
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        return em.find(Student.class, id);
                } catch (Exception e) {
                        throw new SQLException(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        public void addStudent(final Student student) throws SQLException {
                EntityManager em = null;
                EntityTransaction tx = null;
                try {
                        em = emf.createEntityManager();
                        tx = em.getTransaction();
                        tx.begin();
                        em.persist(student);
                        tx.commit();
                } catch (Exception e) {
                        if (tx != null && tx.isActive()) tx.rollback();
                        throw new SQLException(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        public void updateStudent(final Student student) throws SQLException {
                EntityManager em = null;
                EntityTransaction tx = null;
                try {
                        em = emf.createEntityManager();
                        tx = em.getTransaction();
                        tx.begin();
                        Student managed = em.find(Student.class, student.getId());
                        if (managed != null) {
                                managed.setFullname(student.getFullname());
                                managed.setBirthDay(student.getBirthDay());
                                managed.setGrade(student.getGrade());
                                managed.setAddress(student.getAddress());
                                managed.setNotes(student.getNotes());
                        }
                        tx.commit();
                } catch (Exception e) {
                        if (tx != null && tx.isActive()) tx.rollback();
                        throw new SQLException(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        public List<Student> findStudentsByName(String name, String sortBy, int page, int limit) throws SQLException {
                String orderProp = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        String jpql = "select s from Student s where s.fullname like :name order by s." + orderProp;
                        return em.createQuery(jpql, Student.class)
                                .setParameter("name", "%" + (name == null ? "" : name) + "%")
                                .setMaxResults(limit)
                                .setFirstResult(page * limit)
                                .getResultList();
                } catch (Exception e) {
                        throw new SQLException(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        public int countStudentsByName(String name) throws SQLException {
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        Long cnt = em.createQuery("select count(s) from Student s where s.fullname like :name", Long.class)
                                .setParameter("name", "%" + (name == null ? "" : name) + "%")
                                .getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        throw new SQLException(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        public int countStudents() throws SQLException {
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        Long cnt = em.createQuery("select count(s) from Student s", Long.class).getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        throw new SQLException(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        public List<Student> findStudents(int page, String sortBy, int limit) throws SQLException {
                String orderProp = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        String jpql = "select s from Student s order by s." + orderProp;
                        return em.createQuery(jpql, Student.class)
                                .setMaxResults(limit)
                                .setFirstResult(page * limit)
                                .getResultList();
                } catch (Exception e) {
                        throw new SQLException(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        public void deleteStudent(final String id) throws SQLException {
                EntityManager em = null;
                EntityTransaction tx = null;
                try {
                        em = emf.createEntityManager();
                        tx = em.getTransaction();
                        tx.begin();
                        // delete from join table first
                        em.createNativeQuery("delete from QuanLySinhVien.Student_Course where idStudent = ?")
                                .setParameter(1, id)
                                .executeUpdate();
                        Student s = em.find(Student.class, id);
                        if (s != null) em.remove(s);
                        tx.commit();
                } catch (Exception e) {
                        if (tx != null && tx.isActive()) tx.rollback();
                        throw new SQLException(e);
                } finally {
                        if (em != null) em.close();
                }
        }
}
