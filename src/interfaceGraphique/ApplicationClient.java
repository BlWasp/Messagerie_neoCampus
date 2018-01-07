package interfaceGraphique;

import javafx.application.Application;
import paquet.Client;

import javax.swing.*;

public class ApplicationClient extends JFrame {
    private Client client;




    public ApplicationClient() {
        this.client = new Client();
        setTitle("Déconnecté - NeoCampus");


        setSize(700, 500);





        setJMenuBar(new BarreOutils(this.client,this));

//        info.add(new JLabel(client.getUtilisateurCourant().getIdentifiant()+"@"+client.getHost() + ":" + client.getPort()));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

}
