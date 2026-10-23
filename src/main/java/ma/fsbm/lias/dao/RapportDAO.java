package ma.fsbm.lias.dao;

import ma.fsbm.lias.model.RapportStatistiques;
import java.sql.*;

public class RapportDAO {
    private Connection connection;

    public RapportDAO(Connection connection) {
        this.connection = connection;
    }

    public RapportStatistiques getStatistiquesAnnee(int annee) throws SQLException {
        RapportStatistiques stats = new RapportStatistiques();
        stats.setAnnee(annee);

        // Compte total des membres
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) FROM membres");
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) stats.setTotalMembres(rs.getInt(1));
        }

        // Compte total des équipes
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) FROM equipes");
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) stats.setTotalEquipes(rs.getInt(1));
        }

        // Publications sur l'année sélectionnée
        String sqlPub = "SELECT COUNT(*) FROM publications WHERE annee = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlPub)) {
            pstmt.setInt(1, annee);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) stats.setTotalPublications(rs.getInt(1));
            }
        }

        // Événements organisés sur l'année sélectionnée
        String sqlEv = "SELECT COUNT(*) FROM evenements WHERE YEAR(date_debut) = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sqlEv)) {
            pstmt.setInt(1, annee);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) stats.setTotalEvenements(rs.getInt(1));
            }
        }

        // Équipements enregistrés
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT COUNT(*) FROM materiels");
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) stats.setTotalMateriels(rs.getInt(1));
        }

        return stats;
    }
}