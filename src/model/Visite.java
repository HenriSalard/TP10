package model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Visite")
public class Visite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVisite")
    private int idVisite;

    @Column(name = "dateAnalyse")
    private LocalDateTime dateAnalyse;

    @Column(name = "resultat",columnDefinition = "TEXT")
    private String resultat = "Pas encore de résultat";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Utilisateur_numSecuriteSociale")
    private Utilisateur fk_Utilisateur;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Medecin_numSecuriteSociale")
    private Medecin fk_Med;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="typeAnalyse_labelType")
    private TypeAnalyse fk_Type;

    public int getIdVisite() {
        return idVisite;
    }

    public void setIdVisite(int idVisite) {
        this.idVisite = idVisite;
    }

    public LocalDateTime getDateAnalyse() {
        return dateAnalyse;
    }

    public void setDateAnalyse(LocalDateTime dateAnalyse) {
        this.dateAnalyse = dateAnalyse;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public Utilisateur getFk_User() {
        return fk_Utilisateur;
    }

    public void setFk_User(Utilisateur fk_Utilisateur) {
        this.fk_Utilisateur = fk_Utilisateur;
    }

    public Medecin getFk_Med() {
        return fk_Med;
    }

    public void setFk_Med(Medecin fk_Med) {
        this.fk_Med = fk_Med;
    }

    public TypeAnalyse getFk_Type() {
        return fk_Type;
    }

    public void setFk_Type(TypeAnalyse fk_Type) {
        this.fk_Type = fk_Type;
    }
}

