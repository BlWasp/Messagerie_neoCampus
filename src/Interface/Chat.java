package Interface;

import paquet.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.UUID;

public class Chat extends JDialog {
    private JPanel contentPane;
    private JTree chatTree;// TODO Afficher la liste des groupes avec leur fils de discussion
    private JTextField chatField;
    private JButton sendButton;
    private JTextArea filDeChat;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu fichierMenu = new JMenu("Fichier");
    private JMenuItem ajoutTicket = new JMenuItem("Ajouter un ticket");

    public Chat() {
        setContentPane(contentPane);
        setModal(true);
        this.setPreferredSize(new Dimension(500,500));

        menuBar.add(fichierMenu);
        fichierMenu.add(ajoutTicket);
        setJMenuBar(menuBar);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Envoyer le message au server
                //Client c = new Client("127.0.0.1",12700);
                // TODO Envoyer le message avec lid du fil de discussion correspondant
                chatField.setText("");
            }
        });

        chatField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatField.setText("");
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Chat dialog = new Chat();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
