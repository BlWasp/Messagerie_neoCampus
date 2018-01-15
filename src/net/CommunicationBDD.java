package net;

import java.io.*;

/**
 * Permet la communication avec la BDD
 */
public class CommunicationBDD {

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

    /**
     * Test et exemples d'utilisation
     * @param args
     */
    /*public static void main(String[] args) {
        ConcurrentSkipListSet<GroupeNomme> listeGroupe= new ConcurrentSkipListSet<>();
        Groupe global = new Groupe();

        Utilisateur admin = new Utilisateur("Admin", "Admin", 0, "admin", null);
        admin.setPrivilege(Utilisateur.Privilege.ADMIN);
        Utilisateur sylvain =new Utilisateur("DEKER","Sylvain",21400536,"123", TypeUtilisateur.ETUDIANT);
        Utilisateur salim =new Utilisateur("CHERIFI","Salim",21400537,"123", TypeUtilisateur.ETUDIANT);
        Utilisateur guillaume =new Utilisateur("DAUMAS","Guillaume",21400538,"123", TypeUtilisateur.ETUDIANT);

        Utilisateur nadege = new Utilisateur("Lamarque","Nadege",0,"123",TypeUtilisateur.ADMINISTRATIF);
        global.ajouterMembres(admin,sylvain,guillaume,salim,nadege);


        GroupeNomme l3 = new GroupeNomme("L3");
        l3.ajouterMembres(admin);
        l3.ajouterMembres(salim);
        listeGroupe.add(l3);

        Paquet paquet= new Paquet(null,null,listeGroupe,global);


        CommunicationBDD.upload(paquet);

        ConcurrentSkipListSet<GroupeNomme> listeGroupe2;
        Groupe global2;

        Paquet paquet1 = CommunicationBDD.download();
        listeGroupe2 = paquet1.getListeGroupe();
        global2 = paquet1.getGroupeGlobal();

        System.out.println(listeGroupe2);
        System.out.println(global2);
    }*/

}
