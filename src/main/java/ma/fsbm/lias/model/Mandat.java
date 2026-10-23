package ma.fsbm.lias.model;

import java.sql.Date;

public class Mandat {
    private Long id;
    private String role; // ex: Responsable d'équipe, Directeur de labo
    private Date dateDebut;
    private Date dateFin;
    private Long membreId;
    private String membreNomComplet;

    public Mandat() {}

    public Mandat(Long id, String role, Date dateDebut, Date dateFin, Long membreId) {
        this.id = id;
        this.role = role;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.membreId = membreId;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }
    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }
    public Long getMembreId() { return membreId; }
    public void setMembreId(Long membreId) { this.membreId = membreId; }
    public String getMembreNomComplet() { return membreNomComplet; }
    public void setMembreNomComplet(String membreNomComplet) { this.membreNomComplet = membreNomComplet; }
}