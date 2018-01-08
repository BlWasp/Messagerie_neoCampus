package net;

import discussion.FilDeDiscussion;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.Serializable;

import java.util.concurrent.ConcurrentSkipListSet;

public class Paquet extends SupportPricipal implements Serializable{

    public enum Action{AUTHENTIFICATION,REQUETTE,REPONSE}
    private Action action;

    public Paquet(Utilisateur utilisateur, ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion, Groupe global, Action action) {
        super(utilisateur, listeGroupe, listeFilDeDiscussion, global);
        this.action = action;
    }
    public Paquet(Action action,Utilisateur utilisateur){
        super(utilisateur, null, null, null);
        this.action = action;
    }

    public Action getAction() {
        return action;
    }
}
