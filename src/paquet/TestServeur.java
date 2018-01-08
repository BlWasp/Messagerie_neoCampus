package paquet;

import discussion.FilDeDiscussion;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;

public class TestServeur {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Partie BDD
        ConcurrentSkipListSet<GroupeNomme> listeGroupe= new ConcurrentSkipListSet<>();
        ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion = new ConcurrentSkipListSet<>();
        Groupe global = new Groupe();
        // Fin partie BDD

        Utilisateur admin = new Utilisateur("admin", "admin", 0, "admin", null);
        admin.setPrivilege(Utilisateur.Privilege.ADMIN);
        /*Utilisateur sylvain =new Utilisateur("DEKER","Sylvain",21400536,"123", TypeUtilisateur.ETUDIANT);
        Utilisateur salim =new Utilisateur("CHERIFI","Salim",21400537,"123", TypeUtilisateur.ETUDIANT);
        Utilisateur guillaume =new Utilisateur("DAUMAS","GUILLAUME",21400538,"123", TypeUtilisateur.ETUDIANT);

        Utilisateur nadege = new Utilisateur("Lamarque","Nadege",0,"123",TypeUtilisateur.SECRETAIRE);
        Utilisateur nadege2 = new Utilisateur("Lamarque2","Nadege2",2,"123",TypeUtilisateur.SECRETAIRE);
        Utilisateur nadege3 = new Utilisateur("Lamarque3","Nadege3",3,"123",TypeUtilisateur.SECRETAIRE);*/
        global.ajouterMembres(admin);



        ServerSocket sSocket = new ServerSocket(12700);


        while (true) {
            ///////////////////ZONE DE TEST

           // System.out.println("Utilisateurs : ");
           // System.out.println(global);
            if( ! listeFilDeDiscussion.isEmpty()){
                System.out.println("Fil de discussion: ");
                listeFilDeDiscussion.first().printFil();

            }

            ///////////////////FIN ZONE DE TEST
            Socket socket = sSocket.accept();
            Serveur server = new Serveur(socket,listeGroupe,listeFilDeDiscussion,global);
            Thread serveurThread = new Thread(server);
            serveurThread.start();

            /////////// TEST POUR AFFICHAGE
            try {
                serveurThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(listeGroupe);
            ////////////FIN TEST POUR AFFICHAGE


        }
    }
}
