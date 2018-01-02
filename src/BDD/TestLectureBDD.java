package BDD;

import org.apache.log4j.Logger;
import java.sql.*;

public class TestLectureBDD {

    private static Logger LOGGER = Logger.getLogger(TestLectureBDD.class);

    public static void main(String[] args) throws SQLException {
        Connection conn = null;
        Statement state = null;
        ResultSet result = null;

        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://localhost:5432/Messenger";
            String user = "postgres";

            conn = DriverManager.getConnection(url, user, "1234");

            //Création d'un objet Statement
            state = conn.createStatement();

            //L'objet ResultSet contient le résultat de la requête SQL
            result = state.executeQuery("SELECT * FROM users");

            //On récupère les MetaData
            ResultSetMetaData resultMeta = result.getMetaData();

            LOGGER.info("\n**********************************");

            //On affiche le nom des colonnes
            for(int i = 1; i <= resultMeta.getColumnCount(); i++) {
                System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
            }

            System.out.println("\n**********************************");

            while(result.next()){
                for(int i = 1; i <= resultMeta.getColumnCount(); i++) {
                    System.out.print("\t" + result.getObject(i).toString() + "\t |");
                }
                System.out.print("\n---------------------------------");
            }



        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            if(conn != null) {
                conn.close();
            }
            if(state != null) {
                state.close();
            }
            if(result != null) {
                result.close();
            }
        }
    }
}
