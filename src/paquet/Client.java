package paquet;

import discussion.FilDeDiscussion;
import discussion.Message;
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

public class Client extends Groupe{
        String host;
        int port;
        List<GroupeNomme> listeGroupe = new ArrayList<>();
        List<FilDeDiscussion> listeFilDeDiscussion = new ArrayList<>();
        Utilisateur utilisateurCourant;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        this.utilisateurCourant = null;
    }





    public void authentification(int id, String mdp) {

        // TODO Gerer le cas deco / reco
        ObjectOutputStream outToServer = null;
        Socket s = null;
        try {
            s = new Socket(host, port);
        } catch (IOException e) {
           // System.out.println("Echec de connexion : Serveur off ?");
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
        if(utilisateurCourant==null) System.exit(101);//Temporaire à remplacer pas des exeptions
        // TODO utilisateur courant == admin?
        super.ajouterMembres(m);
        envoyerObjetSansReponse(new Paquet(ADD,m));
    }

    @Override
    public int retirerMembres(Utilisateur u) {
        if(utilisateurCourant==null) System.exit(102);//Temporaire à remplacer pas des exeptions
        // TODO utilisateur courant == admin?
        int res = super.retirerMembres(u);
        if(res==1) envoyerObjetSansReponse(new Paquet(Paquet.Action.SUPP,u));
        return res;
    }
    public int ajouterMessage(Message m,FilDeDiscussion f){
        if(utilisateurCourant==null) System.exit(107);//Temporaire à remplacer pas des exeptions
        if( f.ajouterMessage(m)!=null){
            envoyerObjetSansReponse(new Paquet(ADD,m,f.getId()));
            return 1;
        }
        return 0;
    }

    //////////PRIVATE
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
}

/*

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


 */