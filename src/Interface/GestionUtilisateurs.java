package Interface;

import utilisateurs.Utilisateur;
import BDD.ExtractDataBDD;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class GestionUtilisateurs extends JDialog {
    private JPanel contentPane;
    private JTable table1;
    private JTextField rechercherTextField;
    private JButton rechercherButton;
    private JToolBar tools;
    private JButton ajouterUnUtilisateurButton;


    public GestionUtilisateurs() throws SQLException {
        setContentPane(contentPane);
        setModal(true);



        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        ajouterUnUtilisateurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ajoutUtilisateur ajout = new ajoutUtilisateur();
                ajout.pack();
                ajout.setVisible(true);
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        //TODO afficher la liste des utilisateurs
    }



    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) throws SQLException {
        GestionUtilisateurs dialog = new GestionUtilisateurs();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
