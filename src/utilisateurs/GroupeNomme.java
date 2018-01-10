package utilisateurs;

import discussion.FilDeDiscussion;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;

public class GroupeNomme extends Groupe implements Serializable,Comparable<GroupeNomme>{
    private String nom;
    private UUID id = UUID.randomUUID();
    private ConcurrentSkipListSet<FilDeDiscussion> filsDeDiscussion = new ConcurrentSkipListSet<>();

    public GroupeNomme(String nom){
        super();
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public UUID getId() {
        return id;
    }

    public void ajouterFilDeDiscussion(Utilisateur createur,String sujet){
        FilDeDiscussion f = new FilDeDiscussion(sujet,this,createur);
        filsDeDiscussion.add(f);
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
    public int compareTo(GroupeNomme o) {
        return this.nom.compareTo(o.nom);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(getClass() != obj.getClass()) return false;
        GroupeNomme gr = (GroupeNomme) obj;
        return id.equals(gr.id);
    }

    

}
