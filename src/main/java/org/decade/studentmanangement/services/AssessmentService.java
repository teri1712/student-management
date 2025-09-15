package org.decade.studentmanangement.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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

@ApplicationScoped
public class AssessmentService implements AssessmentDao {

        @Inject
        private EntityManager em;

        @Override
        @Transactional
        public void addAssessment(String studentId, String courseId, int courseYear, Integer semester, Integer assessYear, int score) throws Exception {
                try {
                        StudentCourse sc = em.find(StudentCourse.class, new StudentCourseId(studentId, courseId, courseYear));
                        if (sc == null) {
                                throw new SQLException("Enrollment (StudentCourse) not found");
                        }
                        Assessment a = new Assessment(sc, semester, assessYear, score);
                        sc.getAssessments().add(a);
                        em.persist(a);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        @Transactional
        public void importCsv(String courseId, int courseYear, InputStream csvStream) throws Exception {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8))) {
                        String line;
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
                                if (sc == null) continue;
                                Assessment a = new Assessment(sc, semester, assessYear, score);
                                sc.getAssessments().add(a);
                                em.persist(a);
                        }
                } catch (IOException e) {
                        throw new SQLException(e);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
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
