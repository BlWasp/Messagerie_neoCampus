package paquet;

import discussion.FilDeDiscussion;
import discussion.Message;
import org.apache.log4j.Logger;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import java.util.ArrayList;
import java.util.List;

import static paquet.Paquet.Action.ADD;
import static paquet.Paquet.Action.MAJ;
import static paquet.Paquet.Action.SUPP;
import static utilisateurs.Utilisateur.Privilege.ADMIN;

public class Client extends Groupe{
        String host;
        int port;
        List<GroupeNomme> listeGroupe = new ArrayList<>();
        List<FilDeDiscussion> listeFilDeDiscussion = new ArrayList<>();
        Utilisateur utilisateurCourant;

        private static Logger LOGGER = Logger.getLogger(Client.class);

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.utilisateurCourant = null;
    }
    public Client() {
        this.host = "127.0.0.1";
        this.port = 12700;
        this.utilisateurCourant = null;
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

    public void authentification(int id, String mdp) {

        // TODO Gerer le cas deco / reco
        ObjectOutputStream outToServer = null;
        Socket s = null;
        try {
            s = new Socket(host, port);
        } catch (IOException e) {
           // System.out.println("Echec de connexion : ChoixServeur off ?");
           return ;
        }
        envoyerObjet(s,new Connexion(id,mdp));
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(s.getInputStream());
            try {
                Object instruction = in.readObject();
                if (instruction.getClass() == Connexion.class) {
                    Connexion cx = (Connexion) instruction;
                    if (cx.getUtilisateur() != null) {
                        utilisateurCourant =  cx.getUtilisateur();
                        this.ajouterMembres(utilisateurCourant);
                        this.ajouterMembres(cx.getGlobal());
                        this.listeFilDeDiscussion.addAll(cx.getListeFilDeDiscussion());
                        this.listeGroupe.addAll(cx.getListeGroupe());
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ajouterFilDeDiscussion(FilDeDiscussion f){
        if(utilisateurCourant==null) System.exit(103);//Temporaire à remplacer pas des exeptions
        this.listeFilDeDiscussion.add(f);
        envoyerObjetSansReponse(new Paquet(ADD,f));
    }
    @Override
    public void ajouterMembres(Utilisateur m) {
        //if(utilisateurCourant==null) System.exit(101);//Temporaire à remplacer pas des exeptions
        if (utilisateurCourant.privilege == ADMIN) {
            super.ajouterMembres(m);
            envoyerObjetSansReponse(new Paquet(ADD, m));
        } else {
            System.out.println("Opération refusée, des privilèges administrateurs sont nécessaires");
        }
    }

    @Override
    public int retirerMembres(Utilisateur u) {
        if(utilisateurCourant==null){
            LOGGER.error("Pas d'utilisateur courant veuillez vous identifier");
            System.exit(103);
        }
        if (utilisateurCourant.privilege == ADMIN) {
            int res = super.retirerMembres(u);
            if (res == 1) envoyerObjetSansReponse(new Paquet(SUPP, u));
            return res;
        } else {
            System.out.println("Opération refusée, des privilèges administrateurs sont nécessaires");
            return 0;
        }
    }

    public int majMembres(Utilisateur u) {
        if(utilisateurCourant==null){
            LOGGER.error("Pas d'utilisateur courant veuillez vous identifier");
            System.exit(103);
        }
        if (utilisateurCourant.privilege == ADMIN) {
            int res  = super.retirerMembres(u);
            super.ajouterMembres(u);
            if (res == 1) envoyerObjetSansReponse(new Paquet(MAJ, u));
            return res;
        } else {
            System.out.println("Opération refusée, des privilèges administrateurs sont nécessaires");
            return 0;
        }
    }


    public int ajouterMessage(String message,FilDeDiscussion f){
        Message m = new Message(utilisateurCourant,f,message);
        if(utilisateurCourant==null){
            LOGGER.error("Pas d'utilisateur courant, Veuillez vous authentifier");
            System.exit(107);//Temporaire à remplacer pas des exeptions
        }
        if( f.ajouterMessage(m)!=null){
            envoyerObjetSansReponse(new Paquet(ADD,m,f.getId()));
            return 1;
        }
        return 0;
    }

    public void gestionGroupeNomme(GroupeNomme g, Paquet.Action ac) {
        if(utilisateurCourant==null){
            LOGGER.error("Pas d'utilisateur courant, Veuillez vous authentifier");
            System.exit(107);//Temporaire à remplacer pas des exeptions
        }

        if (utilisateurCourant.privilege == ADMIN) {
            if (ac==ADD) {
                listeGroupe.add(g);
                envoyerObjetSansReponse(new Paquet(ADD,g));
            }
            if (ac==SUPP) {
                listeGroupe.remove(g);
                envoyerObjetSansReponse(new Paquet(SUPP,g));
            }
            if (ac==MAJ) {
                listeGroupe.remove(g);
                listeGroupe.add(g);
                envoyerObjetSansReponse(new Paquet(MAJ,g));
            }
        } else {
            System.out.println("Opération refusée, des privilèges administrateurs sont nécessaires");
        }
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

    private void envoyerObjetSansReponse(Object o) {
        try {
            Socket s = new Socket(this.host,this.port);
            envoyerObjet(s,o);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Utilisateur getUtilisateurCourant() {
        return utilisateurCourant;
    }

    public List getListeGroupe() {
        return listeGroupe;
    }

    public List getListeFilDeDiscussion() {
        return listeFilDeDiscussion;
    }

    //TODO a supprimer utilisé que pour les tests sans authentification
    public void setUtilisateurCourant(Utilisateur utilisateurCourant) {
        this.utilisateurCourant = utilisateurCourant;
    }
}

