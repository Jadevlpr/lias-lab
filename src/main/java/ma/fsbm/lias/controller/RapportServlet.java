package ma.fsbm.lias.controller;

import ma.fsbm.lias.dao.RapportDAO;
import ma.fsbm.lias.model.RapportStatistiques;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.Calendar;

@WebServlet("/admin/rapport")
public class RapportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("dbConnection");
        RapportDAO dao = new RapportDAO(conn);

        String anneeStr = request.getParameter("annee");
        int annee = (anneeStr != null && !anneeStr.isEmpty()) 
                    ? Integer.parseInt(anneeStr) 
                    : Calendar.getInstance().get(Calendar.YEAR);

        try {
            RapportStatistiques stats = dao.getStatistiquesAnnee(annee);
            request.setAttribute("stats", stats);
            request.setAttribute("anneeSelectionnee", annee);
            request.getRequestDispatcher("/WEB-INF/views/admin/rapport.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la génération du rapport annuel", e);
        }
    }
}