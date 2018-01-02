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

    @Override
    public String _listeUtisateurToString(){
        StringBuilder cat = new StringBuilder();
        cat.append("Groupe Nommé (" +nom+")"  + this + " :");
        for(Utilisateur u : super.getMembres()){
            cat.append(u.getNom() + ", ");
        }
        return cat.toString();
    }

    @Override
    public int compareTo(GroupeNomme o) {
        return nom.compareTo(o.nom);
    }
}
