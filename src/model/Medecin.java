package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Medecin")
public class Medecin {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numSecuriteSociale")
    private long numSecuriteSociale;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "motDePasse")
    private String motDePasse;

    @Column(name = "salaire")
    private double salaire;

    public Medecin(long numsecu,String nom,String prenom,double salaire)
    {
        this.nom=nom;
        this.prenom=prenom;
        this.numSecuriteSociale=numsecu;
        this.motDePasse="automatic_fake_password";
        this.salaire=salaire;
    }

    public Medecin(){}

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public long getIdMed() {
        return numSecuriteSociale;
    }

    public void setIdMed(long idMed) {
        this.numSecuriteSociale = idMed;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }
}
