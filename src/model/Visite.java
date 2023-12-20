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
    private String resultat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Utilisateur_numSecuriteSociale")
    private User fk_User;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="Medecin_numSecuriteSociale")
    private User fk_Med;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="typeAnalyse_labelType")
    private User fk_Type;
}

