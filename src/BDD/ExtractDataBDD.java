package BDD;

import org.apache.log4j.Logger;
import utilisateurs.GroupeNomme;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;
import net.Paquet;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static utilisateurs.Utilisateur.Privilege.ADMIN;
import static utilisateurs.Utilisateur.Privilege.USER;

public class ExtractDataBDD {

    private static Logger LOGGER = Logger.getLogger(ExtractDataBDD.class);

    /*public static ResultSet launchQuery(String query) throws SQLException {



        Connection conn = null;
        Statement state = null;
        ResultSet result = null;


        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/Messenger";
            conn = DriverManager.getConnection(url, "postgres", "1234");

            //Création d'un objet Statement
            state = conn.createStatement();

            //L'objet ResultSet contient le résultat de la requête SQL
            result = state.executeQuery(query);

            //On récupère les MetaData



        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (state != null) {
                state.close();
            }
            if (result != null) {
                result.close();
            }
        }
        return result;

    }*/


    /*public static List<Utilisateur> shapeUserList(ResultSet result) throws SQLException {
        List<Utilisateur> list = new ArrayList<>();
        while (result.next()) {

            String nom = result.getString("nom");
            String prenom = result.getString("prenom");
            int identifiant = Integer.parseInt(result.getString("ident"));
            String mdp = result.getString("mdp");
            //String type = result.getString("type"); A voir comment resoudre ce probleme
            Utilisateur u = new Utilisateur(nom,prenom,identifiant,mdp,null);
            list.add(u);
        }
        return list;
    }*/

    public static Paquet download() throws SQLException {
        Paquet paquet = null;
        Connection conn = null;
        Statement state = null;
        ResultSet result = null;
        Utilisateur.Privilege privilege;

        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/Messenger";
            conn = DriverManager.getConnection(url, "postgres", "1234");

            //Création d'un objet Statement
            state = conn.createStatement();

            //Ajout des utilisateurs global au paquet
            result = state.executeQuery("SELECT nom,prenom,identifiant,mdp,privilege,typeU FROM Utilisateur");
            while(result.next()) {
                String nom = result.getString("nom");
                String prenom = result.getString("prenom");
                int identifiant = result.getInt("identifiant");
                String mdp = result.getString("mdp");
                Boolean valB = result.getBoolean("privilege");
                if (valB == true) {
                    privilege = ADMIN;
                } else {
                    privilege = USER;
                }
                TypeUtilisateur typeU = (TypeUtilisateur)result.getObject("typeU");

                paquet.getGlobal().ajouterMembres(new Utilisateur(nom,prenom,identifiant,mdp,typeU,privilege));
            }

            //Ajout des groupes au paquet
            result = state.executeQuery("SELECT id,nom FROM GroupeNomme");
            while(result.next()) {
                int id = result.getInt("id");
                String nom = result.getString("nom");

                paquet.getListeGroupe().add(new GroupeNomme(nom));
            }

            //Ajout des fils de discussion à chaque groupe
            result = state.executeQuery("SELECT sujet,id,id_GroupeNomme,identifiant FROM FilDiscussion");
            while(result.next()) {
                String sujet = result.getString("sujet");
                int id = result.getInt("id");
                int id_GroupeNomme = result.getInt("id_GroupeNomme");
                int identifiant = result.getInt("identifiant");

                for (Iterator<GroupeNomme> itr = paquet.getListeGroupe().iterator();itr.hasNext();) {
                    GroupeNomme groupe = itr.next();
                    if (itr.next().getId().equals(id_GroupeNomme)) {
                        groupe.ajouterFilDeDiscussion(paquet.getGlobal().getUtilisateur(identifiant),sujet);
                        paquet.getListeGroupe().remove(groupe);
                        paquet.getListeGroupe().add(groupe);
                    }
                }
            }


        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (state != null) {
                state.close();
            }
            if (result != null) {
                result.close();
            }
        }


        return paquet;
    }

}
