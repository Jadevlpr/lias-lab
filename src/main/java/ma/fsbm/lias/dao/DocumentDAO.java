package ma.fsbm.lias.dao;

import ma.fsbm.lias.model.Document;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentDAO {
    private Connection connection;

    public DocumentDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Document> getAllDocuments() throws SQLException {
        List<Document> liste = new ArrayList<>();
        String sql = "SELECT d.*, CONCAT(m.prenom, ' ', m.nom) AS auteur_nom FROM documents d " +
                     "LEFT JOIN membres m ON d.ajoute_par = m.id ORDER BY d.date_ajout DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Document doc = new Document(
                    rs.getLong("id"),
                    rs.getString("titre"),
                    rs.getString("categorie"),
                    rs.getString("chemin_fichier"),
                    rs.getTimestamp("date_ajout"),
                    rs.getObject("ajoute_par") != null ? rs.getLong("ajoute_par") : null
                );
                doc.setAjouteParNom(rs.getString("auteur_nom"));
                liste.add(doc);
            }
        }
        return liste;
    }

    public boolean createDocument(Document doc) throws SQLException {
        String sql = "INSERT INTO documents (titre, categorie, chemin_fichier, ajoute_par) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, doc.getTitre());
            pstmt.setString(2, doc.getCategorie());
            pstmt.setString(3, doc.getCheminFichier());
            if (doc.getAjouteParId() != null) {
                pstmt.setLong(4, doc.getAjouteParId());
            } else {
                pstmt.setNull(4, Types.BIGINT);
            }
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteDocument(Long id) throws SQLException {
        String sql = "DELETE FROM documents WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
}