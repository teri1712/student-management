package org.decade.studentmanangement.controller;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.decade.studentmanangement.dao.CourseDao;
import org.decade.studentmanangement.dao.CourseStudentDao;
import org.decade.studentmanangement.model.Course;
import org.decade.studentmanangement.model.StudentCourse;

import java.io.IOException;
import java.util.List;


@WebServlet("/management/course-student/*")
public class CourseStudentServlet extends HttpServlet {

        @Inject
        private CourseDao courseDao;

        @Inject
        private CourseStudentDao courseStudentDao;

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                String op = request.getParameter("op");
                String courseId = request.getParameter("courseId");
                String studentId = request.getParameter("studentId");

                try {
                        int year = Integer.parseInt(request.getParameter("year"));
                        Course course = courseDao.getCourse(courseId, year);
                        if ("add".equals(op)) {
                                if (course == null) {
                                        response.sendError(404, "Course Not Found");
                                        return;
                                }
                                courseStudentDao.addStudentToCourse(studentId, courseId, year);
                        }

                        request.setAttribute("course", course);

                        List<StudentCourse> students = courseStudentDao.getListStudentsByCourse(courseId, year);
                        request.setAttribute("students", students);
                        request.getRequestDispatcher("/WEB-INF/management/editcourse.jsp").forward(request, response);
                } catch (Exception e) {
                        e.printStackTrace();
                        response.sendError(400, "Bad request");
                }
        }

        @Override
        protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                String courseId = request.getParameter("courseId");
                String studentId = request.getParameter("studentId");
                int year = Integer.parseInt(request.getParameter("year"));

                try {
                        Course course = courseDao.getCourse(courseId, year);
                        if (course == null) {
                                response.sendError(404, "Course Not Found");
                                return;
                        }
                        courseStudentDao.deleteStudentFromCourse(studentId, courseId, year);
                        response.setContentType("text/plain;charset=UTF-8");
                        response.getWriter().write("Delete success");
                } catch (Exception e) {
                        response.sendError(400, "Bad request");
                }
        }
}
