package utilisateurs;

import java.util.UUID;

public class GroupeNomme extends Groupe implements Comparable<GroupeNomme>{
    private String nom;
    private int id;
    public GroupeNomme(String nom, int id){
        super();
        this.nom = nom;
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public int getId() {
        return id;
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
        return this.id-o.id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(getClass() != obj.getClass()) return false;
        GroupeNomme gr = (GroupeNomme) obj;
        return id == gr.id;
    }

    

}
