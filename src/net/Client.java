package net;

import discussion.FilDeDiscussion;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;

public class Client {
    ConcurrentSkipListSet<GroupeNomme> listeGroupe= new ConcurrentSkipListSet<>(Comparator.comparing(GroupeNomme::getNom));
    Groupe global = new Groupe();
    ObjectOutputStream out;
    ObjectInputStream in;
    Utilisateur utilisateurCourant;
    Socket socket;
    String host;
    int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public int connect(){
        try {
            socket = new Socket(host,port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());


        } catch (IOException e){
            e.printStackTrace();
            return 0;
        }
        return 1;
    }


    public Utilisateur getUtilisateurCourant() {
        return utilisateurCourant;
    }

    public int authentification(Utilisateur u){
        try {
            out.writeObject(new net.Paquet(net.Paquet.Action.AUTHENTIFICATION,u,null,null));
            net.Paquet get = (net.Paquet) in.readObject();
            if(get.getUtilisateur()!=null){
                utilisateurCourant = get.getUtilisateur();

            }
            else return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -2;
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;

    }
    public int download(){
        net.Paquet get=null;
        try {
            out.writeObject(new net.Paquet(net.Paquet.Action.REQUETTE,utilisateurCourant,null,null));
            get = (net.Paquet) in.readObject();

            this.utilisateurCourant = get.getUtilisateur();
            this.global = get.getGlobal();
            this.listeGroupe = get.getListeGroupe();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return -2;
        }
        return 1;
    }

    public int upload(){
        net.Paquet get=null;
        try {
            out.writeObject(new net.Paquet(net.Paquet.Action.REPONSE,utilisateurCourant,listeGroupe,global));
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    public void deconnect(){
        try {
            out.writeObject(new net.Paquet(net.Paquet.Action.DECONNECT,utilisateurCourant,null,null));
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket =null;
    }

    public ConcurrentSkipListSet<GroupeNomme> getListeGroupe() {
        return listeGroupe;
    }

    public Groupe getGlobal() {
        return global;
    }

    public static void main(String[] args) {
        Client c = new Client("127.0.0.1",12700);
        int errno;
        errno = c.connect();
        System.out.println(errno);

        errno = c.authentification(new Utilisateur("","",0,"admin",null));
        System.out.println(errno);




        c.deconnect();
    }

}
