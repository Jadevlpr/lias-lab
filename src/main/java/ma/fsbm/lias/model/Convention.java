package ma.fsbm.lias.model;

import java.sql.Date;

public class Convention {
    private Long id;
    private String titre;
    private String partenaire; // ex: Entreprise, Université partenaire, Organisme
    private Date dateDebut;
    private Date dateFin;
    private Double budget;
    private String statut; // ex: En cours, Terminé, En négociation

    public Convention() {}

    public Convention(Long id, String titre, String partenaire, Date dateDebut, Date dateFin, Double budget, String statut) {
        this.id = id;
        this.titre = titre;
        this.partenaire = partenaire;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.budget = budget;
        this.statut = statut;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getPartenaire() { return partenaire; }
    public void setPartenaire(String partenaire) { this.partenaire = partenaire; }
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public Double getBudget() { return budget; }
    public void setBudget(Double budget) { this.budget = budget; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}