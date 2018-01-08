package paquet;

import discussion.FilDeDiscussion;
import discussion.Message;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

import static paquet.Paquet.Action.ADD;

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
       // Ajout d'un fil de discussion
        FilDeDiscussion f = new FilDeDiscussion("L3");
        f.ajouterMembres(client); // ajoute tout les membres de global dans la convo f
        client.ajouterFilDeDiscussion(f);


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
