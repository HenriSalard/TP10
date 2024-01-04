package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Utilisateur")
public class Utilisateur {

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

    public long getIdUser() {
        return numSecuriteSociale;
    }

    public void setIdUser(long idUser) {
        this.numSecuriteSociale = idUser;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

}
