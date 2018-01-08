package Interface;

import discussion.FilDeDiscussion;
import paquet.Client;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.*;

import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;


public class Chat extends JDialog {
    private JPanel contentPane;
    private JTree chatTree;// TODO Afficher la liste des groupes avec leur fils de discussion
    private JTextField chatField;
    private JButton sendButton;
    private JTextArea filDeChat;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fichierMenu = new JMenu("Fichier");
    private JMenuItem ajoutTicket = new JMenuItem("Ajouter un ticket");
    private JMenuItem gestionServeur = new JMenuItem("Gestion du serveur");

    public Chat(Client c) {
        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);
        setModal(true);
        this.setPreferredSize(new Dimension(500,500));

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });



        JPanel subPanel = new JPanel(new BorderLayout());
            subPanel.add(chatField);
            subPanel.add(sendButton,BorderLayout.EAST);
            this.add(subPanel,BorderLayout.SOUTH);




        this.add(filDeChat,BorderLayout.CENTER);
        menuBar.add(fichierMenu);
        fichierMenu.add(ajoutTicket);
        if (c.getUtilisateurCourant().getPrivilege() == Utilisateur.Privilege.ADMIN){
            fichierMenu.add(gestionServeur);
            gestionServeur.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    GestionUtilisateurs gestion = new GestionUtilisateurs(c);
                    gestion.pack();
                    gestion.setVisible(true);
                }
            });
        }
        setJMenuBar(menuBar);

        buildTree(c);




        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // TODO Envoyer le message avec lid du fil de discussion correspondant
                chatField.setText("");
            }
        });

        chatField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatField.setText("");
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }








    private void buildTree(Client c){
        //TODO ajouter la possibilite de creer un groupe
        //Partie a supprimer juste pour tester larbre
        //List groupe est vide
        List<GroupeNomme> listGroupe = c.getListeGroupe();
        FilDeDiscussion f = new FilDeDiscussion("L3 Info");
        c.ajouterFilDeDiscussion(f);
        List<FilDeDiscussion> listFil = c.getListeFilDeDiscussion();


        //Création d'une racine
        DefaultMutableTreeNode racine = new DefaultMutableTreeNode("Racine");

        //Nous allons ajouter des branches et des feuilles à notre racine
        for(int i = 0; i < listGroupe.size(); i++){
            DefaultMutableTreeNode rep = new DefaultMutableTreeNode(listGroupe.get(i).getNom());

          /*  //On rajoute 4 branches
            if(i < 4){
                DefaultMutableTreeNode rep2 = new DefaultMutableTreeNode("Fichier enfant");
                rep.add(rep2);
            }*/
            //On ajoute la feuille ou la branche à la racine
            racine.add(rep);
        }
        //Nous créons, avec notre hiérarchie, un arbre
        JTree chatTree = new JTree(racine);

        //Que nous plaçons sur le ContentPane de notre JFrame à l'aide d'un scroll
        this.add(new JScrollPane(chatTree),BorderLayout.WEST);
    }









    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

   /* public static void main(String[] args) {
        Chat dialog = new Chat();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
