package paquet;

import discussion.FilDeDiscussion;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Connexion implements Serializable {
    private int identifiant;
    private String mdp;
    private Utilisateur utilisateur;
    List<GroupeNomme> listeGroupe;
    Groupe global;

    public Connexion(int identifiant, String mdp) {
        this.identifiant = identifiant;
        this.mdp = mdp;
        this.utilisateur = null;
    }

    public Connexion(Utilisateur utilisateur, List<GroupeNomme> listeGroupe, Groupe global) {
        this.utilisateur = utilisateur;

        this.listeGroupe = listeGroupe;
        this.global = global;

        this.identifiant = 0;
        this.mdp = null;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public List<GroupeNomme> getListeGroupe() {
        return listeGroupe;
    }



    public Groupe getGlobal() {
        return global;
    }

    public String getMdp() {
        return mdp;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    @Override
    public String toString() {
        return "ChoixServeur{" +
                "identifiant=" + identifiant +
                ", mdp='" + mdp + '\'' +
                ", utilisateur=" + utilisateur +
                '}';
    }
}
