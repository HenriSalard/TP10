package model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Autorisation")
public class Autorisation {
    @EmbeddedId
    private AutorisationId idAuto = new AutorisationId();

    public AutorisationId getIdAuto() {
        return idAuto;
    }

    public void setIdAuto(AutorisationId idAuto) {
        this.idAuto = idAuto;
    }
}
