package discussion;

import java.awt.*;
import java.io.FileDescriptor;
import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import org.apache.log4j.Logger;
import utilisateurs.Groupe;
import utilisateurs.Utilisateur;

import javax.swing.*;
import javax.swing.text.*;


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

    public Message getDernierMessage(){
        return this.filsdediscussion.get(this.filsdediscussion.size()-1);
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

    public Groupe getGroupe() {
        return groupe;
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    public void printFil(){
        for (Message m : filsdediscussion){
            System.out.println("["+m.getFrom().getPrenom() + "] : " + m.getMessage());
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

    public void printMessage(Utilisateur utilisateurCourant,JTextPane pane) throws BadLocationException {


        MutableAttributeSet right = new SimpleAttributeSet();
        StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
        StyleConstants.setFontFamily(right,"Tahoma");
        StyleConstants.setForeground(right, Color.BLUE);

        MutableAttributeSet left = new SimpleAttributeSet();
        StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
        StyleConstants.setFontFamily(left,"Tahoma");
        StyleConstants.setForeground(left, Color.RED);

        StyledDocument sDoc = new DefaultStyledDocument();
        pane.setStyledDocument(sDoc);



        StringBuilder ch = new StringBuilder();
        for (Message m : this.getListMessage()) {
            ch.append(m.getDateEnvoi());
            ch.append("\n");
            ch.append(m.getFrom().getNom() + " " + m.getFrom().getPrenom() + " : ");
            ch.append("\n");
            ch.append(m.getMessage() + "  " + m.getLu());
            ch.append("\n\n");


            if (utilisateurCourant.equals(m.getFrom())){
                sDoc.setParagraphAttributes(sDoc.getLength(),ch.length(),right,true);
                sDoc.insertString(sDoc.getLength(),ch.toString(),right);
            }else{
                sDoc.setParagraphAttributes(sDoc.getLength(),ch.length(),left,true);
                sDoc.insertString(sDoc.getLength(),ch.toString(),left);
            }

            ch.setLength(0);
        }

    }


    public List<Message> getListMessage() {
        return filsdediscussion;
    }

    @Override
    public int compareTo(FilDeDiscussion o) {
        return sujet.compareTo(o.sujet);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilDeDiscussion)) return false;

        FilDeDiscussion that = (FilDeDiscussion) o;

        if (!getSujet().equals(that.getSujet())) return false;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        int result = getSujet().hashCode();
        result = 31 * result + getId().hashCode();
        return result;
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
