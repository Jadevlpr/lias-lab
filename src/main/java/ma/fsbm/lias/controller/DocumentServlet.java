package ma.fsbm.lias.controller;

import ma.fsbm.lias.dao.DocumentDAO;
import ma.fsbm.lias.model.Document;
import ma.fsbm.lias.model.Utilisateur;
import ma.fsbm.lias.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/documents")
public class DocumentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            DocumentDAO dao = new DocumentDAO(conn);

            String action = request.getParameter("action");
            if ("delete".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                dao.deleteDocument(id);
                response.sendRedirect(request.getContextPath() + "/admin/documents");
                return;
            }

            request.setAttribute("documents", dao.getAllDocuments());
            request.getRequestDispatcher("/WEB-INF/views/documents.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la gestion des documents", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            DocumentDAO dao = new DocumentDAO(conn);

            String titre = request.getParameter("titre");
            String categorie = request.getParameter("categorie");
            String cheminFichier = request.getParameter("cheminFichier");

            HttpSession session = request.getSession(false);
            Utilisateur user = session != null ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;
            Long ajouteParId = (user != null && user.getId() != null) ? user.getId() : 1L;

            Document doc = new Document();
            doc.setTitre(titre);
            doc.setCategorie(categorie);
            doc.setCheminFichier(cheminFichier);
            doc.setAjouteParId(ajouteParId);

            dao.createDocument(doc);
            response.sendRedirect(request.getContextPath() + "/admin/documents");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'enregistrement du document", e);
        }
    }
}
