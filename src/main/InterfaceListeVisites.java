package main;

import model.Utilisateur;
import model.Visite;
import org.hibernate.SessionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class InterfaceListeVisites extends JFrame {


    public InterfaceListeVisites(SessionFactory sessFact, Utilisateur selectedUtilisateur) {
        setTitle("Liste des visites");

        List<Visite> listeVisites = Requete.VisFromUser(sessFact, selectedUtilisateur);

        JLabel labelBonjour = new JLabel("Bonjour " + selectedUtilisateur.getPrenom() + " "
                + selectedUtilisateur.getNom() + " voici vos analyses passées ou à venir.");

        labelBonjour.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel mainPanel = new JPanel(new BorderLayout());

        String[] columns = {"Date","Heure", "Type d'analyse", "Resultat"};
        Object[][] data = new Object[listeVisites.size()][4];

        for (int i = 0; i < listeVisites.size(); i++) {
            Visite visite = listeVisites.get(i);
            data[i][0] = visite.getDateAnalyse().toLocalDate();
            data[i][1] = visite.getDateAnalyse().toLocalTime().truncatedTo(ChronoUnit.MINUTES);
            data[i][2] = visite.getFk_Type().getLabelType();
            data[i][3] = visite.getResultat();
        }

        JTable table = new JTable(new DefaultTableModel(data, columns));
        JScrollPane scrollPane = new JScrollPane(table);


        //PARTIE POUR LA DECONNEXION
        JButton decoButton = new JButton("Déconnexion");
        decoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new InterfaceConnexion(sessFact);
                dispose();// Ferme la fenêtre actuelle après ouverture de la nouvelle interface
            }
        });


        // PARTIE POUR LE BOUTTON VERS DEMANDE DE VISITE

        JButton buttonDemandeVisite = new JButton("Faire une nouvelle demande d'analyse");
        buttonDemandeVisite.addActionListener(e -> {
            new InterfaceDemandeVisite(sessFact, selectedUtilisateur);
            dispose(); // Ferme la fenêtre actuelle après ouverture de la nouvelle interface
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(buttonDemandeVisite);
        buttonPanel.add(decoButton);

        //ajout des elements au mainPanel

        mainPanel.add(labelBonjour, BorderLayout.NORTH); // Ajoute le label en haut
        mainPanel.add(scrollPane, BorderLayout.CENTER); // Ajoute le tableau au centre
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); // Ajoute le boutton en bas


        add(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(600, 400); // Taille ajustée à 600x400 pixels
        setLocationRelativeTo(null);
        setVisible(true);


    }

    public static void main(String[] args) {
        SessionFactory sessFact = HibernateUtil.getSessionFactory();

        Utilisateur selectedUser = Requete.UserFromNumSec(sessFact, 100000000001L);

        new InterfaceListeVisites(sessFact, selectedUser);
    }
}
