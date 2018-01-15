package interfaceGraph;

import discussion.FilDeDiscussion;
import discussion.Message;
import net.Client;
import discussion.GroupeNomme;
import utilisateurs.Utilisateur;
import javax.swing.*;

import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


/**
 * Classe gérant l'interface du chat
 */
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


    private JButton rafraichir = new JButton("Rafraichir la liste des groupes");
    private JButton ajoutTicket = new JButton("Ajouter un ticket");
    private JButton gestionUtilisateur = new JButton("Gestion des utilisateurs");
    private JButton gestionGroupe = new JButton("Gestion des groupes");

    /**
     *
     * @param c Client connecte
     */
    public Chat(Client c) {

        c.download();
        setContentPane(contentPane);
        this.setPreferredSize(new Dimension(800,800));
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);


        int delay = 2000; //milliseconds
        ActionListener taskPerformer = evt ->{

            majListMessage(c);
        };
        Timer t = new Timer(delay, taskPerformer);
        t.start();



        int delayTree = 10000; //milliseconds
        ActionListener taskPerformerTree = evt -> buildTree(c);
        Timer tTree = new Timer(delay, taskPerformer);
        tTree.start();


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel(c,t,tTree);
            }
        });
        buildTree(c);


        //Fenetre de chat et zone d'envoi

        this.add(split);
        split.setRightComponent(contenuFenetreChat);

        contenuFenetreChat.add(sendField,BorderLayout.SOUTH);
        contenuFenetreChat.add(new JScrollPane(filDeChat),BorderLayout.CENTER);
        sendField.add(new JScrollPane(chatField),BorderLayout.CENTER);

        sendField.add(sendButton,BorderLayout.EAST);

        filDeChat.setEditable(false);
        toolBar.setFloatable(false);
        sendField.setEnabled(false);
        sendButton.setEnabled(false);



        this.add(toolBar,BorderLayout.NORTH);
        toolBar.add(ajoutTicket);
        toolBar.add(rafraichir);




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

        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                t.setDelay(10000);
                t.stop();
                tTree.start();

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                t.setDelay(2000);
                t.stop();
                tTree.stop();
            }
        });




        chatTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        chatTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) chatTree.getLastSelectedPathComponent();
            if (node.getLevel() > 1){
                majListMessage(c);
                sendField.setEnabled(true);
                sendButton.setEnabled(true);
            }else{
                sendField.setEnabled(false);
                sendButton.setEnabled(false);
            }
        });


        rafraichir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buildTree(c);
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


    /**
     *  Action lance lors de l'appuie sur le bouton ok
     * @param c Client connecte
     */
    public void okPressed(Client c){
        c.download();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)chatTree.getLastSelectedPathComponent();
        if (node != null && node.getLevel()>1){

            GroupeNomme g = c.getGroupeName(node.getParent().toString());
            FilDeDiscussion f = g.getFilsDeDiscussion(node.toString());
            f.ajouterMessage(c.getUtilisateurCourant(),chatField.getText());

            chatField.setText("");
            c.upload();

        }
        majListMessage(c);
    }

    /**
     *  Methode lance toutes les 500ms et mets à jour la liste des messages
     * @param c Client connecte
     */

    private void majListMessage(Client c ){
        c.download();
        filDeChat.setText("");
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
        majStatutMessage(c);
        c.upload();
    }

    /**
     * Mise a jour du statut des messages (Lu, en attente, recu)
     * @param c Client connecte
     */
    private void majStatutMessage(Client c){

        DefaultMutableTreeNode node = (DefaultMutableTreeNode)chatTree.getLastSelectedPathComponent();
        if (node == null){
            for (GroupeNomme g :
                    c.getListeGroupe()) {
                if (g.estMembre(c.getUtilisateurCourant())) {
                    for (FilDeDiscussion f :
                            g.getFilsDeDiscussion()) {
                        for (Message m :
                                f.getListMessage()) {
                            if (m.getEnAttente().estMembre(c.getUtilisateurCourant())) {
                                m.recu(c.getUtilisateurCourant());
                            }
                        }
                    }
                }

            }


        }else if (node.getLevel()>1){

            GroupeNomme g = c.getGroupeName(node.getParent().toString());

            for (FilDeDiscussion f :
                    g.getFilsDeDiscussion()) {
                for (Message m :
                        f.getListMessage()) {
                    if (m.getEnAttente().estMembre(c.getUtilisateurCourant())){
                        m.recu(c.getUtilisateurCourant());
                        m.lu(c.getUtilisateurCourant());
                    }
                    if (m.getRecu().estMembre(c.getUtilisateurCourant())) {
                        m.lu(c.getUtilisateurCourant());
                    }
                }
            }


        }
    }







    /**
     * Construit l'arbre des messages et des fils de discussions
     * @param c Client connecte
     */
    private void buildTree(Client c){
        //List groupe est vide
        NavigableSet<GroupeNomme> listGroupe = new TreeSet<>(Comparator.comparing(GroupeNomme::getNom));
        listGroupe.addAll(c.getListeGroupe());

        //Création d'une racine
        DefaultMutableTreeNode racine = new DefaultMutableTreeNode();

        //Nous allons ajouter des branches et des feuilles à notre racine
        for(GroupeNomme g : listGroupe){
            if (g.getMembres().contains(c.getUtilisateurCourant())) {
                DefaultMutableTreeNode rep = new DefaultMutableTreeNode(g.getNom());
                NavigableSet<FilDeDiscussion> fils = new TreeSet<>(new Comparator<FilDeDiscussion>() {
                    @Override
                    public int compare(FilDeDiscussion o1, FilDeDiscussion o2) {
                        if (o1.getListMessage().isEmpty()){
                            return 1;
                        }
                        if (o2.getListMessage().isEmpty()){
                            return -1;
                        }
                        return o2.getDernierMessage().getDateEnvoi().compareTo(o1.getDernierMessage().getDateEnvoi());
                    }
                });
                fils.addAll(g.getFilsDeDiscussion());
                //On rajoute 4 branches
                for (FilDeDiscussion fil : fils) {
                    DefaultMutableTreeNode rep2 = new DefaultMutableTreeNode(fil.getSujet());
                    rep.add(rep2);
                }

                //On ajoute la feuille ou la branche à la racine
                racine.add(rep);
            }
        }
        //Nous créons, avec notre hiérarchie, un arbre
        this.chatTree = new JTree(racine);
        chatTree.setRootVisible(false);
        //Que nous plaçons sur le ContentPane de notre JFrame à l'aide d'un scroll
        split.setLeftComponent(new JScrollPane(chatTree));
        split.setDividerLocation(200);

    }

    /**
     * Lancer lors de la fermeture de la fenetre
     * @param c Client connecte
     * @param t Timer avant deconnection
     */
    private void onCancel(Client c,Timer... t) {
        // add your code here if necessary
        for (Timer timer: t) {
            timer.stop();
        }
        c.getUtilisateurCourant().setConnecte(false);

        c.upload();
        c.deconnect();
        dispose();
    }

}
