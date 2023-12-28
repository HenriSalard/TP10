package main;

import java.sql.SQLException;
import java.sql.Time;
import java.time.DayOfWeek;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.*;

import static main.Requete.*;

public class CreateData {
    @SuppressWarnings("deprecation")
    public static void FillTable(SessionFactory sessFact) throws SQLException {

        Session session = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = session.beginTransaction();

        //Utilisateur
        User user1 = new User();
        user1.setIdUser(100000000001L);
        user1.setNom("QUINIOU");
        user1.setPrenom("Baptiste");
        user1.setMotDePasse("1234");
        session.save(user1);

        User user2 = new User();
        user2.setIdUser(100000000002L);
        user2.setNom("SALARD");
        user2.setPrenom("Henri");
        user2.setMotDePasse("1234");
        session.save(user2);

        User user3 = new User();
        user3.setIdUser(200000000001L);
        user3.setNom("ONYME");
        user3.setPrenom("Anne");
        user3.setMotDePasse("1234");
        session.save(user3);

        //Medecin
        Medecin med1 = new Medecin();
        med1.setIdMed(100000000003L);
        med1.setNom("BRETON");
        med1.setPrenom("Nathalie");
        med1.setMotDePasse("0000");
        med1.setSalaire(48000);
        session.save(med1);

        Medecin med2 = new Medecin();
        med2.setIdMed(200000000002L);
        med2.setNom("BUCA");
        med2.setPrenom("Paul");
        med2.setMotDePasse("0000");
        med2.setSalaire(46000);
        session.save(med2);

        //Type Analyse
        TypeAnalyse type1 = new TypeAnalyse();
        type1.setLabelType("Sang");
        type1.setDuree(20);
        session.save(type1);

        TypeAnalyse type2 = new TypeAnalyse();
        type2.setLabelType("VIH");
        type2.setDuree(30);
        session.save(type2);

        TypeAnalyse type3 = new TypeAnalyse();
        type3.setLabelType("Diabète");
        type3.setDuree(40);
        session.save(type3);

        //Autorisation
        Autorisation auto1 = new Autorisation();
        auto1.setIdAuto(new AutorisationId(med1,type2));
        session.save(auto1);

        Autorisation auto2 = new Autorisation();
        auto2.setIdAuto(new AutorisationId(med2,type2));
        session.save(auto2);

        Autorisation auto3 = new Autorisation();
        auto3.setIdAuto(new AutorisationId(med1,type3));
        session.save(auto3);

        Autorisation auto4 = new Autorisation();
        auto4.setIdAuto(new AutorisationId(med2,type1));
        session.save(auto4);

        //Planning
        Planning plan1 = new Planning();
        plan1.setIdPlan(new PlanningId(med1, DayOfWeek.MONDAY));
        plan1.setStartHour(new Time(8,0,0));
        plan1.setEndHour(new Time(16,0,0));
        session.save(plan1);

        Planning plan2 = new Planning();
        plan2.setIdPlan(new PlanningId(med1, DayOfWeek.WEDNESDAY));
        plan2.setStartHour(new Time(10,0,0));
        plan2.setEndHour(new Time(18,0,0));
        session.save(plan2);

        Planning plan3 = new Planning();
        plan3.setIdPlan(new PlanningId(med1, DayOfWeek.THURSDAY));
        plan3.setStartHour(new Time(8,0,0));
        plan3.setEndHour(new Time(12,0,0));
        session.save(plan3);

        Planning plan4 = new Planning();
        plan4.setIdPlan(new PlanningId(med2, DayOfWeek.MONDAY));
        plan4.setStartHour(new Time(9,0,0));
        plan4.setEndHour(new Time(17,0,0));
        session.save(plan4);

        Planning plan5 = new Planning();
        plan5.setIdPlan(new PlanningId(med2, DayOfWeek.FRIDAY));
        plan5.setStartHour(new Time(13,0,0));
        plan5.setEndHour(new Time(18,0,0));
        session.save(plan5);



        tr.commit();
        session.close();

        for(Object i : FindMedAut(sessFact,type2))
        {
            System.out.println((long)i);
            for(DayOfWeek d : FindDayMed(sessFact,(long)i)){
                System.out.println(d.toString());
                System.out.println("Temps total du médecin i au jour d : " +FindTotalTimeVisMedDay(sessFact,(long)i,d));
            }
        }
    }
}
