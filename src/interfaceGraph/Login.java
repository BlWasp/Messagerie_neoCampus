package interfaceGraph;

import discussion.FilDeDiscussion;
import discussion.Message;
import net.Client;

import discussion.GroupeNomme;
import net.Serveur;
import utilisateurs.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextField ident;
    private JTextField mdp;
    private JLabel loginFailed;
    private JTextField ipField;
    private JTextField portField;
    private JLabel adressePortIncorrect;

    /**
     * Page de login
     */
    public Login() {
        setContentPane(contentPane);
        this.setPreferredSize(new Dimension(250,350));
        getRootPane().setDefaultButton(buttonOK);
        ipField.setText("127.0.0.1");
        portField.setText("12700");
        loginFailed.setVisible(false);
        adressePortIncorrect.setVisible(false);
        setTitle("LogIn");
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });




        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        this.pack();
        this.setLocationRelativeTo(null);
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * Opération du bouton ok
     */
    private void onOK() {

        if (!ident.getText().isEmpty()  &&
                ident.getText().matches(".*\\d+.*") &&
                !mdp.getText().isEmpty() &&
                !ipField.getText().isEmpty() &&
                !portField.getText().isEmpty() &&
                portField.getText().matches(".*\\d+.*")){
            Client c = new Client(ipField.getText(),Integer.parseInt(portField.getText()));
            int errno = c.connect();
            if (errno == 1){

                adressePortIncorrect.setVisible(false);
                errno = c.authentification(new Utilisateur("","",Integer.parseInt(ident.getText()),mdp.getText(),null));
                if (errno == 1){
                    c.download();
                    loginFailed.setVisible(false);
                    System.out.println("Authentification Reussi!");
                    Chat chat = new Chat(c);
                    chat.pack();
                    chat.setTitle(c.getUtilisateurCourant().getPrenom()+"."+c.getUtilisateurCourant().getPrenom()+"@"+c.getHost()+":"+c.getPort());

                    c.getUtilisateurCourant().setConnecte(true);

                    for (GroupeNomme g : c.getListeGroupe()) {
                        if (g.estMembre(c.getUtilisateurCourant())){
                            for (FilDeDiscussion f : g.getFilsDeDiscussion()) {
                                for (Message m : f.getListMessage()) {
                                    //if (m.getEnAttente().estMembre(c.getUtilisateurCourant())) {
                                        m.recu(c.getUtilisateurCourant());
                                    //}
                                }
                            }
                        }
                    }
                    c.upload();
                    dispose();
                    chat.setVisible(true);

                }else{
                    c.deconnect();
                    loginFailed.setVisible(true);
                    this.pack();
                }

            }else{
                c.deconnect();
                adressePortIncorrect.setVisible(true);
                this.pack();
            }
        }else{
            loginFailed.setVisible(true);
            this.pack();
        }

    }

    /**
     * Action du bouton cancel
     */
    private void onCancel() {
        dispose();
    }

    /**
     * Methode main a lance pour lancer l'interface, l'utilisateur etc
     * @param args Arguments voulu si necessaire
     */
    public static void main(String[] args) {

        Login dialog = new Login();
        dialog.pack();
        dialog.setVisible(true);
    }
}
