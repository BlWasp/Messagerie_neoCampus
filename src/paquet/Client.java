package paquet;

import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) throws IOException {
        List<GroupeNomme> listeGrroupe = new ArrayList<>();
        Groupe global = new Groupe();
        Utilisateur utilisateurCourant = null;

        Socket clientSocket = null;
        ObjectOutputStream outToServer=null;
        try {
            clientSocket = new Socket("127.0.0.1", 6791);

            // Authentification
            utilisateurCourant = authentification(clientSocket, 0,"admin") ;
            if(utilisateurCourant==null) System.out.println("Echec d'authentification");
            else System.out.println("Connecté en tant que: "+ utilisateurCourant);





        } catch (IOException e) {
            System.out.println("Server off");
        }




















    }
    static Utilisateur authentification(Socket s, int id, String mdp) {
        Utilisateur user = null;
        ObjectOutputStream outToServer = null;

        try {
            outToServer = new ObjectOutputStream(s.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outToServer.writeObject(new Connexion(id, mdp));
        } catch (IOException e) {
            e.printStackTrace();
        }
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










}

