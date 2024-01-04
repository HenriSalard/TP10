// ETUDIANTS: Henri SALARD, Baptiste QUINIOU

package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.time.DayOfWeek;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Medecin;
import model.TypeAnalyse;
import model.Utilisateur;
import org.hibernate.SessionFactory;



public class InterfaceDemandeVisite extends JFrame {
    private JPanel cardPanel;
    private JButton centerButton;
    private JComboBox<String> medecinDayComboBox;

    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;

    private JTextField tfCB;


    private static final long serialVersionUID = 1L;

    public InterfaceDemandeVisite(SessionFactory sessFact, Utilisateur selectedUtilisateur) {
        setTitle("Prise de rendez-vous");

        topPanel = createTopPanel(sessFact);
        centerPanel = new JPanel(new GridBagLayout());
        bottomPanel = new JPanel(new GridBagLayout());

        centerButton = new JButton();

        setLayout(new BorderLayout());

        // Ajout des panels à la fenêtre principale avec des positions spécifiques
        add(topPanel, BorderLayout.NORTH); // En haut
        add(centerPanel, BorderLayout.CENTER); // Au centre
        add(bottomPanel, BorderLayout.SOUTH); // En bas


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private JPanel createTopPanel(SessionFactory sessFact) {
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        List<TypeAnalyse> types = Requete.AllType(sessFact);

        String[] labelsAnalyzes = new String[types.size()];

        for (int i = 0; i < types.size(); i++) {
            labelsAnalyzes[i] = types.get(i).getLabelType();
        }



        JLabel labelBienvenue = new JLabel("Bienvenue sur la plateforme du laboratoire d'analyses.");
        JLabel labelConsignes = new JLabel("Sélectionnez le type d'analyse qui vous intéresse");
        JComboBox<String> comboBox = new JComboBox<>(labelsAnalyzes);

        JButton submit = new JButton("Valider");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        topPanel.add(labelBienvenue, gbc);

        gbc.gridy = 1;
        topPanel.add(labelConsignes, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        topPanel.add(comboBox, gbc);


        gbc.gridy = 3;
        topPanel.add(submit, gbc);

        submit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int selectedIndex = comboBox.getSelectedIndex();
                TypeAnalyse selectedType = types.get(selectedIndex);
                updateCenterPanel(sessFact, selectedType);
            }
        });

        return topPanel;
    }


    private void updateCenterPanel(SessionFactory sessFact, TypeAnalyse selectedType) {

        JLabel centerLabel = new JLabel("Sélectionnez un créneau disponible pour le type d'analyse  " + selectedType.getLabelType());

        List<Medecin> lisMed = Requete.FindMedAut(sessFact, selectedType);
        DayOfWeek[] lisDaysOfWeek = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY};
        medecinDayComboBox = new JComboBox<>();

        for (Medecin med : lisMed){
            for (DayOfWeek day : lisDaysOfWeek){
                String comboLabel = "Docteur " + med.getPrenom() + " " + med.getNom() + " le " + GetFrenchDay.getFrenchDay(day);
                medecinDayComboBox.addItem(comboLabel);
            }
        }

        centerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                // On recupere les données selectionnées
                String selectedComboLabel = (String) medecinDayComboBox.getSelectedItem();
                String[] parts = selectedComboLabel.split(" ");
                String selectedMedecinFirstName = parts[1];
                String selectedMedecinSurname = parts[2];
                String selectedDayOfWeekString = parts[4];

                DayOfWeek selectedDayOfWeek = GetFrenchDay.convertToDayOfWeek(selectedDayOfWeekString);

                Medecin selectedMedecin = null;
                for (Medecin med : lisMed) {
                    // si meme nom et prenom alors c'est ce medecin la
                    if (med.getNom().equals(selectedMedecinSurname) && med.getPrenom().equals(selectedMedecinFirstName)) {
                        selectedMedecin = med;
                        break;
                    }
                }


                updateBottomPanel(sessFact, selectedMedecin, selectedDayOfWeek, selectedType);
            }
        });

        centerPanel.removeAll();

        GridBagConstraints gbcCenter = new GridBagConstraints();
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 0;
        gbcCenter.anchor = GridBagConstraints.CENTER;
        gbcCenter.insets = new Insets(5, 5, 5, 5);

        centerPanel.add(centerLabel, gbcCenter);

        gbcCenter.gridy = 1;
        centerPanel.add(medecinDayComboBox, gbcCenter);

        gbcCenter.gridy = 2;
        centerButton.setText("Valider"); // Mettre à jour le texte du bouton
        centerPanel.add(centerButton, gbcCenter);


        centerPanel.revalidate();
        centerPanel.repaint();



    }


    private void updateBottomPanel(SessionFactory sessFact, Medecin selecterMed, DayOfWeek selectedDayOfWeek, TypeAnalyse selectedTypeAnalyse) {

        JLabel bottomLabel = new JLabel("Veuilez saisir vos données bancaires :");
        JButton bottomButton = new JButton("Valider");
        JLabel errorLabel = new JLabel();
        errorLabel.setForeground(Color.red);
        tfCB = new JTextField(16);

        GridBagConstraints gbcBottom = new GridBagConstraints();
        gbcBottom.anchor = GridBagConstraints.CENTER;
        gbcBottom.insets = new Insets(5, 5, 5, 5);

        gbcBottom.gridx = 0;
        gbcBottom.gridy = 0;
        gbcBottom.gridwidth = 1;
        bottomPanel.removeAll();
        bottomPanel.add(bottomLabel, gbcBottom);

        gbcBottom.gridy = 1;
        bottomPanel.add(tfCB, gbcBottom);

        gbcBottom.gridy = 2;
        bottomPanel.add(bottomButton, gbcBottom);

        gbcBottom.gridy = 3;
        bottomPanel.add(errorLabel, gbcBottom);

        // code pour que le textField n'accepte que des chiffre
        tfCB.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                char keyPressed = ke.getKeyChar();

                if (Character.isDigit(keyPressed) || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                    tfCB.setEditable(true);
                    errorLabel.setText("");
                }else {
                    tfCB.setEditable(false);
                    errorLabel.setText(" Entrez seulement des chiffre ");

                }
            }
                            });

        /*bottomButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Requete.AddVisite(sessFact, selectedTypeAnalyse, selecterMed, )
            }
                                       }*/


        bottomPanel.revalidate();
        bottomPanel.repaint();

    }


    public static void main(String[] args){

        SessionFactory sessFact = HibernateUtil.getSessionFactory();

        Utilisateur selectedUtilisateur = new Utilisateur();

        new InterfaceDemandeVisite(sessFact, selectedUtilisateur);

    }

}