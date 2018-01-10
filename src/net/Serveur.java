package net;


import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;

public class Serveur {
    ConcurrentSkipListSet<GroupeNomme> listeGroupe= new ConcurrentSkipListSet<>();
    Groupe global = new Groupe();
    ObjectOutputStream out;
    ObjectInputStream in;
    ServerSocket socketServer;
    int port;

    public Serveur(int port){
        // Zone de TEST
        Utilisateur admin = new Utilisateur("Admin", "admin", 0, "admin", null);
        admin.setPrivilege(Utilisateur.Privilege.ADMIN);
        Utilisateur sylvain =new Utilisateur("DEKER","Sylvain",21400536,"123", TypeUtilisateur.ETUDIANT);
        Utilisateur salim =new Utilisateur("CHERIFI","Salim",21400537,"123", TypeUtilisateur.ETUDIANT);
        Utilisateur guillaume =new Utilisateur("DAUMAS","GUILLAUME",21400538,"123", TypeUtilisateur.ETUDIANT);

        Utilisateur nadege = new Utilisateur("Lamarque","Nadege",0,"123",TypeUtilisateur.ADMINISTRATIF);
        Utilisateur nadege2 = new Utilisateur("Lamarque2","Nadege2",2,"123",TypeUtilisateur.ADMINISTRATIF);
        Utilisateur nadege3 = new Utilisateur("Lamarque3","Nadege3",3,"123",TypeUtilisateur.ADMINISTRATIF);
        global.ajouterMembres(admin,sylvain,guillaume,salim,nadege,nadege2,nadege3);




        // FIN zone de TEST





        this.port = port;
    }

    public int start(){
        try {
            socketServer = new ServerSocket(port);
            Socket socket = socketServer.accept();
            System.out.println("Nouvelle Connexion");
            ThreadServeur threadServeur= new ThreadServeur(socket,global,listeGroupe);
            Thread thread = new Thread(threadServeur);
            thread.start();


        } catch (IOException e) {

            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public static void main(String[] args) {
        Serveur s = new Serveur(12700);
        s.start();
    }
}
