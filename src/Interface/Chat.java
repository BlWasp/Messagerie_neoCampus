package Interface;

import discussion.FilDeDiscussion;
import net.Client;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;
import javax.swing.*;

import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;



public class Chat extends JFrame {
    private JPanel contentPane = new JPanel(new BorderLayout());
    private JPanel treePanel = new JPanel(new GridLayout());
    private JPanel sendField = new JPanel(new BorderLayout());
    private JPanel contenuFenetreChat = new JPanel(new BorderLayout());


    private JTextField chatField = new JTextField();
    private JButton sendButton = new JButton("Send");
    private JTextPane filDeChat = new JTextPane();
    private JTree chatTree = new JTree();
    private JMenuBar menuBarre = new JMenuBar();
    private JToolBar toolBar = new JToolBar();

    private JSplitPane split = new JSplitPane();


    //private JMenu fichierMenu = new JMenu("Fichier");
    private JButton ajoutTicket = new JButton("Ajouter un ticket");
    private JButton gestionUtilisateur = new JButton("Gestion des utilisateurs");
    private JButton gestionGroupe = new JButton("Gestion des groupes");
    public Chat(Client c) {

        setContentPane(contentPane);
        this.setPreferredSize(new Dimension(500,500));
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);


        int delay = 500; //milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                majListMessage(c);

            }
        };
        Timer t = new Timer(delay, taskPerformer);
        t.start();


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel(c,t);
            }
        });
        c.download();
        buildTree(c);


        //Fenetre de chat et zone d'envoi

        //this.add(contenuFenetreChat,BorderLayout.CENTER);
        this.add(split);
        split.setRightComponent(contenuFenetreChat);

        contenuFenetreChat.add(sendField,BorderLayout.SOUTH);
        contenuFenetreChat.add(new JScrollPane(filDeChat),BorderLayout.CENTER);
        sendField.add(new JScrollPane(chatField),BorderLayout.CENTER);

        sendField.add(sendButton,BorderLayout.EAST);



        this.add(toolBar,BorderLayout.NORTH);
        toolBar.add(ajoutTicket);

        filDeChat.setEditable(false);
        toolBar.setFloatable(false);
        sendField.setEnabled(false);
        sendButton.setEnabled(false);



        if (c.getUtilisateurCourant().getPrivilege() == Utilisateur.Privilege.ADMIN){


            toolBar.add(gestionUtilisateur);
            toolBar.add(gestionGroupe);

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
        chatTree.addTreeSelectionListener(e -> {
            majListMessage(c);
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) chatTree.getLastSelectedPathComponent();
            if (node.getLevel() > 1){
                sendField.setEnabled(true);
                sendButton.setEnabled(true);
            }else{
                sendField.setEnabled(false);
                sendButton.setEnabled(false);
            }
        });

        ajoutTicket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AjoutTicket ajout = new AjoutTicket(c);
                ajout.pack();
                ajout.setVisible(true);
                buildTree(c);
            }
        });




        sendButton.addActionListener(e -> {
           okPressed(c);
        });

        chatField.addActionListener(e -> {
            okPressed(c);
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel(c,t);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.pack();
        this.setLocationRelativeTo(null);
    }



    public void okPressed(Client c){
        c.download();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)chatTree.getLastSelectedPathComponent();
        if (node != null && node.getLevel()>1){

            GroupeNomme g = c.getGroupeName(node.getParent().toString());
            FilDeDiscussion f = g.getFilsDeDiscussion(node.toString());
            f.ajouterMessage(c.getUtilisateurCourant(),chatField.getText());
            //c.getGroupeName(node.getParent().toString()).getFilsDeDiscussion(node.toString()).ajouterMessage(c.getUtilisateurCourant(),chatField.getText());

            chatField.setText("");
            c.upload();

        }
        majListMessage(c);
    }


    //TODO
    private void majListMessage(Client c ){
        filDeChat.setText("");
        c.download();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)chatTree.getLastSelectedPathComponent();
        if (node == null) return;

        Object nodeInfo = node.getUserObject();
        if (node.getLevel()>1){
            GroupeNomme g = c.getGroupeName(node.getParent().toString());
            FilDeDiscussion f = g.getFilsDeDiscussion(nodeInfo.toString());
            filDeChat.setText("");
            try {
                f.printMessage(c.getUtilisateurCourant(),this.filDeChat);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }



    private void buildTree(Client c){
        c.download();
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
        //chatTree.setPreferredSize(new Dimension(100,chatTree.getPreferredSize().height));
        //Que nous plaçons sur le ContentPane de notre JFrame à l'aide d'un scroll
        //this.add(new JScrollPane(chatTree),BorderLayout.WEST);
        split.setLeftComponent(new JScrollPane(chatTree));

    }

    private void onCancel(Client c,Timer t) {
        // add your code here if necessary
        t.stop();
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
