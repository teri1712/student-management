package org.decade.studentmanangement.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Course;
import services.DaoService;

/**
 *
 * @author MinhTri
 */
@WebServlet("/coursesession")
public class CourseSession extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	private DaoService service;

	public CourseSession() {
		super();
		service = DaoService.getInstance();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String action = request.getParameter("cc");
			if ("add course".equals(action)) {
				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String lecture = request.getParameter("lecture");
				int year = Integer.parseInt(request.getParameter("year"));
				String notes = request.getParameter("notes");
				String status = service.addCourseProcdedure(new Course(id, name, lecture, year, notes));
				response.getWriter().print(status);
			} else if (action.equals("add student")) {
				String id = request.getParameter("id");
				String studentId = request.getParameter("studentId");
				int year = Integer.parseInt(request.getParameter("year"));
				String status = service.addStudentToCourse(studentId, id, year);
				ArrayList<TreeMap<String, String>> l = service.getListStudentsByCourseProcdedure(id, year);
				response.setContentType("text/xml;charset=UTF-8");
				String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				res += "<result>";
				res += "<status>" + status + "</status>";
				for (TreeMap<String, String> i : l) {
					res += "<Student>" + "<id>" + i.get("id") + "</id>" + "<fullname>" + i.get("fullname")
							+ "</fullname>" + "<grade>" + i.get("grade") + "</grade>" + "<birthday>" + i.get("birthday")
							+ "</birthday>" + "<address>" + i.get("address") + "</address>" + "<notes>" + i.get("notes")
							+ "</notes>" + "<score>" + i.get("score") + "</score>" + "</Student>";
				}
				res += "</result>";
				response.getWriter().print(res);
			} else if ("edit course".equals(action)) {
				String id = request.getParameter("id");
				String name = request.getParameter("name");
				String lecture = request.getParameter("lecture");
				int year = Integer.parseInt(request.getParameter("year"));
				String notes = request.getParameter("notes");
				String status = service.editCourseProcdedure(new Course(id, name, lecture, year, notes));
				response.getWriter().print(status);
			} else if ("delete course".equals(action)) {
				String id = request.getParameter("id");
				int year = Integer.parseInt(request.getParameter("year"));
				String status = service.deleteCourseProcdedure(id, year);
				ArrayList<Course> l = service.getListCourseProcdedure();
				response.setContentType("text/xml;charset=UTF-8");
				String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				res += "<result>";
				res += "<status>" + status + "</status>";
				for (Course i : l) {
					res += "<Course>" + "<id>" + i.getId() + "</id>" + "<name>" + i.getName() + "</name>" + "<year>"
							+ Integer.toString(i.getYear()) + "</year>" + "<lecture>" + i.getLecture() + "</lecture>"
							+ "<notes>" + i.getNote() + "</notes>" + "</Course>";
				}
				res += "</result>";
				response.getWriter().print(res);
			} else if ("delete student".equals(action)) {
				String sid = request.getParameter("studentId");
				String id = request.getParameter("id");
				int year = Integer.parseInt(request.getParameter("year"));
				String status = service.deleteStudentFromCourse(sid, id, year);
				ArrayList<TreeMap<String, String>> l = service.getListStudentsByCourseProcdedure(id, year);
				response.setContentType("text/xml;charset=UTF-8");
				String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				res += "<result>";
				res += "<status>" + status + "</status>";
				for (TreeMap<String, String> i : l) {
					res += "<Student>" + "<id>" + i.get("id") + "</id>" + "<fullname>" + i.get("fullname")
							+ "</fullname>" + "<grade>" + i.get("grade") + "</grade>" + "<birthday>" + i.get("birthday")
							+ "</birthday>" + "<address>" + i.get("address") + "</address>" + "<notes>" + i.get("notes")
							+ "</notes>" + "<score>" + i.get("score") + "</score>" + "</Student>";
				}
				res += "</result>";
				response.getWriter().print(res);
			} else if ("find by name".equals(action)) {
				String name = request.getParameter("name");
				ArrayList<Course> l = service.getListCourseByNameProcdedure(name);
				response.setContentType("text/xml");
				String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				res += "<result>";
				for (Course i : l) {
					res += "<Course>" + "<id>" + i.getId() + "</id>" + "<name>" + i.getName() + "</name>" + "<year>"
							+ Integer.toString(i.getYear()) + "</year>" + "<lecture>" + i.getLecture() + "</lecture>"
							+ "<notes>" + i.getNote() + "</notes>" + "</Course>";
				}
				res += "</result>";
				response.getWriter().print(res);
			} else if ("show student list".equals(action)) {
				ArrayList<TreeMap<String, String>> l = service.getListStudentsByCourseProcdedure(
						request.getParameter("id"), Integer.parseInt(request.getParameter("year")));
				response.setContentType("text/xml;charset=UTF-8");
				String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				res += "<result>";
				for (TreeMap<String, String> i : l) {
					res += "<Student>" + "<id>" + i.get("id") + "</id>" + "<fullname>" + i.get("fullname")
							+ "</fullname>" + "<grade>" + i.get("grade") + "</grade>" + "<birthday>" + i.get("birthday")
							+ "</birthday>" + "<address>" + i.get("address") + "</address>" + "<notes>" + i.get("notes")
							+ "</notes>" + "<score>" + i.get("score") + "</score>" + "</Student>";
				}
				res += "</result>";
				response.getWriter().print(res);
				return;
			} else if (action.equals("show course session")) {
				ArrayList<Course> l = service.getListCourseProcdedure();
				request.setAttribute("course list", l);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("coursesession.jsp");
				requestDispatcher.forward(request, response);
			} else if (action.equals("show edit course session")) {
				Course c = service.getCourse(request.getParameter("id"),
						Integer.parseInt(request.getParameter("year")));
				request.setAttribute("course", c);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("editcourse.jsp");
				requestDispatcher.forward(request, response);
			} else if (action.equals("sort by name")) {
				ArrayList<Course> l = service.getListCourseProcdedure();
				l.sort(new Comparator<Course>() {
					@Override
					public int compare(Course o1, Course o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
				response.setContentType("text/xml;charset=UTF-8");
				String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				res += "<result>";
				for (Course i : l) {
					res += "<Course>" + "<id>" + i.getId() + "</id>" + "<name>" + i.getName() + "</name>" + "<year>"
							+ Integer.toString(i.getYear()) + "</year>" + "<lecture>" + i.getLecture() + "</lecture>"
							+ "<notes>" + i.getNote() + "</notes>" + "</Course>";
				}
				res += "</result>";
				response.getWriter().print(res);
			} else if (action.equals("update student score")) {
				String idCourse = request.getParameter("id");
				String year = request.getParameter("year");
				String idStudent = request.getParameter("studentId");
				String score = request.getParameter("score");

				String status = service.updateStudentScore(idStudent, idCourse, Integer.parseInt(year),
						Integer.parseInt(score));

				response.getWriter().print(status);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
	// + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
