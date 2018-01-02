package test;

import discussion.FilDeDiscussion;
import discussion.Message;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

public class Test_FilDeDiscussion2 {
    public static void main(String[] args) {

        // Utilisateur
        Utilisateur sylvain =new Utilisateur("DEKER","Sylvain",21400536,"123", TypeUtilisateur.ETUDIANT);
        Utilisateur salim =new Utilisateur("CHERIFI","Salim",21400537,"123", TypeUtilisateur.ETUDIANT);
        Utilisateur guillaume =new Utilisateur("DAUMAS","GUILLAUME",21400538,"123", TypeUtilisateur.ETUDIANT);

        Utilisateur nadege = new Utilisateur("Lamarque","Nadege",0,"123",TypeUtilisateur.SECRETAIRE);
        Utilisateur nadege2 = new Utilisateur("Lamarque2","Nadege2",2,"123",TypeUtilisateur.SECRETAIRE);
        Utilisateur nadege3 = new Utilisateur("Lamarque3","Nadege3",3,"123",TypeUtilisateur.SECRETAIRE);


        GroupeNomme l3 = new GroupeNomme("L3");
        l3.ajouterMembres(sylvain,salim,guillaume);

        GroupeNomme secreteria = new GroupeNomme("Secretaria");
        secreteria.ajouterMembres(nadege,nadege2,nadege3);

        // Conversation
        FilDeDiscussion pbedt = new FilDeDiscussion("PB : Emploi du temps");
        pbedt.ajouterMembres(nadege); //Ajout d'un Utilisateur
        pbedt.ajouterMembres(l3); //Ajout d'un Groupe

        // exemple de la convo
        pbedt.ajouterMessage(nadege,"Bonjour, nouveau cours : 5 h de Maths  dans 5 min, Présence obligatoire ! ");
        pbedt.ajouterMessage(sylvain,"Balec salope !");
        pbedt.ajouterMessage(salim,"Non ya pas moyen !");
        pbedt.ajouterMessage(guillaume,"Je peux pas j'ai vélo :/ ");












    }
}
