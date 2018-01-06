package paquet;

import discussion.FilDeDiscussion;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

import static paquet.Paquet.Action.ADD;

public class TestClient {
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1",6791);
        sleep(50);

        // Authetification (Beta)
        client.authentification(0,"admin");
        sleep(50);

        // Ajout d'un Menbre (Beta)
        client.ajouterMembres(new Utilisateur("Daumas","Guillaume",789,"yolo", TypeUtilisateur.ETUDIANT));
        sleep(50);
        // Retrai d'un membre
//        if(client.retirerMembres(new Utilisateur("","",789,"",null))==1){
//            System.out.println("Utilisateur supprimé: ");
//        }else{
//            System.out.println("Utilisateur non trouvé");
//        }
        FilDeDiscussion f = new FilDeDiscussion("L3");
        f.ajouterMembres(client); // ajoute tout les membres de global dans la convo f
        client.ajouterFilDeDiscussion(f);


        sleep(50);















    }
    static void sleep(int s){
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
