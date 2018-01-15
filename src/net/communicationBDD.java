package net;

import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

import java.io.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Permet la communication avec la BDD
 */
public class communicationBDD {

    /**
     * Permet d'envoyer un paquet au serveur pour le synchroniser avec le client
     * @param paquet Paquet à envoyer au serveur
     */
    public static void upload(Paquet paquet){
        try {
            FileOutputStream fichier = new FileOutputStream("NeoCampus.bdd");
            ObjectOutputStream out = new ObjectOutputStream(fichier);
            out.writeObject(paquet);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de télécharger un paquet depuis le serveur pour se synchroniser
     * @return le paquet télécharger
     */
    public static Paquet download(){
        Paquet paquet = null;
        try {
            FileInputStream fichier = new FileInputStream("NeoCampus.bdd");
            ObjectInputStream in = new ObjectInputStream(fichier);
            paquet = (Paquet) in.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(paquet==null)System.exit(3333);
        return paquet;
    }

    public static void main(String[] args) {
        /*ConcurrentSkipListSet<GroupeNomme> listeGroupe= new ConcurrentSkipListSet<>();
        Groupe global = new Groupe();

        Utilisateur admin = new Utilisateur("Admin", "admin", 0, "admin", null);
        admin.setPrivilege(Utilisateur.Privilege.ADMIN);
        Utilisateur sylvain =new Utilisateur("DEKER","Sylvain",21400536,"123", TypeUtilisateur.ETUDIANT);
        Utilisateur salim =new Utilisateur("CHERIFI","Salim",21400537,"123", TypeUtilisateur.ETUDIANT);
        Utilisateur guillaume =new Utilisateur("DAUMAS","GUILLAUME",21400538,"123", TypeUtilisateur.ETUDIANT);

        Utilisateur nadege = new Utilisateur("Lamarque","Nadege",0,"123",TypeUtilisateur.ADMINISTRATIF);
        Utilisateur nadege2 = new Utilisateur("Lamarque2","Nadege2",2,"123",TypeUtilisateur.ADMINISTRATIF);
        Utilisateur nadege3 = new Utilisateur("Lamarque3","Nadege3",3,"123",TypeUtilisateur.ADMINISTRATIF);
        global.ajouterMembres(admin,sylvain,guillaume,salim,nadege,nadege2,nadege3);


        GroupeNomme l3 = new GroupeNomme("L3");
        l3.ajouterMembres(admin);
        l3.ajouterMembres(salim);
        listeGroupe.add(l3);

        Paquet paquet= new Paquet(null,null,listeGroupe,global);


        communicationBDD.upload(paquet);

        ConcurrentSkipListSet<GroupeNomme> listeGroupe2;
        Groupe global2;

        Paquet paquet1 = communicationBDD.download();
        listeGroupe2 = paquet1.getListeGroupe();
        global2 = paquet1.getGroupeGlobal();

        System.out.println(listeGroupe2);
        System.out.println(global2);*/





    }

}
