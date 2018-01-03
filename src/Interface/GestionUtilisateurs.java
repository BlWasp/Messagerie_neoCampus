package Interface;

import javax.swing.*;
import java.awt.event.*;

public class GestionUtilisateurs extends JDialog {
    private JPanel contentPane;
    private JTable table1;
    private JTextField rechercherTextField;
    private JButton rechercherButton;
    private JToolBar tools;
    private JButton ajouterUnUtilisateurButton;


    public GestionUtilisateurs() {
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
    }



    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        GestionUtilisateurs dialog = new GestionUtilisateurs();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
