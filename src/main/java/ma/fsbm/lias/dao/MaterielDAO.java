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
                liste.add(new Materiel(
                    rs.getLong("id"),
                    rs.getString("nom"),
                    rs.getString("numero_serie"),
                    rs.getString("categorie"),
                    rs.getString("etat"),
                    rs.getString("localisation")
                ));
            }
        }
        return liste;
    }

    public boolean createMateriel(Materiel mat) throws SQLException {
        String sql = "INSERT INTO materiels (nom, numero_serie, categorie, etat, localisation) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, mat.getNom());
            pstmt.setString(2, mat.getNumeroSerie());
            pstmt.setString(3, mat.getCategorie());
            pstmt.setString(4, mat.getEtat());
            pstmt.setString(5, mat.getLocalisation());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteMateriel(Long id) throws SQLException {
        String sql = "DELETE FROM materiels WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
}