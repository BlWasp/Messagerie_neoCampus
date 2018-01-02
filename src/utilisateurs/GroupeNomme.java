package utilisateurs;

public class GroupeNomme extends Groupe implements Comparable<GroupeNomme>{
    private String nom;
    public GroupeNomme(String nom){
        super();
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public String _listeUtisateurToString(){
        String cat = "Groupe Nommé (" +nom+")"  + this + " :";
        for(Utilisateur u : super.getMembres()){
            cat += u.getNom() + ", ";
        }
        return cat;
    }

    @Override
    public int compareTo(GroupeNomme o) {
        return nom.compareTo(o.nom);
    }
}
