package discussion;

import java.util.ArrayList;
import java.util.List;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;


public class FilDeDiscussion extends GroupeNomme{
    private String sujet;
    private List<Message> filsdediscussion = new ArrayList<>();

    public FilDeDiscussion(String sujet) {
        super("Conversation : " + sujet);
        this.sujet = sujet;
    }

    public Message ajouterMessage(Utilisateur u, String m) {
        Message messageajoute = null;
        if(this.estMembre(u)){
            messageajoute = new Message(u,this, m);
            filsdediscussion.add(messageajoute);
        }
        else{
            System.err.println("ERREUR : " + u.getPrenom().toString() + " ne participe pas à cette conversation");
            System.exit(1);
        }
        return messageajoute;
    }

    public String toString(){
        String cat = super.getNom()+ "\n";
        Message m;
        for (int i=0; i<filsdediscussion.size();i++){
            m = filsdediscussion.get(i);
            cat += m.getFrom().getPrenom()+ " " + m.getFrom().getNom() + " : " + m.getMesage() + "\n" + "Recu par : "+ m ;
        }
        return cat;
    }

    public Message lireLeMessage(int indice){
        Message message = null;
        int size = filsdediscussion.size();
        if(size>0 && indice < size ){
            message = filsdediscussion.get(size - indice -1);
        }
        else {
            System.err.println("ERREUR : lireMessage() : la file de discussion est vide ! ");
            System.exit(2);
        }
        return message;
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

}
