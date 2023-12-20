package model;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Planning")
public class Planning {
    @EmbeddedId
    private PlanningId idPlan = new PlanningId();

    public PlanningId getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(PlanningId idPlan) {
        this.idPlan = idPlan;
    }
}
