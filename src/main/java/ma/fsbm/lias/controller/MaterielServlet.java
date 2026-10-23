package ma.fsbm.lias.controller;

import ma.fsbm.lias.dao.MaterielDAO;
import ma.fsbm.lias.model.Materiel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/admin/materiels")
public class MaterielServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConnection");
        MaterielDAO dao = new MaterielDAO(conn);

        String action = request.getParameter("action");
        try {
            if ("delete".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                dao.deleteMateriel(id);
                response.sendRedirect(request.getContextPath() + "/admin/materiels");
                return;
            }

            request.setAttribute("materiels", dao.getAllMateriels());
            request.getRequestDispatcher("/WEB-INF/views/admin/materiels.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la gestion du matériel", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConnection");
        MaterielDAO dao = new MaterielDAO(conn);

        try {
            String nom = request.getParameter("nom");
            String numeroSerie = request.getParameter("numeroSerie");
            String categorie = request.getParameter("categorie");
            String etat = request.getParameter("etat");
            String localisation = request.getParameter("localisation");

            Materiel mat = new Materiel();
            mat.setNom(nom);
            mat.setNumeroSerie(numeroSerie);
            mat.setCategorie(categorie);
            mat.setEtat(etat);
            mat.setLocalisation(localisation);

            dao.createMateriel(mat);
            response.sendRedirect(request.getContextPath() + "/admin/materiels");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'enregistrement du matériel", e);
        }
    }
}