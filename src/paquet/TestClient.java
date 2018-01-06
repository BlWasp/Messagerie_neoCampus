package paquet;

import discussion.FilDeDiscussion;
import discussion.Message;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

import static paquet.Paquet.Action.ADD;

public class TestClient {
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1",6791);


        // Authetification (Beta)
        client.authentification(0,"admin");


        // Ajout d'un Menbre (Beta)
        client.ajouterMembres(new Utilisateur("Daumas","Guillaume",789,"yolo", TypeUtilisateur.ETUDIANT));
        client.ajouterMembres(new Utilisateur("CHERIFI","Salim",790,"yolo",TypeUtilisateur.ETUDIANT));
        
       // Ajout d'un fil de discussion
        FilDeDiscussion f = new FilDeDiscussion("L3");
        f.ajouterMembres(client); // ajoute tout les membres de global dans la convo f
        client.ajouterFilDeDiscussion(f);


        // Envoi d'un message sur une convo
       client.ajouterMessage("Coucou ",f);
       client.ajouterMessage("Wesh bien ta vu",f);















    }
    static void sleep(int s){
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
