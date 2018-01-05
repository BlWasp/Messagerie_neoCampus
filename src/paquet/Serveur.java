package paquet;

import discussion.FilDeDiscussion;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class Serveur implements Runnable{
    Socket socket;
    ConcurrentSkipListSet<GroupeNomme> listeGroupe ;
    ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion;
    Groupe global ;

    public Serveur(Socket socket, ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion, Groupe global) {
        this.socket = socket;
        this.listeGroupe = listeGroupe;
        this.listeFilDeDiscussion = listeFilDeDiscussion;
        this.global = global;
    }

    //
//    public Serveur(Socket s) {
//        try{
//            System.out.println("Le client peut se connecter ");
//            socket = s;
//        }catch(Exception e){e.printStackTrace();}
//    }

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
            }else if(instruction.getClass() == Paquet.class){ // Ajout ou maj ou sup d'un Groupe, Utilisateur, File
                Paquet p = (Paquet) instruction; //Paquet
                gestionPaquet(p, listeGroupe,listeFilDeDiscussion,global);
            }
        }catch(IOException e){e.printStackTrace();}
    }


    /*public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Partie BDD
        List<GroupeNomme> listeGroupe = new ArrayList<>();
        List<FilDeDiscussion> listeFilDeDiscussion = new ArrayList<>();
        Groupe global = new Groupe();
        // Fin partie BDD

        global.ajouterMembres(
                new Utilisateur("admin", "admin", 0, "admin", null));
        ServerSocket sSocket = new ServerSocket(6791);

        while (true) {
            ///////////////////ZONE DE TEST

            System.out.println("Utilisateurs : ");
            System.out.println(global);

            ///////////////////FIN ZONE DE TEST
            Socket socket = sSocket.accept();
            Serveur server = new Serveur(socket,listeGroupe,listeFilDeDiscussion,global);
            Thread serveurThread = new Thread(server);
            serveurThread.start();
        }
    }*/


    static synchronized void authentification(Groupe global, Connexion requester, ObjectOutputStream out){
        for(Utilisateur u : global.getMembres()){

            if(u.equals(new Utilisateur("","",requester.getIdentifiant(),requester.getMdp(),null)) ){
                // Authentification réussi
                try {
                    out.writeObject(new Connexion(u));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Authentification réussie");
            }
        }

    }

    static synchronized void gestionPaquet(Paquet p, ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion, Groupe global){

        if(p.getObject().getClass()== FilDeDiscussion.class){
            FilDeDiscussion f = (FilDeDiscussion) p.getObject();
            gestionFilDeDiscussion(f,p.getAction(),listeGroupe,listeFilDeDiscussion,global);
        }else if(p.getObject().getClass() == Utilisateur.class){
            Utilisateur u = (Utilisateur) p.getObject();
            gestionUtilisateur(u,p.getAction(),listeGroupe,listeFilDeDiscussion,global);

        }else if(p.getObject().getClass() == GroupeNomme.class) {
            GroupeNomme g = (GroupeNomme) p.getObject();


        }
    }

    static synchronized void gestionUtilisateur(Utilisateur u, Paquet.Action action,  ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion, Groupe global){
        if(action == Paquet.Action.ADD){
            global.ajouterMembres(u);
           // System.out.println("Ajout de l'utilisateur recu");
          //  System.out.println(global);
        }else if(action == Paquet.Action.MAJ){
            global.retirerMembres(u);
            global.ajouterMembres(u);
        }else if(action == Paquet.Action.SUPP){
            System.out.println("Retrait de "+ u);
            global.retirerMembres(u);
        }
        // TODO Maj Tout les autres client
    }

    static synchronized void gestionFilDeDiscussion(FilDeDiscussion f, Paquet.Action action, ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion, Groupe global){
        if(action == Paquet.Action.ADD){
            global.ajouterMembres(f);
            listeFilDeDiscussion.add(f);
        }else if(action == Paquet.Action.MAJ){
            // TODO
        }else if(action == Paquet.Action.SUPP){
            // TODO
        }

    }

    static synchronized void gestionGroupeNomme(GroupeNomme g ,Paquet.Action action,  ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion, Groupe global){
        if(action== Paquet.Action.ADD){
            listeGroupe.add(g);
            global.ajouterMembres(g);
        }else if(action== Paquet.Action.MAJ){
            // TODO
        }else if(action== Paquet.Action.SUPP){
            // TODO
        }
    }


}