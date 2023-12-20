package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.hibernate.SessionFactory;

public class Main {

    //On crée et remplit les tables si elles l'ont pas été.

    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/tp10", "root", "password");
            Statement statement = connection.createStatement();

            statement.execute("DROP DATABASE TP10");
            statement.execute("CREATE DATABASE TP10");

            statement.close();
            connection.close();

            SessionFactory sessFact = HibernateUtil.getSessionFactory();

            sessFact.close();


        } catch (Exception e) {
            System.out.println("Erreur lors de l'initialisation");
            e.printStackTrace();
        }


    }

}
