package interfaceGraphique;

import paquet.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BarreOutils extends JMenuBar {
    Client client;
    public BarreOutils(Client client,ApplicationClient ac) {
        this.client = client;
        // Listener générique qui affiche l'action du menu utilisé


        // Création du menu Fichier
        JMenu fichierMenu = new JMenu("Fichier");
        JMenuItem item;

        //Nouveau ...
        JMenu sousNouveau= new JMenu("Nouveau");
        item = new JMenuItem("Fil De Discussion");

        sousNouveau.add(item);

        item = new JMenuItem("Uitlisateur");

        sousNouveau.add(item);


       fichierMenu.add(sousNouveau);
        //Fin Nouveau

        //ChoixServeur
        item = new JMenuItem("Serveur");
        ActionListener choixServeur = new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                new ChoixServeur(client);
                System.out.println("Test");
            }
        };
        item.addActionListener(choixServeur);
        fichierMenu.add(item);

        // Connexion
        item = new JMenuItem("Connexion");
        ActionListener choixConnecion= new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               new Connexion(client,ac);
            }
        };
        item.addActionListener(choixConnecion);
        fichierMenu.add(item);

       // QUiter
        item = new JMenuItem("Quitter");
        ActionListener choixQuiter= new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        };
        item.addActionListener(choixQuiter);
        fichierMenu.add(item);

        // ajout des menus à la barre
        add(fichierMenu);


    }
}
