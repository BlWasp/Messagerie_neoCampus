package interfaceGraphique;


import paquet.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

public class Connexion extends JDialog {
    Client client;
    Repertoire rep_utilisateur;

    public Connexion(Client client,ApplicationClient ac){
        this.client = client;
        this.rep_utilisateur = ac.getRep_utilisateur();
        setTitle("ChoixServeur");
        setSize(250, 200);
        this.setLocationRelativeTo(null);
        JPanel container = new JPanel();
        container.setBackground(Color.white);
        container.setLayout(new BorderLayout());
        JPanel pan = new JPanel();
        JButton btest = new JButton ("Se connecter");
        JLabel lid = new JLabel("Identifiant (Uniquement des chiffres) :");
        JFormattedTextField fid = new JFormattedTextField(NumberFormat.getNumberInstance());
        fid.setColumns(15);
        JLabel lmdp = new JLabel("Mot de passe:");
        JTextField fmdp = new JTextField(15);
        btest.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){

               int id = Integer.parseInt(fid.getText());
                client.authentification(id,fmdp.getText());
                // TODO gerer le cas mauvais identifiant
                if(client.getUtilisateurCourant()==null){
                    // System.out.println("Echec de connexion");
                    JOptionPane.showMessageDialog(new Frame(), "Echec de la conexion. Réessayez");
                }else{
                    // System.out.println("Connecté en tant que :"+client.getUtilisateurCourant());
                    ac.setTitle(client.getUtilisateurCourant().getPrenom()+"."+client.getUtilisateurCourant().getNom()+"@"+client.getHost()+":"+client.getPort()+" - NeoCampus");
                    rep_utilisateur.actualiser();
                    ac.getOnglets().updateUI();
                    JOptionPane.showMessageDialog(new Frame(), "Connecté en tant que"+client.getUtilisateurCourant());

                    dispose();
                }

            }
        });

        // fid.setPreferredSize(new Dimension(150, 30));
        // fmdp.setPreferredSize(new Dimension(150, 30));
        pan.add(lid);
        pan.add(fid);
        pan.add(lmdp);
        pan.add(fmdp);
        pan.add(btest);

        this.setContentPane(pan);




        setVisible(true);

    }



}
