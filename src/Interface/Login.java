package Interface;

import paquet.Client;

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

    public Login(Client c) {
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

    private void onOK(Client c) {

        if (!ident.getText().isEmpty()  &&  !mdp.getText().isEmpty()){
            c.authentification(Integer.parseInt(ident.getText()),mdp.getText());
            if (c.getUtilisateurCourant() != null){
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
        Client c = new Client();
        Login dialog = new Login(c);
        dialog.pack();
        dialog.setVisible(true);
    }
}
