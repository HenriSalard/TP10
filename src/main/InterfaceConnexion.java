package main;

import main.HibernateUtil;
import model.TypeAnalyse;
import model.User;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfaceConnexion extends JFrame {

    private static final long serialVersionUID = 1L;

    public InterfaceConnexion(SessionFactory sessFact) {
        setTitle("Connexion");

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelBienvenue = new JLabel("Bienvenue sur l'application du laboratoire d'analyses.");
        JTextField TFnumSecu = new JTextField("Votre numéro de sécurité sociale");
        JTextField TFpassword = new JTextField("Votre mot de passe");
        JButton buttonValider = new JButton("Valider");
        JLabel labelError = new JLabel("");
        labelError.setForeground(Color.red);
        JButton ButtonCreerCompte = new JButton("Creer un nouveau compte");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(labelBienvenue, gbc);

        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        mainPanel.add(TFnumSecu, gbc);

        gbc.gridy = 2;
        mainPanel.add(TFpassword, gbc);

        gbc.gridy = 3;
        mainPanel.add(buttonValider, gbc);

        gbc.gridy = 3;
        mainPanel.add(labelError, gbc);

        gbc.gridy = 5;
        mainPanel.add(ButtonCreerCompte, gbc);






        User selectedUser = null;

        buttonValider.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new InterfaceListeVisites(sessFact, selectedUser);
            }
        });




        add(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(400, 300); // Taille ajustée à 400x300 pixels
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
        new InterfaceConnexion(sessFact);
    }
}