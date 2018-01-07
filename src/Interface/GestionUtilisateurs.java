package Interface;

import paquet.Client;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;
import BDD.ExtractDataBDD;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.util.NavigableSet;

public class GestionUtilisateurs extends JDialog {
    private JPanel contentPane;
    private JTable table1;
    private JTextField rechercherTextField;
    private JButton rechercherButton;
    private JToolBar tools;
    private JButton ajouterUnUtilisateurButton;
    private JButton rafraichirButton;
    private JButton retirerMembreButton;


    public GestionUtilisateurs(Client c) throws SQLException {
        setContentPane(contentPane);
        setModal(true);
        this.setPreferredSize(new Dimension(800,800));

        majTab(c);





        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        ajouterUnUtilisateurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajoutUtilisateur ajout = new ajoutUtilisateur(c);
                ajout.pack();
                ajout.setVisible(true);

            }
        });
        rafraichirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                majTab(c);

            }
        });

        retirerMembreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retirerMembre(c);

            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }




    private void retirerMembre(Client c){
        //dispose();
        RetirerMembre retirer = new RetirerMembre(c);
        retirer.toFront();
        retirer.pack();
        retirer.repaint();
        retirer.setVisible(true);

    }

    private void majTab(Client c){
        String col[] = {"Identifiant","Nom","Prenom"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        tableModel.addRow(col);

        this.add(table1);
        //TODO a enlever juste pour les tests
        c.setUtilisateurCourant(new Utilisateur("Daumas","Guillaume",12,"yolo", TypeUtilisateur.ETUDIANT, Utilisateur.Privilege.ADMIN));
        c.ajouterMembres(new Utilisateur("Daumas","Guillaume",789,"yolo", TypeUtilisateur.ETUDIANT, Utilisateur.Privilege.USER));
        c.ajouterMembres(new Utilisateur("CHERIFI","Salim",790,"yolo",TypeUtilisateur.ETUDIANT,Utilisateur.Privilege.USER));
        c.ajouterMembres(new Utilisateur("12","12",791,"yolo", TypeUtilisateur.ETUDIANT,Utilisateur.Privilege.USER));
        c.ajouterMembres(new Utilisateur("23","23",792,"yolo", TypeUtilisateur.ETUDIANT,Utilisateur.Privilege.USER));


        NavigableSet<Utilisateur> users = c.getMembres();

        for (Utilisateur u :
                users) {

            Object[] data = {u.getIdentifiant(),u.getNom(),u.getPrenom()};
            tableModel.addRow(data);
        }
        table1.setModel(tableModel);
        table1.setDefaultEditor(Object.class,null);
        this.pack();

    }






    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) throws SQLException {
        Client c = new Client();
        GestionUtilisateurs dialog = new GestionUtilisateurs(c);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
