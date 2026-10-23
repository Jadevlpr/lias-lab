package ma.fsbm.lias.controller;

import ma.fsbm.lias.dao.DemandeAdhesionDAO;
import ma.fsbm.lias.model.DemandeAdhesion;
import ma.fsbm.lias.util.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;

@WebServlet({"/adhesion", "/inscription"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1 MB
    maxFileSize = 10 * 1024 * 1024,       // 10 MB
    maxRequestSize = 15 * 1024 * 1024     // 15 MB
)
public class AdhesionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/adhesion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String etablissement = request.getParameter("etablissement");
        String motivation = request.getParameter("motivation");
        String cvLien = request.getParameter("cvLien");

        if (nom == null || nom.isBlank() || prenom == null || prenom.isBlank() || email == null || email.isBlank()) {
            request.setAttribute("erreur", "Veuillez renseigner tous les champs obligatoires (Nom, Prénom, E-mail).");
            request.getRequestDispatcher("/WEB-INF/views/adhesion.jsp").forward(request, response);
            return;
        }

        String cvPath = cvLien != null && !cvLien.isBlank() ? cvLien.trim() : null;

        // Traitement de l'upload de CV si présent
        try {
            Part filePart = request.getPart("cvFile");
            if (filePart != null && filePart.getSize() > 0) {
                String submittedFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String ext = "";
                int dotIndex = submittedFileName.lastIndexOf('.');
                if (dotIndex >= 0) {
                    ext = submittedFileName.substring(dotIndex);
                }
                String safeFileName = "cv_" + System.currentTimeMillis() + ext;
                
                String uploadPath = getServletContext().getRealPath("/uploads/cv");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                filePart.write(uploadPath + File.separator + safeFileName);
                cvPath = "/uploads/cv/" + safeFileName;
            }
        } catch (Exception ignored) {
            // Si l'upload échoue, on conserve cvPath fourni par lien texte
        }

        DemandeAdhesion demande = new DemandeAdhesion();
        demande.setNom(nom.trim());
        demande.setPrenom(prenom.trim());
        demande.setEmail(email.trim().toLowerCase());
        demande.setEtablissement(etablissement != null ? etablissement.trim() : "");
        demande.setMotivation(motivation != null ? motivation.trim() : "");
        demande.setCvPath(cvPath);

        try (Connection conn = DatabaseConnection.getConnection()) {
            DemandeAdhesionDAO dao = new DemandeAdhesionDAO(conn);
            dao.createDemande(demande);
            request.setAttribute("succes", "Votre demande d'adhésion a été enregistrée avec succès. Elle sera examinée par la direction du laboratoire LIAS.");
            request.getRequestDispatcher("/WEB-INF/views/adhesion.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("erreur", "Une erreur technique est survenue lors de l'enregistrement : " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/adhesion.jsp").forward(request, response);
        }
    }
}
