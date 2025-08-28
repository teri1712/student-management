package org.decade.studentmanangement.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.decade.studentmanangement.dao.CourseDao;
import org.decade.studentmanangement.model.Course;

import java.sql.SQLException;
import java.util.List;

public class CourseService implements CourseDao {
        private final EntityManagerFactory emf;

        public CourseService(EntityManagerFactory emf) {
                this.emf = emf;
        }

        // Helpers for sorting and exception wrapping
        private String validateSortBy(String sortBy) {
                return (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;
        }
        private SQLException wrap(Exception e) { return (e instanceof SQLException) ? (SQLException) e : new SQLException(e); }

        @Override
        public Course getCourse(String id, int year) throws SQLException {
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        return em.find(Course.class, new Course.CoursePk(id, year));
                } catch (Exception e) {
                        throw new SQLException(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        @Override
        public void addCourse(final Course course) throws SQLException {
                EntityManager em = null;
                EntityTransaction tx = null;
                try {
                        em = emf.createEntityManager();
                        tx = em.getTransaction();
                        tx.begin();
                        em.persist(course);
                        tx.commit();
                } catch (Exception e) {
                        if (tx != null && tx.isActive()) tx.rollback();
                        throw new SQLException(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        @Override
        public int deleteCourse(final String id, final int year) throws SQLException {
                EntityManager em = null;
                EntityTransaction tx = null;
                try {
                        em = emf.createEntityManager();
                        tx = em.getTransaction();
                        tx.begin();

                        // delete dependent rows from Student_Course first
                        Query q = em.createNativeQuery("delete from QuanLySinhVien.Student_Course where idCourse = ? and courseYear = ?");
                        q.setParameter(1, id);
                        q.setParameter(2, year);
                        q.executeUpdate();

                        Course c = em.find(Course.class, new Course.CoursePk(id, year));
                        if (c != null) {
                                em.remove(c);
                                tx.commit();
                                return 1;
                        } else {
                                tx.commit();
                                return 0;
                        }
                } catch (Exception e) {
                        if (tx != null && tx.isActive()) tx.rollback();
                        throw wrap(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        @Override
        public List<Course> findCourses(String sortBy, int page, int limit) throws SQLException {
                String sort = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        String jpql = "select c from Course c order by c." + sort;
                        return em.createQuery(jpql, Course.class)
                                .setMaxResults(limit)
                                .setFirstResult(page * limit)
                                .getResultList();
                } catch (Exception e) {
                        throw new SQLException(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        @Override
        public int updateCourse(final Course course) throws SQLException {
                EntityManager em = null;
                EntityTransaction tx = null;
                try {
                        em = emf.createEntityManager();
                        tx = em.getTransaction();
                        tx.begin();
                        Course managed = em.find(Course.class, new Course.CoursePk(course.getId(), course.getYear()));
                        if (managed == null) {
                                tx.commit();
                                return 0;
                        }
                        managed.setName(course.getName());
                        managed.setLecture(course.getLecture());
                        managed.setNote(course.getNote());
                        // Do not update PK fields (id/year) here to align with previous JDBC behavior
                        tx.commit();
                        return 1;
                } catch (Exception e) {
                        if (tx != null && tx.isActive()) tx.rollback();
                        throw wrap(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        @Override
        public List<Course> findCoursesByName(final String name, String sortBy, int page, int limit) throws SQLException {
                String sort = validateSortBy(sortBy);
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        String jpql = "select c from Course c where c.name like :name order by c." + sort;
                        return em.createQuery(jpql, Course.class)
                                .setParameter("name", "%" + name + "%")
                                .setMaxResults(limit)
                                .setFirstResult(page * limit)
                                .getResultList();
                } catch (Exception e) {
                        throw wrap(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        @Override
        public int countCourseByName(String name) throws SQLException {
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        Long cnt = em.createQuery("select count(c) from Course c where c.name like :name", Long.class)
                                .setParameter("name", "%" + name + "%")
                                .getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        throw wrap(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        @Override
        public int countCourses() throws SQLException {
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        Long cnt = em.createQuery("select count(c) from Course c", Long.class).getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        throw wrap(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        @Override
        public List<Course> findCoursesByLecturer(String lecturerUsername, String sortBy, int page, int limit) throws SQLException {
                String sort = validateSortBy(sortBy);
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        String jpql = "select c from Course c where c.lecture = :lecturer order by c." + sort;
                        return em.createQuery(jpql, Course.class)
                                .setParameter("lecturer", lecturerUsername)
                                .setMaxResults(limit)
                                .setFirstResult(page * limit)
                                .getResultList();
                } catch (Exception e) {
                        throw wrap(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        @Override
        public int countCoursesByLecturer(String lecturerUsername) throws SQLException {
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        Long cnt = em.createQuery("select count(c) from Course c where c.lecture = :lecturer", Long.class)
                                .setParameter("lecturer", lecturerUsername)
                                .getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        throw wrap(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        @Override
        public List<Course> findCoursesByLecturerAndYear(String lecturerUsername, int year, String sortBy, int page, int limit) throws SQLException {
                String sort = validateSortBy(sortBy);
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        String jpql = "select c from Course c where c.lecture = :lecturer and c.year = :yr order by c." + sort;
                        return em.createQuery(jpql, Course.class)
                                .setParameter("lecturer", lecturerUsername)
                                .setParameter("yr", year)
                                .setMaxResults(limit)
                                .setFirstResult(page * limit)
                                .getResultList();
                } catch (Exception e) {
                        throw wrap(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        @Override
        public int countCoursesByLecturerAndYear(String lecturerUsername, int year) throws SQLException {
                EntityManager em = null;
                try {
                        em = emf.createEntityManager();
                        Long cnt = em.createQuery("select count(c) from Course c where c.lecture = :lecturer and c.year = :yr", Long.class)
                                .setParameter("lecturer", lecturerUsername)
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
