package org.decade.studentmanangement.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.decade.studentmanangement.dao.AssessmentDao;
import org.decade.studentmanangement.model.Assessment;
import org.decade.studentmanangement.model.StudentCourse;
import org.decade.studentmanangement.model.StudentCourseId;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class AssessmentService implements AssessmentDao {

        private final EntityManagerFactory emf;

        public AssessmentService(EntityManagerFactory emf) {
                this.emf = emf;
        }

        // removed wrap(Exception) mapping to SQLException per requirement

        @Override
        public void addAssessment(String studentId, String courseId, int courseYear, Integer semester, Integer assessYear, int score) throws SQLException {
                EntityManager em = null;
                EntityTransaction tx = null;
                try {
                        em = emf.createEntityManager();
                        tx = em.getTransaction();
                        tx.begin();
                        StudentCourse sc = em.find(StudentCourse.class, new StudentCourseId(studentId, courseId, courseYear));
                        if (sc == null) {
                                tx.rollback();
                                throw new SQLException("Enrollment (StudentCourse) not found");
                        }
                        Assessment a = new Assessment(sc, semester, assessYear, score);
                        em.persist(a);


                        tx.commit();
                } catch (Exception e) {
                        if (tx != null && tx.isActive()) tx.rollback();
                        e.printStackTrace();
                } finally {
                        if (em != null) em.close();
                }
        }

        @Override
        public void importCsv(String courseId, int courseYear, InputStream csvStream) throws SQLException {
                EntityManager em = null;
                EntityTransaction tx = null;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8))) {
                        em = emf.createEntityManager();
                        tx = em.getTransaction();
                        tx.begin();
                        String line;
                        // Expect CSV with header: studentId,semester,assessYear,score
                        boolean first = true;
                        while ((line = reader.readLine()) != null) {
                                if (first && line.toLowerCase().contains("student")) {
                                        first = false;
                                        continue;
                                }
                                first = false;
                                String[] parts = line.split(",");
                                if (parts.length < 4) continue;
                                String studentId = parts[0].trim();
                                Integer semester = parseIntOrNull(parts[1]);
                                Integer assessYear = parseIntOrNull(parts[2]);
                                Integer score = parseIntOrNull(parts[3]);
                                if (studentId.isEmpty() || score == null) continue;

                                StudentCourseId scid = new StudentCourseId(studentId, courseId, courseYear);
                                StudentCourse sc = em.find(StudentCourse.class, scid);
                                if (sc == null) {
                                        // skip if enrollment not found
                                        continue;
                                }
                                Assessment a = new Assessment(sc, semester, assessYear, score);
                                em.persist(a);
                        }
                        tx.commit();
                } catch (IOException e) {
                        if (tx != null && tx.isActive()) tx.rollback();
                        throw new SQLException(e);
                } catch (Exception e) {
                        if (tx != null && tx.isActive()) tx.rollback();
                        throw new SQLException(e);
                } finally {
                        if (em != null) em.close();
                }
        }

        private Integer parseIntOrNull(String s) {
                try {
                        return Integer.parseInt(s.trim());
                } catch (Exception e) {
                        return null;
                }
        }
}
