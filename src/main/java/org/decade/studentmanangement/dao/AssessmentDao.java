package org.decade.studentmanangement.dao;

import java.io.InputStream;
import java.sql.SQLException;

public interface AssessmentDao {
        void addAssessment(String studentId, String courseId, int courseYear, Integer semester, Integer assessYear, int score) throws SQLException;

        void importCsv(String courseId, int courseYear, InputStream csvStream) throws SQLException;
}
