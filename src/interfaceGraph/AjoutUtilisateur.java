package interfaceGraph;

import net.Client;
import utilisateurs.TypeUtilisateur;
import utilisateurs.Utilisateur;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class AjoutUtilisateur extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField lastName;
    private JTextField firstName;
    private JTextField ident;
    private JComboBox typeUtilisateur;
    private JTextField passwd;
    private JTextField confirmPasswd;

    /**
     * Fenetre d'ajout d'utilisateur
     * @param c Client connecte
     */
    public AjoutUtilisateur(Client c) {
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
                if (c.getGroupeGlobal().getUtilisateur(Integer.parseInt(ident.getText())) != null){
                    JOptionPane.showMessageDialog(null, "L'utilisateur correspondant à cet ID existe déjà");
                }else
                if (!lastName.getText().isEmpty() &&
                        !firstName.getText().isEmpty() &&
                        !ident.getText().isEmpty() &&
                        !passwd.getText().isEmpty() &&
                        !confirmPasswd.getText().toString().isEmpty() &&
                        !typeUtilisateur.getSelectedItem().toString().isEmpty()){

                    if (passwd.getText().equals(confirmPasswd.getText())) {
                        Utilisateur u = new Utilisateur(lastName.getText().toUpperCase(), firstName.getText(), Integer.parseInt(ident.getText()), passwd.getText().toString(), (TypeUtilisateur) typeUtilisateur.getSelectedItem());

                        c.getGroupeGlobal().ajouterMembres(u);
                        c.upload();
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "Les mots de passes ne correspondent pas");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs");
                }

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
     * Opération du bouton cancel
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /*public static void main(String[] args) {
        AjoutUtilisateur dialog = new AjoutUtilisateur();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
