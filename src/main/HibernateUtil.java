package main;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import model.Utilisateur;

/**
 * @author Quiniou Baptiste
 * @author Salard Henri
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    static {
        try {
            Configuration conf = new Configuration().configure();
            conf.addAnnotatedClass(model.Medecin.class);
            conf.addAnnotatedClass(model.TypeAnalyse.class);
            conf.addAnnotatedClass(model.Utilisateur.class);
            conf.addAnnotatedClass(model.Autorisation.class);
            conf.addAnnotatedClass(model.AutorisationId.class);
            conf.addAnnotatedClass(model.Planning.class);
            conf.addAnnotatedClass(model.PlanningId.class);
            conf.addAnnotatedClass(model.Visite.class);
            conf.configure();
            ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
            sessionFactory = conf.buildSessionFactory(sr);
        } catch (Throwable th) {
            System.err.println("Enitial SessionFactory creation failed" + th);
            throw new ExceptionInInitializerError(th);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}