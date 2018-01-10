package net;

import discussion.FilDeDiscussion;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentSkipListSet;

public class ThreadServeur implements Runnable {
    private Groupe global;
    private ConcurrentSkipListSet<GroupeNomme> listeGroupe;
    Socket socket;
    ObjectOutputStream out;
    ObjectInputStream in;
    boolean connecte;

    public ThreadServeur( Socket socket, Groupe global, ConcurrentSkipListSet<GroupeNomme> listeGroupe) {
        this.socket = socket;
        this.listeGroupe = listeGroupe;
        this.global =global;

        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.connecte =true;
        } catch (IOException e) {
            e.printStackTrace();
            this.connecte=false;
        }
    }


    @Override
    public void run() {
        while(connecte){
            try {
                Paquet paquet = (Paquet) in.readObject();
                if(paquet.getAction()== Paquet.Action.AUTHENTIFICATION){
                    System.out.println("Demande d'authentification de "+paquet.getUtilisateur().getIdentifiant());

                    Paquet retour = authentification(paquet);
                    out.writeObject(retour);
                }
                else if(paquet.getAction()== Paquet.Action.REQUETTE){
                    System.out.println("Demande de téléchargement pour de "+paquet.getUtilisateur().getIdentifiant());
                    Paquet retour = new Paquet(Paquet.Action.REPONSE,paquet.getUtilisateur(),listeGroupe,global);
                    out.writeObject(retour);
                }else if(paquet.getAction()== Paquet.Action.REPONSE){
                    System.out.println("Envoi infos depuis le Client " + paquet.getUtilisateur().getIdentifiant());
                    this.global = paquet.getGroupeGlobal();
                    this.listeGroupe = paquet.getListeGroupe();
                }
                
                else if (paquet.getAction()== Paquet.Action.DECONNECT){
                    System.out.println("Déconnexion de "+paquet.getUtilisateur());
                    connecte=false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }//while

    }

    Paquet authentification(Paquet paquet){
        Utilisateur co =null;
        for(Utilisateur u : global.getMembres() ){
            if(u.getIdentifiant()==paquet.getUtilisateur().getIdentifiant()  && u.getMotDePasse().equals(paquet.getUtilisateur().getMotDePasse())){
                co =u;
            }
        }
        System.out.println(co);
        return new Paquet(Paquet.Action.AUTHENTIFICATION,co,null,null);
    }

}


