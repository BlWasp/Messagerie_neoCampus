
package discussion;

import org.apache.log4j.Logger;
import utilisateurs.Groupe;
import utilisateurs.Utilisateur;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;


public class Message implements Serializable{
    private Utilisateur from;
    private String message;
    private Groupe enAttente = new Groupe();
    private Groupe recu = new Groupe();
    private Groupe lu = new Groupe();
    private final UUID id = UUID.randomUUID();
    private Etat etat;
    private static Logger LOGGER = Logger.getLogger(Message.class);
    private String dateEnvoi;
    enum Etat{
        ENVOIE_SERVEUR, // [GRIS] le message n'est pas encore recu par le server
        PAS_RECU_PAR_TOUS, // [ROUGE] le message n'est pas recu par tout les destinataires
        PAS_LU_PAR_TOUS,// [ORANGE] le message n'est pas lu par tout les destinataires
        LU_PAR_TOUS, // [VERT] le message est lu par tout les destinataires
    }


    public Message(Utilisateur from, Groupe to, String message) {
        this.from = from;
        this.message = message;


        enAttente.ajouterMembres(to);
        enAttente.ajouterMembres(from);
        etat = Etat.ENVOIE_SERVEUR;

        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date auj = new Date();
        this.dateEnvoi = formater.format(auj);
    }

    public Utilisateur getFrom() {
        return from;
    }

    public void recu(Utilisateur u){

        if( ! enAttente.estMembre(u) ){
            LOGGER.error("ERREUR : recu() l'utilisateur " + u.getPrenom() + " n'est pas en attente du message ");
            System.exit(3);
        }
        if( recu.estMembre(u)){
            LOGGER.error("ERREUR : recu() : l'utilisateur est déja dans recu");
            System.exit(4);
        }

        this.enAttente.retirerMembres(u);
        this.recu.ajouterMembres(u);
    }

    public void lu(Utilisateur u){

        if( !recu.estMembre(u) ){
            LOGGER.error("ERREUR : lu() l'utilisateur désigné n'est pas dans la liste des messages recus");
            System.exit(5);
        }
        if( lu.estMembre(u) ){
            LOGGER.error("ERREUR : lu() : l'utilisateur est déja dans lu ");
            System.exit(6);
        }

        this.recu.retirerMembres(u);
        this.lu.ajouterMembres(u);
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from=" + from.getPrenom() +
                ", message='" + message + '\'' +
                ", enAttente=" + enAttente._listeUtisateurToString() +
                ", recu=" + recu._listeUtisateurToString() +
                ", lu=" + lu._listeUtisateurToString() +
                ", etat=" + etat +
                '}';
    }

    public Groupe getEnAttente() {
        return enAttente;
    }

    public Groupe getRecu() {
        return recu;
    }

    public Groupe getLu() {
        return lu;
    }

    public UUID getId() {
        return id;
    }

    public String getDateEnvoi() {
        return dateEnvoi;
    }
}
