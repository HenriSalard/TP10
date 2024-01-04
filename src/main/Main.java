package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.hibernate.SessionFactory;

public class Main {

    //On crée et remplit les tables si elles l'ont pas été.

    public static void main(String[] args) {
        try {

            SessionFactory sessFact = HibernateUtil.getSessionFactory();


            new InterfaceConnexion(sessFact);


        } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation");
            e.printStackTrace();
        }


    }

}
