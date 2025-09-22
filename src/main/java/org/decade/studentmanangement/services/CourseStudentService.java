package org.decade.studentmanangement.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.decade.studentmanangement.dao.CourseStudentDao;
import org.decade.studentmanangement.model.Course;
import org.decade.studentmanangement.model.Student;
import org.decade.studentmanangement.model.StudentCourse;
import org.decade.studentmanangement.model.StudentCourseId;

import java.util.List;

@ApplicationScoped
public class CourseStudentService implements CourseStudentDao {

        @Inject
        private EntityManager em;


        @Override
        @Transactional
        public int addStudentToCourse(final String student, final String course, final int year)
                throws Exception {
                try {
                        StudentCourseId id = new StudentCourseId(student, course, year);
                        StudentCourse sc = em.find(StudentCourse.class, id);
                        if (sc != null) {
                                return 0;
                        }
                        sc = new StudentCourse();
                        sc.setId(id);
                        sc.setStudent(em.find(Student.class, student));
                        sc.setCourse(em.find(Course.class, new Course.CoursePk(course, year)));
                        em.persist(sc);
                        return 1;
                } catch (Exception e) {
                        throw e;
                }
        }

        @Override
        public int updateStudentScore(final String student, final String course, final int year, final int score)
                throws Exception {
                return 0;
        }

        @Override
        @Transactional
        public int deleteStudentFromCourse(final String student, final String course, final int year)
                throws Exception {
                try {
                        StudentCourse sc = em.find(StudentCourse.class, new StudentCourseId(student, course, year));
                        if (sc != null) {
                                em.remove(sc);
                                return 1;
                        }
                        return 0;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public List<StudentCourse> getListStudentsByCourse(final String id, final int year)
                throws Exception {
                try {
                        return em.createQuery(
                                        "select sc from StudentCourse sc left join fetch sc.assessments join fetch sc.student join fetch sc.course where sc.id.courseId = :cid and sc.id.courseYear = :yr",
                                        StudentCourse.class)
                                .setParameter("cid", id)
                                .setParameter("yr", year)
                                .getResultList();
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public List<StudentCourse> getCoursesByStudentInTheYear(final String studentId, final int year) throws Exception {
                try {
                        String jpql = "select sc from StudentCourse sc left join fetch sc.assessments join fetch sc.course where sc.id.studentId = :sid" +
                                (year != -1 ? " and sc.id.courseYear = :yr" : "");
                        TypedQuery<StudentCourse> q = em.createQuery(jpql, StudentCourse.class)
                                .setParameter("sid", studentId);
                        if (year != -1) q.setParameter("yr", year);
                        return q.getResultList();
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public int countStudentsOfCourse(String courseId, int year) throws Exception {
                try {
                        Long cnt = em.createQuery("select count(sc) from StudentCourse sc where sc.id.courseId = :cid and sc.id.courseYear = :yr", Long.class)
                                .setParameter("cid", courseId)
                                .setParameter("yr", year)
                                .getSingleResult();
                        return cnt.intValue();
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }
}