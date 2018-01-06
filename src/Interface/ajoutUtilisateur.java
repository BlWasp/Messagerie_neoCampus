package Interface;

import paquet.Client;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

import javax.swing.*;
import java.awt.event.*;

public class ajoutUtilisateur extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField lastName;
    private JTextField firstName;
    private JTextField ident;
    private JPasswordField passwd;
    private JPasswordField confirmPasswd;
    private JComboBox typeUtilisateur;
    private JLabel labelIdent;

    public ajoutUtilisateur() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        for (TypeUtilisateur type :
                TypeUtilisateur.values()) {
            this.typeUtilisateur.addItem(type);
        }
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client c = new Client("127.0.0.1",12700);
                if (lastName.getText() != null && firstName != null && ident != null && passwd != null && confirmPasswd != null && typeUtilisateur != null){
                    Utilisateur u = new Utilisateur(lastName.getText(),firstName.getText(),Integer.parseInt(ident.getText()),passwd.getPassword().toString(),(TypeUtilisateur)typeUtilisateur.getSelectedItem());
                    c.ajouterMembres(u);
                }
                dispose();
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

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        ajoutUtilisateur dialog = new ajoutUtilisateur();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
