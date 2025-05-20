package org.decade.studentmanangement.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.decade.studentmanangement.model.Student;
import org.decade.studentmanangement.services.DaoService;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;


/**
 *
 * @author MinhTri
 */
@WebServlet("/studentsession")
public class StudentSession extends HttpServlet {

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

	public StudentSession() {
		super();
		service = DaoService.getInstance();
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			String action = request.getParameter("cc");
			if (action.equals("add student")) {
				String id = request.getParameter("id");
				String name = request.getParameter("fullname");
				Date birthday = Date.valueOf(request.getParameter("birthday"));
				int grade = Integer.parseInt(request.getParameter("grade"));
				String notes = request.getParameter("notes");
				String address = request.getParameter("address");
				String status = service.addStudentProcdedure(new Student(id, name, birthday, grade, address, notes));
				response.getWriter().print(status);
			} else if (action.equals("edit student")) {
				String id = request.getParameter("id");
				String name = request.getParameter("fullname");
				Date birthday = Date.valueOf(request.getParameter("birthday"));
				int grade = Integer.parseInt(request.getParameter("grade"));
				String notes = request.getParameter("notes");
				String address = request.getParameter("address");
				String status = service.editStudentProcdedure(new Student(id, name, birthday, grade, address, notes));
				response.getWriter().print(status);
			} else if ("delete student".equals(action)) {
				String id = request.getParameter("id");
				String status = service.deleteStudentProcdedure(id);
				ArrayList<Student> l = service.getListStudentsProcdedure();
				response.setContentType("text/xml");
				String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				res += "<result>";
				res += "<status>" + status + "</status>";
				for (Student i : l) {
					res += "<Student>" + "<id>" + i.getId() + "</id>" + "<name>" + i.getName() + "</name>" + "<grade>"
							+ Integer.toString(i.getGrade()) + "</grade>" + "<birthday>" + i.getBirdthDay().toString()
							+ "</birthday>" + "<address>" + i.getAddress() + "</address>" + "<notes>" + i.getNotes()
							+ "</notes>" + "</Student>";
				}
				res += "</result>";
				response.getWriter().print(res);
			} else if (action.equals("find by name")) {
				String name = request.getParameter("name");
				ArrayList<Student> l = service.getListStudentsByNameProcdedure(name);
				response.setContentType("text/xml");
				String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				res += "<result>";
				for (Student i : l) {
					res += "<Student>" + "<id>" + i.getId() + "</id>" + "<name>" + i.getName() + "</name>" + "<grade>"
							+ Integer.toString(i.getGrade()) + "</grade>" + "<birthday>" + i.getBirdthDay().toString()
							+ "</birthday>" + "<address>" + i.getAddress() + "</address>" + "<notes>" + i.getNotes()
							+ "</notes>" + "</Student>";
				}
				res += "</result>";
				response.getWriter().print(res);
			} else if (action.equals("show course list")) {
				String id = request.getParameter("id");
				int year = Integer.parseInt(request.getParameter("year"));
				ArrayList<TreeMap<String, String>> l = service.getListCourseByStudentInTheYearProcdedure(id, year);
				response.setContentType("text/xml;charset=UTF-8");
				String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				res += "<result>";
				for (TreeMap<String, String> i : l) {
					res += "<Course>" + "<id>" + i.get("id") + "</id>" + "<name>" + i.get("name") + "</name>" + "<year>"
							+ i.get("year") + "</year>" + "<lecture>" + i.get("lecture") + "</lecture>" + "<notes>"
							+ i.get("notes") + "</notes>" + "<score>" + i.get("score") + "</score>" + "</Course>";
				}
				res += "</result>";
				response.getWriter().print(res);
			} else if (action.equals("show student session")) {
				ArrayList<Student> l = service.getListStudentsProcdedure();
				request.setAttribute("student list", l);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("studentsession.jsp");
				requestDispatcher.forward(request, response);
			} else if (action.equals("show edit student session")) {
				Student s = service.getStudent(request.getParameter("id"));
				request.setAttribute("student", s);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("editstudent.jsp");
				requestDispatcher.forward(request, response);
			} else if (action.equals("get score board")) {

			} else if (action.equals("sort by name")) {
				ArrayList<Student> l = service.getListStudentsProcdedure();
				l.sort(new Comparator<Student>() {
					@Override
					public int compare(Student o1, Student o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
				response.setContentType("text/xml");
				String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				res += "<result>";
				for (Student i : l) {
					res += "<Student>" + "<id>" + i.getId() + "</id>" + "<name>" + i.getName() + "</name>" + "<grade>"
							+ Integer.toString(i.getGrade()) + "</grade>" + "<birthday>" + i.getBirdthDay().toString()
							+ "</birthday>" + "<address>" + i.getAddress() + "</address>" + "<notes>" + i.getNotes()
							+ "</notes>" + "</Student>";
				}
				res += "</result>";
				response.getWriter().print(res);
			} else if (action.equals("sort by grade")) {
				ArrayList<Student> l = service.getListStudentsProcdedure();
				l.sort(new Comparator<Student>() {
					@Override
					public int compare(Student o1, Student o2) {
						return Integer.compare(o1.getGrade(), o2.getGrade());
					}
				});
				response.setContentType("text/xml");
				String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				res += "<result>";
				for (Student i : l) {
					res += "<Student>" + "<id>" + i.getId() + "</id>" + "<name>" + i.getName() + "</name>" + "<grade>"
							+ Integer.toString(i.getGrade()) + "</grade>" + "<birthday>" + i.getBirdthDay().toString()
							+ "</birthday>" + "<address>" + i.getAddress() + "</address>" + "<notes>" + i.getNotes()
							+ "</notes>" + "</Student>";
				}
				res += "</result>";
				response.getWriter().print(res);
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
