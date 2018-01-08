package Interface;

import paquet.Client;

import javax.swing.*;
import java.awt.event.*;

public class RetirerMembre extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JFormattedTextField idField;

    public RetirerMembre(Client c) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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
        // add your code here
        if (idField.getText().matches(".*\\d+.*")){
            //TODO faire l'opération et retirer l'utilisateur
            //TODO si l'utilisateur est pas trouve
        }else{
            //TODO afficherle message comme quoi ce n'est pas valide
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

 /*   public static void main(String[] args) {
        RetirerMembre dialog = new RetirerMembre(new Client());
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
