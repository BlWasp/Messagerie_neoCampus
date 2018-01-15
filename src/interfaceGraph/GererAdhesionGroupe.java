package interfaceGraph;

import net.Client;
import discussion.GroupeNomme;
import utilisateurs.Utilisateur;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GererAdhesionGroupe extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel nom;
    private JLabel prenom;
    private JTextField groupe;
    private JTextField ID;
    private JButton rechercherButton;
    private JLabel utilisateurInconnu;
    private JLabel groupeInexistantLabel;
    UUID idOldGroupe;

    /**
     *
     * @param c Client connecte
     */
    public GererAdhesionGroupe(Client c) {
        setContentPane(contentPane);
        c.download();
        getRootPane().setDefaultButton(buttonOK);
        groupeInexistantLabel.setVisible(false);
        utilisateurInconnu.setVisible(false);

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changerGroupe(c);
            }
        });


        rechercherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFields(c,Integer.parseInt(ID.getText()));
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
    private void changerGroupe(Client c){
        GroupeNomme oldGroupe = c.getGroupeID(idOldGroupe);
        GroupeNomme newGroupe = c.getGroupeName(groupe.getText());
        if (newGroupe == null){
            groupeInexistantLabel.setVisible(true);
            this.pack();
        }else{
            groupeInexistantLabel.setVisible(false);
            this.pack();
            System.out.println(oldGroupe);
            oldGroupe.retirerMembres(c.getGroupeGlobal().getUtilisateur(Integer.parseInt(ID.getText().toString())));
            newGroupe.ajouterMembres(c.getGroupeGlobal().getUtilisateur(Integer.parseInt(ID.getText())));
            c.upload();
            dispose();
        }

    }

    /**
     *
     * @param c Client connecte
     * @param id Id du groupe
     */
    private void setFields(Client c, int id){
        c.download();
        Utilisateur u = c.getGroupeGlobal().getUtilisateur(id);
        if (u != null){
            utilisateurInconnu.setVisible(false);
            nom.setText(u.getNom());
            prenom.setText(u.getPrenom());

            List<GroupeNomme> grps = new ArrayList<>();
            grps.addAll(c.getListeGroupe());
            for (GroupeNomme grp :
                    grps) {
                for (Utilisateur utilisateur :
                        grp.getMembres()) {
                    if (utilisateur.getIdentifiant() == id ){
                        groupe.setText(grp.getNom());
                        idOldGroupe = grp.getId();
                        break;
                    }
                }
                if (!groupe.getText().isEmpty()){
                    break;
                }
            }
        }else{
            utilisateurInconnu.setVisible(true);
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
        GererAdhesionGroupe dialog = new GererAdhesionGroupe();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }*/
}
