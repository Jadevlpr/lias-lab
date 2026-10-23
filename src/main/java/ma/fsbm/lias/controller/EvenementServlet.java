package ma.fsbm.lias.controller;

import ma.fsbm.lias.dao.EvenementDAO;
import ma.fsbm.lias.model.Evenement;
import ma.fsbm.lias.model.Utilisateur;
import ma.fsbm.lias.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;

@WebServlet("/admin/evenements")
public class EvenementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            EvenementDAO dao = new EvenementDAO(conn);

            String action = request.getParameter("action");
            if ("delete".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                dao.deleteEvenement(id);
                response.sendRedirect(request.getContextPath() + "/admin/evenements");
                return;
            }

            request.setAttribute("evenements", dao.getAllEvenements());
            request.getRequestDispatcher("/WEB-INF/views/evenements.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la gestion des événements", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            EvenementDAO dao = new EvenementDAO(conn);

            String titre = request.getParameter("titre");
            String type = request.getParameter("type");
            String description = request.getParameter("description");
            String lieu = request.getParameter("lieu");
            String lienEnLigne = request.getParameter("lienEnLigne");
            
            String dateDebutStr = request.getParameter("dateDebut");
            String dateFinStr = request.getParameter("dateFin");

            Timestamp dateDebut = (dateDebutStr != null && !dateDebutStr.isEmpty())
                    ? Timestamp.valueOf(dateDebutStr.replace("T", " ") + (dateDebutStr.length() == 16 ? ":00" : ""))
                    : new Timestamp(System.currentTimeMillis());
                    
            Timestamp dateFin = (dateFinStr != null && !dateFinStr.isEmpty()) 
                    ? Timestamp.valueOf(dateFinStr.replace("T", " ") + (dateFinStr.length() == 16 ? ":00" : ""))
                    : null;

            Evenement ev = new Evenement();
            ev.setTitre(titre);
            ev.setType(type);
            ev.setDescription(description);
            ev.setDateDebut(dateDebut);
            ev.setDateFin(dateFin);
            ev.setLieu(lieu);
            ev.setLienEnLigne(lienEnLigne);

            HttpSession session = request.getSession(false);
            Utilisateur user = session != null ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;
            Long createurId = (user != null && user.getId() != null) ? user.getId() : 1L;

            dao.createEvenement(ev, createurId);
            response.sendRedirect(request.getContextPath() + "/admin/evenements");
        } catch (Exception e) {
            throw new ServletException("Erreur lors de l'enregistrement de l'événement", e);
        }
    }
}
