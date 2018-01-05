package paquet;

import discussion.FilDeDiscussion;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class TestServeur {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Partie BDD
        ConcurrentLinkedDeque<GroupeNomme> listeGroupe= new ConcurrentLinkedDeque<>();
        ConcurrentLinkedDeque<FilDeDiscussion> listeFilDeDiscussion = new ConcurrentLinkedDeque<>();
        Groupe global = new Groupe();
        // Fin partie BDD

        global.ajouterMembres(
                new Utilisateur("admin", "admin", 0, "admin", null));
        ServerSocket sSocket = new ServerSocket(6791);


        while (true) {
            ///////////////////ZONE DE TEST

            System.out.println("Utilisateurs : ");
            System.out.println(global);

            ///////////////////FIN ZONE DE TEST
            Socket socket = sSocket.accept();
            Serveur server = new Serveur(socket,listeGroupe,listeFilDeDiscussion,global);
            Thread serveurThread = new Thread(server);
            serveurThread.start();


            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
