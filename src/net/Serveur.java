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
import java.util.concurrent.ConcurrentSkipListSet;

public class Serveur {

    ObjectOutputStream out;
    ObjectInputStream in;
    ServerSocket socketServer = null;
    int port;

    /**
     * Constructeur
     * @param port Port sur lequel se connecter
     */
    public Serveur(int port){

        ConcurrentSkipListSet<GroupeNomme> listeGroupe= new ConcurrentSkipListSet<>();
        Groupe global = new Groupe();

        // Zone de TEST
        Utilisateur admin = new Utilisateur("Admin", "admin", 0, "admin", null);
        admin.setPrivilege(Utilisateur.Privilege.ADMIN);
        Utilisateur sylvain =new Utilisateur("DEKER","Sylvain",21400536,"123", TypeUtilisateur.ETUDIANT);
        Utilisateur salim =new Utilisateur("CHERIFI","Salim",21400537,"123", TypeUtilisateur.ETUDIANT);
        Utilisateur guillaume =new Utilisateur("DAUMAS","Guillaume",21400538,"123", TypeUtilisateur.ETUDIANT);

        Utilisateur nadege = new Utilisateur("Lamarque","Nadege",0,"123",TypeUtilisateur.ADMINISTRATIF);

        global.ajouterMembres(admin,sylvain,guillaume,salim,nadege);

        GroupeNomme l3 = new GroupeNomme("L3");
        l3.ajouterMembres(admin);
        l3.ajouterMembres(salim);
        //l3.ajouterMembres(guillaume);
        GroupeNomme l2 = new GroupeNomme("L2");
        l2.ajouterMembres(sylvain);
        GroupeNomme m2 = new GroupeNomme("M2");


        l3.ajouterFilDeDiscussion(admin,"Fil 1");
        l3.ajouterFilDeDiscussion(admin,"Fil 2");
        l3.ajouterFilDeDiscussion(admin,"Erreur réseau");

        l2.ajouterFilDeDiscussion(sylvain,"Fil 3");

        listeGroupe.add(l3);
        listeGroupe.add(l2);
        listeGroupe.add(m2);
        // FIN zone de TEST

        communicationBDD.upload(new Paquet(null,null,listeGroupe,global));

        this.port = port;
    }

    /**
     * Fonction start pour lancer les threads
     */
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
                ThreadServeur threadServeur = new ThreadServeur(socket,this);
                Thread thread = new Thread(threadServeur);
                thread.start();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }



    }

    /*
    synchronized public void maj(ConcurrentSkipListSet<GroupeNomme> listeGroupe, Groupe groupe){
        this.listeGroupe = listeGroupe;
        this.global = groupe;
    }
    */

    /**
     * Fonction main pour lancer le serveur
     * @param args
     */
    public static void main(String[] args) {
        Serveur s = new Serveur(12700);
        s.start();
    }
}
