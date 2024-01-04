package main;

import model.Utilisateur;
import org.hibernate.SessionFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InterfaceConnexion extends JFrame {

    private static final long serialVersionUID = 1L;

    public InterfaceConnexion(SessionFactory sessFact) {
        setTitle("Connexion");

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelBienvenue = new JLabel("Bienvenue sur l'application du laboratoire d'analyses.");
        JTextField TFnumSecu = new JTextField("Votre numéro de sécurité sociale", 18);
        JTextField TFpassword = new JTextField("Votre mot de passe", 18);
        JButton buttonValider = new JButton("Valider");
        JLabel labelError = new JLabel("");
        labelError.setForeground(Color.red);
        JButton buttonCreerCompte = new JButton("Creer un nouveau compte");

       TFnumSecu.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (TFnumSecu.getText().equals("Votre numéro de sécurité sociale")) {
                    TFnumSecu.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (TFnumSecu.getText().isEmpty()) {
                    TFnumSecu.setText("Votre numéro de sécurité sociale");
                }
            }
        });
        TFpassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (TFpassword.getText().equals("Votre mot de passe")) {
                    TFpassword.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (TFpassword.getText().isEmpty()) {
                    TFpassword.setText("Votre mot de passe");
                }
            }
        });

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
        mainPanel.add(labelError, gbc);

        gbc.gridy = 4;
        mainPanel.add(buttonValider, gbc);

        gbc.gridy = 5;
        mainPanel.add(buttonCreerCompte, gbc);


        buttonValider.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                // Recuperation du numero de securite sociale
                long numeroSecu = Long.parseLong(TFnumSecu.getText());


                // Recuperation de l'utilisateur
                Utilisateur selectedUtilisateur = Requete.UserFromNumSec(sessFact, numeroSecu);

                if (selectedUtilisateur == null){
                    labelError.setText("Erreur, cet utilisateur n'existe pas");
                }
                else{

                    // On verifie le mot de passe
                    if(selectedUtilisateur.getMotDePasse().equals(TFpassword.getText())){
                        System.out.println(selectedUtilisateur.getPrenom());
                        new InterfaceListeVisites(sessFact, selectedUtilisateur);
                        dispose();
                    }
                    else{
                        labelError.setText("Mot de passse incorect");
                    }

                }



            }
        });

        buttonCreerCompte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new InterfaceNouvelUtilisateur(sessFact);
                dispose();
            }
        });

        // code pour que le textField n'accepte que des chiffre
        TFnumSecu.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                char keyPressed = ke.getKeyChar();
                if (Character.isDigit(keyPressed) || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    TFnumSecu.setEditable(true);
                    labelError.setText("");
                }else {
                    TFnumSecu.setEditable(false);
                    labelError.setText(" Entrez seulement des chiffre ");

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

    public static void main(String[] args) {
        SessionFactory sessFact = HibernateUtil.getSessionFactory();
        new InterfaceConnexion(sessFact);
    }
}