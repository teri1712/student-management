package org.decade.studentmanangement.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.decade.studentmanangement.dao.StudentDao;
import org.decade.studentmanangement.model.Student;

import java.util.List;

@ApplicationScoped
public class StudentService implements StudentDao {

        @Inject
        private EntityManager em;


        @Override
        @Transactional
        public Student getStudent(String id) throws Exception {
                try {
                        return em.find(Student.class, id);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public void addStudent(final Student student) throws Exception {
                try {
                        em.persist(student);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public void updateStudent(final Student student) throws Exception {
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
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public List<Student> findStudentsByName(String name, String sortBy, int page, int limit) throws Exception {
                String orderProp = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;
                try {
                        String jpql = "select s from Student s where s.fullname like :name order by s." + orderProp;
                        return em.createQuery(jpql, Student.class)
                                .setParameter("name", "%" + (name == null ? "" : name) + "%")
                                .setMaxResults(limit)
                                .setFirstResult(page * limit)
                                .getResultList();
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public int countStudentsByName(String name) throws Exception {
                try {
                        Long cnt = em.createQuery("select count(s) from Student s where s.fullname like :name", Long.class)
                                .setParameter("name", "%" + (name == null ? "" : name) + "%")
                                .getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public int countStudents() throws Exception {
                try {
                        Long cnt = em.createQuery("select count(s) from Student s", Long.class).getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public List<Student> findStudents(int page, String sortBy, int limit) throws Exception {
                String orderProp = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;
                try {
                        String jpql = "select s from Student s order by s." + orderProp;
                        return em.createQuery(jpql, Student.class)
                                .setMaxResults(limit)
                                .setFirstResult(page * limit)
                                .getResultList();
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public void deleteStudent(final String id) throws Exception {
                try {
                        em.createNativeQuery("delete from QuanLySinhVien.Student_Course where idStudent = ?")
                                .setParameter(1, id)
                                .executeUpdate();
                        Student s = em.find(Student.class, id);
                        if (s != null) em.remove(s);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }
}
