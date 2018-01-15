package Interface;

import net.Client;
import utilisateurs.Utilisateur;

import javax.swing.*;
import java.awt.event.*;

public class ModifierMembre extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textFieldNom;
    private JLabel nom;
    private JTextField textFieldPrenom;
    private JLabel prenom;
    private JTextField textFieldmdp;
    private JLabel mdp;

    /**
     *
     * @param c Client connecte
     * @param u Utilisateur à modifier
     */
    public ModifierMembre(Client c, Utilisateur u) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        textFieldPrenom.setText(u.getPrenom());
        textFieldNom.setText(u.getNom());

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

        this.pack();
        this.setLocationRelativeTo(null);
    }

    /**
     *
     * @param c Client connecte
     * @param u Utilisateur voulu
     */
    private void onOK(Client c,Utilisateur u) {
        c.getGroupeGlobal().retirerMembres(u);
        if (u.getNom() != nom.getText()){
            u.setNom(textFieldNom.getText());
        }
        if (u.getPrenom() != prenom.getText()){
            u.setPrenom(textFieldPrenom.getText());
        }
        if (mdp.getText() != null){
            mdp.setText(textFieldmdp.getText());
        }else{
            u.setMotDePasse(u.getMotDePasse());
        }
        //TODO a ajouter quand upload fonctionnera
        //c.majMembres(u);
        c.getGroupeGlobal().ajouterMembres(u);
        c.upload();

        dispose();
    }

    /**
     *
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


}
