package ma.fsbm.lias.model;

public class Equipe {
    private Long id;
    private String nom;
    private String acronyme;
    private String description;
    private Long chefId; // ID du membre responsable
    private String chefNomComplet; // Utilisé pour l'affichage

    public Equipe() {}

    public Equipe(Long id, String nom, String acronyme, String description, Long chefId) {
        this.id = id;
        this.nom = nom;
        this.acronyme = acronyme;
        this.description = description;
        this.chefId = chefId;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getAcronyme() { return acronyme; }
    public void setAcronyme(String acronyme) { this.acronyme = acronyme; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Long getChefId() { return chefId; }
    public void setChefId(Long chefId) { this.chefId = chefId; }
    public String getChefNomComplet() { return chefNomComplet; }
    public void setChefNomComplet(String chefNomComplet) { this.chefNomComplet = chefNomComplet; }
}