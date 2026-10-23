package ma.fsbm.lias.service;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;

import ma.fsbm.lias.dao.UtilisateurDAO;
import ma.fsbm.lias.model.Utilisateur;
import ma.fsbm.lias.util.PasswordUtil;

public class AuthService {

    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public Optional<Utilisateur> authentifier(
            String email,
            String motDePasse
    ) throws SQLException {

        if (email == null || email.isBlank()
                || motDePasse == null || motDePasse.isBlank()) {
            return Optional.empty();
        }

        String emailNormalise = email.trim().toLowerCase(Locale.ROOT);

        Optional<Utilisateur> resultat =
                utilisateurDAO.trouverParEmail(emailNormalise);

        if (resultat.isEmpty()) {
            return Optional.empty();
        }

        Utilisateur utilisateur = resultat.get();

        if (!utilisateur.isActif()) {
            return Optional.empty();
        }

        boolean motDePasseCorrect = PasswordUtil.verifier(
                motDePasse,
                utilisateur.getMotDePasse()
        );

        return motDePasseCorrect
                ? Optional.of(utilisateur)
                : Optional.empty();
    }
}