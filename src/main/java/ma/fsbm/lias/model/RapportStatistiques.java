package ma.fsbm.lias.model;

public class RapportStatistiques {
    private int annee;
    private int totalMembres;
    private int totalEquipes;
    private int totalPublications;
    private int totalEvenements;
    private int totalMateriels;

    public RapportStatistiques() {}

    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }
    public int getTotalMembres() { return totalMembres; }
    public void setTotalMembres(int totalMembres) { this.totalMembres = totalMembres; }
    public int getTotalEquipes() { return totalEquipes; }
    public void setTotalEquipes(int totalEquipes) { this.totalEquipes = totalEquipes; }
    public int getTotalPublications() { return totalPublications; }
    public void setTotalPublications(int totalPublications) { this.totalPublications = totalPublications; }
    public int getTotalEvenements() { return totalEvenements; }
    public void setTotalEvenements(int totalEvenements) { this.totalEvenements = totalEvenements; }
    public int getTotalMateriels() { return totalMateriels; }
    public void setTotalMateriels(int totalMateriels) { this.totalMateriels = totalMateriels; }
}