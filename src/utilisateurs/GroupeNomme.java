package utilisateurs;

import discussion.FilDeDiscussion;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;

public class GroupeNomme extends Groupe implements Comparable<GroupeNomme>{
    private String nom;
    private int id;
    private ConcurrentSkipListSet<FilDeDiscussion> filsDeDiscussion = new ConcurrentSkipListSet<>();

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

    public void ajouterFilDeDiscussion(FilDeDiscussion fil){
        filsDeDiscussion.add(fil);
    }

    public ConcurrentSkipListSet<FilDeDiscussion> getFilsDeDiscussion() {
        return filsDeDiscussion;
    }

    public FilDeDiscussion getFilsDeDiscussion(String name){
        for (FilDeDiscussion f :
                this.filsDeDiscussion) {
            if (f.getSujet() == name){
                return f;
            }
        }
        return null;
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
