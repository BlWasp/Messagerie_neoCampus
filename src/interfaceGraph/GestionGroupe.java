package interfaceGraph;

import net.Client;
import discussion.GroupeNomme;
import utilisateurs.Utilisateur;

import javax.swing.*;
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
    private JTextField idMembreAajouter;
    private JButton ajouterUnGroupeButton;
    private JButton rafraichirButton;

    /**
     *
     * @param c Client connecte
     */
    public GestionGroupe(Client c) {
        setContentPane(contentPane);

        this.setPreferredSize(new Dimension(1000,800));
        this.setTitle("Gestion des groupes");
        buildListGroupe(c);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        supprimerMembreButton.setEnabled(false);
        supprimerGroupeButton.setEnabled(false);
        rafraichirButton.setEnabled(false);
        modifierAppartenanceAuGroupeButton.setEnabled(false);

        listeGroupe.addListSelectionListener(e -> {
            idMembreAajouter.setEnabled(true);
            ajouterUnMembreExistantButton.setEnabled(true);
            supprimerGroupeButton.setEnabled(true);
            rafraichirButton.setEnabled(true);

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


        listeGroupe.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                modifierAppartenanceAuGroupeButton.setEnabled(true);
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

        listeUtilisateurGroupe.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                supprimerMembreButton.setEnabled(true);
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


        rafraichirButton.addActionListener(e -> buildListUtilisateurGroupe(c,listeGroupe.getSelectedValue().toString()));

        modifierAppartenanceAuGroupeButton.addActionListener(e -> {
            GererAdhesionGroupe a = new GererAdhesionGroupe(c);
            a.pack();
            a.setVisible(true);
            buildListUtilisateurGroupe(c,listeGroupe.getSelectedValue().toString());
        });



       ajouterUnMembreExistantButton.addActionListener(e -> {
           if (!idMembreAajouter.getText().isEmpty()){
               if (c.getGroupeGlobal().getUtilisateur(Integer.parseInt(idMembreAajouter.getText())) != null){

                   if (c.getGroupeName(listeGroupe.getSelectedValue().toString()).getUtilisateur(Integer.parseInt(idMembreAajouter.getText())) == null) {
                       GroupeNomme g = c.getGroupeName(listeGroupe.getSelectedValue().toString());
                       System.out.println(g);
                       Utilisateur ajout = c.getGroupeGlobal().getUtilisateur(Integer.parseInt(idMembreAajouter.getText().toString()));
                       System.out.println(ajout);
                       g.ajouterMembres(ajout);
                       c.upload();
                       JOptionPane.showMessageDialog(null,"Utilisateur ajouté");
                       //buildListUtilisateurGroupe(c, listeGroupe.getSelectedValue().toString());
                   }else{
                       JOptionPane.showMessageDialog(null,"Utilisateur déjà présent dans ce groupe");
                   }
               }else{
                   JOptionPane.showMessageDialog(null,"Utilisateur inconnu");
               }

           }else{
               JOptionPane.showMessageDialog(null,"Veuillez remplir le champ");
           }
       });


       supprimerMembreButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (listeUtilisateurGroupe.getSelectedRow() != -1) {
                   int result = JOptionPane.showConfirmDialog(null,"Etes vous sur de vouloir supprimer l'utilisateur ?");
                   if (result == JOptionPane.YES_OPTION) {
                       int id = (int) listeUtilisateurGroupe.getValueAt(listeUtilisateurGroupe.getSelectedRow(), 0);
                       int retour = c.getGroupeName(listeGroupe.getSelectedValue().toString()).retirerMembres(c.getGroupeGlobal().getMembre(id));
                       c.upload();
                       //buildListUtilisateurGroupe(c, listeGroupe.getSelectedValue().toString());
                   }
               }else{
                   JOptionPane.showMessageDialog(null, "Veuillez sélectionner un utilisateur");
               }
           }
       });

       ajouterUnGroupeButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               AjouterGroupe ajout = new AjouterGroupe(c);
               ajout.pack();
               ajout.setVisible(true);
               buildListGroupe(c);
           }
       });

       supprimerGroupeButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if (!listeGroupe.getSelectedValue().toString().isEmpty()) {
                   int result = JOptionPane.showConfirmDialog(null, "Etes-vous sur ?");

                   if (result == JOptionPane.YES_OPTION) {
                       String nomGroupe = listeGroupe.getSelectedValue().toString();
                       c.getListeGroupe().remove(c.getGroupeName(nomGroupe));
                       c.upload();
                       buildListGroupe(c);
                   }
               }else{
                   JOptionPane.showMessageDialog(null, "Veuillez sélectionner un groupe");
               }
           }
       });



        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        this.pack();
        this.setLocationRelativeTo(null);
    }

    /**
     *
     * @param c Client connecte
     * @param selectedGroupe
     */
    private void buildListUtilisateurGroupe(Client c, String selectedGroupe){
        c.download();
        List<GroupeNomme> listGroupe = new ArrayList<>();
        listGroupe.addAll( c.getListeGroupe());
        NavigableSet<Utilisateur> listUtilisateur = new TreeSet<>(Comparator.comparing(Utilisateur::getNom));
        String col[] = {"<html><b>Identifiant</b></html>","<html><b>Nom</b></html>","<html><b>Prenom</b></html>"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0);
        tableModel.addRow(col);
        for (Utilisateur u : c.getGroupeName(listeGroupe.getSelectedValue().toString()).getMembres()) {
            Object[] data = {u.getIdentifiant(),u.getNom(),u.getPrenom()};
            tableModel.addRow(data);
        }
        listeUtilisateurGroupe.setModel(tableModel);
        listeUtilisateurGroupe.setDefaultEditor(Object.class,null);
        this.pack();
    }

    /**
     *
     * @param c Client connecte
     */
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

    /**
     *
     */
    private void onOK() {
        // add your code here
        dispose();
    }

    /**
     *
     */
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
