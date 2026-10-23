package ma.fsbm.lias.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.fsbm.lias.dao.MembreDAO;
import ma.fsbm.lias.model.Membre;
import ma.fsbm.lias.model.MembreResume;
import ma.fsbm.lias.model.StatutMembre;
import ma.fsbm.lias.model.Utilisateur;
import ma.fsbm.lias.service.GestionMembreService;

@WebServlet({"/admin/membres", "/admin/membres/nouveau"})
public class MembreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final MembreDAO membreDAO = new MembreDAO();
    private final GestionMembreService service = new GestionMembreService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getRequestURI().endsWith("/nouveau")) {
            request.getRequestDispatcher("/WEB-INF/views/membre-form.jsp").forward(request, response);
            return;
        }
        try {
            List<MembreResume> membres = membreDAO.listerTous();
            request.setAttribute("membres", membres);
            request.getRequestDispatcher("/WEB-INF/views/membres.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Impossible de charger les membres.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(request.getParameter("nom"));
            utilisateur.setPrenom(request.getParameter("prenom"));
            utilisateur.setEmail(request.getParameter("email"));
            utilisateur.setMotDePasse(request.getParameter("motDePasse"));

            Membre membre = new Membre();
            membre.setDateNaissance(date(request.getParameter("dateNaissance")));
            membre.setDateEmbauche(date(request.getParameter("dateEmbauche")));
            membre.setEtablissementOrigine(request.getParameter("etablissementOrigine"));
            membre.setLaboratoireOrigine(request.getParameter("laboratoireOrigine"));
            membre.setBiographie(request.getParameter("biographie"));
            membre.setCentresInteret(request.getParameter("centresInteret"));

            StatutMembre statut = StatutMembre.valueOf(request.getParameter("statut"));
            service.creerMembre(utilisateur, membre, statut, date(request.getParameter("dateDebutAffiliation")));
            request.getSession().setAttribute("succes", "Le membre a été créé avec succès.");
            response.sendRedirect(request.getContextPath() + "/admin/membres");
        } catch (IllegalArgumentException | SQLException e) {
            request.setAttribute("erreur", "Création impossible : " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/membre-form.jsp").forward(request, response);
        }
    }

    private LocalDate date(String valeur) {
        return valeur == null || valeur.isBlank() ? null : LocalDate.parse(valeur);
    }
}
