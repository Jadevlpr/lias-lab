package ma.fsbm.lias.dao;

import ma.fsbm.lias.model.Evenement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementDAO {
    private Connection connection;

    public EvenementDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Evenement> getAllEvenements() throws SQLException {
        List<Evenement> liste = new ArrayList<>();
        String sql = "SELECT * FROM evenements ORDER BY date_debut DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String lien = safeGetString(rs, "lien_en_ligne");
                Evenement ev = new Evenement(
                    rs.getLong("id"),
                    rs.getString("titre"),
                    rs.getString("type"),
                    safeGetString(rs, "description"),
                    rs.getTimestamp("date_debut"),
                    rs.getTimestamp("date_fin"),
                    safeGetString(rs, "lieu"),
                    lien
                );
                liste.add(ev);
            }
        }
        return liste;
    }

    public boolean createEvenement(Evenement ev, Long createurId) throws SQLException {
        String typeEnum = normalizeType(ev.getType());
        String sql = """
            INSERT INTO evenements (titre, type, description, date_debut, date_fin, lieu, lien_en_ligne, statut, createur_id) 
            VALUES (?, ?, ?, ?, ?, ?, ?, 'PUBLIE', ?)
            """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ev.getTitre());
            pstmt.setString(2, typeEnum);
            pstmt.setString(3, ev.getDescription());
            pstmt.setTimestamp(4, ev.getDateDebut());
            pstmt.setTimestamp(5, ev.getDateFin());
            pstmt.setString(6, ev.getLieu());
            pstmt.setString(7, ev.getLienEnLigne());
            pstmt.setLong(8, createurId != null ? createurId : 1L);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteEvenement(Long id) throws SQLException {
        String sql = "DELETE FROM evenements WHERE id = ?";
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

    private String normalizeType(String type) {
        if (type == null) return "AUTRE";
        String upper = type.toUpperCase().replace("É", "E");
        if (upper.contains("CONF")) return "CONFERENCE";
        if (upper.contains("SEMINAIRE")) return "SEMINAIRE";
        if (upper.contains("WORKSHOP") || upper.contains("ATELIER")) return "WORKSHOP";
        if (upper.contains("REUNION")) return "REUNION";
        return "AUTRE";
    }
}
