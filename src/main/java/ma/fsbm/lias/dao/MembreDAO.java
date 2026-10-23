package ma.fsbm.lias.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ma.fsbm.lias.model.Membre;
import ma.fsbm.lias.model.MembreResume;
import ma.fsbm.lias.model.StatutMembre;
import ma.fsbm.lias.util.DatabaseConnection;

public class MembreDAO {

    public List<MembreResume> listerTous() throws SQLException {
        String sql = """
                SELECT m.id, u.nom, u.prenom, u.email, m.etablissement_origine, a.statut
                FROM membres m
                JOIN utilisateurs u ON u.id = m.utilisateur_id
                LEFT JOIN affiliations a ON a.membre_id = m.id AND a.date_fin IS NULL
                ORDER BY u.nom, u.prenom
                """;
        List<MembreResume> membres = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultats = statement.executeQuery()) {
            while (resultats.next()) {
                MembreResume membre = new MembreResume();
                membre.setId(resultats.getLong("id"));
                membre.setNom(resultats.getString("nom"));
                membre.setPrenom(resultats.getString("prenom"));
                membre.setEmail(resultats.getString("email"));
                membre.setEtablissement(resultats.getString("etablissement_origine"));
                membre.setStatut(resultats.getString("statut"));
                membres.add(membre);
            }
        }
        return membres;
    }

    public long creerProfilAvecAffiliation(
            Membre membre,
            StatutMembre statutInitial,
            LocalDate dateDebutAffiliation
    ) throws SQLException {

        String sqlMembre = """
                INSERT INTO membres (
                    utilisateur_id,
                    photo,
                    date_naissance,
                    etablissement_origine,
                    laboratoire_origine,
                    biographie,
                    centres_interet,
                    date_embauche
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        String sqlAffiliation = """
                INSERT INTO affiliations (
                    membre_id,
                    statut,
                    date_debut,
                    date_fin
                )
                VALUES (?, ?, ?, NULL)
                """;

        try (Connection connection = DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            try {
                long membreId;

                try (PreparedStatement statement =
                        connection.prepareStatement(
                                sqlMembre,
                                Statement.RETURN_GENERATED_KEYS
                        )) {

                    statement.setLong(1, membre.getUtilisateurId());
                    statement.setString(2, membre.getPhoto());

                    definirDate(
                            statement,
                            3,
                            membre.getDateNaissance()
                    );

                    statement.setString(
                            4,
                            membre.getEtablissementOrigine()
                    );

                    statement.setString(
                            5,
                            membre.getLaboratoireOrigine()
                    );

                    statement.setString(6, membre.getBiographie());
                    statement.setString(7, membre.getCentresInteret());

                    definirDate(
                            statement,
                            8,
                            membre.getDateEmbauche()
                    );

                    statement.executeUpdate();

                    try (ResultSet cles =
                            statement.getGeneratedKeys()) {

                        if (!cles.next()) {
                            throw new SQLException(
                                    "Impossible de récupérer l'identifiant du membre."
                            );
                        }

                        membreId = cles.getLong(1);
                    }
                }

                try (PreparedStatement statement =
                        connection.prepareStatement(sqlAffiliation)) {

                    statement.setLong(1, membreId);
                    statement.setString(2, statutInitial.name());

                    definirDate(
                            statement,
                            3,
                            dateDebutAffiliation
                    );

                    statement.executeUpdate();
                }

                connection.commit();
                return membreId;

            } catch (SQLException e) {
                connection.rollback();
                throw e;

            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    private void definirDate(
            PreparedStatement statement,
            int position,
            LocalDate date
    ) throws SQLException {

        if (date == null) {
            statement.setNull(position, Types.DATE);
        } else {
            statement.setDate(position, Date.valueOf(date));
        }
    }
}
