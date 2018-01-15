package net;

import utilisateurs.Groupe;
import discussion.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.Serializable;
import java.util.concurrent.ConcurrentSkipListSet;

public class Paquet implements Serializable{
    public enum Action{AUTHENTIFICATION,REQUETTE,REPONSE,INSTRUCTION,DECONNECT}
    Action action;
    ConcurrentSkipListSet<GroupeNomme> listeGroupe;
    Utilisateur utilisateur;
    Groupe global;

    /**
     * Constructeur
     * @param action Action a affecte au paquet
     * @param utilisateur Utilisateur du paquet
     * @param listeGroupe Liste des groupes
     * @param global Ensemble des utilisateurs
     */
    public Paquet(Action action,Utilisateur utilisateur, ConcurrentSkipListSet<GroupeNomme> listeGroupe, Groupe global) {
        this.action = action;
        this.utilisateur = utilisateur;
        this.listeGroupe = listeGroupe;
        this.global = global;
    }

    /**
     *
     * @return l'action affectée au paquet
     */
    public Action getAction() {
        return action;
    }

    /**
     *
     * @return la liste des groupes
     */
    public ConcurrentSkipListSet<GroupeNomme> getListeGroupe() {
        return listeGroupe;
    }

    /**
     *
     * @return l'ensemble des utilisateurs
     */
    public Groupe getGroupeGlobal() {
        return global;
    }

    /**
     *
     * @return utilisateur du paquet
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     *
     * @param action Action a applique au paquet
     */
    public void setAction(Action action) {
        this.action = action;
    }

    /**
     *
     * @param listeGroupe La liste des groupes a applique au paquet
     */
    public void setListeGroupe(ConcurrentSkipListSet<GroupeNomme> listeGroupe) {
        this.listeGroupe = listeGroupe;
    }

    /**
     *
     * @param utilisateur L'utilisateur a applique au paquet
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     *
     * @return l'ensemble des utilisateurs
     */
    public Groupe getGlobal() {
        return global;
    }

    /**
     *
     * @param global Ensemble des utilisateurs a applique au paquet
     */
    public void setGlobal(Groupe global) {
        this.global = global;
    }
}
