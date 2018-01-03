package server;

import java.io.*;
import java.net.*;

package server;

public class Server implements Runnable{
    Socket connectionSocket;

    public Server(Socket s) {
        try{
            System.out.println("Le client peut se connecter ");
            connectionSocket = s;
        }catch(Exception e){e.printStackTrace();}
    }

    public void run() {
        try{
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
            while(true) {
                String data1 = reader.readLine().trim();
                System.out.println("Recu : " + data1);
            }
        }catch(Exception e){e.printStackTrace();}
    }
}
