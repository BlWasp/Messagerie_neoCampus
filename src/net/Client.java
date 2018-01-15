package net;

import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;

import static utilisateurs.TypeUtilisateur.ENSEIGNANT;

/**
 * Client, voir exemple d'utilisation dans le main
 * Ne pas oublier de lancer le serveur !
 */
public class Client {
    ConcurrentSkipListSet<GroupeNomme> listeGroupe= new ConcurrentSkipListSet<>();// List local des groupes genre L3,L3 info,L2, etc...
    Groupe groupeGlobal = new Groupe(); // Ensemble des utilisateurs
    ObjectOutputStream out;
    ObjectInputStream in;
    Utilisateur utilisateurCourant; //Utilisateur connecté, null si déconnecté
    Socket socket;
    String host;
    int port;

    /**
     * Constructeur
     * @param host adresse du serveur
     * @param port port du serveur
     */
    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Methode pour connecter le client au serveur, doit etre suivi de la methode déconnecte()
     * @return 0 si echec, 1 si reussi,-2 si le port est invalide
     */
    public int connect(){
        if(port>65535 || port<1)return -2;// port invalide
        try {
            socket = new Socket(host,port);
            if(socket==null)return 0;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e){
            //e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**
     * Methode permettant de
     * @param name
     * @return
     */

    public GroupeNomme getGroupeName(String name){
        for (GroupeNomme g :
                this.listeGroupe) {
            if (g.getNom().equals(name)) {
                return g;
            }
        }
        return null;
    }

    /**
     * Getter su l'Utilisateur Courant, null si déconnecté.
     * @return L'utilisateur courant ou null si déconnecté.
     */
    public Utilisateur getUtilisateurCourant() {
        return utilisateurCourant;
    }

    /**
     * Methode permetant de s'authentifier auprès du serveur (la connexion doit etre préalablement établie)
     *
     * @param u Utiliser un objet Degenéré avec les champ id et mdp non vide !
     * @return 1 authentification réussie,-1 si le couple id/mdp est incorrecte, -2 si le serveur est innaccéssible,-3 autre,
     *
     */
    public int authentification(Utilisateur u){
        try {
            // Envoi une requette d'authentificaation
            out.writeObject(new net.Paquet(net.Paquet.Action.AUTHENTIFICATION,u,null,null));
            // Récéption de la réponse
            net.Paquet get = (net.Paquet) in.readObject();
            if(get.getUtilisateur()!=null){
                // Le paquet renvoyé contient l'object Utilisateur
                utilisateurCourant = get.getUtilisateur();
            }
            else return -1;
        } catch (IOException e) {
            return -2;
        }
        catch (ClassNotFoundException e) {
            return -3;
        }
        return 1;

    }

    /**
     * Methode permettant de synchroniser les données à partir du serveur
     * @return 1 synchro réussie, -1 pb de connexion, -2 autre
     */
    public int download(){
        net.Paquet get=null;
        try {
            out.writeObject(new net.Paquet(net.Paquet.Action.REQUETTE,utilisateurCourant,null,null));
            get = (net.Paquet) in.readObject();

            this.utilisateurCourant = get.getUtilisateur();
            this.groupeGlobal = get.getGroupeGlobal();
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

    /**
     * Méthode permettant de synchroniser les données du serveur
     * @return 1 synchronisation réussie,-1 echec de synchronisation
     */
    public int upload(){
        net.Paquet get=null;
        try {
            out.writeObject(new net.Paquet(net.Paquet.Action.REPONSE,utilisateurCourant,listeGroupe,groupeGlobal));
        } catch (IOException e) {
            return -1;
        }
        return 1;
    }

    /**
     * Methode Permettant la déconnexion du client
     * (Peut etre appellée même si le client n'est pas connecté)
     */
    public void deconnect(){
        if(socket!=null){
            try {
                out.writeObject(new net.Paquet(net.Paquet.Action.DECONNECT,utilisateurCourant,null,null));
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket =null;
        }
    }

    /**
     * Getter sur la liste de Groupe
     * @return liste de groupe
     */
    public ConcurrentSkipListSet<GroupeNomme> getListeGroupe() {
        return listeGroupe;
    }

    /**
     * Getter sur la liste global des utilisateurs
     * @return Groupe d'utilisateur
     */
    public Groupe getGroupeGlobal() {
        return groupeGlobal;
    }
    
    public GroupeNomme getGroupeID(UUID ID){
        for (GroupeNomme g :
                this.listeGroupe) {
            if (g.getId().equals(ID)){
                return g;
            }
        }
        return null;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    /**
     * Test et exemples d'utilisation 
     * @param args
     */
    /*public static void main(String[] args) {
        Client c = new Client("127.0.0.1",12700);
        int errno;
        errno = c.connect();
        System.out.println(errno);
        if(errno==1){
            errno = c.authentification(new Utilisateur("","",0,"admin",null));
            System.out.println(errno);

            errno = c.download();
            System.out.println(errno);

            // Ajout de Membres
            errno = c.download();
            System.out.println(errno);
            c.getGroupeGlobal().ajouterMembres(new Utilisateur("Patrick","BLURP",36985,"mot",ENSEIGNANT));
            errno = c.upload();
            System.out.println(errno);

            //Ajout groupe
            errno = c.download();
            System.out.println(errno);
            c.listeGroupe.add(new GroupeNomme("L3"));
            c.listeGroupe.first().ajouterMembres(c.utilisateurCourant);
            errno = c.upload();
            System.out.println(errno);

            //Ajout fil de discussion et message au fil
            errno = c.download();
            System.out.println(errno);
            c.listeGroupe.first().ajouterFilDeDiscussion(c.utilisateurCourant,"La cuite, parlons-en");
            c.listeGroupe.first().getFilsDeDiscussion("La cuite, parlons-en").ajouterMessage(c.utilisateurCourant,"Vomir c'est repartir");
            errno = c.upload();
            System.out.println(errno);



        }








        c.deconnect();
    }*/

}
