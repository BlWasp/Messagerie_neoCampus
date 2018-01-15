package Interface;

import net.Client;
import utilisateurs.GroupeNomme;

import javax.swing.*;
import java.awt.event.*;

public class AjouterGroupe extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nomGroupe;
    private JLabel labelNomGroupe;
    private JButton gestionDeGroupeValideButton;
    private JLabel errorNomGroupe;

    /**
     *
     * @param c Client connecte
     */
    public AjouterGroupe(Client c) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        errorNomGroupe.setVisible(false);
        buttonOK.addActionListener(e -> onOK(c));

        buttonCancel.addActionListener(e -> onCancel());

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

    /**
     *
     * @param c Client connecte
     */
    private void onOK(Client c) {
        c.download();
        if (!nomGroupe.getText().isEmpty()) {
            errorNomGroupe.setVisible(false);

            GroupeNomme g = new GroupeNomme(nomGroupe.getText());
            c.getListeGroupe().add(g);
            c.upload();
            dispose();
        }else{
            errorNomGroupe.setVisible(true);
        }
    }

    /**
     *
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

   /* public static void main(String[] args) {
        AjouterGroupe dialog = new AjouterGroupe();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
