package ma.fsbm.lias.model;

public class Materiel {
    private Long id;
    private String nom;
    private String numeroSerie;
    private String categorie; // ex: Serveur, Calculateur, Capteur, PC
    private String etat; // ex: Disponible, Emprunté, En maintenance
    private String localisation;

    public Materiel() {}

    public Materiel(Long id, String nom, String numeroSerie, String categorie, String etat, String localisation) {
        this.id = id;
        this.nom = nom;
        this.numeroSerie = numeroSerie;
        this.categorie = categorie;
        this.etat = etat;
        this.localisation = localisation;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getNumeroSerie() { return numeroSerie; }
    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }
    public String getLocalisation() { return localisation; }
    public void setLocalisation(String localisation) { this.localisation = localisation; }
}