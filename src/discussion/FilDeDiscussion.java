package discussion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;


public class FilDeDiscussion extends Groupe implements Serializable,Comparable<FilDeDiscussion>{
    private String sujet;
    private List<Message> filsdediscussion = new ArrayList<>();
    private final UUID id = UUID.randomUUID();
    private static Logger LOGGER = Logger.getLogger(FilDeDiscussion.class);

    public FilDeDiscussion(String sujet) {
        super();
        this.sujet = sujet;
    }

    public Message ajouterMessage(Utilisateur u, String m) {
        Message messageajoute = null;
        if(this.estMembre(u)){
            messageajoute = new Message(u,this, m);
            filsdediscussion.add(messageajoute);
        }
        else{
            LOGGER.error("ERREUR : " + u.getPrenom() + " ne participe pas à cette conversation");
            System.exit(1);
        }
        return messageajoute;
    }
    public Message ajouterMessage(Message m) {
        Utilisateur u = m.getFrom();
        if(this.estMembre(u)){
            filsdediscussion.add(m);
        }
        else{
            LOGGER.error("ERREUR : " + u.getPrenom() + " ne participe pas à cette conversation");
            System.exit(1);
        }
        return m;
    }


    public Message lireLeMessage(int indice){
        Message message = null;
        int size = filsdediscussion.size();
        if(size>0 && indice < size ){
            message = filsdediscussion.get(size - indice -1);
        }
        else {
            LOGGER.error("ATTENTION: lireMessage() : la file de discussion est vide ! ");
        }
        return message;
    }
    public UUID getId() {
        return id;
    }




    public void printFil(){
        for (Message m : filsdediscussion){
            System.out.println(m.getFrom().getPrenom() + ": " + m.getMesage());
            System.out.print("Utilisateur en attente : ");
            for(Utilisateur u : m.getEnAttente().getMembres()){
                System.out.print(u.getPrenom() + ", ");
            }
            System.out.println("");

            System.out.print("Utilisateur ayant recu : ");
            for(Utilisateur u : m.getRecu().getMembres()){
                System.out.print(u.getPrenom() + ", ");
            }
            System.out.println("");

            System.out.print("Utilisateur ayant lu: ");
            for(Utilisateur u : m.getLu().getMembres()){
                System.out.print(u.getPrenom() + ", ");
            }
            System.out.println("\n");
        }
    }


    @Override
    public int compareTo(FilDeDiscussion o) {
        return sujet.compareTo(o.sujet);
    }
}
