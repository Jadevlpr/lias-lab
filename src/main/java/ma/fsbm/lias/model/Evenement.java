package ma.fsbm.lias.model;

import java.sql.Timestamp;

public class Evenement {
    private Long id;
    private String titre;
    private String type; // ex: Séminaire, Soutenance, Workshop, Conférence
    private String description;
    private Timestamp dateDebut;
    private Timestamp dateFin;
    private String lieu;
    private String lienEnLigne;

    public Evenement() {}

    public Evenement(Long id, String titre, String type, String description, 
                     Timestamp dateDebut, Timestamp dateFin, String lieu, String lienEnLigne) {
        this.id = id;
        this.titre = titre;
        this.type = type;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.lieu = lieu;
        this.lienEnLigne = lienEnLigne;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Timestamp getDateDebut() { return dateDebut; }
    public void setDateDebut(Timestamp dateDebut) { this.dateDebut = dateDebut; }
    public Timestamp getDateFin() { return dateFin; }
    public void setDateFin(Timestamp dateFin) { this.dateFin = dateFin; }
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    public String getLienEnLigne() { return lienEnLigne; }
    public void setLienEnLigne(String lienEnLigne) { this.lienEnLigne = lienEnLigne; }
}