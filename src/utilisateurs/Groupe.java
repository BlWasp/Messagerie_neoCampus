
package utilisateurs;

import java.util.NavigableSet;
import java.util.TreeSet;


public class Groupe {

    private NavigableSet<Utilisateur> membres = new TreeSet<>();

    public boolean estMembre(Utilisateur u){
        return membres.contains(u);
    }

    public void ajouterMembres(Utilisateur m){
        if( ! this.estMembre(m)){
            this.membres.add(m);
        }
    }
    public void ajouterMembres(Utilisateur ... m){
        for(Utilisateur i: m){
            this.ajouterMembres(i);
        }
    }

    public void ajouterMembres(Groupe grp){
        for(Utilisateur i: grp.getMembres()){
            this.ajouterMembres(i);
        }
    }
    public void ajouterMembres(Groupe... grps){
        for(Groupe g: grps){
            this.ajouterMembres(g);
        }
    }




    public int retirerMembres(Utilisateur u){
        int found = 0;
        if(this.estMembre(u)){
            found = 1;
            membres.remove(u);
        }
        return found;
    }

    public int retirerMembres(Utilisateur... utls){
        int found = 0;
        for (Utilisateur u : utls){
            found += this.retirerMembres(u);
        }
        return found;
    }

    public int retirerMembres(Groupe grp){
        int found = 0;
        for(Utilisateur u : grp.getMembres()){
            found += this.retirerMembres(u);
        }
        return found;
    }
    public int retirerMembres(Groupe... grps){
        int found = 0;
        for(Groupe g : grps){
            found += this.retirerMembres(g);
        }
        return found;
    }

    public NavigableSet<Utilisateur> getMembres() {
        return membres;
    }

    public String _listeUtisateurToString(){
        String cat = "Groupe " + this + " :";
        for(Utilisateur u : membres){
            cat += u.getNom() + ", ";
        }
        return cat;
    }
}
