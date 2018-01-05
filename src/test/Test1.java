
package test;

import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

public class Test1 {
    public static void main(String[] args) {
        Utilisateur u = new Utilisateur("Deker","Sylvain",21400536,"0000", TypeUtilisateur.ETUDIANT);
        Groupe g = new Groupe();
        g.ajouterMembres(u);
        System.out.println(g);
       // g.retirerMembres(new Utilisateur("","",21400536,"",null));
        System.out.println(g);

    }
}
