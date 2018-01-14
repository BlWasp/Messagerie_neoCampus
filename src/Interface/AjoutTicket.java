package Interface;

import discussion.FilDeDiscussion;
import net.Client;
import utilisateurs.GroupeNomme;

import javax.swing.*;
import java.awt.event.*;

public class AjoutTicket extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField groupe;
    private JTextField ticket;

    public AjoutTicket(Client c) {
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

    private void onOK(Client c) {
        if (!groupe.getText().isEmpty() && !ticket.getText().isEmpty()){
            GroupeNomme g = c.getGroupeName(groupe.getText());
            FilDeDiscussion f = g.getFilsDeDiscussion(ticket.getText());
            if (g != null && f == null){
                g.ajouterFilDeDiscussion(c.getUtilisateurCourant(),ticket.getText());
                c.upload();

                dispose();
            }else{
                JOptionPane.showMessageDialog(null,"Groupe inexistant ou ticket déjà crée");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Veuillez entrer des informations valides");
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


}
