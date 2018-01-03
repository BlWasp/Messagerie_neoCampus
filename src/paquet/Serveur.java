package paquet;

import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Serveur {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Partie BDD
        List<GroupeNomme> listeGrroupe = new ArrayList<>();
        Groupe global = new Groupe();
        // Fin partie BDD

        global.ajouterMembres(
           new Utilisateur("Deker","Sylvain",21400536,"0000", TypeUtilisateur.ETUDIANT));
        ServerSocket sSocket = new ServerSocket(6791);

        while (true) {
            Socket socket = sSocket.accept();
            ObjectInputStream in =
                    new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            Object instruction = in.readObject();
            System.out.println(instruction.getClass());
            if(instruction.getClass() == Connexion.class){
                Connexion requester = (Connexion) instruction;
               //  System.out.println(requester.getIdentifiant()+ requester.getMdp());
               for(Utilisateur u : global.getMembres()){

                   if(u.equals(new Utilisateur("","",requester.getIdentifiant(),requester.getMdp(),null)) ){
                       // Authentification réussi
                      // out.writeObject(u);
                       System.out.println("Authetification réussi");
                   }
               }

            }


        }
    }


}