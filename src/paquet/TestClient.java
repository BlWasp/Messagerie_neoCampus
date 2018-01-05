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


       /* PROCHAINNEMENT :
        // Retrai d'un membre (Pas encore implementé pas le serveur !!!!)
        client.retirerMembres(new Utilisateur("","",2156,"",null));// Seul l'id suffit
        */
    }
}
