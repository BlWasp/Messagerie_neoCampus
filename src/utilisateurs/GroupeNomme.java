package utilisateurs;

import discussion.FilDeDiscussion;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Les Groupes Nommés re présentent les groupes d'Utilisateur d'une même entitée ex: L3, Secrétaria, M1, etc ..
 */
public class GroupeNomme extends Groupe implements Serializable,Comparable<GroupeNomme>{
    private String nom;
    private UUID id = UUID.randomUUID();
    private ConcurrentSkipListSet<FilDeDiscussion> filsDeDiscussion = new ConcurrentSkipListSet<>();

    /**
     * Constructeur
     * @param nom le nom du Groupe
     */
    public GroupeNomme(String nom){
        super();
        this.nom = nom;
    }

    /**
     * Getter du nom du Groupe
     * @return nom du Groupe
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de numero son identification
     * @return numero son identification
     */
    public UUID getId() {
        return id;
    }

    /**
     * Methode permettant d'ajouter un fil de discussion à un groupe nommé
     * @param createur Personne n'appartenant pas forcément au GroupeNomme voulant lancer une conversation avec le groupe
     * @param sujet Titre de la conversation
     */
    public void ajouterFilDeDiscussion(Utilisateur createur,String sujet){
        FilDeDiscussion f = new FilDeDiscussion(sujet,this,createur);
        filsDeDiscussion.add(f);
    }

    /**
     * Getter sur la liste de Fil de discussion
     * @return la liste de Fil de discussion
     */
    public ConcurrentSkipListSet<FilDeDiscussion> getFilsDeDiscussion() {
        return filsDeDiscussion;
    }

    /**
     * Methode permettant de récuperer la premiere occurance du fil de discussion à partir de son nom (sujet)
     * @param name Sujet du Fil de discussion
     * @return fil de discussion
     */
    public FilDeDiscussion getFilsDeDiscussion(String name){
        for (FilDeDiscussion f :
                this.filsDeDiscussion) {
            if (f.getSujet().equals(name)){
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

    @Override
    public String toString() {
        return "GroupeNomme{" +
                "nom='" + nom + '\'' +
                ", id=" + id +
                ", filsDeDiscussion=" + filsDeDiscussion +
                '}';
    }
}
