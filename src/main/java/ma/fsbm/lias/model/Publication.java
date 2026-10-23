package ma.fsbm.lias.model;

import java.util.List;

public class Publication {
    private Long id;
    private String titre;
    private String type; // ex: Revue, Conférence, Brevet, Chapitre
    private Integer annee;
    private String journalOuConference;
    private String doi;
    private String lienPdf;
    private String bibtex;
    private String listeAuteurs; // String concaténée pour l'affichage simple

    public Publication() {}

    public Publication(Long id, String titre, String type, Integer annee, 
                       String journalOuConference, String doi, String lienPdf, String bibtex) {
        this.id = id;
        this.titre = titre;
        this.type = type;
        this.annee = annee;
        this.journalOuConference = journalOuConference;
        this.doi = doi;
        this.lienPdf = lienPdf;
        this.bibtex = bibtex;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Integer getAnnee() { return annee; }
    public void setAnnee(Integer annee) { this.annee = annee; }
    public String getJournalOuConference() { return journalOuConference; }
    public void setJournalOuConference(String journalOuConference) { this.journalOuConference = journalOuConference; }
    public String getDoi() { return doi; }
    public void setDoi(String doi) { this.doi = doi; }
    public String getLienPdf() { return lienPdf; }
    public void setLienPdf(String lienPdf) { this.lienPdf = lienPdf; }
    public String getBibtex() { return bibtex; }
    public void setBibtex(String bibtex) { this.bibtex = bibtex; }
    public String getListeAuteurs() { return listeAuteurs; }
    public void setListeAuteurs(String listeAuteurs) { this.listeAuteurs = listeAuteurs; }
}