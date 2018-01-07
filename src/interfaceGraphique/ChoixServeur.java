package interfaceGraphique;

import paquet.Client;
import utilisateurs.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.text.NumberFormat;

public class ChoixServeur extends JDialog {
    Client client;

    public ChoixServeur(Client client){
        this.client = client;
        setTitle("ChoixServeur");
        setSize(350, 100);
        this.setLocationRelativeTo(null);
        JPanel container = new JPanel();
        container.setBackground(Color.white);
        container.setLayout(new BorderLayout());
        JPanel pan = new JPanel();
        JButton btest = new JButton ("Enregistrer");
        JLabel ladresse = new JLabel("adresse:");
        JLabel lPort = new JLabel("Port:");
        JTextField fadresse = new JTextField(client.getHost());
        JTextField fPort = new JTextField(String.valueOf(client.getPort()));
        btest.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent event){
                client.setHost(fadresse.getText());
               client.setPort(Integer.parseInt(fPort.getText()) );
                System.out.println("Adresse:"+client.getHost()+", port :"+client.getPort());
                dispose();
            }
        });

       // fadresse.setPreferredSize(new Dimension(150, 30));
       // fPort.setPreferredSize(new Dimension(150, 30));
        pan.add(ladresse);
        pan.add(fadresse);
        pan.add(lPort);
        pan.add(fPort);
        pan.add(btest);

        this.setContentPane(pan);




        setVisible(true);

    }




}
