package server;

import utilisateurs.Utilisateur;
import static utilisateurs.TypeUtilisateur.ETUDIANT;

public class TestClient {
    public static void main(String argv[]) {
        try{
            Utilisateur courant = new Utilisateur("Charles", "Aznavour", 1234, "abcd", ETUDIANT);
            Client c = new Client(courant);
            Thread t1 = new Thread(c);
            t1.start();
        }catch(Exception e){e.printStackTrace();}
    }
}
