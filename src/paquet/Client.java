package paquet;

import utilisateurs.Groupe;
import utilisateurs.GroupeNomme;
import utilisateurs.Utilisateur;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<GroupeNomme> listeGrroupe = new ArrayList<>();
        Groupe global = new Groupe();

        Socket clientSocket = new Socket("127.0.0.1", 6791);
        ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
        outToServer.writeObject(new Connexion(21400536,"0000"));















    }
}
