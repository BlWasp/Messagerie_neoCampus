package Interface;

import discussion.FilDeDiscussion;
import discussion.Message;
import net.Client;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


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
    //TODO Raffraichissement de la liste des groupes toutes les n secondes
    public Chat(Client c) {
        contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);
        this.setPreferredSize(new Dimension(500,500));
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel(c);
            }
        });
        c.download();
        buildTree(c);

        /*chatTree.setPreferredSize(new Dimension(100,200));*/


        //Fenetre de chat et zone d'envoi
        this.add(contenuFenetreChat,BorderLayout.CENTER);
        contenuFenetreChat.add(sendField,BorderLayout.SOUTH);
        contenuFenetreChat.add(filDeChat,BorderLayout.CENTER);
        sendField.add(new JScrollPane(chatField),BorderLayout.CENTER);
        sendField.add(sendButton,BorderLayout.EAST);

        filDeChat.setEditable(false);

        menuBarre.add(fichierMenu);
        fichierMenu.add(ajoutTicket);
        setJMenuBar(menuBarre);




        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                System.out.println("On focus");
                buildTree(c);
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                //
            }
        });






        if (c.getUtilisateurCourant().getPrivilege() == Utilisateur.Privilege.ADMIN){
            fichierMenu.add(gestionUtilisateur);
            fichierMenu.add(gestionGroupe);

            gestionUtilisateur.addActionListener(e -> {
                GestionUtilisateurs gestion = new GestionUtilisateurs(c);
                gestion.pack();
                gestion.setVisible(true);
            });

            gestionGroupe.addActionListener(e -> {
                GestionGroupe gestion = new GestionGroupe(c);
                gestion.pack();
                gestion.setVisible(true);
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

                    Message m = new Message( c.getUtilisateurCourant()
                                            ,c.getGroupeName(node.getParent().toString())
                                            ,chatField.getText().toString());
                    Object nodeInfo = node.getUserObject();
                    FilDeDiscussion fil = c.getGroupeName(node.getParent().toString()).getFilsDeDiscussion(nodeInfo.toString());
                    System.out.println(fil);
                    //TODO AJOUTER QUAND AJOUTERMESSAGE SERA FAIT
                    //c.ajouterMessage(m.getMesage(),fil);
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
                onCancel(c);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }



    //TODO
    private void majListMessage(Client c ){
        c.download();
        filDeChat.setText("");
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)chatTree.getLastSelectedPathComponent();

                /* if nothing is selected */
        if (node == null) return;

                /* retrieve the node that was selected */
        Object nodeInfo = node.getUserObject();

        if (node.getLevel()>1){
            FilDeDiscussion fil = c.getGroupeName(node.getParent().toString()).getFilsDeDiscussion(nodeInfo.toString());



            filDeChat.setText(fil.printMessage());
        }
    }



    private void buildTree(Client c){
        c.download();
        if (chatTree != null) {
            chatTree.removeAll();
        }
        //List groupe est vide
        List<GroupeNomme> listGroupe = new ArrayList<>();
        listGroupe.addAll(c.getListeGroupe());

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
        chatTree.setPreferredSize(new Dimension(100,chatTree.getPreferredSize().height));
        //Que nous plaçons sur le ContentPane de notre JFrame à l'aide d'un scroll
        this.add(new JScrollPane(chatTree),BorderLayout.WEST);

    }









    private void onCancel(Client c) {
        // add your code here if necessary
        c.deconnect();
        dispose();
    }

   /* public static void main(String[] args) {
        Chat dialog = new Chat();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
