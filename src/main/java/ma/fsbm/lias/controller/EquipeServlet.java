package ma.fsbm.lias.controller;

import ma.fsbm.lias.dao.EquipeDAO;
import ma.fsbm.lias.model.Equipe;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/equipes")
public class EquipeServlet extends HttpServlet {
    
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    
	    // Utilisation directe de DatabaseConnection
	    try (Connection conn = ma.fsbm.lias.util.DatabaseConnection.getConnection()) {
	        EquipeDAO equipeDAO = new EquipeDAO(conn);

	        String action = request.getParameter("action");
	        if ("delete".equals(action)) {
	            Long id = Long.parseLong(request.getParameter("id"));
	            equipeDAO.deleteEquipe(id);
	            response.sendRedirect(request.getContextPath() + "/admin/equipes");
	            return;
	        }

	        request.setAttribute("equipes", equipeDAO.getAllEquipes());
	        request.getRequestDispatcher("/WEB-INF/views/admin/equipes.jsp").forward(request, response);
	    } catch (Exception e) {
	        throw new ServletException("Erreur lors de la gestion des équipes", e);
	    }
	}
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConnection");
        EquipeDAO equipeDAO = new EquipeDAO(conn);

        String nom = request.getParameter("nom");
        String acronyme = request.getParameter("acronyme");
        String description = request.getParameter("description");
        String chefIdStr = request.getParameter("chefId");

        Equipe eq = new Equipe();
        eq.setNom(nom);
        eq.setAcronyme(acronyme);
        eq.setDescription(description);
        if (chefIdStr != null && !chefIdStr.isEmpty()) {
            eq.setChefId(Long.parseLong(chefIdStr));
        }

        try {
            equipeDAO.createEquipe(eq);
            response.sendRedirect(request.getContextPath() + "/admin/equipes");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la création de l'équipe", e);
        }
    }
}