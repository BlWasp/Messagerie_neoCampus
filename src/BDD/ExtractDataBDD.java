package BDD;

import org.apache.log4j.Logger;
import utilisateurs.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExtractDataBDD {

    private static Logger LOGGER = Logger.getLogger(ExtractDataBDD.class);

    public static ResultSet launchQuery(String query) throws SQLException {



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

    }


    public static List<Utilisateur> shapeUserList(ResultSet result) throws SQLException {
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
    }

}
