package net;

import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.Serializable;
import java.util.concurrent.ConcurrentSkipListSet;

public class Paquet implements Serializable{
    public enum Action{AUTHENTIFICATION,REQUETTE,REPONSE,INSTRUCTION,DECONNECT}
    Action action;
    ConcurrentSkipListSet<GroupeNomme> listeGroupe;
    Utilisateur utilisateur;
    Groupe global;

    public Paquet(Action action,Utilisateur utilisateur, ConcurrentSkipListSet<GroupeNomme> listeGroupe, Groupe global) {
        this.action = action;
        this.utilisateur = utilisateur;
        this.listeGroupe = listeGroupe;
        this.global = global;
    }

    public Action getAction() {
        return action;
    }

    public ConcurrentSkipListSet<GroupeNomme> getListeGroupe() {
        return listeGroupe;
    }

    public Groupe getGroupeGlobal() {
        return global;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
}
