
package utilisateurs;

import discussion.FilDeDiscussion;

import javax.rmi.CORBA.Util;
import java.io.Serializable;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;


public class Groupe implements Serializable {

//    private NavigableSet<Utilisateur> membres = new TreeSet<>();
    private ConcurrentSkipListSet<Utilisateur> membres = new ConcurrentSkipListSet<>();

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

    public void retirerMembre(int id){
        for (Utilisateur u :
                this.membres) {
            if (u.getIdentifiant() == id){
                this.membres.remove(u);
            }
        }
    }

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
