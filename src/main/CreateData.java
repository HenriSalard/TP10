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
        user1.setNom("QUINIOU");
        user1.setPrenom("Baptiste");
        user1.setMotDePasse("1234");
        session.save(user1);

        User user2 = new User();
        user2.setNom("SALARD");
        user2.setPrenom("Henri");
        user2.setMotDePasse("1234");
        session.save(user2);

        User user3 = new User();
        user2.setNom("ONYME");
        user2.setPrenom("Anne");
        user2.setMotDePasse("1234");
        session.save(user2);


        tr.commit();
        session.close();
    }
}
