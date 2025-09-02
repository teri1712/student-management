package org.decade.studentmanangement.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.decade.studentmanangement.dao.StudentDao;
import org.decade.studentmanangement.model.Student;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class StudentService implements StudentDao {

        @Inject
        private EntityManager em;

        private SQLException wrap(Exception e) {
                return (e instanceof SQLException) ? (SQLException) e : new SQLException(e);
        }

        @Override
        @Transactional(Transactional.TxType.SUPPORTS)
        public Student getStudent(String id) throws SQLException {
                try {
                        return em.find(Student.class, id);
                } catch (Exception e) {
                        throw wrap(e);
                }
        }

        @Override
        @Transactional
        public void addStudent(final Student student) throws SQLException {
                try {
                        em.persist(student);
                } catch (Exception e) {
                        throw wrap(e);
                }
        }

        @Override
        @Transactional
        public void updateStudent(final Student student) throws SQLException {
                try {
                        Student managed = em.find(Student.class, student.getId());
                        if (managed != null) {
                                managed.setFullname(student.getFullname());
                                managed.setBirthDay(student.getBirthDay());
                                managed.setGrade(student.getGrade());
                                managed.setAddress(student.getAddress());
                                managed.setNotes(student.getNotes());
                        }
                } catch (Exception e) {
                        throw wrap(e);
                }
        }

        @Override
        @Transactional(Transactional.TxType.SUPPORTS)
        public List<Student> findStudentsByName(String name, String sortBy, int page, int limit) throws SQLException {
                String orderProp = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;
                try {
                        String jpql = "select s from Student s where s.fullname like :name order by s." + orderProp;
                        return em.createQuery(jpql, Student.class)
                                .setParameter("name", "%" + (name == null ? "" : name) + "%")
                                .setMaxResults(limit)
                                .setFirstResult(page * limit)
                                .getResultList();
                } catch (Exception e) {
                        throw wrap(e);
                }
        }

        @Override
        @Transactional(Transactional.TxType.SUPPORTS)
        public int countStudentsByName(String name) throws SQLException {
                try {
                        Long cnt = em.createQuery("select count(s) from Student s where s.fullname like :name", Long.class)
                                .setParameter("name", "%" + (name == null ? "" : name) + "%")
                                .getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        throw wrap(e);
                }
        }

        @Override
        @Transactional(Transactional.TxType.SUPPORTS)
        public int countStudents() throws SQLException {
                try {
                        Long cnt = em.createQuery("select count(s) from Student s", Long.class).getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        throw wrap(e);
                }
        }

        @Override
        @Transactional(Transactional.TxType.SUPPORTS)
        public List<Student> findStudents(int page, String sortBy, int limit) throws SQLException {
                String orderProp = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;
                try {
                        String jpql = "select s from Student s order by s." + orderProp;
                        return em.createQuery(jpql, Student.class)
                                .setMaxResults(limit)
                                .setFirstResult(page * limit)
                                .getResultList();
                } catch (Exception e) {
                        throw wrap(e);
                }
        }

        @Override
        @Transactional
        public void deleteStudent(final String id) throws SQLException {
                try {
                        // delete from join table first
                        em.createNativeQuery("delete from QuanLySinhVien.Student_Course where idStudent = ?")
                                .setParameter(1, id)
                                .executeUpdate();
                        Student s = em.find(Student.class, id);
                        if (s != null) em.remove(s);
                } catch (Exception e) {
                        throw wrap(e);
                }
        }
}
