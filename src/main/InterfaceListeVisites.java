package main;

import model.User;
import model.Visite;
import org.hibernate.SessionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InterfaceListeVisites extends JFrame {


    public InterfaceListeVisites(SessionFactory sessFact, User selectedUser) {
        setTitle("Liste des visites");

        List<Visite> listeVisites = Requete.VisFromUser(sessFact, selectedUser);

        JPanel mainPanel = new JPanel(new BorderLayout());

        String[] columns = {"Date", "Type d'analyse", "Resultat"};
        Object[][] data = new Object[listeVisites.size()][3];

        for (int i = 0; i < listeVisites.size(); i++) {
            Visite visite = listeVisites.get(i);
            data[i][0] = visite.getDateAnalyse();
            data[i][1] = visite.getFk_Type().getLabelType();
            data[i][2] = visite.getResultat();
        }

        JTable table = new JTable(new DefaultTableModel(data, columns));
        JScrollPane scrollPane = new JScrollPane(table);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(600, 400); // Taille ajustée à 600x400 pixels
        setLocationRelativeTo(null);
        setVisible(true);


    }
}
