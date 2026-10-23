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
        String sql = "SELECT p.*, GROUP_CONCAT(CONCAT(m.prenom, ' ', m.nom) SEPARATOR ', ') AS auteurs " +
                     "FROM publications p " +
                     "LEFT JOIN auteurs_publications ap ON p.id = ap.publication_id " +
                     "LEFT JOIN membres m ON ap.membre_id = m.id " +
                     "GROUP BY p.id ORDER BY p.annee DESC, p.id DESC";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Publication pub = new Publication(
                    rs.getLong("id"),
                    rs.getString("titre"),
                    rs.getString("type"),
                    rs.getInt("annee"),
                    rs.getString("journal_or_conf"),
                    rs.getString("doi"),
                    rs.getString("lien_pdf"),
                    rs.getString("bibtex")
                );
                pub.setListeAuteurs(rs.getString("auteurs"));
                liste.add(pub);
            }
        }
        return liste;
    }

    public boolean createPublication(Publication pub) throws SQLException {
        String sql = "INSERT INTO publications (titre, type, annee, journal_or_conf, doi, lien_pdf, bibtex) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, pub.getTitre());
            pstmt.setString(2, pub.getType());
            pstmt.setInt(3, pub.getAnnee());
            pstmt.setString(4, pub.getJournalOuConference());
            pstmt.setString(5, pub.getDoi());
            pstmt.setString(6, pub.getLienPdf());
            pstmt.setString(7, pub.getBibtex());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deletePublication(Long id) throws SQLException {
        String sql = "DELETE FROM publications WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
}