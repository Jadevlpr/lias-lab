package ma.fsbm.lias.dao;

import ma.fsbm.lias.model.DemandeAdhesion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DemandeAdhesionDAO {
    private Connection connection;

    public DemandeAdhesionDAO(Connection connection) {
        this.connection = connection;
    }

    public List<DemandeAdhesion> getAllDemandes() throws SQLException {
        List<DemandeAdhesion> liste = new ArrayList<>();
        String sql = """
            SELECT d.*, 
                   CONCAT(u.prenom, ' ', u.nom) AS decision_par_nom 
            FROM demandes_adhesion d 
            LEFT JOIN utilisateurs u ON d.decision_par = u.id 
            ORDER BY d.date_demande DESC
            """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DemandeAdhesion d = mapResultSet(rs);
                liste.add(d);
            }
        }
        return liste;
    }

    public Optional<DemandeAdhesion> getById(Long id) throws SQLException {
        String sql = """
            SELECT d.*, 
                   CONCAT(u.prenom, ' ', u.nom) AS decision_par_nom 
            FROM demandes_adhesion d 
            LEFT JOIN utilisateurs u ON d.decision_par = u.id 
            WHERE d.id = ?
            """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSet(rs));
                }
            }
        }
        return Optional.empty();
    }

    public boolean createDemande(DemandeAdhesion d) throws SQLException {
        String sql = """
            INSERT INTO demandes_adhesion (nom, prenom, email, etablissement, motivation, cv_path, statut) 
            VALUES (?, ?, ?, ?, ?, ?, 'EN_ATTENTE')
            """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, d.getNom());
            pstmt.setString(2, d.getPrenom());
            pstmt.setString(3, d.getEmail());
            pstmt.setString(4, d.getEtablissement());
            pstmt.setString(5, d.getMotivation());
            pstmt.setString(6, d.getCvPath());
            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        d.setId(rs.getLong(1));
                    }
                }
                return true;
            }
            return false;
        }
    }

    public boolean updateStatut(Long id, String statut, Long decisionPar) throws SQLException {
        String sql = "UPDATE demandes_adhesion SET statut = ?, decision_par = ?, date_decision = NOW() WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, statut);
            if (decisionPar != null) {
                pstmt.setLong(2, decisionPar);
            } else {
                pstmt.setNull(2, Types.BIGINT);
            }
            pstmt.setLong(3, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    private DemandeAdhesion mapResultSet(ResultSet rs) throws SQLException {
        DemandeAdhesion d = new DemandeAdhesion();
        d.setId(rs.getLong("id"));
        d.setNom(rs.getString("nom"));
        d.setPrenom(rs.getString("prenom"));
        d.setEmail(rs.getString("email"));
        d.setEtablissement(rs.getString("etablissement"));
        d.setMotivation(rs.getString("motivation"));
        d.setCvPath(rs.getString("cv_path"));
        d.setStatut(rs.getString("statut"));
        d.setDateDemande(rs.getTimestamp("date_demande"));
        d.setDecisionPar(rs.getObject("decision_par") != null ? rs.getLong("decision_par") : null);
        d.setDateDecision(rs.getTimestamp("date_decision"));
        
        try {
            d.setDecisionParNom(rs.getString("decision_par_nom"));
        } catch (SQLException ignored) {}
        
        return d;
    }
}
