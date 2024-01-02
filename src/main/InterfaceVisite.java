// ETUDIANTS: Henri SALARD, Baptiste QUINIOU

package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import org.hibernate.SessionFactory;



public class InterfaceVisite extends JFrame {
    private JPanel cardPanel;
    private JButton centerButton;

    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;

    private JTextField tfCB;


    private static final long serialVersionUID = 1L;

    public InterfaceVisite(SessionFactory sessFact) {
        setTitle("Prise de rendez-vous");

        topPanel = createTopPanel();
        centerPanel = new JPanel(new GridBagLayout());
        bottomPanel = new JPanel(new GridBagLayout());

        centerButton = new JButton();

        centerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                updateBottomPanel();
            }
        });

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

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel labelBienvenue = new JLabel("Bienvenue sur la plateforme du laboratoire d'analyses.");
        JLabel labelConsignes = new JLabel("Sélectionnez le type d'analyse qui vous intéresse");
        String s1[] = { "SANG", "VIH", "DIABETE" };
        JComboBox<String> comboBox = new JComboBox<>(s1);

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
                String selectedValue = (String) comboBox.getSelectedItem();
                updateCenterPanel(selectedValue);
            }
        });

        return topPanel;
    }


    private void updateCenterPanel(String selectedValue) {

        JLabel centerLabel = new JLabel("Sélectionnez un créneau disponible pour le type d'analyse  " + selectedValue);

        centerPanel.removeAll();

        GridBagConstraints gbcCenter = new GridBagConstraints();
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 0;
        gbcCenter.anchor = GridBagConstraints.CENTER;
        gbcCenter.insets = new Insets(5, 5, 5, 5);

        centerPanel.add(centerLabel, gbcCenter);

        gbcCenter.gridy = 1;
        centerButton.setText("Action pour " + selectedValue); // Mettre à jour le texte du bouton
        centerPanel.add(centerButton, gbcCenter);


        centerPanel.revalidate();
        centerPanel.repaint();



    }


    private void updateBottomPanel() {

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
                String value = tfCB.getText();
                if (ke.getKeyChar() >= '0'  && ke.getKeyChar() <= '9'){
                    tfCB.setEditable(true);
                    errorLabel.setText("");
                }else {
                    tfCB.setEditable(false);
                    errorLabel.setText(" Entrez seulement des chiffre ");

                }
            }
                            });


        bottomPanel.revalidate();
        bottomPanel.repaint();

    }


    public static void main(String[] args){

        SessionFactory sessFact = HibernateUtil.getSessionFactory();

        new InterfaceVisite(sessFact);

    }

}