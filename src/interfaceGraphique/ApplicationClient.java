package interfaceGraphique;

import paquet.Client;

import javax.swing.*;

public class ApplicationClient extends JFrame {
    private Client client;
    Repertoire rep_utilisateur;
    Onglet onglets;




    public ApplicationClient() {
        this.client = new Client();
        this.rep_utilisateur = new Repertoire(client);
        this.onglets = new Onglet(this.client,rep_utilisateur);
        setTitle("Déconnecté - NeoCampus");


        setSize(700, 500);





        setJMenuBar(new BarreOutils(this.client,this));
        JPanel pan_onglet;
        pan_onglet = new JPanel();
        getContentPane().add(this.onglets);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public Repertoire getRep_utilisateur() {
        return rep_utilisateur;
    }

    public Onglet getOnglets() {
        return onglets;
    }
}
