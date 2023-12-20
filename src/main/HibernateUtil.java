package main;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import model.Medecin;
import model.TypeAnalyse;
import model.User;

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
            conf.addAnnotatedClass(model.User.class);
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