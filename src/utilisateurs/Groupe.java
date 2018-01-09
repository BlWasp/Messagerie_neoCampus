
package utilisateurs;

import discussion.FilDeDiscussion;

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


    public int ajouterMembres(Utilisateur m){

        return this.membres.add(m)? 1 : 0 ;

    }

    public int ajouterMembres(Utilisateur ... m){
        int nbAjout =0;
        for(Utilisateur i: m){
            nbAjout +=this.ajouterMembres(i);
        }
        return nbAjout;
    }

    public int ajouterMembres(Groupe grp){
        int nbAjout =0;
        for(Utilisateur i: grp.getMembres()){
            nbAjout += this.ajouterMembres(i);
        }
        return nbAjout;
    }

    public int ajouterMembres(Groupe... grps){
        int nbAjout = 0;
        for(Groupe g: grps){
            nbAjout += this.ajouterMembres(g);
        }
        return nbAjout;
    }




    public int retirerMembres(Utilisateur u){
         return  membres.remove(u) ? 1 : 0 ;
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

    public int retirerMembre(int id){
        return retirerMembres(new Utilisateur("","",id,"",null));
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
