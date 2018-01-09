package net;

import discussion.FilDeDiscussion;
import paquet.Connexion;
import paquet.Paquet;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class Serveur{
    private ConcurrentSkipListSet<GroupeNomme> listeGroupe ;
    private ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion;
    private Groupe global ;
    ServerSocket sSocket;
    NavigableSet<Thread> listeServerThread = new TreeSet<>();
    boolean enFonctionnement;

    public Serveur() {
        this.enFonctionnement = false;
    }

    public void stop(){
        enFonctionnement =false;
    }

    public void start() throws IOException {
        try {
            sSocket = new ServerSocket(12700);
            enFonctionnement = true;
        } catch (IOException e) {
            enFonctionnement = false;
        }


        while(enFonctionnement){
            Socket socket = sSocket.accept();
            paquet.Serveur serveur = new paquet.Serveur(socket,listeGroupe,listeFilDeDiscussion,global);
            Thread serveurThread = new Thread(serveur);
            listeServerThread.add(serveurThread);
            serveurThread.start();
        }
    }







}
