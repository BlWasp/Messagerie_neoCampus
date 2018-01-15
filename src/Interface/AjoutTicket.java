package Interface;

import discussion.FilDeDiscussion;
import net.Client;
import discussion.GroupeNomme;

import javax.swing.*;
import java.awt.event.*;

public class AjoutTicket extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField groupe;
    private JTextField ticket;

    /**
     *
     * @param c Client connecte
     */
    public AjoutTicket(Client c) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.setTitle("Ajouter un ticket");
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
        if (!groupe.getText().isEmpty() && !ticket.getText().isEmpty()){
            GroupeNomme g = c.getGroupeName(groupe.getText());
            if (g != null){
                FilDeDiscussion f = g.getFilsDeDiscussion(ticket.getText());
                if (f == null) {
                    if (g.getMembres().contains(c.getUtilisateurCourant())) {
                        g.ajouterFilDeDiscussion(c.getUtilisateurCourant(), ticket.getText());
                        c.upload();

                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Vous n'appartenez pas au groupe");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Ticket déjà crée");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Groupe inexistant");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Veuillez entrer des informations valides");
        }

    }

    /**
     *
     */
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


}
