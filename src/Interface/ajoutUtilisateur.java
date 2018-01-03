package Interface;

import javax.swing.*;
import java.awt.event.*;

public class ajoutUtilisateur extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField lastName;
    private JTextField firstName;
    private JTextField userName;
    private JPasswordField passwd;
    private JPasswordField confirmPasswd;
    private JComboBox typeUtilisateur;

    public ajoutUtilisateur() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //envoyer au serveur les infos d'une nouvel utilisateur
                dispose();
            }
        });

        typeUtilisateur.addItem("Ce que je veux en fonction de la bdd");
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
