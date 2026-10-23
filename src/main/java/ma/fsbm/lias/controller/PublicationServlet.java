package ma.fsbm.lias.controller;

import ma.fsbm.lias.dao.PublicationDAO;
import ma.fsbm.lias.model.Publication;
import ma.fsbm.lias.model.Utilisateur;
import ma.fsbm.lias.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/publications")
public class PublicationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PublicationDAO dao = new PublicationDAO(conn);

            String action = request.getParameter("action");
            if ("delete".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                dao.deletePublication(id);
                response.sendRedirect(request.getContextPath() + "/admin/publications");
                return;
            }

            request.setAttribute("publications", dao.getAllPublications());
            request.getRequestDispatcher("/WEB-INF/views/publications.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération des publications", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            PublicationDAO dao = new PublicationDAO(conn);

            String titre = request.getParameter("titre");
            String type = request.getParameter("type");
            String anneeStr = request.getParameter("annee");
            int annee = (anneeStr != null && !anneeStr.isEmpty()) ? Integer.parseInt(anneeStr) : 2026;
            String journalOrConf = request.getParameter("journalOrConf");
            String doi = request.getParameter("doi");
            String lienPdf = request.getParameter("lienPdf");
            String bibtex = request.getParameter("bibtex");

            Publication pub = new Publication();
            pub.setTitre(titre);
            pub.setType(type);
            pub.setAnnee(annee);
            pub.setJournalOuConference(journalOrConf);
            pub.setDoi(doi);
            pub.setLienPdf(lienPdf);
            pub.setBibtex(bibtex);

            HttpSession session = request.getSession(false);
            Utilisateur user = session != null ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;
            Long ajouteParId = (user != null && user.getId() != null) ? user.getId() : 1L;

            dao.createPublication(pub, ajouteParId);
            response.sendRedirect(request.getContextPath() + "/admin/publications");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'enregistrement de la publication", e);
        }
    }
}
