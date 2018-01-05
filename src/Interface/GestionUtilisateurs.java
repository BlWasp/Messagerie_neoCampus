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

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        //Permet l'affichage des données de la bdd TODO A ajouter a la classe serveur
       /* List<Utilisateur> users = ExtractDataBDD.printUsers();
        String[] entetes = {"identifiant","nom","prenom",};
        DefaultTableModel tableModel = new DefaultTableModel(entetes, 0);

        table1 = new JTable(tableModel);
        for (int i = 0; i < users.size(); i++) {
            String nom = users.get(i).getNom();
            String prenom = users.get(i).getPrenom();
            int identifiant = users.get(i).getIdentifiant();
            Object[] data = {identifiant,nom,prenom};
            tableModel.addRow(data);


        }
        this.add(table1);*/
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
