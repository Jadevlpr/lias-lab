package ma.fsbm.lias.dao;

import ma.fsbm.lias.model.Materiel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterielDAO {
    private Connection connection;

    public MaterielDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Materiel> getAllMateriels() throws SQLException {
        List<Materiel> liste = new ArrayList<>();
        String sql = "SELECT * FROM materiels ORDER BY id DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String nom = safeGetString(rs, "nom");
                if (nom == null || nom.isBlank()) {
                    nom = safeGetString(rs, "libelle");
                }

                String numeroSerie = safeGetString(rs, "numero_serie");
                if (numeroSerie == null || numeroSerie.isBlank()) {
                    numeroSerie = safeGetString(rs, "reference_materiel");
                }

                String categorie = safeGetString(rs, "categorie");
                if (categorie == null || categorie.isBlank()) {
                    categorie = "Équipement";
                }

                String etat = safeGetString(rs, "etat");
                if (etat == null || etat.isBlank()) {
                    etat = "Disponible";
                }

                String localisation = safeGetString(rs, "localisation");
                if (localisation == null || localisation.isBlank()) {
                    localisation = "Laboratoire";
                }

                liste.add(new Materiel(
                    rs.getLong("id"),
                    nom,
                    numeroSerie,
                    categorie,
                    etat,
                    localisation
                ));
            }
        }
        return liste;
    }

    public boolean createMateriel(Materiel mat) throws SQLException {
        String sql = """
            INSERT INTO materiels (nom, numero_serie, categorie, etat, localisation, libelle, reference_materiel, quantite_totale, quantite_disponible, date_arrivage) 
            VALUES (?, ?, ?, ?, ?, ?, ?, 1, 1, CURDATE())
            """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, mat.getNom());
            pstmt.setString(2, mat.getNumeroSerie());
            pstmt.setString(3, mat.getCategorie());
            pstmt.setString(4, mat.getEtat());
            pstmt.setString(5, mat.getLocalisation());
            pstmt.setString(6, mat.getNom()); // libelle
            pstmt.setString(7, mat.getNumeroSerie()); // reference_materiel
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            // Fallback au schéma basique si les colonnes étendues n'existent pas encore
            String fallbackSql = "INSERT INTO materiels (libelle, reference_materiel, etat, quantite_totale, quantite_disponible, date_arrivage) VALUES (?, ?, ?, 1, 1, CURDATE())";
            try (PreparedStatement fallbackPstmt = connection.prepareStatement(fallbackSql)) {
                fallbackPstmt.setString(1, mat.getNom());
                fallbackPstmt.setString(2, mat.getNumeroSerie());
                fallbackPstmt.setString(3, mat.getEtat());
                return fallbackPstmt.executeUpdate() > 0;
            }
        }
    }

    public boolean deleteMateriel(Long id) throws SQLException {
        // Supprimer d'abord d'éventuelles distributions
        try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM distributions_materiel WHERE materiel_id = ?")) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ignored) {}

        String sql = "DELETE FROM materiels WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    private String safeGetString(ResultSet rs, String column) {
        try {
            return rs.getString(column);
        } catch (SQLException e) {
            return null;
        }
    }
}
