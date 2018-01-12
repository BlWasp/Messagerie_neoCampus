package utilisateurs;

import java.io.Serializable;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Un Groupe est un ensemble de d'Utilisateurs
 */
public class Groupe implements Serializable {

    private ConcurrentSkipListSet<Utilisateur> membres = new ConcurrentSkipListSet<>();

    /**
     * Méthode pour determiner si un Utilisateur est membre d'un groupe.
     * @param u Determine si un utilisateur appartient au Groupe.
     * @return  true si appartient false sinon.
     */
    public boolean estMembre(Utilisateur u){
        return membres.contains(u);
    }

    /**
     * Setter d'ajout d'Utilisateur(s) au Groupe groupe.
     * @param m Utilisateur à ajouter au groupe (s'il n'est pas déjà présent).
     * @return le nombre d'utilisateur réelement ajouté.
     */
    public int ajouterMembres(Utilisateur m){

        return this.membres.add(m)? 1 : 0 ;

    }

    /**
     * Setter d'ajout d'Utilisateur(s) au Groupe groupe.
     * @param m Utilisateur(s) à ajouter au groupe.
     * @return le nombre d'utilisateur réelement ajouté.
     */
    public int ajouterMembres(Utilisateur ... m){
        int nbAjout =0;
        for(Utilisateur i: m){
            nbAjout +=this.ajouterMembres(i);
        }
        return nbAjout;
    }

    /**
     * Setter d'ajout d'Utilisateur(s) au Groupe groupe.
     * @param grp Ajoute tous les Utilisateurs (si il ne sont pas déjà present) de grp au groupe courant.
     * @return le nombre d'utilisateur réelement ajouté.
     */
    public int ajouterMembres(Groupe grp){
        int nbAjout =0;
        for(Utilisateur i: grp.getMembres()){
            nbAjout += this.ajouterMembres(i);
        }
        return nbAjout;
    }

    /**
     * Setter d'ajout d'Utilisateur(s) au Groupe groupe.
     * @param grps Ajoute tous les Utilisateurs (si il ne sont pas déjà present) des groupes grps au groupe courant.
     * @return le nombre d'utilisateur réelement ajouté.
     */
    public int ajouterMembres(Groupe... grps){
        int nbAjout = 0;
        for(Groupe g: grps){
            nbAjout += this.ajouterMembres(g);
        }
        return nbAjout;
    }

    /**
     * Setter de retrait de D'Utilisateur(s) membre du groupe courant.
     * @param u Retirer le ou les Utilisateur(s) en paramètre (s'il(s) existe(nt))
     * @return le nombre d'utilisateur réelement retiré
     */
    public int retirerMembres(Utilisateur u){
         return  membres.remove(u) ? 1 : 0 ;
    }

    /**
     * Setter de retrait de D'Utilisateur(s) membre du groupe courant.
     * @param utls Retirer le ou les Utilisateur(s) en paramètre (s'il(s) existe(nt))
     * @return le nombre d'utilisateur réelement retiré
     */
    public int retirerMembres(Utilisateur... utls){
        int found = 0;
        for (Utilisateur u : utls){
            found += this.retirerMembres(u);
        }
        return found;
    }

    /**
     * Setter de retrait de D'Utilisateur(s) membre du groupe courant.
     * @param grp Retirer le ou les Utilisateur(s) en paramètre (s'il(s) existe(nt))
     * @return le nombre d'utilisateur réelement retiré
     */
    public int retirerMembres(Groupe grp){
        int found = 0;
        for(Utilisateur u : grp.getMembres()){
            found += this.retirerMembres(u);
        }
        return found;
    }

    /**
     * Setter de retrait de D'Utilisateur(s) membre du groupe courant.
     * @param grps Retirer le ou les Utilisateur(s) en paramètre (s'il(s) existe(nt))
     * @return le nombre d'utilisateur réelement retiré
     */
    public int retirerMembres(Groupe... grps){
        int found = 0;
        for(Groupe g : grps){
            found += this.retirerMembres(g);
        }
        return found;
    }

    /**
     * Setter de retrait de D'Utilisateur(s) membre du groupe courant.
     * @param id Retirer le ou les Utilisateur(s) en paramètre (s'il(s) existe(nt))
     * @return le nombre d'utilisateur réelement retiré
     */
    public int retirerMembre(int id){
        return retirerMembres(new Utilisateur("","",id,"",null));
    }


    /**
     * Getter sur la reference d'un Utilisateur à partir de son id
     * @param id identifiant
     * @return
     */
    public Utilisateur getUtilisateur(int id){
        for (Utilisateur u :
                this.membres) {
            if (u.getIdentifiant() == id) {
                return u;
            }
        }
        return null;
    }



    public NavigableSet<Utilisateur> getMembres() {
        return membres;
    }


    public String _listeUtisateurToString(){
        StringBuilder cat = new StringBuilder();
        cat.append("Groupe " + this + " :");
        for(Utilisateur u : membres){
            cat.append(u.getNom() + ", ");
        }
        return cat.toString();
    }

    @Override
    public String toString() {
        String cat = "";
        for(Utilisateur u : membres){
            cat += u.toString() + '\n';
        }
        return cat;
    }
}
