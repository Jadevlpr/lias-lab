package ma.fsbm.lias.dao;

import ma.fsbm.lias.model.Publication;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicationDAO {
    private Connection connection;

    public PublicationDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Publication> getAllPublications() throws SQLException {
        List<Publication> liste = new ArrayList<>();
        // Jointure avec publication_auteurs, membres et utilisateurs pour concaténer les auteurs
        String sql = """
            SELECT p.*, 
                   GROUP_CONCAT(CONCAT(u.prenom, ' ', u.nom) ORDER BY pa.ordre_auteur SEPARATOR ', ') AS auteurs 
            FROM publications p 
            LEFT JOIN publication_auteurs pa ON p.id = pa.publication_id 
            LEFT JOIN membres m ON pa.membre_id = m.id 
            LEFT JOIN utilisateurs u ON m.utilisateur_id = u.id 
            GROUP BY p.id 
            ORDER BY p.annee DESC, p.id DESC
            """;

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String journal = safeGetString(rs, "journal_or_conf");
                String lienPdf = safeGetString(rs, "lien_pdf");
                if (lienPdf == null || lienPdf.isBlank()) {
                    lienPdf = safeGetString(rs, "url");
                }
                String bibtex = safeGetString(rs, "bibtex");
                if (bibtex == null || bibtex.isBlank()) {
                    bibtex = safeGetString(rs, "resume");
                }

                Publication pub = new Publication(
                    rs.getLong("id"),
                    rs.getString("titre"),
                    safeGetString(rs, "type"),
                    rs.getInt("annee"),
                    journal,
                    safeGetString(rs, "doi"),
                    lienPdf,
                    bibtex
                );
                pub.setListeAuteurs(safeGetString(rs, "auteurs"));
                liste.add(pub);
            }
        }
        return liste;
    }

    public boolean createPublication(Publication pub, Long ajouteParId) throws SQLException {
        String sql = """
            INSERT INTO publications (titre, type, annee, journal_or_conf, doi, lien_pdf, bibtex, url, resume, ajoute_par) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, pub.getTitre());
            pstmt.setString(2, pub.getType());
            pstmt.setInt(3, pub.getAnnee());
            pstmt.setString(4, pub.getJournalOuConference());
            pstmt.setString(5, pub.getDoi());
            pstmt.setString(6, pub.getLienPdf());
            pstmt.setString(7, pub.getBibtex());
            pstmt.setString(8, pub.getLienPdf()); // mapped to url as well for schema compatibility
            pstmt.setString(9, pub.getBibtex());  // mapped to resume as well
            pstmt.setLong(10, ajouteParId != null ? ajouteParId : 1L);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deletePublication(Long id) throws SQLException {
        // Supprimer d'abord les associations dans publication_auteurs
        try (PreparedStatement pstmt = connection.prepareStatement("DELETE FROM publication_auteurs WHERE publication_id = ?")) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ignored) {}

        String sql = "DELETE FROM publications WHERE id = ?";
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
