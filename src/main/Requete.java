package main;

import model.Medecin;
import model.TypeAnalyse;
import model.Utilisateur;
import model.Visite;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static long FindTotalTimeVisMedDate(SessionFactory sessFact, long idMed, LocalDate date){

        //Retourne le temps total des visites d'un médecin à un date donnée
        //Renvoie -1 si pas de visites ce jour-là

        Session session = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = session.beginTransaction();

        //VERIFIE QUE VISITE N'EST PAS VIDE
        Query<Long> querycount = session.createQuery("SELECT COUNT(*) FROM Visite", Long.class);
        if(querycount.getSingleResult()>0){
            Query query = session.createQuery("SELECT SUM(T.duree) "
                    + "FROM Visite V INNER JOIN V.fk_Med M join V.fk_Type T "
                    + "WHERE  M.numSecuriteSociale =: idMed "
                    + "AND YEAR(V.dateAnalyse) =: dateYear "
                    + "AND MONTH(V.dateAnalyse) =: dateMonth "
                    + "AND DAYOFMONTH(V.dateAnalyse) =: dateDay ");
            query.setParameter("idMed", idMed);
            query.setParameter("dateYear", date.getYear());
            query.setParameter("dateMonth", date.getMonth().getValue());
            query.setParameter("dateDay", date.getDayOfMonth());

            if(query.getSingleResult() != null){
                long time = (long)query.getSingleResult();
                tr.rollback();
                session.close();
                return time;
            }
        }
        else{
            System.out.println("Pas de visite passé ou à venir.");
        }
        tr.rollback();
        session.close();
        return 0;
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
    public static List<Visite> VisFromUser(SessionFactory sessFact, Utilisateur utilisateur){

        Session session = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = session.beginTransaction();

        Query<Visite> query = session.createQuery("FROM Visite " +
                "WHERE fk_Utilisateur =: userParam", Visite.class);
        query.setParameter("userParam", utilisateur);

        List<Visite> lisVis = query.getResultList();

        tr.rollback();
        session.close();
        return lisVis;
    }

    public static boolean AddVisite(SessionFactory sessFact, TypeAnalyse type, Medecin med, Utilisateur patient, LocalDateTime day_time){
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

    public static Utilisateur UserFromNumSec(SessionFactory sessFact, long numSec){
        Session session = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = session.beginTransaction();

        Query<Utilisateur> query = session.createQuery("FROM Utilisateur " +
                "WHERE numSecuriteSociale =: userParam", Utilisateur.class);
        query.setParameter("userParam",numSec);

        try{
            Utilisateur utilisateur = query.getSingleResult();
            tr.rollback();
            session.close();
            return utilisateur;
        }
        catch(NoResultException exc){
            System.out.println("Pas d'utilisateur");
            tr.rollback();
            session.close();
            return null;
        }

    }

    public static ArrayList<LocalDateTime> DateRDVForMedDuration(SessionFactory sessFact, Medecin med, int duree){

        ArrayList<LocalDateTime> lisDayRdDV = new ArrayList<LocalDateTime>();

        //On regarde a partir de demain
        LocalDateTime dayRDV = LocalDateTime.now();
        int iSemaine = 0;
        //Tant que l'on a pas trouvé le jour
        //On cherche les 6 premières dates et dans les 10 premières semaines
        while( iSemaine < 10 && lisDayRdDV.size() < 6 ){
            iSemaine++;
            //Seulement les jours où le médecin travaille
            for(DayOfWeek dayWeek : FindDayMed(sessFact,med.getIdMed())){

                //On se met à ce jour
                dayRDV = dayRDV.with(TemporalAdjusters.next(dayWeek));

                //On récupère le temps utilisé par le médecin ce jour là
                long timeused = Requete.FindTotalTimeVisMedDate(sessFact,med.getIdMed(),dayRDV.toLocalDate());

                //On récupèrele temps total de ce médecin ce jour là
                Session session = sessFact.getCurrentSession();
                org.hibernate.Transaction tr = session.beginTransaction();

                Query query = session.createQuery("SELECT endHour,startHour " +
                        "FROM Planning " +
                        "WHERE idPlan.planMed =: medParam AND idPlan.planJour =: dayParam");
                query.setParameter("medParam",med);
                query.setParameter("dayParam",dayWeek);

                Object[] list = (Object[]) query.getSingleResult();
                int startHour = ((Time)list[1]).getHours();
                int endHour = ((Time)list[0]).getHours();
                int startMin = ((Time)list[1]).getMinutes();
                int endMin = ((Time)list[0]).getMinutes();

                int minuteDay = (endHour-startHour)*60 + endMin - startMin;

                System.out.println("Temps total : "+minuteDay + " Temps utilisé : " + timeused );
                System.out.println("Duree : "+duree + " Temps restant : " + (minuteDay - timeused) );

                tr.rollback();
                session.close();

                if(minuteDay - timeused > duree) {
                    dayRDV = dayRDV.withHour(startHour);
                    dayRDV = dayRDV.withMinute(startMin);
                    dayRDV = dayRDV.plusMinutes(timeused);
                    lisDayRdDV.add(dayRDV);
                }
                if(lisDayRdDV.size() >= 6){
                    break;
                }
            }
        }
        return lisDayRdDV;
    }

    public static boolean AddUsertoDB(SessionFactory sessFact, long numSec, String nom, String prenom, String mdp){

        Session session = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = session.beginTransaction();

        Query<Utilisateur> query = session.createQuery("FROM Utilisateur " +
                "WHERE numSecuriteSociale =: userParam", Utilisateur.class);
        query.setParameter("userParam",numSec);

        try{
            Utilisateur utilisateur = query.getSingleResult();
            System.out.println("L'utilisateur existe déjà");
            tr.rollback();
            session.close();
            return false;
        }
        catch(NoResultException exc){
            System.out.println("On rajoute l'utilisateur");

            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setIdUser(numSec);
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setMotDePasse(mdp);
            session.save(utilisateur);

            tr.commit();
            session.close();
            return true;
        }
    }

}
