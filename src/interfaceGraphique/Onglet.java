package interfaceGraphique;

import paquet.Client;

import javax.swing.*;
import java.awt.*;

public class Onglet extends JTabbedPane {
    Client client;
    Repertoire rep_utilisateur;
    public Onglet(Client client,Repertoire rep_utilisateur) {
        super(SwingConstants.TOP);
        this.rep_utilisateur = rep_utilisateur;
        this.client = client;
        JPanel onglet1 = new JPanel();
        JLabel titreOnglet1 = new JLabel("Utilisateur");

        onglet1.add(rep_utilisateur.getArbre());
        onglet1.setPreferredSize(new Dimension(300, 80));
        this.addTab("Utilisateurs", onglet1);

        JPanel onglet2 = new JPanel();
        JLabel titreOnglet2 = new JLabel("Groupe");
        onglet2.add(titreOnglet2);
        this.addTab("Groupes", onglet2);

        JPanel onglet3 = new JPanel();
        JLabel titreOnglet3 = new JLabel("Conversation");
        onglet3.add(titreOnglet3);
        this.addTab("Conversations", onglet3);

        this.setOpaque(true);


    }

}
