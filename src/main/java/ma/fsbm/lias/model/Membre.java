package ma.fsbm.lias.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Membre {

    private Long id;
    private Long utilisateurId;

    private String photo;
    private LocalDate dateNaissance;
    private String etablissementOrigine;
    private String laboratoireOrigine;
    private String biographie;
    private String centresInteret;
    private LocalDate dateEmbauche;

    private LocalDateTime dateCreation;

    public Membre() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEtablissementOrigine() {
        return etablissementOrigine;
    }

    public void setEtablissementOrigine(String etablissementOrigine) {
        this.etablissementOrigine = etablissementOrigine;
    }

    public String getLaboratoireOrigine() {
        return laboratoireOrigine;
    }

    public void setLaboratoireOrigine(String laboratoireOrigine) {
        this.laboratoireOrigine = laboratoireOrigine;
    }

    public String getBiographie() {
        return biographie;
    }

    public void setBiographie(String biographie) {
        this.biographie = biographie;
    }

    public String getCentresInteret() {
        return centresInteret;
    }

    public void setCentresInteret(String centresInteret) {
        this.centresInteret = centresInteret;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}