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
        // Table documents_laboratoire définie dans schema.sql avec jointure utilisateurs
        String sql = """
            SELECT d.id, 
                   d.nom_original AS titre, 
                   d.categorie, 
                   d.chemin AS chemin_fichier, 
                   d.date_ajout, 
                   d.ajoute_par, 
                   CONCAT(u.prenom, ' ', u.nom) AS auteur_nom 
            FROM documents_laboratoire d 
            LEFT JOIN utilisateurs u ON d.ajoute_par = u.id 
            ORDER BY d.date_ajout DESC
            """;

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
        String categorieEnum = normalizeCategorie(doc.getCategorie());
        String sql = "INSERT INTO documents_laboratoire (nom_original, categorie, chemin, ajoute_par) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, doc.getTitre());
            pstmt.setString(2, categorieEnum);
            pstmt.setString(3, doc.getCheminFichier());
            if (doc.getAjouteParId() != null) {
                pstmt.setLong(4, doc.getAjouteParId());
            } else {
                pstmt.setLong(4, 1L);
            }
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteDocument(Long id) throws SQLException {
        String sql = "DELETE FROM documents_laboratoire WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    private String normalizeCategorie(String cat) {
        if (cat == null) return "AUTRE";
        String upper = cat.toUpperCase();
        if (upper.contains("PV")) return "PV";
        if (upper.contains("RAPPORT")) return "RAPPORT";
        if (upper.contains("FINANCE")) return "FINANCEMENT";
        if (upper.contains("PROGRAMME")) return "PROGRAMME";
        if (upper.contains("ATTEST")) return "ATTESTATION";
        if (upper.contains("CONVENT")) return "CONVENTION";
        if (upper.contains("ADMIN") || upper.contains("REGLEMENT")) return "ADMINISTRATIF";
        return "AUTRE";
    }
}
