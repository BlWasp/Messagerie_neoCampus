package Interface;

import net.Client;
import net.Paquet;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.util.List;

public class GestionGroupe extends JFrame {
    private JPanel contentPane;
    private JTable listeUtilisateurGroupe;
    private JButton ajouterUnMembreExistantButton;
    private JButton supprimerGroupeButton;
    private JButton supprimerMembreButton;
    private JButton modifierAppartenanceAuGroupeButton;
    private JList listeGroupe;
    private JButton modifierGroupeButton;
    private JTextField idMembreAajouter;

    public GestionGroupe(Client c) {
        setContentPane(contentPane);
        //setModal(true);
        c.download();

        buildListGroupe(c);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        listeGroupe.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                idMembreAajouter.setEnabled(true);
                ajouterUnMembreExistantButton.setEnabled(true);
            }
        });

        this.listeGroupe.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                buildListUtilisateurGroupe(c,listeGroupe.getSelectedValue().toString());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        /*this.listeGroupe.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                buildListUtilisateurGroupe(c,listeGroupe.getSelectedValue().toString());
            }
        });*/
        idMembreAajouter.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                idMembreAajouter.setText("");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        modifierAppartenanceAuGroupeButton.addActionListener(e -> {
            GererAdhesionGroupe a = new GererAdhesionGroupe(c);
            a.pack();
            a.setVisible(true);
        });

       ajouterUnMembreExistantButton.addActionListener(e -> {

           Boolean found = false;
               if (c.getGroupeGlobal().getUtilisateur(Integer.parseInt(idMembreAajouter.getText())) != null){
                   Utilisateur ajout = c.getGroupeGlobal().getUtilisateur(Integer.parseInt(idMembreAajouter.getText()));
                   System.out.println(ajout.toString());
                   c.getGroupeName(listeGroupe.getSelectedValue().toString()).ajouterMembres(ajout);
                   buildListUtilisateurGroupe(c,listeGroupe.getSelectedValue().toString());
               }else{
                   idMembreAajouter.setText("Utilisateur inconnu");
               }

       });



       supprimerGroupeButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               String nomGroupe = listeGroupe.getSelectedValue().toString();
               c.getListeGroupe().remove(c.getGroupeName(nomGroupe));
               c.upload();
               buildListGroupe(c);
           }
       });

       //TODO modifier le groupe ajouter interface modif


        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }



    private void buildListUtilisateurGroupe(Client c, String selectedGroupe){
        c.download();
        List<GroupeNomme> listGroupe = new ArrayList<>();
        listGroupe.addAll( c.getListeGroupe());
        NavigableSet<Utilisateur> listUtilisateur = new TreeSet<>(Comparator.comparing(Utilisateur::getNom));

        String col[] = {"<html><b>Identifiant</b></html>","<html><b>Nom</b></html>","<html><b>Prenom</b></html>"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        tableModel.addRow(col);



        for (GroupeNomme g :
                listGroupe) {
            if (g.getNom() == selectedGroupe){
                listUtilisateur = g.getMembres();
                break;
            }
        }
        for (Utilisateur u : listUtilisateur) {

            Object[] data = {u.getIdentifiant(),u.getNom(),u.getPrenom()};
            tableModel.addRow(data);
        }
        listeUtilisateurGroupe.setModel(tableModel);
        listeUtilisateurGroupe.setDefaultEditor(Object.class,null);
        this.pack();
    }



    private void buildListGroupe(Client c){
        c.download();
        DefaultListModel<String> model = new DefaultListModel<>();
        this.listeGroupe.setModel(model);



        List<GroupeNomme> listGroupe = new ArrayList<>();
        listGroupe.addAll(c.getListeGroupe());
        JList<String> list = new JList<>( model );
        if (!listGroupe.isEmpty()) {
            for (int i = 0; i < listGroupe.size(); i++) {
                model.addElement(listGroupe.get(i).getNom());
            }
        }
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

   /* public static void main(String[] args) {
        GestionGroupe dialog = new GestionGroupe();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
