package ma.fsbm.lias.service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Locale;

import ma.fsbm.lias.dao.MembreDAO;
import ma.fsbm.lias.dao.UtilisateurDAO;
import ma.fsbm.lias.model.Membre;
import ma.fsbm.lias.model.Role;
import ma.fsbm.lias.model.StatutMembre;
import ma.fsbm.lias.model.Utilisateur;
import ma.fsbm.lias.util.PasswordUtil;

public class GestionMembreService {

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private final MembreDAO membreDAO = new MembreDAO();

    public long creerMembre(
            Utilisateur utilisateur,
            Membre membre,
            StatutMembre statutInitial,
            LocalDate dateDebutAffiliation
    ) throws SQLException {

        if (utilisateur.getEmail() == null
                || utilisateur.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "L'e-mail est obligatoire."
            );
        }

        if (utilisateur.getMotDePasse() == null
                || utilisateur.getMotDePasse().isBlank()) {
            throw new IllegalArgumentException(
                    "Le mot de passe est obligatoire."
            );
        }

        utilisateur.setEmail(
                utilisateur.getEmail()
                        .trim()
                        .toLowerCase(Locale.ROOT)
        );

        utilisateur.setMotDePasse(
                PasswordUtil.chiffrer(
                        utilisateur.getMotDePasse()
                )
        );

        utilisateur.setActif(true);

        if (utilisateur.getRoles().isEmpty()) {
            utilisateur.getRoles().add(Role.MEMBRE);
        }

        long utilisateurId = utilisateurDAO.creer(utilisateur);

        membre.setUtilisateurId(utilisateurId);

        return membreDAO.creerProfilAvecAffiliation(
                membre,
                statutInitial,
                dateDebutAffiliation
        );
    }
}