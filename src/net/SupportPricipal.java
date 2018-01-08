package net;

import discussion.FilDeDiscussion;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.util.concurrent.ConcurrentSkipListSet;

public abstract class SupportPricipal {
    private Utilisateur utilisateurCourant;
    private ConcurrentSkipListSet<GroupeNomme> listeGroupe ;
    private ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion;
    private Groupe global ;

    public SupportPricipal(Utilisateur utilisateurCourant, ConcurrentSkipListSet<GroupeNomme> listeGroupe, ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion, Groupe global) {
        this.utilisateurCourant = utilisateurCourant;
        this.listeGroupe = listeGroupe;
        this.listeFilDeDiscussion = listeFilDeDiscussion;
        this.global = global;
    }

    public Utilisateur getUtilisateurCourant() {
        return utilisateurCourant;
    }

    public void setUtilisateurCourant(Utilisateur utilisateurCourant) {
        this.utilisateurCourant = utilisateurCourant;
    }

    public ConcurrentSkipListSet<GroupeNomme> getListeGroupe() {
        return listeGroupe;
    }

    public void setListeGroupe(ConcurrentSkipListSet<GroupeNomme> listeGroupe) {
        this.listeGroupe = listeGroupe;
    }

    public ConcurrentSkipListSet<FilDeDiscussion> getListeFilDeDiscussion() {
        return listeFilDeDiscussion;
    }

    public void setListeFilDeDiscussion(ConcurrentSkipListSet<FilDeDiscussion> listeFilDeDiscussion) {
        this.listeFilDeDiscussion = listeFilDeDiscussion;
    }

    public Groupe getGlobal() {
        return global;
    }

    public void setGlobal(Groupe global) {
        this.global = global;
    }
}
