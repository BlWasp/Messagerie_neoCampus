package test;

import discussion.FilDeDiscussion;
import discussion.Message;
import utilisateurs.GroupeNomme;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

public class Test_FilDeDiscussion1 {
    public static void main(String[] args) {

        // Utilisateur
        Utilisateur sylvain =new Utilisateur("DEKER","Sylvain",21400536,"123", TypeUtilisateur.ETUDIANT);
        Utilisateur salim =new Utilisateur("CHERIFI","Salim",21400537,"123", TypeUtilisateur.ETUDIANT);
        Utilisateur guillaume =new Utilisateur("DAUMAS","GUILLAUME",21400538,"123", TypeUtilisateur.ETUDIANT);

        Utilisateur nadege = new Utilisateur("Lamarque","Nadege",0,"123",TypeUtilisateur.SECRETAIRE);
        Utilisateur nadege2 = new Utilisateur("Lamarque2","Nadege2",2,"123",TypeUtilisateur.SECRETAIRE);
        Utilisateur nadege3 = new Utilisateur("Lamarque3","Nadege3",3,"123",TypeUtilisateur.SECRETAIRE);

        // Groupes
        GroupeNomme l3 = new GroupeNomme("L3");
        l3.ajouterMembres(sylvain,salim,guillaume);

        GroupeNomme secreteria = new GroupeNomme("Secretaria");
        secreteria.ajouterMembres(nadege,nadege2,nadege3);

       // Conversation
        FilDeDiscussion pbedt = new FilDeDiscussion("PB : Emploi du temps");
        pbedt.ajouterMembres(nadege); //Ajout d'un Utilisateur
        pbedt.ajouterMembres(l3); //Ajout d'un Groupe

        // Envoi d'un Message
        Message cur;
        cur = pbedt.ajouterMessage(nadege,"Bonjour, nouveau cours : 5 h de Maths  dans 5 min, Présence obligatoire ! ");
        if(cur == null) System.out.println("Violation d'accés"); // Peut arriver si nadege n'appartient pas au fil de discussion pbedt

        //Réception d'un message
        Message rcp;
        rcp = pbedt.lireLeMessage(0); // Lit le dernier message de la file, si index =1 avant-dernier message , index =2 avavnt-avant dernier etc ...

        // Exemple avec Sylvain (mais à faire ppour tout les membres de la conversation) :
        // le message est dans l'état "en-attente" pour sylvain
        rcp.recu(sylvain);
        // le message est dans l'état "recu"
        rcp.lu(sylvain);
        // le message est dans l'etat "lu"

        System.out.println("["+rcp.getFrom().getPrenom()+"] "+ rcp.getMesage() );









    }
}
