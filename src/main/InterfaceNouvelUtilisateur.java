package main;

import model.Utilisateur;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfaceNouvelUtilisateur extends JFrame {

    private static final long serialVersionUID = 1L;

    public InterfaceNouvelUtilisateur(SessionFactory sessFact) {

        setTitle("Nouvel utilisateur");

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelConsignes = new JLabel("Entrez vos informations afin de créer votre nouveau compte :");
        JLabel labelNum = new JLabel("Votre numero de securité sociale");
        JLabel labelPrenom = new JLabel("Votre Prenom");
        JLabel labelNom = new JLabel("Votre nom");
        JLabel labelMotDePasse = new JLabel("Votre mot de passe");
        JLabel labelError = new JLabel("");
        labelError.setForeground(Color.red);

        JTextField TFNum = new JTextField(14);
        JTextField TFPrenom = new JTextField(14);
        JTextField TFNom = new JTextField(14);
        JTextField TFMotDePasse = new JTextField(14);

        JButton buttonValider = new JButton("Valider");


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(labelConsignes, gbc);


        gbc.gridy = 1;
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(labelNum, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(TFNum, gbc);

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(labelPrenom, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(TFPrenom, gbc);

        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(labelNom, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(TFNom, gbc);

        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(labelMotDePasse, gbc);

        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(TFMotDePasse, gbc);

        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonValider, gbc);

        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(labelError, gbc);

        // CREATION DE L'UTILISATEUR

        buttonValider.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                    if(TFNum.getText().isEmpty() || TFNom.getText().isEmpty() || TFPrenom.getText().isEmpty() || TFMotDePasse.getText().isEmpty()){
                        labelError.setText("Veuillez remplir tous les champs.");

                    }
                    else{
                        // Recuperation des infos
                        long numeroSecu = Long.parseLong(TFNum.getText());
                        String prenom = TFPrenom.getText();
                        String nom = TFNom.getText();
                        String motDePasse = TFMotDePasse.getText();


                        // Creation de l'utilisateur


                    }

                }
        });


        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(400, 300); // Taille ajustée à 400x300 pixels
        setLocationRelativeTo(null);
        setVisible(true);

    }


}
