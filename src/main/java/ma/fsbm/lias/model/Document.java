package ma.fsbm.lias.model;

import java.sql.Timestamp;

public class Document {
    private Long id;
    private String titre;
    private String categorie; // ex: PV Reunion, Reglement, Formulaire, Rapport
    private String cheminFichier;
    private Timestamp dateAjout;
    private Long ajouteParId;
    private String ajouteParNom;

    public Document() {}

    public Document(Long id, String titre, String categorie, String cheminFichier, Timestamp dateAjout, Long ajouteParId) {
        this.id = id;
        this.titre = titre;
        this.categorie = categorie;
        this.cheminFichier = cheminFichier;
        this.dateAjout = dateAjout;
        this.ajouteParId = ajouteParId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public String getCheminFichier() { return cheminFichier; }
    public void setCheminFichier(String cheminFichier) { this.cheminFichier = cheminFichier; }
    public Timestamp getDateAjout() { return dateAjout; }
    public void setDateAjout(Timestamp dateAjout) { this.dateAjout = dateAjout; }
    public Long getAjouteParId() { return ajouteParId; }
    public void setAjouteParId(Long ajouteParId) { this.ajouteParId = ajouteParId; }
    public String getAjouteParNom() { return ajouteParNom; }
    public void setAjouteParNom(String ajouteParNom) { this.ajouteParNom = ajouteParNom; }
}