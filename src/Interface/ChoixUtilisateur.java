package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.management.LockInfo;

public class ChoixUtilisateur extends JDialog {
    private JPanel contentPane;
    private JButton utilisateurDuCampusButton;
    private JButton serviceAdministratifOuTechniqueButton;


    public ChoixUtilisateur() {
        setContentPane(contentPane);
        setModal(true);

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

        utilisateurDuCampusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Login log = new Login();
                log.pack();
                log.setVisible(true);
            }
        });
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        ChoixUtilisateur dialog = new ChoixUtilisateur();
        dialog.pack();
        dialog.setVisible(true);
    }
}
