package net;


import discussion.FilDeDiscussion;
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
    ServerSocket socketServer = null;
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

        GroupeNomme l3 = new GroupeNomme("L3");
        l3.ajouterMembres(admin);
        l3.ajouterMembres(salim);
        GroupeNomme l2 = new GroupeNomme("L2");
        GroupeNomme m2 = new GroupeNomme("M2");

        this.listeGroupe.add(l3);
        this.listeGroupe.add(l2);
        this.listeGroupe.add(m2);


        l3.ajouterFilDeDiscussion(admin,"WAZA");

        listeGroupe.add(l3);
        listeGroupe.add(l2);
        listeGroupe.add(m2);





        // FIN zone de TEST





        this.port = port;
    }

    public void start(){

        try {
            socketServer = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true) {
            try {
                Socket socket = socketServer.accept();
                System.out.println("Nouvelle connexion");
                ThreadServeur threadServeur = new ThreadServeur(socket, global, listeGroupe,this);
                Thread thread = new Thread(threadServeur);
                thread.start();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }



    }

    synchronized public void maj(ConcurrentSkipListSet<GroupeNomme> listeGroupe, Groupe groupe){
        this.listeGroupe = listeGroupe;
        this.global = groupe;
    }

    public static void main(String[] args) {
        Serveur s = new Serveur(12700);
        s.start();
    }
}
