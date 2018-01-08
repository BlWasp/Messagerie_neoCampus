package Interface;

import paquet.Client;
import utilisateurs.Utilisateur;

import javax.swing.*;
import java.awt.event.*;

public class ModifierMembre extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldPrenom;
    private JLabel nom;
    private JTextField textFieldNom;
    private JLabel prenom;
    private JTextField textFieldmdp;
    private JLabel mdp;

    public ModifierMembre(Client c, Utilisateur u) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        nom.setText(u.getNom());
        prenom.setText(u.getPrenom());

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(c,u);
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

    private void onOK(Client c,Utilisateur u) {
        if (u.getNom() != nom.getText()){
            u.setNom(nom.getText());
        }
        if (u.getPrenom() != prenom.getText()){
            u.setPrenom(prenom.getText());
        }
        if (mdp.getText() != null){
            mdp.setText(textFieldmdp.getText());
        }
        c.
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


}
