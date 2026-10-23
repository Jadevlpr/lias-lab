package ma.fsbm.lias.model;

import java.sql.Timestamp;

public class DemandeAdhesion {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String etablissement;
    private String motivation;
    private String cvPath;
    private String statut; // EN_ATTENTE, ACCEPTEE, REFUSEE
    private Timestamp dateDemande;
    private Long decisionPar;
    private String decisionParNom;
    private Timestamp dateDecision;

    public DemandeAdhesion() {}

    public DemandeAdhesion(Long id, String nom, String prenom, String email, String etablissement,
                           String motivation, String cvPath, String statut, Timestamp dateDemande,
                           Long decisionPar, Timestamp dateDecision) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.etablissement = etablissement;
        this.motivation = motivation;
        this.cvPath = cvPath;
        this.statut = statut;
        this.dateDemande = dateDemande;
        this.decisionPar = decisionPar;
        this.dateDecision = dateDecision;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEtablissement() { return etablissement; }
    public void setEtablissement(String etablissement) { this.etablissement = etablissement; }

    public String getMotivation() { return motivation; }
    public void setMotivation(String motivation) { this.motivation = motivation; }

    public String getCvPath() { return cvPath; }
    public void setCvPath(String cvPath) { this.cvPath = cvPath; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public Timestamp getDateDemande() { return dateDemande; }
    public void setDateDemande(Timestamp dateDemande) { this.dateDemande = dateDemande; }

    public Long getDecisionPar() { return decisionPar; }
    public void setDecisionPar(Long decisionPar) { this.decisionPar = decisionPar; }

    public String getDecisionParNom() { return decisionParNom; }
    public void setDecisionParNom(String decisionParNom) { this.decisionParNom = decisionParNom; }

    public Timestamp getDateDecision() { return dateDecision; }
    public void setDateDecision(Timestamp dateDecision) { this.dateDecision = dateDecision; }
}
