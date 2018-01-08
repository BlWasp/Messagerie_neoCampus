package paquet;

import discussion.FilDeDiscussion;
import discussion.Message;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class Serveur implements Runnable{
    private Socket socket;
    private ConcurrentSkipListSet<GroupeNomme> listeGroupe ;
    private ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion;
    private Groupe global ;

    public Serveur(Socket socket, ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion, Groupe global) {
        this.socket = socket;
        this.listeGroupe = listeGroupe;
        this.listeFilDeDiscussion = listeFilDeDiscussion;
        this.global = global;
    }


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


            if (instruction.getClass() == Connexion.class) {
                Connexion requester = (Connexion) instruction;
                authentification(global, requester, out,listeGroupe,listeFilDeDiscussion);
            }else if(instruction.getClass() == Paquet.class){ // Ajout ou maj ou sup d'un Groupe, Utilisateur, File
                Paquet p = (Paquet) instruction; //Paquet
                gestionPaquet(p, listeGroupe,listeFilDeDiscussion,global);
            }
        }catch(IOException e){e.printStackTrace();}
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    static synchronized void authentification(Groupe global, Connexion requester, ObjectOutputStream out,ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion){
        Utilisateur co = null;
        for(Utilisateur u : global.getMembres()){

            if(u.equals(new Utilisateur("","",requester.getIdentifiant(),requester.getMdp(),null)) ){
               co =u;
            }
        }
        // Authentification réussi
        List<GroupeNomme> listeGroupeTemp = new ArrayList<>();
        List<FilDeDiscussion> listeFilDeDiscussionTemp = new ArrayList<>();
        for(GroupeNomme grpn : listeGroupe){
            if(grpn.estMembre(co) || co.getPrivilege()== Utilisateur.Privilege.ADMIN )  listeGroupeTemp.add(grpn);
        }
        for(FilDeDiscussion f : listeFilDeDiscussion){
            if(f.estMembre(co) || co.getPrivilege()== Utilisateur.Privilege.ADMIN ) listeFilDeDiscussionTemp.add(f);
        }

        try {
            out.writeObject(new Connexion(co,listeGroupeTemp,listeFilDeDiscussionTemp,global));
        } catch (IOException e) {
            e.printStackTrace();
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
            // TODO
        } else if(p.getObject().getClass()==Message.class){
//            Ajout du message m au fil De Discussion ayant l'UUID id
            Message m = (Message)p.getObject();
            UUID id = p.getUuid() ;
            gestionMessage(m,id,p.getAction(),listeGroupe,listeFilDeDiscussion,global);
        }
    }

    static synchronized void gestionUtilisateur(Utilisateur u, Paquet.Action action,  ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion, Groupe global){
        if(action == Paquet.Action.ADD){
            global.ajouterMembres(u);
           System.out.println("Ajout de l'utilisateur recu");
          //  System.out.println(global);
        }else if(action == Paquet.Action.MAJ){
            global.retirerMembres(u);
            global.ajouterMembres(u);
        }else if(action == Paquet.Action.SUPP){
            global.retirerMembres(u);
        }
        // TODO Maj Tout les autres client
    }

    static synchronized void gestionFilDeDiscussion(FilDeDiscussion f, Paquet.Action action, ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion, Groupe global){
        if(action == Paquet.Action.ADD){
//            global.ajouterMembres(f);
//            System.out.println("Ajout du fil");
            listeFilDeDiscussion.add(f);
        }else if(action == Paquet.Action.MAJ){
//            UUID id = f.getId();
//            listeFilDeDiscussion.remove(id);
//            global.ajouterMembres(f);
//            listeFilDeDiscussion.add(f);
            System.err.println("Erreur pas de MAJ pour FILS DE DISCUSSION !!");
            System.exit(25);
        }else if(action == Paquet.Action.SUPP){
            listeFilDeDiscussion.remove(f);
        }
        // TODO Maj Tout les utilisateurs concerné
    }
    static private FilDeDiscussion trouverFilDeDiscussion(UUID filid,ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion){
        for (FilDeDiscussion f : listeFilDeDiscussion){
            if(f.getId().equals(filid)) return f;
        }
        return null;
    }

    static synchronized  void gestionMessage(Message m, UUID fil, Paquet.Action action, ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion, Groupe global){
        FilDeDiscussion f = trouverFilDeDiscussion(fil,listeFilDeDiscussion);
        if(f==null) System.exit(404);
        if(action== Paquet.Action.ADD){
            f.ajouterMessage(m) ;
        }else if(action== Paquet.Action.MAJ){
            f.majMessage(m);
        }else if(action== Paquet.Action.SUPP){
            f.retirerMessage(m);
        }
    }

    static synchronized void gestionGroupeNomme(GroupeNomme g ,Paquet.Action action,  ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion, Groupe global){
        if(action== Paquet.Action.ADD){
            listeGroupe.add(g);
            global.ajouterMembres(g);
        }else if(action== Paquet.Action.MAJ){
            listeGroupe.remove(g);
            listeGroupe.add(g);
        }else if(action== Paquet.Action.SUPP){
            listeGroupe.remove(g);
        }
    }


}