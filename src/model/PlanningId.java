package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serial;
import java.io.Serializable;
import java.time.DayOfWeek;

@Embeddable
public class PlanningId implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "idMedecin")
    private Medecin planMed;

    @Column(name = "Jour")
    private DayOfWeek planJour;

    public PlanningId(Medecin med, DayOfWeek dayOfWeek) {
        planMed = med;
        planJour = dayOfWeek;
    }

    public PlanningId() {

    }

    public Medecin getPlanMed() {
        return planMed;
    }

    public void setPlanMed(Medecin planMed) {
        this.planMed = planMed;
    }

    public DayOfWeek getPlanJour() {
        return planJour;
    }

    public void setPlanJour(DayOfWeek planJour) {
        this.planJour = planJour;
    }
}
