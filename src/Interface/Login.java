package Interface;

import net.Client;
import paquet.Paquet;
import utilisateurs.GroupeNomme;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField ident;
    private JTextField mdp;
    private JButton inscriptionButton;
    private JLabel labelIdent;
    private JLabel labelMdp;
    private JLabel loginFailed;
    private JTextField ipField;
    private JLabel ipLabel;
    private JTextField portField;


    public Login() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        loginFailed.setVisible(false);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(c);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {

        if (!ident.getText().isEmpty()  &&
                ident.getText().matches(".*\\d+.*") &&
                !mdp.getText().isEmpty() &&
                !ipField.getText().isEmpty() &&
                !portField.getText().isEmpty() &&
                portField.getText().matches(".*\\d+.*")){
            
            c.authentification(new Utilisateur(null,null,Integer.parseInt(ident.getText()),mdp.getText(),null));
            if (c.getUtilisateurCourant() != null){

                //TODO juste pour le test a enlever
                GroupeNomme grp = new GroupeNomme("L3",1200);
                GroupeNomme grp2 = new GroupeNomme("L2",1201);
                Utilisateur salim = new Utilisateur("CHERIFI","Salim",12700,"123", TypeUtilisateur.ETUDIANT);

                c.gestionGroupeNomme(grp, Paquet.Action.ADD);
                c.gestionGroupeNomme(grp2, Paquet.Action.ADD);

                c.getGroupeName("L3").ajouterMembres(salim);

                loginFailed.setVisible(false);
                System.out.println("Authentification Reussi!");
                //En fonction du statut de l'étudiant
                dispose();
                Chat chat = new Chat(c);
                chat.pack();
                chat.setVisible(true);






            }else{
                loginFailed.setVisible(true);
                this.pack();
            }
        }else{
            loginFailed.setVisible(true);
            this.pack();
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {

        Login dialog = new Login();
        dialog.pack();
        dialog.setVisible(true);
    }
}
