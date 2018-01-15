package interfaceGraph;

import net.Client;
import utilisateurs.Utilisateur;

import javax.swing.*;
import java.awt.event.*;

public class RetirerMembre extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JFormattedTextField idField;

    /**
     *
     * @param c Client connecte
     */
    public RetirerMembre(Client c) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

        this.pack();
        this.setLocationRelativeTo(null);

    }

    /**
     *
     * @param c Client connecte
     */
    private void onOK(Client c) {
        c.download();
        if (idField.getText().matches(".*\\d+.*")){
            if (c.getGroupeGlobal().getUtilisateur(Integer.parseInt(idField.getText())) != null){
                c.getGroupeGlobal().retirerMembres(new Utilisateur("","",Integer.parseInt(idField.getText()),"",null));
                c.upload();
                dispose();
            }else{
                JOptionPane.showMessageDialog(null,"Veuillez entrer un ID valide");
            }

        }else{
            JOptionPane.showMessageDialog(null,"Veuillez entrer un ID valide");

        }
    }

    /**
     *
     */
    private void onCancel() {
        dispose();
    }

 /*   public static void main(String[] args) {
        RetirerMembre dialog = new RetirerMembre(new Client());
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
