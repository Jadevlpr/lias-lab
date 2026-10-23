package ma.fsbm.lias.model;

import java.sql.Timestamp;

public class Reunion {
    private Long id;
    private String ordreDuJour;
    private Timestamp dateReunion;
    private String lieu;
    private String compteRendu;

    public Reunion() {}

    public Reunion(Long id, String ordreDuJour, Timestamp dateReunion, String lieu, String compteRendu) {
        this.id = id;
        this.ordreDuJour = ordreDuJour;
        this.dateReunion = dateReunion;
        this.lieu = lieu;
        this.compteRendu = compteRendu;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOrdreDuJour() { return ordreDuJour; }
    public void setOrdreDuJour(String ordreDuJour) { this.ordreDuJour = ordreDuJour; }
    public Timestamp getDateReunion() { return dateReunion; }
    public void setDateReunion(Timestamp dateReunion) { this.dateReunion = dateReunion; }
    public String getLieu() { return lieu; }
    public void setLieu(String lieu) { this.lieu = lieu; }
    public String getCompteRendu() { return compteRendu; }
    public void setCompteRendu(String compteRendu) { this.compteRendu = compteRendu; }
}