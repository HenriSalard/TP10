package main;

import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import model.*;

public class CreateData {
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

        tr.commit();
        session.close();
    }
}
