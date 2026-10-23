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
        // Jointure avec membres et utilisateurs pour récupérer le nom complet du responsable
        // Note : dans la table membres, prenom et nom se trouvent dans utilisateurs
        String sql = """
            SELECT e.*, 
                   CONCAT(u.prenom, ' ', u.nom) AS chef_nom
            FROM equipes e
            LEFT JOIN membres m ON e.chef_id = m.id
            LEFT JOIN utilisateurs u ON m.utilisateur_id = u.id
            ORDER BY e.id DESC
            """;
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Equipe eq = new Equipe();
                eq.setId(rs.getLong("id"));
                eq.setNom(rs.getString("nom"));
                
                String acronyme = safeGetString(rs, "acronyme");
                if (acronyme == null) {
                    acronyme = safeGetString(rs, "axe_recherche");
                }
                eq.setAcronyme(acronyme);
                eq.setDescription(safeGetString(rs, "description"));
                
                Long chefId = null;
                try {
                    Object obj = rs.getObject("chef_id");
                    if (obj != null) {
                        chefId = rs.getLong("chef_id");
                    }
                } catch (SQLException ignored) {}
                eq.setChefId(chefId);
                
                eq.setChefNomComplet(safeGetString(rs, "chef_nom"));
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

    private String safeGetString(ResultSet rs, String column) {
        try {
            return rs.getString(column);
        } catch (SQLException e) {
            return null;
        }
    }
}
