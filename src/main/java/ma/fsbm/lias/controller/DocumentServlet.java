package ma.fsbm.lias.controller;

import ma.fsbm.lias.dao.DocumentDAO;
import ma.fsbm.lias.model.Document;
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
        
        try (Connection conn = ma.fsbm.lias.util.DatabaseConnection.getConnection()) {
            DocumentDAO dao = new DocumentDAO(conn);

            String action = request.getParameter("action");
            if ("delete".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                dao.deleteDocument(id);
                response.sendRedirect(request.getContextPath() + "/admin/documents");
                return;
            }

            request.setAttribute("documents", dao.getAllDocuments());
            request.getRequestDispatcher("/WEB-INF/views/admin/documents.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la gestion des documents", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConnection");
        DocumentDAO dao = new DocumentDAO(conn);

        try {
            String titre = request.getParameter("titre");
            String categorie = request.getParameter("categorie");
            String cheminFichier = request.getParameter("cheminFichier");

            Document doc = new Document();
            doc.setTitre(titre);
            doc.setCategorie(categorie);
            doc.setCheminFichier(cheminFichier);

            dao.createDocument(doc);
            response.sendRedirect(request.getContextPath() + "/admin/documents");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'enregistrement du document", e);
        }
    }
}