package main;

import org.hibernate.SessionFactory;

import javax.swing.*;

public class InterfaceNewUser extends JFrame {

    private static final long serialVersionUID = 1L;

    public InterfaceNewUser(SessionFactory sessFact) {







        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(400, 300); // Taille ajustée à 400x300 pixels
        setLocationRelativeTo(null);
        setVisible(true);

    }


}
