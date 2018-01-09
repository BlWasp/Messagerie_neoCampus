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
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

import static net.Paquet.Action.REPONSE;

public class ThreadServeur extends SupportNet implements Runnable {


    public ThreadServeur( Socket socket,Utilisateur utilisateurCourant, ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion, Groupe global) {
        super(socket,utilisateurCourant, listeGroupe, listeFilDeDiscussion, global);

    }
    public void run() {
        try {
            ObjectInputStream in =
                    new ObjectInputStream(getSocket().getInputStream());
            ObjectOutputStream out =
                    new ObjectOutputStream(getSocket().getOutputStream());

            Object instruction = null;
            try {
                instruction = in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(instruction.getClass() == net.Paquet.class){
                // TODO
            }
            else System.err.println("Paquet recu ilisible");
        }catch(IOException e){e.printStackTrace();}
        finally {
            try {
                getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void gestionPaquet(net.Paquet paquet) {
        if(paquet.getAction()== net.Paquet.Action.AUTHENTIFICATION){
            authentification(paquet);
        }else if(paquet.getAction()== net.Paquet.Action.REQUETTE) {
            envoyerObjet(new net.Paquet(REPONSE,paquet.getUtilisateurCourant(),getListeGroupe(),getListeFilDeDiscussion(),getGlobal()));
        }else if (paquet.getAction()== net.Paquet.Action.REPONSE){
            setGlobal(paquet.getGlobal());
            setListeGroupe(paquet.getListeGroupe());
            setListeFilDeDiscussion(paquet.getListeFilDeDiscussion());
            setUtilisateurCourant(paquet.getUtilisateurCourant());
        }
    }

    private void authentification(net.Paquet paquet){
        Utilisateur co = null;
        for(Utilisateur u : getGlobal().getMembres()){

            if(u.equals(paquet.getUtilisateurCourant())){
                co =u;
            }
        }
        // Authentification réussi
        net.Paquet data = new net.Paquet(REPONSE,co,getListeGroupe(),getListeFilDeDiscussion(),getGlobal());
        envoyerObjet(data);
    }



}
