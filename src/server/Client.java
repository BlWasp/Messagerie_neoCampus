package server;

import utilisateurs.Utilisateur;

import java.io.*;
import java.net.*;

public class Client implements Runnable{
    Utilisateur courant;
    BufferedWriter writer;
    BufferedReader reader;

    public Client(Utilisateur u) {
        this.courant = u;
        try {
            Socket socketClient = new Socket("127.0.0.1",5555);

            writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        }catch(Exception e){e.printStackTrace();}
    }

    public void run() {

        try {
            String serverMsg = "";
            while((serverMsg = reader.readLine()) != null) {
                System.out.println("depuis le serveur :" + serverMsg);
            }
        }catch(Exception e){e.printStackTrace();}
    }
}
