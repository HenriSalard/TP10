package model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serial;
import java.io.Serializable;

@Embeddable
public class AutorisationId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "idMedecin")
    private Medecin autoMed;

    @ManyToOne
    @JoinColumn(name = "idAnalyse")
    private TypeAnalyse autoType;

    public AutorisationId(Medecin med, TypeAnalyse type) {
        autoMed=med;
        autoType=type;
    }

    public AutorisationId() {

    }

    public Medecin getAutoMed() {
        return autoMed;
    }

    public void setAutoMed(Medecin autoMed) {
        this.autoMed = autoMed;
    }

    public TypeAnalyse getAutoType() {
        return autoType;
    }

    public void setAutoType(TypeAnalyse autoType) {
        this.autoType = autoType;
    }
}
