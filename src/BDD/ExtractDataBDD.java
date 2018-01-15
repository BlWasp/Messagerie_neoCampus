package BDD;

import discussion.FilDeDiscussion;
import discussion.Message;
import org.apache.log4j.Logger;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;
import net.Paquet;

import java.sql.*;
import java.util.concurrent.ConcurrentSkipListSet;

import static utilisateurs.Utilisateur.Privilege.ADMIN;
import static utilisateurs.Utilisateur.Privilege.USER;

public class ExtractDataBDD {

    private static Logger LOGGER = Logger.getLogger(ExtractDataBDD.class);

    /**
     * Recupère la base de donnee en SQL
     * @return paquet contenant la BDD pret à être envoyé au client
     * @throws SQLException
     */
    public static Paquet download() throws SQLException {
        Paquet paquet = null;
        Connection conn = null;
        Statement state = null;
        ResultSet result = null;
        ResultSet resultGr = null;
        ResultSet resultFil = null;
        ResultSet resultMess = null;
        Utilisateur.Privilege privilege;

        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/Messenger";
            conn = DriverManager.getConnection(url, "postgres", "1234");

            //Création d'un objet Statement
            state = conn.createStatement();

            //Ajout des utilisateurs global au paquet
            Utilisateur[] listU = null;
            int cptU = 0;
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

                listU[cptU] = new Utilisateur(nom,prenom,identifiant,mdp,typeU,privilege);
                cptU++;
            }

            GroupeNomme[] listGr = null;
            int cptGr = 0;
            FilDeDiscussion[] listFil = null;
            int cptFil = 0;

            //Creation liste de tous les groupes
            resultGr = state.executeQuery("SELECT id,nom FROM GroupeNomme");
            while (resultGr.next()) {
                String nom = resultGr.getString("nom");
                listGr[cptGr] = new GroupeNomme(nom);
                cptGr++;
            }

            //Creation liste de tous les fils de discussion
            resultFil = state.executeQuery("SELECT sujet,id,id_GroupeNomme,identifiant FROM FilDiscussion");
            while (resultFil.next()) {
                String sujet = resultFil.getString("sujet");
                int id_GroupeNomme = resultFil.getInt("id_GroupeNomme");
                int identifiant = resultFil.getInt("identifiant");

                for (int i = 0; i < cptGr; i++) {
                    if (listGr[i].getId().equals(id_GroupeNomme)) {
                        for (int j=0;j<cptU;j++) {
                            if (listU[j].getIdentifiant() == identifiant) {
                                listFil[cptFil] = new FilDeDiscussion(sujet, listGr[i], listU[j]);
                            }
                        }
                    }
                }
                cptFil++;
            }

            //Ajout des messages aux fils de discussion
            resultMess = state.executeQuery("SELECT message,id,id_FilDiscussion,identifiant FROM Message");
            while(resultMess.next()) {
                String message = resultMess.getString("message");
                int id_FilDiscussion = resultMess.getInt("id_FilDiscussion");
                int identifiant = resultMess.getInt("identifiant");

                for (int i=0;i<cptFil;i++) {
                    if (listFil[i].getId().equals(id_FilDiscussion)) {
                        for (int j=0;j<cptU;j++) {
                            if (listU[j].getIdentifiant() == identifiant) {
                                listFil[i].ajouterMessage(listU[j],message);
                            }
                        }
                    }
                }
            }


            //Ajout des fils de discussion aux groupes
            for (int i=0;i<cptGr;i++) {
                for (int j = 0; j < cptFil; j++) {
                    if (listGr[i].equals(listFil[j].getGroupe())) {
                        listGr[i].ajouterFilDeDiscussion(listFil[j].getCreateur(), listFil[i].getSujet());
                    }
                }
            }

            //Creation nouveau paquet avec ajout des groupes et users
            ConcurrentSkipListSet<GroupeNomme> skipListGr = new ConcurrentSkipListSet<>();
            Groupe global = new Groupe();

            for (int i=0;i<cptGr;i++) {
                skipListGr.add(listGr[i]);
            }
            for (int i=0;i<cptU;i++) {
                global.ajouterMembres(listU[i]);
            }

            paquet = new Paquet(null,null,skipListGr,global);


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
