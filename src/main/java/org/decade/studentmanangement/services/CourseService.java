package org.decade.studentmanangement.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.decade.studentmanangement.dao.CourseDao;
import org.decade.studentmanangement.model.Course;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class CourseService implements CourseDao {

        @Inject
        private EntityManager em;

        private String validateSortBy(String sortBy) {
                return (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;
        }

        @Override
        @Transactional
        public Course getCourse(String id, int year) throws Exception {
                try {
                        return em.find(Course.class, new Course.CoursePk(id, year));
                } catch (Exception e) {
                        throw new SQLException(e);
                }
        }

        @Override
        @Transactional
        public void addCourse(final Course course) throws Exception {
                try {
                        em.persist(course);
                } catch (Exception e) {
                        throw new SQLException(e);
                }
        }

        @Override
        @Transactional
        public int deleteCourse(final String id, final int year) throws Exception {
                try {

                        Course c = em.find(Course.class, new Course.CoursePk(id, year));
                        if (c != null) {
                                em.remove(c);
                                return 1;
                        } else {
                                return 0;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public List<Course> findCourses(String sortBy, int page, int limit) throws Exception {
                String sort = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy;
                try {
                        String jpql = "select c from Course c order by c." + sort;
                        return em.createQuery(jpql, Course.class)
                                .setMaxResults(limit)
                                .setFirstResult(page * limit)
                                .getResultList();
                } catch (Exception e) {
                        throw new SQLException(e);
                }
        }

        @Override
        @Transactional
        public int updateCourse(final Course course) throws Exception {
                try {
                        Course managed = em.find(Course.class, new Course.CoursePk(course.getId(), course.getYear()));
                        if (managed == null) {
                                return 0;
                        }
                        managed.setName(course.getName());
                        managed.setLecture(course.getLecture());
                        managed.setNote(course.getNote());
                        return 1;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public List<Course> findCoursesByName(final String name, String sortBy, int page, int limit) throws Exception {
                String sort = validateSortBy(sortBy);
                try {
                        String jpql = "select c from Course c where c.name like :name order by c." + sort;
                        return em.createQuery(jpql, Course.class)
                                .setParameter("name", "%" + name + "%")
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
        public int countCourseByName(String name) throws Exception {
                try {
                        Long cnt = em.createQuery("select count(c) from Course c where c.name like :name", Long.class)
                                .setParameter("name", "%" + name + "%")
                                .getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public int countCourses() throws Exception {
                try {
                        Long cnt = em.createQuery("select count(c) from Course c", Long.class).getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public List<Course> findCoursesByLecturer(String lecturerUsername, String sortBy, int page, int limit) throws Exception {
                String sort = validateSortBy(sortBy);
                try {
                        String jpql = "select c from Course c where c.lecture = :lecturer order by c." + sort;
                        return em.createQuery(jpql, Course.class)
                                .setParameter("lecturer", lecturerUsername)
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
        public int countCoursesByLecturer(String lecturerUsername) throws Exception {
                try {
                        Long cnt = em.createQuery("select count(c) from Course c where c.lecture = :lecturer", Long.class)
                                .setParameter("lecturer", lecturerUsername)
                                .getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public List<Course> findCoursesByLecturerAndYear(String lecturerUsername, int year, String sortBy, int page, int limit) throws Exception {
                String sort = validateSortBy(sortBy);
                try {
                        String jpql = "select c from Course c where c.lecture = :lecturer and c.year = :yr order by c." + sort;
                        return em.createQuery(jpql, Course.class)
                                .setParameter("lecturer", lecturerUsername)
                                .setParameter("yr", year)
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
        public int countCoursesByLecturerAndYear(String lecturerUsername, int year) throws Exception {
                try {
                        Long cnt = em.createQuery("select count(c) from Course c where c.lecture = :lecturer and c.year = :yr", Long.class)
                                .setParameter("lecturer", lecturerUsername)
                                .setParameter("yr", year)
                                .getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }
}
