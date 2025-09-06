package org.decade.studentmanangement.dao;

import java.io.InputStream;

public interface AssessmentDao {
        void addAssessment(String studentId, String courseId, int courseYear, Integer semester, Integer assessYear, int score) throws Exception;

        void importCsv(String courseId, int courseYear, InputStream csvStream) throws Exception;
}
