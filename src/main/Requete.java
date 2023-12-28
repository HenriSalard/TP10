package main;

import model.Medecin;
import model.TypeAnalyse;
import model.Visite;
import net.bytebuddy.asm.Advice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Requete {


    public static List<Object[]> FindMedAut(SessionFactory sessFact, TypeAnalyse type){

        Session session = sessFact.getCurrentSession();
        org.hibernate.Transaction tr = session.beginTransaction();

        Query<Object[]> query = session.createQuery("SELECT M.numSecuriteSociale "
                + "FROM Autorisation A INNER JOIN A.idAuto.autoMed M INNER JOIN A.idAuto.autoType T "
                + "WHERE  idType=: typA",Object[].class);
        query.setParameter("typA", type.getIdType());

        List<Object[]> lisIdMed = query.getResultList();

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
}
