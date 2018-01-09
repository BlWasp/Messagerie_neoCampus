package net;

import discussion.FilDeDiscussion;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentSkipListSet;

public abstract class SupportNet extends SupportPricipal {
    private Socket socket;

    public SupportNet( Socket socket,Utilisateur utilisateurCourant, ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion, Groupe global) {
        super(utilisateurCourant, listeGroupe, listeFilDeDiscussion, global);
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void envoyerObjet(Object o){
        if(socket==null)System.exit(777);
        ObjectOutputStream outToServer = null;
        try {
            outToServer = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outToServer.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
