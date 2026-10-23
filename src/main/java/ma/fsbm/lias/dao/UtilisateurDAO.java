package ma.fsbm.lias.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

import ma.fsbm.lias.model.Role;
import ma.fsbm.lias.model.Utilisateur;
import ma.fsbm.lias.util.DatabaseConnection;

public class UtilisateurDAO {

    public Optional<Utilisateur> trouverParEmail(String email)
            throws SQLException {

        String sql = """
                SELECT u.id, u.nom, u.prenom, u.email, u.mot_de_passe,
                       u.actif, u.date_creation, r.nom AS role_nom
                FROM utilisateurs u
                LEFT JOIN utilisateur_roles ur
                    ON u.id = ur.utilisateur_id
                LEFT JOIN roles r
                    ON ur.role_id = r.id
                WHERE u.email = ?
                """;

        Utilisateur utilisateur = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultat = statement.executeQuery()) {

                while (resultat.next()) {
                    if (utilisateur == null) {
                        utilisateur = new Utilisateur();

                        utilisateur.setId(resultat.getLong("id"));
                        utilisateur.setNom(resultat.getString("nom"));
                        utilisateur.setPrenom(resultat.getString("prenom"));
                        utilisateur.setEmail(resultat.getString("email"));
                        utilisateur.setMotDePasse(
                                resultat.getString("mot_de_passe")
                        );
                        utilisateur.setActif(resultat.getBoolean("actif"));

                        Timestamp dateCreation =
                                resultat.getTimestamp("date_creation");

                        if (dateCreation != null) {
                            utilisateur.setDateCreation(
                                    dateCreation.toLocalDateTime()
                            );
                        }
                    }

                    String nomRole = resultat.getString("role_nom");

                    if (nomRole != null) {
                        utilisateur.getRoles().add(
                                Role.valueOf(nomRole)
                        );
                    }
                }
            }
        }

        return Optional.ofNullable(utilisateur);
    }
    public long creer(Utilisateur utilisateur) throws SQLException {

        if (utilisateur.getRoles().isEmpty()) {
            throw new IllegalArgumentException(
                    "Un utilisateur doit avoir au moins un rôle."
            );
        }

        String sqlUtilisateur = """
                INSERT INTO utilisateurs (
                    nom,
                    prenom,
                    email,
                    mot_de_passe,
                    actif
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        String sqlRole = """
                INSERT INTO utilisateur_roles (
                    utilisateur_id,
                    role_id
                )
                SELECT ?, id
                FROM roles
                WHERE nom = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                long utilisateurId;

                try (PreparedStatement statement =
                        connection.prepareStatement(
                                sqlUtilisateur,
                                Statement.RETURN_GENERATED_KEYS
                        )) {

                    statement.setString(1, utilisateur.getNom());
                    statement.setString(2, utilisateur.getPrenom());
                    statement.setString(3, utilisateur.getEmail());
                    statement.setString(4, utilisateur.getMotDePasse());
                    statement.setBoolean(5, utilisateur.isActif());

                    statement.executeUpdate();

                    try (ResultSet cles =
                            statement.getGeneratedKeys()) {

                        if (!cles.next()) {
                            throw new SQLException(
                                    "Impossible de récupérer l'identifiant utilisateur."
                            );
                        }

                        utilisateurId = cles.getLong(1);
                    }
                }

                try (PreparedStatement statement =
                        connection.prepareStatement(sqlRole)) {

                    for (Role role : utilisateur.getRoles()) {
                        statement.setLong(1, utilisateurId);
                        statement.setString(2, role.name());
                        statement.addBatch();
                    }

                    statement.executeBatch();
                }

                connection.commit();
                return utilisateurId;

            } catch (SQLException e) {
                connection.rollback();
                throw e;

            } finally {
                connection.setAutoCommit(true);
            }
        }
    }
}