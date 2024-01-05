package main;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import jdk.jshell.execution.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.*;

public class CreateData {
    @SuppressWarnings("deprecation")
    public static void FillTable(SessionFactory sessFact) throws SQLException {

        Session session = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = session.beginTransaction();

        //Utilisateur
        Utilisateur utilisateur1 = new Utilisateur();
        utilisateur1.setIdUser(100000000001L);
        utilisateur1.setNom("QUINIOU");
        utilisateur1.setPrenom("Baptiste");
        utilisateur1.setMotDePasse("1234");
        session.save(utilisateur1);

        Utilisateur utilisateur2 = new Utilisateur();
        utilisateur2.setIdUser(100000000002L);
        utilisateur2.setNom("SALARD");
        utilisateur2.setPrenom("Henri");
        utilisateur2.setMotDePasse("1234");
        session.save(utilisateur2);

        Utilisateur utilisateur3 = new Utilisateur();
        utilisateur3.setIdUser(200000000001L);
        utilisateur3.setNom("ONYME");
        utilisateur3.setPrenom("Anne");
        utilisateur3.setMotDePasse("1234");
        session.save(utilisateur3);

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
        type1.setLabelType("Groupe sanguin");
        type1.setDuree(20);
        session.save(type1);

        TypeAnalyse type2 = new TypeAnalyse();
        type2.setLabelType("Hémogramme");
        type2.setDuree(30);
        session.save(type2);

        TypeAnalyse type3 = new TypeAnalyse();
        type3.setLabelType("Vitesse de sédimentation");
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

        Visite vis = new Visite();
        vis.setFk_User(utilisateur1);
        vis.setFk_Type(type2);
        vis.setFk_Med(med1);
        vis.setDateAnalyse(LocalDateTime.of(2024, Month.JANUARY,8, 10,0));
        session.save(vis);

        tr.commit();
        session.close();


        //System.out.println(Requete.UserFromNumSec(sessFact,1000000001L));

        //for (LocalDateTime loc : Requete.DateRDVForMedDuration(sessFact,med1,30)) System.out.println(loc.toString());

        if(Requete.AddUsertoDB(sessFact,100000000003L,"TORTEVOIS", "Evan","password")){
            Utilisateur user0 = Requete.UserFromNumSec(sessFact,100000000003L);
            assert user0 != null;
            System.out.println(user0.getNom());
        }
        else {
            System.out.println("Erreur déjà existant");
        }

    }


    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/tp10", "root", "password");
            Statement statement = connection.createStatement();

            statement.execute("DROP DATABASE TP10");
            statement.execute("CREATE DATABASE TP10");

            statement.close();
            connection.close();

            SessionFactory sessFact = HibernateUtil.getSessionFactory();

            CreateData.FillTable(sessFact);

            sessFact.close();

        } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation");
            e.printStackTrace();
        }


    }

}
