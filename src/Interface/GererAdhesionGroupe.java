package Interface;

import net.Client;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GererAdhesionGroupe extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel labelID;
    private JLabel nom;
    private JLabel NomLabel;
    private JLabel prenomLabel;
    private JLabel prenom;
    private JLabel groupeLabel;
    private JTextField groupe;
    private JTextField ID;
    private JButton rechercherButton;
    private JLabel utilisateurInconnu;
    private JButton changerButton;
    private JLabel groupeInexistantLabel;
    UUID idOldGroupe;

    public GererAdhesionGroupe(Client c) {
        setContentPane(contentPane);
        setModal(true);
        c.download();
        getRootPane().setDefaultButton(buttonOK);
        groupeInexistantLabel.setVisible(false);
        utilisateurInconnu.setVisible(false);

        changerButton.addActionListener(new ActionListener() {
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

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
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

    private void changerGroupe(Client c){

        c.getGroupeID(idOldGroupe).retirerMembre(Integer.parseInt(ID.getText()));

        GroupeNomme newGroupe = c.getGroupeName(groupe.getText());
        if (newGroupe == null){
            groupeInexistantLabel.setVisible(true);
        }else{
            newGroupe.ajouterMembres(c.getGroupeGlobal().getUtilisateur(Integer.parseInt(ID.getText())));
        }

    }


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
