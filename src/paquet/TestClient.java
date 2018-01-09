package paquet;

import discussion.FilDeDiscussion;
import discussion.Message;
import utilisateurs.GroupeNomme;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

import static paquet.Paquet.Action.ADD;
import static paquet.Paquet.Action.MAJ;
import static paquet.Paquet.Action.SUPP;

public class TestClient {
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1",12700);


        // Authetification (Beta)
        client.authentification(0,"admin");


        // Ajout d'un Menbre (Beta)
        client.ajouterMembres(new Utilisateur("Daumas","Guillaume",789,"yolo", TypeUtilisateur.ETUDIANT));
        client.ajouterMembres(new Utilisateur("CHERIFI","Salim",790,"yolo",TypeUtilisateur.ETUDIANT));

        client.ajouterMembres(new Utilisateur("12","12",791,"yolo", TypeUtilisateur.ETUDIANT));
        client.ajouterMembres(new Utilisateur("23","23",792,"yolo", TypeUtilisateur.ETUDIANT));
        client.ajouterMembres(new Utilisateur("34","34",793,"yolo", TypeUtilisateur.ETUDIANT));
        client.ajouterMembres(new Utilisateur("45","45",794,"yolo", TypeUtilisateur.ETUDIANT));
        client.ajouterMembres(new Utilisateur("56","56",795,"yolo", TypeUtilisateur.ETUDIANT));
        client.ajouterMembres(new Utilisateur("67","67",796,"yolo", TypeUtilisateur.ETUDIANT));
        client.ajouterMembres(new Utilisateur("78","78",797,"yolo", TypeUtilisateur.ETUDIANT));
        client.ajouterMembres(new Utilisateur("89","89",798,"yolo", TypeUtilisateur.ETUDIANT));

        Utilisateur testU = new Utilisateur("Test", "Test", 800, "Yolo", TypeUtilisateur.ENSEIGNANT);
        client.ajouterMembres(testU);
        client.majMembres(new Utilisateur("TestMaj", "TestMaj", 800, "Yolo", TypeUtilisateur.ENSEIGNANT));

        GroupeNomme L3 = new GroupeNomme("L3", 5);
        L3.ajouterMembres(testU);
        client.gestionGroupeNomme(L3,ADD);

        client.gestionGroupeNomme(new GroupeNomme("L4", 5),MAJ);


       // Ajout d'un fil de discussion
        FilDeDiscussion f = new FilDeDiscussion("L3");
        f.ajouterMembres(client); // ajoute tout les membres de global dans la convo f
        //client.ajouterFilDeDiscussion(f);


        // Envoi d'un message sur une convo
        client.ajouterMessage("Coucou ",f);
        client.ajouterMessage("Wesh bien ta vu",f);
        client.ajouterMessage("1 ",f);
        client.ajouterMessage("2 ",f);
        client.ajouterMessage("3 ",f);
        client.ajouterMessage("4 ",f);
        client.ajouterMessage("5 ",f);
        client.ajouterMessage("6 ",f);
















    }
    static void sleep(int s){
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
