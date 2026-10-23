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
                Evenement ev = new Evenement(
                    rs.getLong("id"),
                    rs.getString("titre"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getTimestamp("date_debut"),
                    rs.getTimestamp("date_fin"),
                    rs.getString("lieu"),
                    rs.getString("lien_en_ligne")
                );
                liste.add(ev);
            }
        }
        return liste;
    }

    public boolean createEvenement(Evenement ev) throws SQLException {
        String sql = "INSERT INTO evenements (titre, type, description, date_debut, date_fin, lieu, lien_en_ligne) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, ev.getTitre());
            pstmt.setString(2, ev.getType());
            pstmt.setString(3, ev.getDescription());
            pstmt.setTimestamp(4, ev.getDateDebut());
            pstmt.setTimestamp(5, ev.getDateFin());
            pstmt.setString(6, ev.getLieu());
            pstmt.setString(7, ev.getLienEnLigne());
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
}