package Interface;

import paquet.Client;
import utilisateurs.Utilisateur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.TreeSet;

public class GestionUtilisateurs extends JDialog {
    private JPanel contentPane;
    private JTable table1;
    private JTextField rechercherTextField;
    private JButton rechercherButton;
    private JToolBar tools;
    private JButton ajouterUnUtilisateurButton;
    private JButton rafraichirButton;
    private JButton retirerMembreButton;
    private JButton modifierButton;


    public GestionUtilisateurs(Client c) {
        setContentPane(contentPane);
        setModal(true);
        this.setPreferredSize(new Dimension(800,800));
        c.authentification(0,"admin");
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
                majTab(c);

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

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifierMembre(c);
                majTab(c);
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }

    private void modifierMembre(Client c){

        int idSelected = (int)table1.getValueAt(table1.getSelectedRow(),0);
        Utilisateur u = c.getUtilisateur(idSelected);
        System.out.println(u.toString());
        //TODO faire linterface
        ModifierMembre modif = new ModifierMembre(c,u);
        modif.pack();
        modif.toFront();
        modif.setVisible(true);
        /*table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
                System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
            }
        });*/
    }




    private void retirerMembre(Client c){
        //dispose();
        RetirerMembre retirer = new RetirerMembre(c);
        retirer.toFront();
        retirer.pack();
        retirer.repaint();
        retirer.setVisible(true);
        majTab(c);

    }

    private void majTab(Client c){

        String col[] = {"<html><b>Identifiant</b></html>","<html><b>Nom</b></html>","<html><b>Prenom</b></html>"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        tableModel.addRow(col);

        this.add(table1);



        NavigableSet<Utilisateur> users = new TreeSet<>(new Comparator<Utilisateur>() {
            @Override
            public int compare(Utilisateur o1, Utilisateur o2) {
                if (o1.getNom().equals(o2.getNom())){
                    Integer i1 = o1.getIdentifiant();
                    Integer i2 = o2.getIdentifiant();
                    return i1.compareTo(i2);
                }else{
                    return o1.getNom().compareTo(o2.getNom());
                }
            }
        });
        users.addAll(c.getMembres());


        for (Utilisateur u : users) {

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

   /* public static void main(String[] args) {
        Client c = new Client();
        GestionUtilisateurs dialog = new GestionUtilisateurs(c);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
