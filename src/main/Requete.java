package main;

import model.Medecin;
import model.TypeAnalyse;
import model.User;
import model.Visite;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public class Requete {

    /**
     * Methode qui renvoie la liste des medecin pouvant realiser un type d'analyse
     * @param sessFact
     * @param type
     * @return
     */
    public static List<Medecin> FindMedAut(SessionFactory sessFact, TypeAnalyse type){

        Session session = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = session.beginTransaction();

        Query<Medecin> query = session.createQuery("SELECT new Medecin(M.numSecuriteSociale,M.nom,M.prenom,M.salaire) "
                + "FROM Autorisation A INNER JOIN A.idAuto.autoMed M INNER JOIN A.idAuto.autoType T "
                + "WHERE  idType=: typA",Medecin.class);
        query.setParameter("typA", type.getIdType());

        List<Medecin> lisIdMed = query.getResultList();

        tr.rollback();
        session.close();
        return lisIdMed;

    }

    public static List<DayOfWeek> FindDayMed(SessionFactory sessFact, long idMed){

        Session session = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = session.beginTransaction();

        Query<DayOfWeek> query = session.createQuery("SELECT P.idPlan.planJour "
                + "FROM Planning P INNER JOIN P.idPlan.planMed M "
                + "WHERE  M.numSecuriteSociale =: idMed", DayOfWeek.class);
        query.setParameter("idMed", idMed);

        List<DayOfWeek> lisDay = query.getResultList();

        tr.rollback();
        session.close();
        return lisDay;
    }

    public static Integer FindTotalTimeVisMedDay(SessionFactory sessFact, long idMed, DayOfWeek day){

        Session session = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = session.beginTransaction();

        //VERIFIE QUE VISITE N4EST PAS VIDE
        Query<Long> querycount = session.createQuery("SELECT COUNT(*) FROM Visite", Long.class);
        if(querycount.getSingleResult()>0){
            Query<Integer> query = session.createQuery("SELECT SUM(duree) "
                    + "FROM Visite V INNER JOIN V.fk_Med M "
                    + "WHERE  M.numSecuriteSociale =: idMed AND DAYNAME(V.dateAnalyse) =: dayParam", Integer.class);
            query.setParameter("idMed", idMed);
            query.setParameter("dayParam", day);

            tr.rollback();
            session.close();
            return query.getSingleResult();
        }
        else{
            System.out.println("Pas de visite passé ou à venir.");
            tr.rollback();
            session.close();
            return -1;
        }
    }

    //Renvoie tous les Types d'analyses
    public static List<TypeAnalyse> AllType(SessionFactory sessFact){

        Session session = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = session.beginTransaction();

        Query<TypeAnalyse> query = session.createQuery("FROM TypeAnalyse", TypeAnalyse.class);

        List<TypeAnalyse> lisType = query.getResultList();

        tr.rollback();
        session.close();
        return lisType;
    }

    //Renvoie les visites d'un Utilisateur
    public static List<Visite> VisFromUser(SessionFactory sessFact, User user){

        Session session = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = session.beginTransaction();

        Query<Visite> query = session.createQuery("FROM Visite " +
                "WHERE fk_user =: userParam", Visite.class);
        query.setParameter("userParam",user);

        List<Visite> lisVis = query.getResultList();

        tr.rollback();
        session.close();
        return lisVis;
    }

    public static boolean AddVisite(SessionFactory sessFact, TypeAnalyse type, Medecin med, User patient, LocalDateTime day_time){
        Session session = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = session.beginTransaction();

        //Attention on ne vérifie pas (pour l'instant) la cohérence des informations
        Visite vis = new Visite();
        vis.setDateAnalyse(day_time);
        vis.setFk_Med(med);
        vis.setFk_Type(type);
        vis.setFk_User(patient);
        session.save(vis);

        tr.commit();
        session.close();

        return true;

    }

}
