package org.decade.studentmanangement.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.decade.studentmanangement.dao.CourseStudentDao;
import org.decade.studentmanangement.model.Course;
import org.decade.studentmanangement.model.Student;
import org.decade.studentmanangement.model.StudentCourse;
import org.decade.studentmanangement.model.StudentCourseId;

import java.sql.SQLException;
import java.util.List;

public class CourseStudentService implements CourseStudentDao {
        private final EntityManagerFactory emf;

        public CourseStudentService(EntityManagerFactory emf) {
                this.emf = emf;
        }

        private SQLException wrap(Exception e) {
                return (e instanceof SQLException) ? (SQLException) e : new SQLException(e);
        }

        public int addStudentToCourse(final String student, final String course, final int year)
                throws SQLException {
                EntityManager em = null;
                EntityTransaction tx = null;
                try {
                        em = emf.createEntityManager();
                        tx = em.getTransaction();
                        tx.begin();
                        StudentCourseId id = new StudentCourseId(student, course, year);
                        StudentCourse sc = em.find(StudentCourse.class, id);
                        if (sc != null) {
                                tx.commit();
                                return 0; // already exists
                        }
                        sc = new StudentCourse();
                        sc.setId(id);
                        sc.setStudent(em.find(Student.class, student));
                        sc.setCourse(em.find(Course.class, new Course.CoursePk(course, year)));
                        // score is derived from assessments; no direct score set here
                        em.persist(sc);
                        tx.commit();
                        return 1;
                } catch (Exception e) {
                        if (tx != null && tx.isActive()) tx.rollback();
                        throw wrap(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        public int updateStudentScore(final String student, final String course, final int year, final int score)
                throws SQLException {
                // Score is derived from assessments; direct update is not supported anymore.
                return 0;
        }

        public int deleteStudentFromCourse(final String student, final String course, final int year)
                throws SQLException {
                EntityManager em = null;
                EntityTransaction tx = null;
                try {
                        em = emf.createEntityManager();
                        tx = em.getTransaction();
                        tx.begin();
                        StudentCourse sc = em.find(StudentCourse.class, new StudentCourseId(student, course, year));
                        if (sc != null) {
                                em.remove(sc);
                                tx.commit();
                                return 1;
                        }
                        tx.commit();
                        return 0;
                } catch (Exception e) {
                        if (tx != null && tx.isActive()) tx.rollback();
                        throw wrap(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        public List<StudentCourse> getListStudentsByCourse(final String id, final int year)
                throws SQLException {
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        return em.createQuery(
                                        "select sc from StudentCourse sc join fetch sc.student s join fetch sc.course c where sc.id.courseId = :cid and sc.id.courseYear = :yr",
                                        StudentCourse.class)
                                .setParameter("cid", id)
                                .setParameter("yr", year)
                                .getResultList();
                } catch (Exception e) {
                        throw wrap(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        public List<StudentCourse> getCoursesByStudentInTheYear(final String studentId, final int year) throws SQLException {
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        String jpql = "select sc from StudentCourse sc join fetch sc.course c join fetch sc.student s where sc.id.studentId = :sid" +
                                (year != -1 ? " and sc.id.courseYear = :yr" : "");
                        var q = em.createQuery(jpql, StudentCourse.class)
                                .setParameter("sid", studentId);
                        if (year != -1) q.setParameter("yr", year);
                        return q.getResultList();
                } catch (Exception e) {
                        throw wrap(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        public int countStudentsOfCourse(String courseId, int year) throws SQLException {
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        Long cnt = em.createQuery("select count(sc) from StudentCourse sc where sc.id.courseId = :cid and sc.id.courseYear = :yr", Long.class)
                                .setParameter("cid", courseId)
                                .setParameter("yr", year)
                                .getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        throw wrap(e);
                } finally {
                        if (em != null) em.close();
                }
        }
}
