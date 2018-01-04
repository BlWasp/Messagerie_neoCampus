package paquet;

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

public class Client {
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 6791;
        List<GroupeNomme> listeGroupe = new ArrayList<>();
        Groupe global = new Groupe();
        Utilisateur utilisateurCourant = null;

        Socket clientSocket = null;
        ObjectOutputStream outToServer=null;

        // Authentification
        utilisateurCourant = authentification(host,port, 0,"admin") ; // On se connecte avec id = 0 , mdp = admin
        if(utilisateurCourant==null) System.out.println("Echec d'authentification");
        else System.out.println("Connecté en tant que: "+ utilisateurCourant);

        if(utilisateurCourant!=null){ // null = déconnecté
            // Ajout d'un Utilisateur (à restreindre au compte admin ..)
            Utilisateur u = new Utilisateur("Daumas","Guillaume",789,"yolo", TypeUtilisateur.ETUDIANT);
            envoyerObjetSansReponse(host,port,new Paquet(Paquet.Action.ADD,u));
        }


    }


    static Utilisateur authentification(String host,int port,int id, String mdp) {
        Utilisateur user = null;
        ObjectOutputStream outToServer = null;
        Socket s = null;
        try {
            s = new Socket(host, port);
        } catch (IOException e) {
            System.out.println("Echec de connexion : Serveur off ?");
           return null;
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
                        user =  cx.getUtilisateur();
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    static void envoyerObjet(Socket s, Object o){
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

    static void envoyerObjetSansReponse(String host,int port,Object o) {
        try {
            Socket s = new Socket(host,port);
            envoyerObjet(s,o);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

