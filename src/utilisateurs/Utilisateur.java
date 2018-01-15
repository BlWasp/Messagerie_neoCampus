
package utilisateurs;


import java.io.Serializable;
import java.lang.reflect.Type;

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

    /**
     * Constructeur sans privilège
     * @param nom de l'utilisateur
     * @param prenom de l'utilisateur
     * @param identifiant de l'utilisateur
     * @param motDePasse de l'utilisateur
     * @param type de l'utilisateur
     */
    public Utilisateur(String nom, String prenom, int identifiant,String motDePasse, TypeUtilisateur type) {
        this.nom = nom.toUpperCase();
        this.prenom = prenom;
        this.identifiant = identifiant;
        //this.motDePasse = motDePasse; //TODO FAIRE SECURITE
        this.motDePasse = motDePasse;
        this.type = type;
    }

    /**
     * Constructeur avec privilège
     * @param nom de l'utilisateur
     * @param prenom de l'utilisateur
     * @param identifiant de l'utilisateur
     * @param motDePasse de l'utilisateur
     * @param type de l'utilisateur
     * @param priv de l'utilisateur
     */
    public Utilisateur(String nom, String prenom, int identifiant,String motDePasse, TypeUtilisateur type, Privilege priv) {
        this.nom = nom.toUpperCase();
        this.prenom = prenom;
        this.identifiant = identifiant;
        this.motDePasse = EncodePasswd.encode(motDePasse);
        this.motDePasse = motDePasse;
        this.type = type;
        this.privilege = priv;
    }

    /**
     *
     * @param p Privilège à appliquer à l'Utilisateur
     */
    public void setPrivilege(Privilege p){
        this.privilege = p;
    }

    /**
     *
     * @param nom Nom à appliquer à l'Utilisateur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *
     * @param prenom Prenom à appliquer à l'Utilisateur
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     *
     * @param motDePasse Mot De Passe à appliquer à l'Utilisateur
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = EncodePasswd.encode(motDePasse);
    }

    /**
     *
     * @return les privilèges de l'Utilisateur
     */
    public Privilege getPrivilege() {
        return privilege;
    }

    /**
     *
     * @return le nom de l'Utilisateur
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @return le prenom de l'Utilisateur
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     *
     * @return l'id de l'Utilisateur
     */
    public int getIdentifiant() {
        return identifiant;
    }

    /**
     *
     * @return le Mot De Passe de l'Utilisateur
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     *
     * @return le type de l'Utilisateur
     */
    public TypeUtilisateur getType() {
        return type;
    }

    /**
     *
     * @param o Utilisateur à tester
     * @return redéfinition de compareTo
     */
    @Override
    public int compareTo(Utilisateur o) {

        return this.identifiant-o.identifiant;
    }

    /**
     *
     * @param obj
     * @return redéfinition de equals
     */
    @Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if(getClass() != obj.getClass()) return false;
        Utilisateur u = (Utilisateur) obj;
        return identifiant == u.identifiant;
    }

    /**
     *
     * @return toString de l'Utilisateur
     */
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
