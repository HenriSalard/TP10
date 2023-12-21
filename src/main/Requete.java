package main;

import model.Medecin;
import model.TypeAnalyse;
import net.bytebuddy.asm.Advice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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

    public static List<DayOfWeek> FindRDVMed(SessionFactory sessFact, long idMed){

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
}
