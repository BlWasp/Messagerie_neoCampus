package net;


import discussion.FilDeDiscussion;
import discussion.Message;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentSkipListSet;

import static net.Paquet.Action.AUTHENTIFICATION;
import static net.Paquet.Action.REQUETTE;
import static utilisateurs.Utilisateur.Privilege.USER;

public class Client extends SupportPricipal{
    ObjectOutputStream outToServer;
    Socket socket;
    String host;
    int port;

    public Client(String host, int port) {
        super(null, new ConcurrentSkipListSet<GroupeNomme>(), new ConcurrentSkipListSet<FilDeDiscussion>(),new Groupe());
        this.host = host;
        this.port = port;
        outToServer = null;
        socket = null;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }



    public int ajouterFilDeDiscussion(FilDeDiscussion f){
        if(getUtilisateurCourant()==null) return -1;
        if(f==null) return -2;
        return super.getListeFilDeDiscussion().add(f)? 1 : 0;

    }
    
 
    public int ajouterMembres(Utilisateur m) {
        if(getUtilisateurCourant()==null) return -1;
        if(getUtilisateurCourant().getPrivilege()==USER) return -2;
        super.getGlobal().ajouterMembres(m);
        return 1;
    }


    public int retirerMembres(Utilisateur u) {
        if(getUtilisateurCourant()==null) return -1;
        if(getUtilisateurCourant().getPrivilege()==USER) return -2;
        return super.getGlobal().retirerMembres(u);
    }


    public int ajouterMessage(String message,FilDeDiscussion f){
        if(getUtilisateurCourant()==null) return -1;
        if(message==null || f==null) return -2;
        f.ajouterMessage(new Message(getUtilisateurCourant(),f,message));
        return 1;
    }

    public int retirerMessage(Message message,FilDeDiscussion f){
        if(getUtilisateurCourant()==null) return -1;
        if(message==null || f==null) return -2;
        if(message.getFrom()!=getUtilisateurCourant()) return -3;
        return f.retirerMessage(message);
    }

    public int ajouterGroupe(GroupeNomme grp){
        if(getUtilisateurCourant()==null) return -1;
        if(grp==null) return -2;
        return super.getListeGroupe().add(grp)? 1 : 0 ;
    }
    public int retirerGroupe(GroupeNomme grp){
        if(getUtilisateurCourant()==null) return -1;
        if(grp==null) return -2;
        return super.getListeGroupe().remove(grp)?1:0;
    }



    public int connecter(){
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            return 0; // Serveur Off, deco d'net ect ...
        }
        return 1;
    }

    public int deconnecter(){
        if(socket==null) return 0;
        try {
            socket.close();
            socket=null;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    public int authentification(int id, String mdp) {

        Paquet authPaquet = new Paquet(AUTHENTIFICATION, new Utilisateur("","",id,mdp,null));
        Paquet recu = requette(authPaquet);
        if(recu==null)return -1;
        if(recu.getUtilisateurCourant()==null)return 0;
        this.setUtilisateurCourant(recu.getUtilisateurCourant());
        return 1;
    }


    public int actualiser(){
        Paquet reqPaquet = new Paquet(REQUETTE,null);
        Paquet recu = requette(reqPaquet);
        if(recu==null)return -1;
        // TODO Verification contenu paquet recu


        return 1;
    }

    private Paquet requette(Paquet paquetRequette){
        Paquet recu=null;
        envoyerObjet(socket,paquetRequette);
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            try {
                Object instruction = in.readObject();
                if (instruction.getClass() == Paquet.class) {
                    recu = (Paquet) instruction;
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return recu;
    }

    private void envoyerObjet(Socket s, Object o){
        ObjectOutputStream outToServer = null;
        try {
            outToServer = new ObjectOutputStream(s.getOutputStream());
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

