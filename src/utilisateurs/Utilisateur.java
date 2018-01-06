
package utilisateurs;


import java.io.Serializable;

import static utilisateurs.Utilisateur.Privilege.ADMIN;
import static utilisateurs.Utilisateur.Privilege.USER;

public class Utilisateur implements Comparable<Utilisateur>,Serializable{
    private String nom;
    private String prenom;
    private int identifiant;
    private String motDePasse;
    private TypeUtilisateur type;
    public Privilege privilege = USER;
    public enum Privilege{USER,ADMIN}

    public Utilisateur(String nom, String prenom, int identifiant,String motDePasse, TypeUtilisateur type) {
        this.nom = nom;
        this.prenom = prenom;
        this.identifiant = identifiant;
        // this.motDePasse = EncodePasswd.encode(motDePasse);
        this.motDePasse = motDePasse;
        this.type = type;
    }

    public void setPrivilege(Privilege p){
        this.privilege = p;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public TypeUtilisateur getType() {
        return type;
    }

    @Override
    public int compareTo(Utilisateur o) {

        return this.identifiant-o.identifiant;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(getClass() != obj.getClass()) return false;
        Utilisateur u = (Utilisateur) obj;
        return identifiant == u.identifiant;
    }

    @Override
    public String toString() {
        String str =   "Utilisateur{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", identifiant=" + identifiant +
                ", type=" + type ;

        str += "}";
        return str;
    }

}
