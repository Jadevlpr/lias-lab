package ma.fsbm.lias.dao;

import ma.fsbm.lias.model.Equipe;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeDAO {
    private Connection connection;

    public EquipeDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Equipe> getAllEquipes() throws SQLException {
        List<Equipe> equipes = new ArrayList<>();
        String sql = "SELECT e.*, CONCAT(m.prenom, ' ', m.nom) AS chef_nom FROM equipes e " +
                     "LEFT JOIN membres m ON e.chef_id = m.id ORDER BY e.id DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Equipe eq = new Equipe();
                eq.setId(rs.getLong("id"));
                eq.setNom(rs.getString("nom"));
                eq.setAcronyme(rs.getString("acronyme"));
                eq.setDescription(rs.getString("description"));
                eq.setChefId(rs.getObject("chef_id") != null ? rs.getLong("chef_id") : null);
                eq.setChefNomComplet(rs.getString("chef_nom"));
                equipes.add(eq);
            }
        }
        return equipes;
    }

    public boolean createEquipe(Equipe equipe) throws SQLException {
        String sql = "INSERT INTO equipes (nom, acronyme, description, chef_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, equipe.getNom());
            pstmt.setString(2, equipe.getAcronyme());
            pstmt.setString(3, equipe.getDescription());
            if (equipe.getChefId() != null) {
                pstmt.setLong(4, equipe.getChefId());
            } else {
                pstmt.setNull(4, Types.BIGINT);
            }
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteEquipe(Long id) throws SQLException {
        String sql = "DELETE FROM equipes WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
}