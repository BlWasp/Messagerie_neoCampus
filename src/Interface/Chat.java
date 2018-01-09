package Interface;

import discussion.FilDeDiscussion;
import discussion.Message;
import paquet.Client;
import utilisateurs.GroupeNomme;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import java.awt.*;

import java.awt.event.*;
import java.security.acl.Group;
import java.sql.SQLException;
import java.util.List;
import java.util.NavigableSet;


public class Chat extends JFrame {
    private JPanel contentPane;
    private JPanel treePanel = new JPanel(new GridLayout());
    private JPanel sendField = new JPanel(new BorderLayout());
    private JPanel contenuFenetreChat = new JPanel(new BorderLayout());


    private JTextField chatField = new JTextField();
    private JButton sendButton = new JButton("Send");
    private JTextArea filDeChat = new JTextArea();
    private JTree chatTree;
    private JMenuBar menuBarre = new JMenuBar();
    private JMenu fichierMenu = new JMenu("Fichier");
    private JMenuItem ajoutTicket = new JMenuItem("Ajouter un ticket");
    private JMenuItem gestionUtilisateur = new JMenuItem("Gestion des utilisateurs");
    private JMenuItem gestionGroupe = new JMenuItem("Gestion des groupes");

    public Chat(Client c) {
        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);
        this.setPreferredSize(new Dimension(500,500));
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        buildTree(c);

        chatTree.setPreferredSize(new Dimension(100,200));


        //Fenetre de chat et zone d'envoi
        this.add(contenuFenetreChat,BorderLayout.CENTER);
        contenuFenetreChat.add(sendField,BorderLayout.SOUTH);
        contenuFenetreChat.add(filDeChat,BorderLayout.CENTER);
        sendField.add(chatField,BorderLayout.CENTER);
        sendField.add(sendButton,BorderLayout.EAST);

        filDeChat.setEditable(false);

        menuBarre.add(fichierMenu);
        fichierMenu.add(ajoutTicket);
        setJMenuBar(menuBarre);








        if (c.getUtilisateurCourant().getPrivilege() == Utilisateur.Privilege.ADMIN){
            fichierMenu.add(gestionUtilisateur);
            fichierMenu.add(gestionGroupe);
            gestionUtilisateur.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    GestionUtilisateurs gestion = new GestionUtilisateurs(c);
                    gestion.pack();
                    gestion.setVisible(true);
                }
            });
            gestionGroupe.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GestionGroupe gestion = new GestionGroupe(c);
                    gestion.pack();
                    gestion.setVisible(true);
                }
            });
        }



        chatTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        chatTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                majListMessage(c);
            }
        });







        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)chatTree.getLastSelectedPathComponent();
                if (node != null){
                    System.out.println(c.getUtilisateurCourant().toString());
                    System.out.println(c.getGroupeName(node.getParent().toString()));
                    System.out.println(chatField.getText().toString());
                    Message m = new Message( c.getUtilisateurCourant()
                                            ,c.getGroupeName(node.getParent().toString())
                                            ,chatField.getText().toString());
                    Object nodeInfo = node.getUserObject();
                    FilDeDiscussion fil = c.getGroupeName(node.getParent().toString()).getFilsDeDiscussion(nodeInfo.toString());
                    System.out.println(fil);
                    c.ajouterMessage(m.getMesage(),fil);
                    majListMessage(c);
                    chatField.setText("");

                }

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




    private void majListMessage(Client c ){
        filDeChat.setText("");
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)chatTree.getLastSelectedPathComponent();

                /* if nothing is selected */
        if (node == null) return;

                /* retrieve the node that was selected */
        Object nodeInfo = node.getUserObject();

        if (node.getLevel()>1){
            FilDeDiscussion fil = c.getGroupeName(node.getParent().toString()).getFilsDeDiscussion(nodeInfo.toString());

            //TODO TEST A RETIRER
           /* Message message = new Message(c.getUtilisateur(21400537),c.getGroupeName(node.getParent().toString()),"BENDO MA BENDO");
            Message message2 = new Message(c.getUtilisateur(21400537),c.getGroupeName(node.getParent().toString()),"COUCOU");
            fil.ajouterMessage(message);
            fil.ajouterMessage(message2);*/

            filDeChat.setText(fil.printMessage());
        }
    }



    private void buildTree(Client c){
        //TODO Partie a supprimer juste pour tester larbre
        //List groupe est vide
        List<GroupeNomme> listGroupe = c.getListeGroupe();
        GroupeNomme g = c.getGroupeName("L3");
        GroupeNomme g2 = c.getGroupeName("L2");
        FilDeDiscussion f = new FilDeDiscussion("L3 Info");
        FilDeDiscussion f2 = new FilDeDiscussion("L2 Info");
        g.ajouterFilDeDiscussion(f);
        g2.ajouterFilDeDiscussion(f2);

        //Création d'une racine
        DefaultMutableTreeNode racine = new DefaultMutableTreeNode();

        //Nous allons ajouter des branches et des feuilles à notre racine
        for(int i = 0; i < listGroupe.size(); i++){
            DefaultMutableTreeNode rep = new DefaultMutableTreeNode(listGroupe.get(i).getNom());

            //On rajoute 4 branches
            for (FilDeDiscussion fil :
                    listGroupe.get(i).getFilsDeDiscussion()) {
                DefaultMutableTreeNode rep2 = new DefaultMutableTreeNode(fil.getSujet());
                rep.add(rep2);
            }

            //On ajoute la feuille ou la branche à la racine
            racine.add(rep);
        }
        //Nous créons, avec notre hiérarchie, un arbre
        this.chatTree = new JTree(racine);
        chatTree.setRootVisible(false);
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
