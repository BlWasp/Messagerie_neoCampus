package interfaceGraphique;

import paquet.Client;
import utilisateurs.Utilisateur;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;

public class Repertoire {
    JTree arbre;
    Client client;
    DefaultMutableTreeNode racine;

    public Repertoire(Client client) {
        this.client = client;
        arbre =new JTree(racine);
        actualiser();
    }

    public void actualiser(){
        racine = new DefaultMutableTreeNode("Liste des utilisateurs");
        if(client.getUtilisateurCourant() !=null){
            for(Utilisateur u : client.getMembres()){

                DefaultMutableTreeNode element = new DefaultMutableTreeNode(u.getNom() + " "+u.getPrenom());
                racine.add(element);
            }
        }


        ((DefaultTreeModel) arbre.getModel()).setRoot(racine);
        ((DefaultTreeModel) arbre.getModel()).reload();

        this.arbre.updateUI();
    }


    public JTree getArbre() {
        return arbre;
    }
}
