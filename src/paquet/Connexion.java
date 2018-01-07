package paquet;

import utilisateurs.Utilisateur;

import java.io.Serializable;

public class Connexion implements Serializable {
    private int identifiant;
    private String mdp;
    private Utilisateur utilisateur;

    public Connexion(int identifiant, String mdp) {
        this.identifiant = identifiant;
        this.mdp = mdp;
        this.utilisateur = null;
    }

    public Connexion(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.identifiant = 0;
        this.mdp = null;
    }

    public int getIdentifiant() {
        return identifiant;
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
