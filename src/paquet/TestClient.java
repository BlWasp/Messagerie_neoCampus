package paquet;

import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

public class TestClient {
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1",6791);
        // Authetification (Beta)
        client.authentification(0,"admin");
        // Ajout d'un Menbre (Beta)
        client.ajouterMembres(new Utilisateur("Daumas","Guillaume",789,"yolo", TypeUtilisateur.ETUDIANT));

        // Retrai d'un membre
        if(client.retirerMembres(new Utilisateur("","",789,"",null))==1){
            System.out.println("Utilisateur supprimé: ");
        }else{
            System.out.println("Utilisateur non trouvé");
        }


    }
}
