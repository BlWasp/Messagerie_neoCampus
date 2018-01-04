package paquet;

import discussion.FilDeDiscussion;
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

public class Serveur implements Runnable{
    Socket socket;

    // Partie BDD
    List<GroupeNomme> listeGrroupe = new ArrayList<>();
    List<FilDeDiscussion> listeFilDeDiscussion = new ArrayList<>();
    static Groupe global = new Groupe();
    // Fin partie BDD

    public Serveur(Socket s) {
        try{
            System.out.println("Le client peut se connecter ");
            socket = s;
        }catch(Exception e){e.printStackTrace();}
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in =
                    new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out =
                    new ObjectOutputStream(socket.getOutputStream());

            Object instruction = null;
            try {
                instruction = in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            System.out.println(instruction.getClass());
            if (instruction.getClass() == Connexion.class) {
                Connexion requester = (Connexion) instruction;
                authentification(global, requester, out);
            } else if (instruction.getClass() == FilDeDiscussion.class) {
                FilDeDiscussion f = (FilDeDiscussion) instruction;
                listeFilDeDiscussion.add(f);
                // TODO
            }
        }catch(IOException e){e.printStackTrace();}
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        global.ajouterMembres(
           new Utilisateur("admin","admin",0,"admin", null));

        ServerSocket sSocket = new ServerSocket(6791);

        while (true) {
            Socket socket = sSocket.accept();
            Serveur server = new Serveur(socket);
            Thread serveurThread = new Thread(server);
            serveurThread.start();
        }
    }

    static void authentification(Groupe global, Connexion requester, ObjectOutputStream out){
        for(Utilisateur u : global.getMembres()){

            if(u.equals(new Utilisateur("","",requester.getIdentifiant(),requester.getMdp(),null)) ){
                // Authentification réussi
                try {
                    out.writeObject(new Connexion(u));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Authetification réussi");
            }
        }

    }










}