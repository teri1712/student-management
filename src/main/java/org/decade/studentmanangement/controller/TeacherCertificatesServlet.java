package org.decade.studentmanangement.controller;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.decade.studentmanangement.model.FileAttachment;
import org.decade.studentmanangement.model.StaffUser;

import java.io.IOException;
import java.util.List;

@WebServlet("/teacher/certificates")
public class TeacherCertificatesServlet extends HttpServlet {

    @Resource(name = "services/EntityManagerFactory")
    private EntityManagerFactory emf;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Object u = session == null ? null : session.getAttribute("user");
        if (!(u instanceof StaffUser)) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        StaffUser user = (StaffUser) u;
        String username = user.getUserName();

        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            List<FileAttachment> list = em.createQuery(
                    "select f from FileAttachment f where f.owner.userName = :u and f.type = :t order by f.createdAt desc",
                    FileAttachment.class)
                .setParameter("u", username)
                .setParameter("t", "certificate")
                .getResultList();
            req.setAttribute("files", list);
            req.getRequestDispatcher("/WEB-INF/teacher/certificates.jsp").forward(req, resp);
        } finally {
            if (em != null) em.close();
        }
    }
}
