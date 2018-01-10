package discussion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;


public class FilDeDiscussion implements Serializable,Comparable<FilDeDiscussion>{
    private String sujet;
    private Groupe groupe;
    private Utilisateur createur;
    private List<Message> filsdediscussion = new ArrayList<>();
    private UUID id = UUID.randomUUID();
    private static Logger LOGGER = Logger.getLogger(FilDeDiscussion.class);

    public FilDeDiscussion(String sujet, Groupe groupe, Utilisateur createur) {
        this.sujet = sujet;
        this.groupe = groupe;
        this.createur = createur;
    }

    public Message ajouterMessage(Utilisateur u, String m) {
        Message messageajoute = null;
        if(groupe.estMembre(u) || u.equals(createur) ){
            Groupe g = new Groupe();
            g.ajouterMembres(groupe);
            g.ajouterMembres(createur);
            messageajoute = new Message(u,g, m);
            filsdediscussion.add(messageajoute);
        }
        else{
            LOGGER.error("ERREUR : " + u.getPrenom() + " ne participe pas à cette conversation");
        }
        return messageajoute;
    }

    public int retirerMessage(Message m){
        return  filsdediscussion.remove(m)?1:0;
    }

    public UUID getId() {
        return id;
    }

    public String getSujet() {
        return sujet;
    }

    public void printFil(){
        for (Message m : filsdediscussion){
            System.out.println("["+m.getFrom().getPrenom() + "] : " + m.getMesage());
            System.out.print("............. Utilisateur en attente : ");
            for(Utilisateur u : m.getEnAttente().getMembres()){
                System.out.print( u.getPrenom() + ", ");
            }
            System.out.println("");

            System.out.print("............. Utilisateur ayant recu : ");
            for(Utilisateur u : m.getRecu().getMembres()){
                System.out.print( u.getPrenom() + ", ");
            }
            System.out.println("");

            System.out.print("............. Utilisateur ayant lu: ");
            for(Utilisateur u : m.getLu().getMembres()){
                System.out.print(u.getPrenom() + ", ");
            }
            System.out.println("\n");
        }
    }

    public String printMessage(){
        StringBuilder ch = new StringBuilder();
        for (Message m :
                this.getListMessage()) {
            ch.append(m.getMesage());
            ch.append("\n");

        }
        return ch.toString();
    }

    public List<Message> getListMessage() {
        return filsdediscussion;
    }

    @Override
    public int compareTo(FilDeDiscussion o) {
        return sujet.compareTo(o.sujet);
    }

    @Override
    public String toString() {
        return "FilDeDiscussion{" +
                "sujet='" + sujet + '\'' +
                ", filsdediscussion=" + filsdediscussion +
                ", id=" + id +
                '}';
    }
}
