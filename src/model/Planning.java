package model;


import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Time;

@Entity
@Table(name = "Planning")
public class Planning {
    @EmbeddedId
    private PlanningId idPlan = new PlanningId();

    @Column(name = "startHour")
    private Time startHour;

    @Column(name = "endHour")
    private Time endHour;

    public PlanningId getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(PlanningId idPlan) {
        this.idPlan = idPlan;
    }

    public Time getEndHour() {
        return endHour;
    }

    public void setEndHour(Time endHour) {
        this.endHour = endHour;
    }

    public Time getStartHour() {
        return startHour;
    }

    public void setStartHour(Time startHour) {
        this.startHour = startHour;
    }
}
