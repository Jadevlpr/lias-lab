package ma.fsbm.lias.controller;

import ma.fsbm.lias.dao.DemandeAdhesionDAO;
import ma.fsbm.lias.dao.UtilisateurDAO;
import ma.fsbm.lias.model.DemandeAdhesion;
import ma.fsbm.lias.model.Membre;
import ma.fsbm.lias.model.Role;
import ma.fsbm.lias.model.StatutMembre;
import ma.fsbm.lias.model.Utilisateur;
import ma.fsbm.lias.service.GestionMembreService;
import ma.fsbm.lias.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@WebServlet("/admin/adhesions")
public class AdminAdhesionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final GestionMembreService membreService = new GestionMembreService();
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            DemandeAdhesionDAO dao = new DemandeAdhesionDAO(conn);
            List<DemandeAdhesion> demandes = dao.getAllDemandes();
            request.setAttribute("demandes", demandes);
            request.getRequestDispatcher("/WEB-INF/views/adhesions.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur lors de la récupération des demandes d'adhésion", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String idStr = request.getParameter("id");

        if (idStr == null || idStr.isBlank() || action == null) {
            response.sendRedirect(request.getContextPath() + "/admin/adhesions");
            return;
        }

        Long id = Long.parseLong(idStr);

        HttpSession session = request.getSession(false);
        Utilisateur user = session != null ? (Utilisateur) session.getAttribute("utilisateurConnecte") : null;
        Long decisionPar = user != null ? user.getId() : 1L;

        try (Connection conn = DatabaseConnection.getConnection()) {
            DemandeAdhesionDAO dao = new DemandeAdhesionDAO(conn);
            Optional<DemandeAdhesion> optDemande = dao.getById(id);

            if (optDemande.isEmpty()) {
                session.setAttribute("erreur", "Demande introuvable.");
                response.sendRedirect(request.getContextPath() + "/admin/adhesions");
                return;
            }

            DemandeAdhesion demande = optDemande.get();

            if ("accepter".equalsIgnoreCase(action)) {
                // Conformité Cahier des charges : Décision Acceptée -> création de compte
                Optional<Utilisateur> existant = utilisateurDAO.trouverParEmail(demande.getEmail());
                if (existant.isEmpty()) {
                    Utilisateur nouvelUtilisateur = new Utilisateur();
                    nouvelUtilisateur.setNom(demande.getNom());
                    nouvelUtilisateur.setPrenom(demande.getPrenom());
                    nouvelUtilisateur.setEmail(demande.getEmail());
                    nouvelUtilisateur.setMotDePasse("Lias2026@"); // Mot de passe initial
                    nouvelUtilisateur.getRoles().add(Role.MEMBRE);

                    Membre nouveauMembre = new Membre();
                    nouveauMembre.setEtablissementOrigine(demande.getEtablissement());
                    nouveauMembre.setBiographie(demande.getMotivation());

                    membreService.creerMembre(
                        nouvelUtilisateur, 
                        nouveauMembre, 
                        StatutMembre.PERMANENT, 
                        LocalDate.now()
                    );
                }

                dao.updateStatut(id, "ACCEPTEE", decisionPar);
                session.setAttribute("succes", "La demande de " + demande.getPrenom() + " " + demande.getNom() 
                    + " a été acceptée. Son compte membre a été créé avec le mot de passe initial : Lias2026@");

            } else if ("refuser".equalsIgnoreCase(action)) {
                dao.updateStatut(id, "REFUSEE", decisionPar);
                session.setAttribute("succes", "La demande d'adhésion a été refusée.");
            }

            response.sendRedirect(request.getContextPath() + "/admin/adhesions");

        } catch (Exception e) {
            throw new ServletException("Erreur lors du traitement de la décision d'adhésion", e);
        }
    }
}
